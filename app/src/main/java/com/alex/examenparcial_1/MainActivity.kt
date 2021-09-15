package com.alex.examenparcial_1

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.constraintlayout.widget.*
import androidx.fragment.app.Fragment

class MainActivity : AppCompatActivity() {

    private val KEY = "STATE_KEY"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        replaceFragment(LoginFragment().apply {
            arguments = Bundle().apply {
                putString("key", "This is main fragment")

            }
        })

    }

    override fun onSaveInstanceState(outState: Bundle) {
        outState.run {
            putBoolean(KEY, false)
        }

        super.onSaveInstanceState(outState)
    }


    private fun replaceFragment(fragment: Fragment) {
        supportFragmentManager.beginTransaction().apply {
          /*  setCustomAnimations(
                androidx.constraintlayout.widget.R.anim.slide_in_right,
                androidx.constraintlayout.widget.R.anim.slide_out_left,
                androidx.constraintlayout.widget.R.anim.slide_in_left,
                androidx.constraintlayout.widget.R.anim.slide_out_right
            )*/
            replace(R.id.container, fragment)
            addToBackStack(fragment.tag)
            commit()
        }
    }
}