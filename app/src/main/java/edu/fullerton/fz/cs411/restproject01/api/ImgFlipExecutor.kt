package edu.fullerton.fz.cs411.restproject01.api

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import edu.fullerton.fz.cs411.restproject01.TAG
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

//********************************
// THIS IS ABSOLUTELY TERRIBLE
private const val DO_NOT_DO_THIS_NEVER_EVER_Username = "frankzk"
private const val DO_NOT_DO_THIS_NEVER_EVER_Password = "ABcd12!@"
// instead of this, either use a username password that you make the user enter
// or in some cases use OAuth


class ImgFlipExecutor {
    private val api: ImgFlipApi
    init {
        val retrofit: Retrofit = Retrofit.Builder()
            .baseUrl("https://api.imgflip.com/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
        this.api = retrofit.create(ImgFlipApi::class.java)
    }

    fun fetchTemplates(): LiveData<List<MemeTemplateItem>> {
        val responseLiveData: MutableLiveData<List<MemeTemplateItem>> = MutableLiveData()
        val imgFlipRequest: Call<ImgFlipGetMemesResponse> = this.api.fetchTemplates()

            imgFlipRequest.enqueue(object: Callback<ImgFlipGetMemesResponse> {

            override fun onResponse(
                call: Call<ImgFlipGetMemesResponse>,
                response: Response<ImgFlipGetMemesResponse>
            ) {
                Log.d(TAG, "Failed to fetch imgFlip meme templates")
                val imgFlipMemesResponse: ImgFlipGetMemesResponse? = response.body()
                val imgFlipGetMemesResponseData: ImgFlipGetMemesResponseData? = imgFlipMemesResponse?.data
                var memeTemplates: List<MemeTemplateItem> = imgFlipGetMemesResponseData?.templates ?: mutableListOf()
                memeTemplates = memeTemplates.filterNot {
                    it.url.isBlank()
                }
                //Log.d(TAG, "Templates: $memeTemplates")
                responseLiveData.value = memeTemplates
            }

            override fun onFailure(call: Call<ImgFlipGetMemesResponse>, t: Throwable) {
                Log.d(TAG, "Failed to fetch imgFlip meme templates")
            }

        })
        return responseLiveData
    }

    fun captionImage(templateID: String, caption: String): LiveData<ImgFlipCaptionedImageResponseData> {
        val responseLiveData: MutableLiveData<ImgFlipCaptionedImageResponseData> = MutableLiveData()
        val imgFlipRequest: Call<ImgFlipCaptionedImageResponse> = this.api.captionImage(
            templateID = templateID,
            caption1 = caption,
            caption2 = caption,
            username = DO_NOT_DO_THIS_NEVER_EVER_Username,
            password = DO_NOT_DO_THIS_NEVER_EVER_Password
        )
        imgFlipRequest.enqueue(object: Callback<ImgFlipCaptionedImageResponse> {
            override fun onResponse(
                call: Call<ImgFlipCaptionedImageResponse>,
                response: Response<ImgFlipCaptionedImageResponse>
            ) {
                Log.d(TAG, "Response received from ImgFlip caption_image endpoint")
                val imgFlipCaptionedImageResponse: ImgFlipCaptionedImageResponse? = response.body()
                if (imgFlipCaptionedImageResponse?.success == true) {
                    val imgFlipCaptionedImageResponseData: ImgFlipCaptionedImageResponseData = imgFlipCaptionedImageResponse.data
                    responseLiveData.value = imgFlipCaptionedImageResponseData
                    Log.d(TAG, "Captioned image url: ${imgFlipCaptionedImageResponseData.url}")
                } else {
                    Log.d(TAG, "Request failed with error message \"${imgFlipCaptionedImageResponse?.errorMessage}\"")
                }
            }

            override fun onFailure(call: Call<ImgFlipCaptionedImageResponse>, t: Throwable) {
                Log.d(TAG, "ImgFlip failed to create captioned image")
            }

        })
        return responseLiveData
    }
}