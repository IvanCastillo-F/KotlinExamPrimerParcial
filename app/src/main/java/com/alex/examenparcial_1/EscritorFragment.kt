package com.alex.examenparcial_1

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.view.isGone
import androidx.core.view.isVisible
import com.squareup.moshi.Moshi

class EscritorFragment : Fragment(R.layout.fragment_escritor) {

    private val MY_PREFERENCES = "MY_PREFERENCES"
    private val USER_PREFS = "USER_PREFS"
    private lateinit var preferences: SharedPreferences
    private val moshi = Moshi.Builder().build()
    private lateinit var user: User

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {

        var view = inflater.inflate(R.layout.fragment_escritor, container, false)

        preferences = activity?.getSharedPreferences(MY_PREFERENCES, Context.MODE_PRIVATE)!!
        user = getUser()

        initView(view)

        return view
    }

    private lateinit var clWritter: ConstraintLayout
    private lateinit var clReader: ConstraintLayout

    private lateinit var imageView: ImageView
    private lateinit var textNickname: TextView
    private lateinit var textType: TextView
    private lateinit var textCounter: TextView
    private lateinit var delete: Button
    private lateinit var update: Button
    private lateinit var newArticle: Button
    private lateinit var imageSelected: ImageView
    private lateinit var btnLeft: ImageView
    private lateinit var btnRight: ImageView
    private lateinit var txtTitle: TextView


    private lateinit var imageViewReader: ImageView
    private lateinit var textNicknameReader: TextView
    private lateinit var textTypeReader: TextView
    private lateinit var textCounterReader: TextView
    private lateinit var imageSelectReader: ImageView
    private lateinit var btnLeftReader: ImageView
    private lateinit var btnRightReader: ImageView
    private lateinit var txtTitleReader: TextView
    private lateinit var imageHearth: ImageView


    private fun initView(view: View){

        clWritter = view.findViewById(R.id.clWritter)
        clReader = view.findViewById(R.id.clReader)

        imageView = view.findViewById(R.id.imageView)
        textNickname = view.findViewById(R.id.textNickname)
        textType = view.findViewById(R.id.textType)
        textCounter = view.findViewById(R.id.textCounter)
        delete = view.findViewById(R.id.delete)
        update = view.findViewById(R.id.update)
        newArticle = view.findViewById(R.id.newArticle)
        imageSelected = view.findViewById(R.id.imageSelected)
        btnRight = view.findViewById(R.id.btnRight)
        btnLeft = view.findViewById(R.id.btnLeft)
        txtTitle = view.findViewById(R.id.txtTitle)

        imageViewReader = view.findViewById(R.id.imageViewReader)
        textNicknameReader = view.findViewById(R.id.textNicknameReader)
        textTypeReader = view.findViewById(R.id.textTypeReader)
        textCounterReader = view.findViewById(R.id.textCounterReader)
        imageSelectReader = view.findViewById(R.id.imageSelectReader)
        btnLeftReader = view.findViewById(R.id.btnLeftReader)
        btnRightReader = view.findViewById(R.id.btnRightReader)
        txtTitleReader = view.findViewById(R.id.txtTitleReader)
        imageHearth = view.findViewById(R.id.imageHearth)

        imageView.setImageResource(user.pImage!!)
        imageViewReader.setImageResource(user.pImage!!)

        textNickname.text = user.username
        textNicknameReader.text = user.username

        user.loginType?.let { textType.setText(it.text) }
        user.loginType?.let { textTypeReader.setText(it.text) }

        when (user.loginType) {
            LoginType.READER -> {
                initReader()
            }
            LoginType.WRITTER  -> {
                initWritter()
            }
        }
    }

    private fun initReader(){
        clReader.isVisible = true
        clWritter.isGone = true
    }

    private fun initWritter(){
        clWritter.isVisible = true
        clReader.isGone = true
    }


    private fun getUser() =
        preferences.getString("USER_PREFS", null)?.let {
            return@let try {
                moshi.adapter(User::class.java).fromJson(it)
            } catch (e: Exception) {
                User()
            }
        } ?: User()

}