package com.alex.examenparcial_1

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.fragment.app.FragmentTransaction
import com.squareup.moshi.Moshi


class LoginFragment : Fragment(R.layout.fragment_login) {

    private val MY_PREFERENCES = "MY_PREFERENCES"
    private val USER_PREFS = "USER_PREFS"
    private lateinit var preferences: SharedPreferences
    private val moshi = Moshi.Builder().build()
    private lateinit var user: User

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {

        var view = inflater.inflate(R.layout.fragment_login, container, false)

        initView(view)

        preferences = activity?.getSharedPreferences(MY_PREFERENCES, Context.MODE_PRIVATE)!!

        user = getUser()

        return view

    }

    private lateinit var loginImage: ImageView
    private lateinit var edtUser: EditText
    private lateinit var btnLogin: Button
    private lateinit var edtPassword: EditText

    private fun initView(view: View){

        loginImage = view.findViewById(R.id.loginImage)
        edtUser = view.findViewById(R.id.edtUser)
        btnLogin = view.findViewById(R.id.btnLogin)
        edtPassword = view.findViewById(R.id.edtPassword)

        edtUser.addTextChangedListener(textWatcherlogin)
        edtPassword.addTextChangedListener(textWatcherpassword)

        btnLogin.setOnClickListener {

            user.apply {
                loginType = user.setLogintype(edtUser.text.toString())
                username = edtUser.text.toString()
                password = edtPassword.text.toString()
            }

            doLogin()
        }

    }


    private val textWatcherlogin = object : TextWatcher {
        override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

        }

        override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

        }

        override fun afterTextChanged(s: Editable?) {

            edtPassword.isEnabled = s != null

        }
    }


    private val textWatcherpassword = object : TextWatcher {
        override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

        }

        override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

        }

        override fun afterTextChanged(s: Editable?) {

            btnLogin.isEnabled = s != null
        }
    }


    private fun doLogin() =
        user.validate()?.let {
            saveUser(it)
            replaceFragment(EscritorFragment().apply {
                arguments = Bundle().apply {
                    putParcelable("key", it)
                }
            })
            showMessege("Welcome ${it.username}")
        } ?: showMessege("This user doesn´t exist or you type wrong your password")



    private fun showMessege(message: String) = Toast.makeText(requireContext(), message, Toast.LENGTH_SHORT).show()



    private fun saveUser(user: User) {
        preferences.edit().putString("USER_PREFS",moshi.adapter(User::class.java).toJson(user)).apply()

    }

    private fun getUser() =
        preferences.getString("USER_PREFS", null)?.let {
            return@let try {
                moshi.adapter(User::class.java).fromJson(it)
            } catch (e: Exception) {
                User()
            }
        } ?: User()

    private fun replaceFragment(fragment: Fragment) {
        val someFragment: Fragment = fragment
        val transaction: FragmentTransaction = requireFragmentManager().beginTransaction()
       transaction.setCustomAnimations(
            R.anim.slide_in_right,
            R.anim.slide_out_left,
            R.anim.slide_in_left,
            R.anim.slide_out_right)
        transaction.replace(
            R.id.container,
            someFragment
        )

        transaction.addToBackStack(null)

        transaction.commit()
    }

}