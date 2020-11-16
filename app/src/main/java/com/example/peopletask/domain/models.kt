package com.example.peopletask.domain

import android.os.Parcelable
import com.squareup.moshi.Json
import kotlinx.android.parcel.Parcelize

/**
 * Domain objects are plain Kotlin data classes that represent the things in our app. These are the
 * objects that should be displayed on screen, or manipulated by the app.
 */

/**
 * data class that hold data about [Person]s and paging
 * when [getPopularPeople] called response will return object of [PopularPeopleResponse]
 */
data class PopularPeopleResponse(
    val page: Int,
    @Json(name = "total_pages") val totalPages: Int,
    @Json(name = "results") val personsList: List<Person>
)

/**
 * data class that hold summary data about persons
 * in [PopularPeopleResponse] there is list of [Person]
 */
data class Person(
    val name: String,
    val id: Long,
    @Json(name = "profile_path") val imgPath: String?,
    @Json(name = "known_for_department") val personType: String
)

/**
 * data class tha hold [PersonDetails] that carry details about person details
 * when [getPersonDetails] called response will return object of [PersonDetails]
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
    val isBirthdayNull: Boolean = birthday == null
    val isBiographyEmpty: Boolean = biography.isEmpty()
    val isOtherNamesEmpty: Boolean = otherNames.isEmpty()
}

/**
 * data class that holds [PersonImage] object that contains details of images
 * this class used with [PersonProfile]
 */
@Parcelize
data class PersonImage(
    @Json(name = "file_path") val imgPath: String,
    val height: Int,
    val width: Int,
    @Json(name = "aspect_ratio") val aspectRatio: Double
): Parcelable

/**
 * data class that hold data of [PersonProfile] object that carry list of [PersonImage]
 * when getImagesCalled response will return [PersonProfile] object
 */
data class PersonProfile(
    @Json(name = "profiles") val personImages: List<PersonImage>
)


