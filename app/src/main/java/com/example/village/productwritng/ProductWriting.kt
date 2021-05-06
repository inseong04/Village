package com.example.village.productwritng

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.databinding.DataBindingUtil
import com.example.village.MainActivity
import com.example.village.R
import com.example.village.databinding.ActivityProductWritingBinding
import com.example.village.home.Home

class ProductWriting : AppCompatActivity() {

    private lateinit var binding : ActivityProductWritingBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this,R.layout.activity_product_writing)

        binding.goHomeBtn.setOnClickListener {
            val intent = Intent(this,MainActivity::class.java)
            startActivity(intent)
        }
    }

}