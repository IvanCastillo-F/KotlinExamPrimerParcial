package com.alex.examenparcial_1

import android.os.Parcelable
import com.squareup.moshi.JsonClass
import kotlinx.parcelize.Parcelize

@JsonClass(generateAdapter = true)
@Parcelize
class User( var username: String = "",
            var password: String = "",
            var loginType: LoginType? = null,
            var pImage: Int? = null): Parcelable {
    companion object {
    val users = arrayOf(
        User("alex", "valorant", LoginType.READER,R.drawable.ic_user),
        User("Paka", "gorra", LoginType.READER,R.drawable.ic_user__1_),
        User("Poncho", "musicaelectrica", LoginType.READER,R.drawable.ic_user__2_),
        User("admin1", "123", LoginType.WRITER,R.drawable.ic_user__3_),
        User("admin2", "123", LoginType.WRITER,R.drawable.ic_user__4_),
        User("admin3", "123", LoginType.WRITER,R.drawable.ic_user__5_)
    ) }

    fun validate() = users.firstOrNull {

        (it.username == this.username && it.password == this.password)
    }

    fun setLogintype(a :String):LoginType {
       var lo = LoginType.WRITER
        if(a == "alex" || a == "Paka" || a =="Poncho")
            lo = LoginType.READER
        else if(a == "admin1"|| a == "admin2" || a == "admin3")
            lo =LoginType.WRITER

        return lo
    }

    fun setImage(a :String):Int {
        val lo : Int = when (a) {
            in "alex" -> R.drawable.ic_user
            in "Paka" -> R.drawable.ic_user__1_
            in "Poncho" -> R.drawable.ic_user__2_
            in "admin1" -> R.drawable.ic_user__3_
            in "admin2" -> R.drawable.ic_user__4_
            in "admin3" -> R.drawable.ic_user__5_
            else -> 0
        }

        return lo
    }

}