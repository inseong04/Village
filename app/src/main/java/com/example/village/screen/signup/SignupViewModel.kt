package com.example.village.screen.signup

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class SignupViewModel : ViewModel() {

    var name = MutableLiveData("")
    var email = MutableLiveData("")
    var password = MutableLiveData("")
    var emailBtnEnable = false;
    var nameBtnEnable = false;
    var passwordBtnEnable = false;

}