package com.kkk.baseapp.data.vos

import androidx.annotation.NonNull
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName

//@Entity(tableName = "movie")
class MovieVO {
    @NonNull
    @PrimaryKey
    @SerializedName("id")
    var id: String? = null
    @SerializedName("title")
    var title: String? = null
    @SerializedName("brief")
    var brief: String? = null
    @SerializedName("image_url")
    var imageUrl: String? = null
}