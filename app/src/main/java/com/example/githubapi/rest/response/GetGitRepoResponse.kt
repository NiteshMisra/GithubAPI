package com.example.githubapi.rest.response

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

data class GetGitRepoResponse(
    @SerializedName("items") val repos : List<GitRepoData> = emptyList()
)

@Parcelize
data class GitRepoData(
    @SerializedName("full_name") val fullName : String ?= "",
    @SerializedName("default_branch") val defaultBranch : String ?= "",
    @SerializedName("owner") val owner : OwnerDetail?
) : Parcelable

@Parcelize
data class OwnerDetail(
    @SerializedName("avatar_url") val avatarUrl : String ?= ""
) : Parcelable