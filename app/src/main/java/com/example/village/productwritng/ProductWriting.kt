package com.example.village.productwritng

import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.Selection
import android.text.TextUtils
import android.text.TextWatcher
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.example.village.MainActivity
import com.example.village.R
import com.example.village.databinding.ActivityProductWritingBinding
import java.text.DecimalFormat


class ProductWriting : AppCompatActivity() {

    private lateinit var binding : ActivityProductWritingBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_product_writing)
        binding.activity


        var price : String

        binding.goHomeBtn.setOnClickListener {
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
        }

        var strAmount : String = ""
        binding.priceEtv.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {
            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                if (!TextUtils.isEmpty(s.toString()) && !s.toString().equals(strAmount)) {
                    if(s.toString() != null) {
                        strAmount = toComma(s.toString().replace(",", ""))
                    }
                    binding.priceEtv.setText(strAmount)
                    val editable: Editable = binding.priceEtv.getText()
                    Selection.setSelection(editable, strAmount.length)
                }
            }

            fun toComma(str: String): String {
                var value : Double = str.toDouble()
                var decimalFormat : DecimalFormat = DecimalFormat("#,###")
                return decimalFormat.format(value)
            }
        })

    }



}