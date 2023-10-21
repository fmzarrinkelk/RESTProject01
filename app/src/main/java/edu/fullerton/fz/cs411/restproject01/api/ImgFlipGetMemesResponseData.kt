package edu.fullerton.fz.cs411.restproject01.api

import com.google.gson.annotations.SerializedName

class ImgFlipGetMemesResponseData {
    @SerializedName("memes")
    lateinit var templates: List<MemeTemplateItem>
}