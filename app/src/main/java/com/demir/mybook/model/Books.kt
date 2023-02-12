package com.demir.mybook.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Books(
    val authors: List<Author>?,
    val bookshelves: List<String>?,
    val copyright: Boolean,
    val download_count: Int,
    val id: Int,
    val languages: List<String>,
    val media_type: String,
    val subjects: List<String>?=null,
    val title: String?,
    val formats: Formats?,
    val translators: List<Translator>?
):Parcelable{
    override fun hashCode(): Int{
        var result = id.hashCode()
        if(formats?.imagejpeg.isNullOrEmpty()){
            result = 31 * result + formats?.imagejpeg.hashCode()
        }
        if(authors.isNullOrEmpty()){
            result = 31 * result + formats?.imagejpeg.hashCode()
        }
        if(languages.isNullOrEmpty()){
            result = 31 * result + formats?.imagejpeg.hashCode()
        }
        if(subjects.isNullOrEmpty()){
            result = 31 * result + formats?.imagejpeg.hashCode()
        }
        if(title.isNullOrEmpty()){
            result = 31 * result + formats?.imagejpeg.hashCode()
        }
        return result
    }
}