package com.example.village.screen.chat

import android.view.View
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.SetOptions

class ChatingViewModel : ViewModel(){
    var chatArrayList : ArrayList<ChatingData> = ArrayList<ChatingData>()
    var etvContent : MutableLiveData<String> = MutableLiveData()
    lateinit var postNumber : String
    lateinit var roomNumber : String
    lateinit var title : String
    var rental : Boolean = false
    val uid = FirebaseAuth.getInstance().uid

    fun addChatArrayList(type : Int, chat : String) {
        var chatingData : ChatingData = ChatingData(type, chat)
        chatArrayList.add(chatingData)
    }

    fun sendMessage (view : View) {
        if (!etvContent.equals("")) {
           chatArrayList.add(ChatingData(ChatingType.SEND_MESSAGE, etvContent.value))
            val db = FirebaseFirestore.getInstance()
            val map = hashMapOf(uid to etvContent,
                                "chat-$roomNumber" to etvContent)

            db.collection("chat").document(roomNumber)
                    .set(map as Map<String, Any>, SetOptions.merge())
           etvContent.value = ""
        }
    }

}