package com.example.village.util

import android.app.Dialog
import android.content.Context
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import com.example.village.R

class SecssionDialog(context : Context, mainmessage:String, message:String, logoutOncilck: logoutOncilck): Dialog(context) {
    private var mainmessage : String
    private var message : String

    private var logoutOncilck : logoutOncilck

    init{
        this.mainmessage = mainmessage
        this.message = message
        this.logoutOncilck = logoutOncilck
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.fragment_dialog_warning)

        findViewById<TextView>(R.id.dialogTv1).text = mainmessage
        findViewById<TextView>(R.id.dialogTv2).text = message

        findViewById<TextView>(R.id.dialogTv3).setOnClickListener {
            this.logoutOncilck.onClickdialog()
        }
    }
}