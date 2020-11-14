package com.example.peopletask.domain

import com.squareup.moshi.Json

/**
 * Domain objects are plain Kotlin data classes that represent the things in our app. These are the
 * objects that should be displayed on screen, or manipulated by the app.
 */


data class PopularPeopleResponse(
    val page: Int,
    @Json(name = "total_pages") val totalPages: Int,
    @Json(name = "results")val personsList: List<PersonResult>
)


/**
 *
{"popularity": 63.741,
"known_for_department": "Acting", // {1}
"gender": 2,
"id": 18918,
"profile_path": "\/kuqFzlYMc2IrsOyPznMd1FroeGq.jpg", // {3}
"adult": false,
"known_for": [],
"name": "Dwayne Johnson" // {2}}
 */
/**
 * response will return list of [PersonResult]
 */

data class PersonResult(
    val name: String,
    @Json(name = "profile_path") val imgPath: String?,
    @Json(name = "known_for_department") val personType: String
)


/**
 * response will return object of [PersonDetails]
 */
class PersonDetails


/**
 * response will return list of [PersonProfile]
 */
class PersonProfile