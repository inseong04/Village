package com.example.village.screen.signup

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import com.example.village.R
import com.example.village.databinding.FragmentSignup4Binding
import com.example.village.screen.login.Login

class SignupFragment4 : Fragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        val binding : FragmentSignup4Binding = DataBindingUtil.inflate(inflater, R.layout.fragment_signup4,
                container, false)
        binding.lifecycleOwner = requireActivity()

        binding.signupBtn.setOnClickListener {
            val intent = Intent(requireContext(), Login::class.java)
            startActivity(intent)
            requireActivity().finish()
        }

        return binding.root
    }

}