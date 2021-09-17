package com.alex.examenparcial_1

import android.os.Parcelable
import kotlinx.parcelize.Parcelize


@Parcelize
enum class LoginType(val text: Int): Parcelable {

    READER(R.string.lector),
    WRITER(R.string.escritor)

}