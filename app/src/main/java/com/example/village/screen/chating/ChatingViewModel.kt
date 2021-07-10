package com.example.village.screen.chating

import androidx.lifecycle.ViewModel
import com.google.firebase.auth.FirebaseAuth

class ChatingViewModel : ViewModel(){

    var chatArrayList : ArrayList<ChatingData> = ArrayList<ChatingData>()
    lateinit var postNumber : String
    lateinit var roomNumber : String
    lateinit var title : String
    var chatSum : Int = 0
    var rental : Boolean = false
    val uid = FirebaseAuth.getInstance().uid
    var warningRun : Boolean = false

    fun addChatArrayList( uid : String, chat : String) {
        var chatingData : ChatingData = ChatingData(uid, chat)
        chatArrayList.add(chatingData)
    }

}