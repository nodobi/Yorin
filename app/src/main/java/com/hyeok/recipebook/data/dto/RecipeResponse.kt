package com.hyeok.recipebook.data.dto

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

/**
 * 식품 건강나라에서 제공하는 레시피 데이터
 *
 * [조리식품의 레시피 DB](https://www.foodsafetykorea.go.kr/apiMain.do)
 *
 * @property id 사용하지 않음
 * @property recipe_id 레시피 고유번호
 * @property name 음식 이름
 * @property ingredients 레시피에 사용되는 자료, 개행 문자가 포함된 문자열
 * @property image 음식 이미지 경로
 * @property type 음식 종류 (반찬,  .. )
 * @property fact_eng 성분 - 칼로리
 * @property fact_car 성분 - 탄수화물
 * @property fact_pro 성분 - 단백질
 * @property fact_fat 성분 - 지방
 * @property manual 레시피
 */
@Serializable
data class RecipeResponse(
    @SerialName("id")
    val id: Int,
    @SerialName("recipe_id")
    val recipe_id: Int,
    @SerialName("name")
    val name: String,
    @SerialName("ingredients")
    val ingredients: String?,
    @SerialName("image")
    val image: String?,
    @SerialName("type")
    val type: String?,
    @SerialName("fact_eng")
    val fact_eng: Int?,
    @SerialName("fact_car")
    val fact_car: Int?,
    @SerialName("fact_pro")
    val fact_pro: Int?,
    @SerialName("fact_fat")
    val fact_fat: Int?,
    @SerialName("manual")
    val manual: List<RecipeManualResponse>?
)

/**
 * 레시피 순서 데이터
 *
 * 설명이 존재해도 이미지는 존재하지 않을 수도 있다.
 *
 * @property text 설명
 * @property image 이미지 경로
 */
@Serializable
data class RecipeManualResponse(
    @SerialName("text")
    val text: String?,
    @SerialName("image")
    val image: String?
)