package edu.fullerton.fz.cs411.restproject01.api

import com.google.gson.annotations.SerializedName

class ImgFlipGetMemesResponse {
    @SerializedName("data")
    lateinit var data: ImgFlipGetMemesResponseData
}