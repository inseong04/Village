package com.example.village.screen.signup

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.fragment.app.Fragment
import com.example.village.R

class SignupActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        var transition = supportFragmentManager.beginTransaction()
                .replace(R.id.fragment, SignupFragment1())
        transition.commit()

    }

}