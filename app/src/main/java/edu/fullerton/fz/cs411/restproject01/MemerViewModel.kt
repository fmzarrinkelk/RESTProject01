package edu.fullerton.fz.cs411.restproject01

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import edu.fullerton.fz.cs411.restproject01.api.ImgFlipCaptionedImageResponseData
import edu.fullerton.fz.cs411.restproject01.api.ImgFlipExecutor
import edu.fullerton.fz.cs411.restproject01.api.MemeTemplateItem

class MemerViewModel: ViewModel() {

    private var templateIndex = 0

    val memeTemplateLiveData: LiveData<List<MemeTemplateItem>>
    var captionedMemeLiveData: LiveData<ImgFlipCaptionedImageResponseData> = MutableLiveData()

    init {
        this.memeTemplateLiveData = ImgFlipExecutor().fetchTemplates()
    }

    fun setTemplateIndex(index: Int) {
        this.templateIndex = index
    }
    fun getTemplateIndex() :Int {
        return this.templateIndex
    }
    fun increaseTemplateIndex(): Int {
        templateIndex += 1
        if (templateIndex >= (memeTemplateLiveData.value?.size ?: 0)) {
            templateIndex = 0
        }
        return templateIndex
    }
    fun decreaseTemplateIndex(): Int {
        templateIndex -= 1
        if (templateIndex <0 && ((memeTemplateLiveData.value?.size ?: 0) > 0)) {
            templateIndex = memeTemplateLiveData.value!!.size - 1
        }
        return templateIndex
    }

    fun getCurrentMemeTemplate(): MemeTemplateItem? {
        if (memeTemplateLiveData.value != null
            && (templateIndex >= 0 && templateIndex <= memeTemplateLiveData.value!!.size)) {
            return memeTemplateLiveData.value!![this.templateIndex]
        } else {
            return null
        }
    }

    fun captionMeme(templateID: String, caption: String): LiveData<ImgFlipCaptionedImageResponseData> {
        Log.d(TAG, "received request to caption meme $templateID with $caption")
        this.captionedMemeLiveData = ImgFlipExecutor().captionImage(templateID, caption)
        return captionedMemeLiveData
    }
}