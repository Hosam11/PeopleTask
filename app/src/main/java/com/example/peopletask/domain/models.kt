package com.example.peopletask.domain

import com.squareup.moshi.Json

/**
 * Domain objects are plain Kotlin data classes that represent the things in our app. These are the
 * objects that should be displayed on screen, or manipulated by the app.
 */

/**
 * response will return object of [PopularPeopleResponse]
 */
data class PopularPeopleResponse(
    val page: Int,
    @Json(name = "total_pages") val totalPages: Int,
    @Json(name = "results") val personsList: List<PersonResult>
)

/**
 * in [PopularPeopleResponse] there is list of [PersonResult]
 */
data class PersonResult(
    val name: String,
    val id: Long,
    @Json(name = "profile_path") val imgPath: String?,
    @Json(name = "known_for_department") val personType: String
)


/**
 * response will return object of [PersonDetails]
 */
data class PersonDetails(
    @Json(name = "profile_path") val imgPath: String?,
    val name: String,
    val biography: String,
    @Json(name = "known_for_department") val personType: String,
    val gender: Int,
    val birthday: String?,
    @Json(name = "also_known_as") val otherNames: List<String>,
    val popularity: Float

) {
    // check for nullability and add result to variables if the variable is true hide the related view to it
    val isBirthdayNull:Boolean = birthday == null
    val isBiographyEmpty:Boolean = biography.isEmpty()
    val isOtherNamesEmpty:Boolean = otherNames.isEmpty()
}

/**
 * response will return list of [PersonImages]
 */
class PersonImages