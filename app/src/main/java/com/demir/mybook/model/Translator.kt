package com.demir.mybook.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Translator(
    val birth_year: Int,
    val death_year: Int,
):Parcelable