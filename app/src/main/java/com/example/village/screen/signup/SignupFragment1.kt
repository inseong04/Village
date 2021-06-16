package com.example.village.screen.signup

import android.graphics.Color
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.village.R
import com.example.village.databinding.FragmentSignup1Binding

class SignupFragment1 : Fragment() {

    lateinit var viewModel: SignupViewModel

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        val binding : FragmentSignup1Binding = DataBindingUtil.inflate(inflater, R.layout.fragment_signup1,
                container, false)

        binding.lifecycleOwner = requireActivity()
        viewModel = ViewModelProvider(requireActivity()).get(SignupViewModel::class.java)
        binding.viewModel = viewModel

        viewModel.name.observe(requireActivity(), { it ->
            if (it != "") {
                binding.btnNameNext.isEnabled = true
                binding.alarm1.setImageResource(R.drawable.ic_signup_active)
                binding.btnNameNext.setBackgroundColor(Color.parseColor("#2962ff"))
            } else {
                binding.btnNameNext.isEnabled = false
                binding.alarm1.setImageResource(R.drawable.ic_signup_inactivation)
            }
        })

        binding.btnNameNext.setOnClickListener {
            activity?.supportFragmentManager?.beginTransaction()
                    ?.replace(R.id.fragment, SignupFragment2())?.commit()
        }

        return binding.root
    }

}