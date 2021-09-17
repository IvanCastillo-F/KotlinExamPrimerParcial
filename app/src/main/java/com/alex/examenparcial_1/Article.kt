package com.alex.examenparcial_1

import android.os.Parcelable
import com.squareup.moshi.JsonClass
import kotlinx.parcelize.Parcelize

@JsonClass(generateAdapter = true)
@Parcelize
class Article(var id: Int? = null,
              var Tittle: String = "",
              var Descrip: String = "",
              var aImage: Int? = null,
              var writter: String = ""): Parcelable {

    companion object {
        val articles = mutableListOf(
            Article(0,"Fishes", "This is a book about fishes.", R.drawable.ic_bluebook,"admin1"),
            Article(1,"Gardens and More", "This is a book about how to plant your own garden.", R.drawable.ic_greenbook,"admin1"),
            Article(2,"Cooking Mama", "This is a book about how to cook certain recipes.", R.drawable.ic_orangebook,"admin1"),
            Article(3,"History for dummies", "This is a book of the most important events in history.", R.drawable.ic_violetbook,"admin2"),
            Article(4,"Drawings 101", "This is a book to learn how to draw.",R.drawable.ic_redbook,"admin3")
        ) }

}