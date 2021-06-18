package com.example.village

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class ChangePhoneNumberViewModel : ViewModel() {
    var phoneNumber = MutableLiveData("")
}