package com.alex.examenparcial_1

import android.os.Parcelable
import com.squareup.moshi.JsonClass
import kotlinx.parcelize.Parcelize

@JsonClass(generateAdapter = true)
@Parcelize
class User( var username: String = "",
            var password: String = "",
            var loginType: LoginType? = null): Parcelable {
    companion object {
    val users = arrayOf(                                //tipo de logeo             // user level
        User("alex", "valorant", LoginType.READER),
        User("Paka", "gorra", LoginType.READER),
        User("Poncho", "musicaelectrica", LoginType.READER),
        User("admin1", "123", LoginType.WRITTER),
        User("admin2", "123", LoginType.WRITTER),
        User("admin3", "123", LoginType.WRITTER),
    ) }

    fun validate() = users.firstOrNull {

        (it.username == this.username && it.password == this.password)
    }

    fun setLogintype(a :String):LoginType {
       var lo = LoginType.WRITTER
        if(a == "alex" || a == "Paka" || a =="Poncho")
            lo = LoginType.READER
        else if(a == "admin1"|| a == "admin2" || a == "admin3")
            lo =LoginType.WRITTER

        return lo

    }

}