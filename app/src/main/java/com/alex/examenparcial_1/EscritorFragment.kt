package com.alex.examenparcial_1

import android.content.Context
import android.content.SharedPreferences
import android.location.Location
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.view.isGone
import androidx.core.view.isVisible
import androidx.fragment.app.FragmentTransaction
import com.alex.examenparcial_1.Article.Companion.articles
import com.squareup.moshi.Moshi
import com.squareup.moshi.Types
import com.squareup.moshi.adapter

class EscritorFragment : Fragment(R.layout.fragment_escritor) {

    private val MY_PREFERENCES = "MY_PREFERENCES"
    private val USER_PREFS = "USER_PREFS"
    private val WRITTEN_PREFS = "WRITTEN_PREFS"
    private lateinit var preferences: SharedPreferences
    private val moshi = Moshi.Builder().build()
    private lateinit var objUser: User
    private lateinit var article: Article
    private var writedArticle = mutableListOf<Article>()
    private var writerArticles = mutableListOf<Article>()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {

        var view = inflater.inflate(R.layout.fragment_escritor, container, false)

        preferences = activity?.getSharedPreferences(MY_PREFERENCES, Context.MODE_PRIVATE)!!
        objUser = getUser()
        writedArticle = getWritedArticles()
       if (writedArticle.isEmpty())
            writedArticle.addAll(articles)

        writerArticles = mutableListOf()

        if(objUser.loginType == LoginType.WRITER){
            writedArticle.forEachIndexed { index, article ->
                if (objUser.username == writedArticle[index].writter){
                    writerArticles.add(writedArticle[index])
                }
            }

        }




        initView(view)

        return view
    }

    private lateinit var clWriter: ConstraintLayout
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

    var inum: Int = 0

    var limitaw: Int = 0
    var limitbw: Int =  0

    var limita: Int = 0
    var limitb: Int =  0


    private fun initView(view: View){

        clWriter = view.findViewById(R.id.clWritter)
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

        imageView.setImageResource(objUser.pImage!!)
        imageViewReader.setImageResource(objUser.pImage!!)

        textNickname.text = objUser.username
        textNicknameReader.text = objUser.username

        objUser.loginType?.let { textType.setText(it.text) }
        objUser.loginType?.let { textTypeReader.setText(it.text) }

        textCounter.text = "Counter writed: " + (writerArticles.size - 1).toString()

        when (objUser.loginType) {
            LoginType.READER -> {
                initReader()
            }
            LoginType.WRITER  -> {
                initWritter()
            }
        }
        limita = writedArticle.size - 2
        limitb = writedArticle.size - 1

        limitaw = writerArticles.size - 2
        limitbw = writerArticles.size - 1

        btnRightReader.setOnClickListener {  next() }
        btnLeftReader.setOnClickListener { previous() }

        btnRight.setOnClickListener { nextwriter() }
        btnLeft.setOnClickListener { previouswriter() }

        delete.setOnClickListener(){
            if(writerArticles.size != 1 ||writerArticles.size != 0){
            writedArticle.forEach{it ->
                if(it.id!! == writerArticles[inum].id)
                    article = it
            }
            writedArticle.remove(article)
            saveArticles(writedArticle)
            showMessege("Element eliminated")
            }else{
                showMessege("Is Empty!!!!")
            }
        }

        update.setOnClickListener(){
            saveArticles(writedArticle)
            saveArticle(writedArticle[writedArticle[inum].id!!])
            replaceFragment(ViewArticleFragment().apply {
                arguments = Bundle().apply {
                    putParcelable("key", writerArticles[inum])
                }
            })
        }

        newArticle.setOnClickListener(){
            saveArticles(writedArticle)
            saveArticle(writedArticle[writedArticle[inum].id!!])
            replaceFragment(ViewArticleFragment().apply {
                arguments = Bundle().apply {
                    putParcelable("key", writerArticles[inum])
                }
            })
        }

        imageSelectReader.setOnClickListener(){
            saveArticles(writedArticle)
            saveArticle(writedArticle[inum])
            replaceFragment(ViewArticleFragment().apply {
                arguments = Bundle().apply {
                    putParcelable("key", writedArticle[inum])
                }
            })

        }

    }

    private fun next(){
        when(inum){
            in 0..limita ->
            {
                inum++
                imageSelectReader.setImageResource(writedArticle[inum].aImage!!)
                txtTitleReader.text = writedArticle[inum].Tittle

            }
            else ->
            {
                inum = 0
            }
        }
    }

    private fun nextwriter(){
        when(inum){
            in 0..limitaw ->
            {
                inum++
                imageSelected.setImageResource(writerArticles[inum].aImage!!)
                txtTitle.text = writerArticles[inum].Tittle

            }
            else ->
            {
                inum = 0
            }
        }
    }

    private fun previouswriter(){
        when(inum){
            in 1..limitbw ->
            {
                inum--
                imageSelectReader.setImageResource(writedArticle[inum].aImage!!)
                txtTitle.text = writerArticles[inum].Tittle

            }
            else ->
            {
                inum = limitbw
            }
        }
    }

    private fun previous(){
        when(inum){
            in 1..limitb ->
            {
                inum--
                imageSelectReader.setImageResource(writedArticle[inum].aImage!!)
                txtTitleReader.text = writedArticle[inum].Tittle
            }
            else ->
            {
                inum = limitb
            }
        }
    }

    private fun showMessege(message: String) = Toast.makeText(requireContext(), message, Toast.LENGTH_SHORT).show()

    private fun initReader(){
        clReader.isVisible = true
        clWriter.isGone = true
    }

    private fun initWritter(){
        clWriter.isVisible = true
        clReader.isGone = true
    }

    private fun replaceFragment(fragment: Fragment) {
        val fragment: Fragment = fragment
        val transaction: FragmentTransaction = requireFragmentManager().beginTransaction()
        transaction.setCustomAnimations(
            R.anim.slide_in_right,
            R.anim.slide_out_left,
            R.anim.slide_in_left,
            R.anim.slide_out_right)
        transaction.replace(
            R.id.container,
            fragment
        )

        transaction.addToBackStack(null)

        transaction.commit()
    }


    private fun getWritedArticles() =
        preferences.getString("WRITTEN_PREFS", null)?.let {
            return@let try {
                val listMyData = Types.newParameterizedType(List::class.java, Article::class.java)
                moshi.adapter<MutableList<Article>>(listMyData).fromJson(it)
            } catch (e: Exception) {
                mutableListOf()
            }
        } ?: mutableListOf()

    private fun saveArticles(arti: MutableList<Article>) {

        val listMyData = Types.newParameterizedType(List::class.java, Article::class.java)
        val jsonAdapter = moshi.adapter<List<Article>>(listMyData)
        val json = jsonAdapter.toJson(arti)
        preferences.edit().putString("WRITTEN_PREFS",json).apply()
    }

    private fun saveArticle(article: Article) {
        preferences.edit().putString("ARTICLE_PREFS",moshi.adapter(Article::class.java).toJson(article)).apply()
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