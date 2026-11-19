package com.hyeok.recipebook.presentation.home

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.hyeok.recipebook.data.database.model.RecipeDetailsModel
import com.hyeok.recipebook.data.database.model.RecipeSummaryModel
import com.hyeok.recipebook.data.repository.IngredientRepository
import com.hyeok.recipebook.data.repository.RecipeRepository
import com.hyeok.recipebook.presentation.home.model.OwnedIngredientUiModel
import com.hyeok.recipebook.presentation.home.model.RecipeRecommendationUiModel
import com.hyeok.recipebook.presentation.home.model.toUiModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.emitAll
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.datetime.DateTimeUnit
import kotlinx.datetime.LocalDate
import kotlinx.datetime.TimeZone
import kotlinx.datetime.atStartOfDayIn
import kotlinx.datetime.plus
import kotlinx.datetime.toLocalDateTime
import kotlinx.datetime.until
import javax.inject.Inject
import kotlin.time.Clock
import kotlin.time.ExperimentalTime

class HomeViewModel @Inject constructor(
    private val ingredientRepository: IngredientRepository,
    private val recipeRepository: RecipeRepository,
    savedStateHandle: SavedStateHandle
) : ViewModel() {

    // 날이 바뀔 때 마다 현재 LocalDate 를 방출한다
    @OptIn(ExperimentalTime::class)
    private val _today: Flow<LocalDate> = flow {
        while (true) {
            val now = Clock.System.now()
            val today = now.toLocalDateTime(TimeZone.currentSystemDefault()).date
            // 써머타임과 같은 변수를 고려한 자정 시간
            val midnight = today.plus(1, DateTimeUnit.DAY).atStartOfDayIn(TimeZone.currentSystemDefault())

            emit(today)
            delay(midnight - now)
        }
    }

    private val _ownedIngredients: Flow<List<OwnedIngredientUiModel>> = ingredientRepository.getIngredients()
        .map {
            it.getOrElse {
                emptyList()
            }
        }
        .combine(_today) { ingredientUiModels, today ->
            ingredientUiModels.map {
                val daysToExpiration = it.expirationDate?.let {
                    today.until(it, DateTimeUnit.DAY)
                }?.toInt()

                OwnedIngredientUiModel(
                    id = it.id,
                    name = it.name,
                    purchaseDate = it.purchaseDate,
                    expirationDate = it.expirationDate,
                    daysToExpiration = daysToExpiration,
                    weight = it.weight,
                    weightUnit = it.weightUnit,
                    description = it.description
                )
            }
        }

    // TOOD:: 사용자의 이벤트를 받아 추가로 emit 하는 방식으로 구현해야 함, Channel 고려할것
    private val _recommendationRecipe: Flow<RecipeRecommendationUiModel?> = flow {
        emit(null)
        val recipeRecommendation = recipeRepository.getRecipeRecommendationsStream()
            .map { it.toUiModel() }

        emitAll(recipeRecommendation)
    }

    private val _readyToCookRecipes = flow {
        emit(
            recipeRepository.getReadyToCookRecipes(4)
                .getOrElse { emptyList() }
                .map { it.toSummaryModel() }
        )
    }

    private val _recentlyCookedRecipes = flow {
        emit(
            recipeRepository.getRecentlyCookedRecipes(4)
                .getOrElse { emptyList() }
                .map { it.toSummaryModel() }
        )
    }

    val uiState = combine(
        _ownedIngredients,
        _recommendationRecipe,
        _readyToCookRecipes,
        _recentlyCookedRecipes,
        _today
    ) { ownedIngredients, recommendationRecipe, readyToCookRecipes, recentlyCookedRecipes, _ ->
        val expiredIngredients = ownedIngredients.filter {
            it.daysToExpiration == 0
        }

        val expiringIngredients = ownedIngredients.filter {
            it.daysToExpiration in EXPIRATION_DAY_URGENT..EXPIRATION_DAY_WARNING
        }

        HomeUiState.Success(
            recipeRecommendation = recommendationRecipe,
            expiredIngredients = expiredIngredients,
            expiringIngredients = expiringIngredients,
            readyToCookRecipes = readyToCookRecipes,
            recentlyCookedRecipes = recentlyCookedRecipes
        )
    }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(5_000), HomeUiState.empty())
}

private fun RecipeDetailsModel.toSummaryModel(): RecipeSummaryModel = RecipeSummaryModel(
    id = id,
    name = name,
    registerDate = registerDate,
    cookingTime = cookingTime,
    photoUrl = photoUrl,
    rawIngredients = ingredients.joinToString { it.name },
    averageScore = averageScore
)

// TODO:: 소비시간을 모델링하기 전 사용하는 곳을 추적하기 위해 추가한 임시 상수
const val EXPIRATION_DAY_URGENT = 1
const val EXPIRATION_DAY_WARNING = 5