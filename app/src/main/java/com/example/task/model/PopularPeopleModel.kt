package com.example.task.model


import android.annotation.SuppressLint
import com.google.gson.annotations.SerializedName
//import android.support.annotation.Keep
import kotlinx.android.parcel.Parcelize
import android.os.Parcelable
import androidx.annotation.Keep

@SuppressLint("ParcelCreator")
@Parcelize
@Keep
data class PopularPeopleModel(
    @SerializedName("page") var page: Int,
    @SerializedName("results") var results: List<Result>,
    @SerializedName("total_pages") var totalPages: Int,
    @SerializedName("total_results") var totalResults: Int
) : Parcelable {
    @SuppressLint("ParcelCreator")
    @Parcelize
    @Keep
    data class Result(
        @SerializedName("adult") var adult: Boolean,
        @SerializedName("gender") var gender: Int,
        @SerializedName("id") var id: Int,
        @SerializedName("known_for") var knownFor: List<KnownFor>,
        @SerializedName("known_for_department") var knownForDepartment: String,
        @SerializedName("name") var name: String,
        @SerializedName("popularity") var popularity: Double,
        @SerializedName("profile_path") var profilePath: String
    ) : Parcelable {
        @SuppressLint("ParcelCreator")
        @Parcelize
        @Keep
        data class KnownFor(
            @SerializedName("adult") var adult: Boolean,
            @SerializedName("backdrop_path") var backdropPath: String,
            @SerializedName("first_air_date") var firstAirDate: String,
            @SerializedName("genre_ids") var genreIds: List<Int>,
            @SerializedName("id") var id: Int,
            @SerializedName("media_type") var mediaType: String,
            @SerializedName("name") var name: String,
            @SerializedName("origin_country") var originCountry: List<String>,
            @SerializedName("original_language") var originalLanguage: String,
            @SerializedName("original_name") var originalName: String,
            @SerializedName("original_title") var originalTitle: String,
            @SerializedName("overview") var overview: String,
            @SerializedName("poster_path") var posterPath: String,
            @SerializedName("release_date") var releaseDate: String,
            @SerializedName("title") var title: String,
            @SerializedName("video") var video: Boolean,
            @SerializedName("vote_average") var voteAverage: Double,
            @SerializedName("vote_count") var voteCount: Int
        ) : Parcelable
    }
}