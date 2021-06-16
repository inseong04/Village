package com.example.village.screen.signup

import android.graphics.Color
import android.os.Bundle
import android.util.Log
import android.util.Patterns
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import com.example.village.R
import com.example.village.databinding.FragmentSignup1Binding
import com.example.village.databinding.FragmentSignup2Binding

class SignupFragment2 : Fragment() {

    lateinit var viewModel: SignupViewModel
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        val binding : FragmentSignup2Binding = DataBindingUtil.inflate(inflater, R.layout.fragment_signup2,
                container, false)
        binding.lifecycleOwner = requireActivity()
        viewModel = ViewModelProvider(requireActivity()).get(SignupViewModel::class.java)
        binding.viewModel = viewModel

        viewModel.email.observe(requireActivity(), { it ->
            if (Patterns.EMAIL_ADDRESS.matcher(it).matches()) {
                Log.e("test", "test1 : " + viewModel.name.value)
                binding.alarm2.setImageResource(R.drawable.ic_signup_active)
                binding.btnIdNext.isEnabled = true
                binding.btnIdNext.setBackgroundColor(Color.parseColor("#2962ff"))
            } else {
                binding.btnIdNext.isEnabled = false
                binding.alarm2.setImageResource(R.drawable.ic_signup_inactivation)
            }
        })

        binding.btnIdNext.setOnClickListener { v ->
            activity?.supportFragmentManager?.beginTransaction()
                    ?.replace(R.id.fragment, SignupFragment3())?.commit()
        }

        return binding.root
    }

}