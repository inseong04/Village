package com.example.village.screen.signup

import android.content.Intent
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
import com.example.village.databinding.FragmentSignup3Binding
import com.google.android.gms.tasks.OnCompleteListener
import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import java.util.regex.Pattern


class SignupFragment3 : Fragment() {

    lateinit var viewModel: SignupViewModel
    private var mAuth: FirebaseAuth? = null

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        val binding : FragmentSignup3Binding = DataBindingUtil.inflate(inflater, R.layout.fragment_signup3,
                container, false)
        binding.lifecycleOwner = requireActivity()
        viewModel = ViewModelProvider(requireActivity()).get(SignupViewModel::class.java)
        binding.viewModel = viewModel

        viewModel.password.observe(requireActivity(), { it ->
            if (isVaildPassword(it)) {
                binding.alarm3.setImageResource(R.drawable.ic_signup_active)
                binding.btnPasswordNext.isEnabled = true
                binding.btnPasswordNext.setBackgroundColor(Color.parseColor("#2962ff"))
            } else {
                binding.alarm3.setImageResource(R.drawable.ic_signup_inactivation)
                binding.btnPasswordNext.isEnabled = false
            }
        })

        mAuth = FirebaseAuth.getInstance()
        binding.btnPasswordNext.setOnClickListener {
            mAuth!!.createUserWithEmailAndPassword(viewModel.email.value, viewModel.password.value)
                    .addOnCompleteListener(requireActivity(),
                            OnCompleteListener<AuthResult?> { task ->
                                if (task.isSuccessful) {
                                    Log.d("firebase", "createUserWithEmail:success")
                                    saveUsername(mAuth!!, viewModel.name.value)
                                    activity?.supportFragmentManager?.beginTransaction()
                                            ?.replace(R.id.fragment, SignupFragment3())?.commit()
                                } else {
                                    Log.w("firebase", "createUserWithEmail:failure", task.exception)
                                }
                            })
        }

        return binding.root
    }

    private fun saveUsername(mAuth: FirebaseAuth, name: String?) {
        val db = FirebaseFirestore.getInstance()
        val user = mAuth.currentUser
        val uid = user?.uid

        val map = hashMapOf("name" to name)

        db.collection("users").document(uid!!)
                .set(map)
    }

    private fun isVaildPassword(password: String): Boolean {
        val pattern = Pattern.compile("/^[a-z0-9_-]{6,18}$/")
        return pattern.matcher(password).find()
    }

}