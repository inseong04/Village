package com.example.village.screen.signup

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class SignupViewModel : ViewModel() {

    var name = MutableLiveData("")
    var phoneNumber = MutableLiveData("")
    var location = MutableLiveData("")
    var email = MutableLiveData("")
    var password = MutableLiveData("")

    public fun setPhoneNumber (text : String) {
        phoneNumber.value = text
    }

}