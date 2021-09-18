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

class ViewArticleFragment : Fragment(R.layout.fragment_view_article) {

    private val MY_PREFERENCES = "MY_PREFERENCES"
    private val USER_PREFS = "USER_PREFS"
    private val WRITTEN_PREFS = "WRITTEN_PREFS"
    private lateinit var preferences: SharedPreferences
    private lateinit var objUser: User
    private lateinit var objArticle: Article
    private val moshi = Moshi.Builder().build()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {

        var view = inflater.inflate(R.layout.fragment_view_article, container, false)

        preferences = activity?.getSharedPreferences(MY_PREFERENCES, Context.MODE_PRIVATE)!!

        objUser = getUser()
        objArticle = getArticle()

        initView(view)

        return view
    }

    private lateinit var clListWri: ConstraintLayout
    private lateinit var clListReader: ConstraintLayout

    private lateinit var imageSelectWri: ImageView
    private lateinit var btnLeft: ImageView
    private lateinit var btnRight: ImageView
    private lateinit var editTitle: EditText
    private lateinit var editDesc: EditText
    private lateinit var btnEdit: Button
    private lateinit var btnCreate: Button

    private lateinit var imageHearth: ImageView
    private lateinit var imageSelectRead: ImageView
    private lateinit var viewDesc: TextView
    private lateinit var viewTitle: TextView
    private lateinit var btnDone: Button

    private fun initView(view: View){

        clListWri = view.findViewById(R.id.clListWri)
        clListReader = view.findViewById(R.id.clListReader)

        imageSelectWri = view.findViewById(R.id.imageSelectWri)
        btnLeft = view.findViewById(R.id.btnLeft)
        btnRight = view.findViewById(R.id.btnRight)
        editTitle = view.findViewById(R.id.editTitle)
        editDesc = view.findViewById(R.id.editDesc)
        btnEdit = view.findViewById(R.id.btnEdit)
        btnCreate = view.findViewById(R.id.btnCreate)

        imageHearth = view.findViewById(R.id.imageHearth)
        imageSelectRead = view.findViewById(R.id.imageSelectRead)
        viewDesc = view.findViewById(R.id.viewDesc)
        viewTitle = view.findViewById(R.id.viewTitle)
        btnDone = view.findViewById(R.id.btnDone)


        imageSelectWri.setImageResource(objArticle.aImage!!)
        imageSelectRead.setImageResource(objArticle.aImage!!)

        editTitle.setText(objArticle.Tittle)
        viewTitle.text = objArticle.Tittle

        editDesc.setText(objArticle.Descrip)
        viewDesc.text = objArticle.Descrip

        when (objUser.loginType) {
            LoginType.READER -> {
                initReader()
            }
            LoginType.WRITER  -> {
                initWritter()
            }
        }



    }


    private fun initReader(){
        clListReader.isVisible = true
        clListWri.isGone = true
    }

    private fun initWritter(){
        clListWri.isVisible = true
        clListReader.isGone = true
    }


    private fun getUser() =
        preferences.getString("USER_PREFS", null)?.let {
            return@let try {
                moshi.adapter(User::class.java).fromJson(it)
            } catch (e: Exception) {
                User()
            }
        } ?: User()

    private fun getArticle() =
        preferences.getString("ARTICLE_PREFS", null)?.let {
            return@let try {
                moshi.adapter(Article::class.java).fromJson(it)
            } catch (e: Exception) {
                Article()
            }
        } ?: Article()

}