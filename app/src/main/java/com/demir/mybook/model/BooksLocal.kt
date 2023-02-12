package com.demir.mybook.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class BooksLocal(
    @ColumnInfo(name="title")
    val title:String,
    @ColumnInfo(name="imagejpeg")
    val imagejpeg: String?,
    @PrimaryKey
    @ColumnInfo(name = "id")
    val id:Int,
    @ColumnInfo(name="name")
    val name: String?,
    var selected:Boolean?=false

){

}