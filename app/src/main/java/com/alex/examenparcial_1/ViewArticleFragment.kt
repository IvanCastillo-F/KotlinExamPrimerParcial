package com.alex.examenparcial_1

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.view.isGone
import androidx.core.view.isVisible
import com.squareup.moshi.Moshi
import com.squareup.moshi.Types

class ViewArticleFragment : Fragment(R.layout.fragment_view_article) {

    private val MY_PREFERENCES = "MY_PREFERENCES"
    private val USER_PREFS = "USER_PREFS"
    private val WRITTEN_PREFS = "WRITTEN_PREFS"
    private lateinit var preferences: SharedPreferences
    private lateinit var objUser: User
    private lateinit var objArticle: Article
    private lateinit var article: Article
    private var userlike = mutableListOf<String>()
    private var image = mutableListOf<Int>()
    private var writedArticle = mutableListOf<Article>()
    private val moshi = Moshi.Builder().build()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {

        var view = inflater.inflate(R.layout.fragment_view_article, container, false)

        preferences = activity?.getSharedPreferences(MY_PREFERENCES, Context.MODE_PRIVATE)!!

        image.add(R.drawable.ic_redbook)
        image.add(R.drawable.ic_violetbook)
        image.add(R.drawable.ic_bluebook)
        image.add(R.drawable.ic_orangebook)
        image.add(R.drawable.ic_greenbook)

        objUser = getUser()
        objArticle = getArticle()
        writedArticle = getWritedArticles()
        if (writedArticle.isEmpty())
            writedArticle.addAll(Article.articles)

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
    private lateinit var txtCounter: TextView
    private var index : Int = 0
    var inum: Int = 0
    var limita: Int = 0
    var limitb: Int =  0
    var hearthOrn = false

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
        txtCounter = view.findViewById(R.id.txtCounter)


        imageSelectWri.setImageResource(objArticle.aImage!!)
        imageSelectRead.setImageResource(objArticle.aImage!!)

        editTitle.setText(objArticle.Tittle)
        viewTitle.text = objArticle.Tittle

        editDesc.setText(objArticle.Descrip)
        viewDesc.text = objArticle.Descrip

        txtCounter.text = objArticle.liked?.size.toString()

        limitb = writedArticle.size - 1
        limita = writedArticle.size - 2

        when (objUser.loginType) {
            LoginType.READER -> {
                initReader()
            }
            LoginType.WRITER  -> {
                initWritter()
            }
        }

        btnEdit.setOnClickListener(){

            writedArticle.forEach{it ->
                if(it.id!! == objArticle.id)
                    article = it
            }
            index = writedArticle.indexOf(article)
            article = Article(index,editTitle.text.toString(),editDesc.text.toString(),image[inum],objUser.username)
            writedArticle[index] = article
            saveArticles(writedArticle)
            showMessege("The element has been edited")

        }

        btnCreate.setOnClickListener(){
            index = writedArticle.lastIndex + 1
            article = Article(index,editTitle.text.toString(),editDesc.text.toString(),image[inum],objUser.username)
            writedArticle.add(article)
            saveArticles(writedArticle)
            showMessege("The element has been added")
        }

        imageHearth.setOnClickListener(){



            userlike = objArticle.liked!!


            if(hearthOrn){
                userlike.add(objUser.username)
                writedArticle.forEach{it ->
                    if(it.id!! == objArticle.id)
                        article = it
                }
                index = writedArticle.indexOf(article)
                article = Article(index,editTitle.text.toString(),editDesc.text.toString(),image[inum],objUser.username,userlike)
                writedArticle[index] = article
                txtCounter.text = article.liked!!.size.toString()
                imageHearth.setImageResource(R.drawable.ic_hearth)
                saveArticles(writedArticle)
            } else{
                userlike.remove(objUser.username)
                writedArticle.forEach{it ->
                    if(it.id!! == objArticle.id)
                        article = it
                }
                index = writedArticle.indexOf(article)
                article = Article(index,editTitle.text.toString(),editDesc.text.toString(),image[inum],objUser.username,userlike)
                writedArticle[index] = article
                txtCounter.text = article.liked!!.size.toString()
                imageHearth.setImageResource(R.drawable.ic_empty_hearth)
                saveArticles(writedArticle)
            }
            hearthOrn = !hearthOrn

        }


        btnLeft.setOnClickListener(){ previous() }

        btnRight.setOnClickListener(){  next()  }

    }


    private fun previous(){
        when(inum){
            in 1..limitb ->
            {
                inum--
                imageSelectWri.setImageResource(image[inum]!!)

            }
            else ->
            {
                inum = limitb
            }
        }
    }


    private fun next(){
        when(inum){
            in 0..limita ->
            {
                inum++
                imageSelectWri.setImageResource(image[inum]!!)

            }
            else ->
            {
                inum = 0
            }
        }
    }


    private fun showMessege(message: String) = Toast.makeText(requireContext(), message, Toast.LENGTH_SHORT).show()


    private fun initReader(){
        clListReader.isVisible = true
        clListWri.isGone = true
    }

    private fun initWritter(){
        clListWri.isVisible = true
        clListReader.isGone = true
    }


    private fun saveArticles(arti: MutableList<Article>) {

        val listMyData = Types.newParameterizedType(List::class.java, Article::class.java)
        val jsonAdapter = moshi.adapter<List<Article>>(listMyData)
        val json = jsonAdapter.toJson(arti)
        preferences.edit().putString("WRITTEN_PREFS",json).apply()
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

    private fun getWritedArticles() =
        preferences.getString("WRITTEN_PREFS", null)?.let {
            return@let try {
                val listMyData = Types.newParameterizedType(List::class.java, Article::class.java)
                moshi.adapter<MutableList<Article>>(listMyData).fromJson(it)
            } catch (e: Exception) {
                mutableListOf()
            }
        } ?: mutableListOf()

}