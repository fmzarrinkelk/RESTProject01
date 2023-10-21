package edu.fullerton.fz.cs411.restproject01.api

import com.google.gson.annotations.SerializedName

class ImgFlipCaptionedImageResponse {
    @SerializedName("success")
    var success = false

    @SerializedName("data")
    lateinit var data: ImgFlipCaptionedImageResponseData

    @SerializedName("error_message")
    lateinit var errorMessage: String
}