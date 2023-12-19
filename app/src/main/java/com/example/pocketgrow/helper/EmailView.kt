package com.example.pocketgrow.helper

import android.content.Context
import android.text.Editable
import android.text.TextWatcher
import android.util.AttributeSet
import androidx.appcompat.widget.AppCompatEditText
import com.example.pocketgrow.R

class EmailView : AppCompatEditText {

    constructor(context: Context) : super(context) {
        init()
    }

    constructor(context: Context, attrs: AttributeSet) : super(context, attrs) {
        init()
    }

    constructor(context: Context, attrs: AttributeSet, defStyleAttr: Int) : super(context, attrs, defStyleAttr) {
        init()
    }

    private fun init() {
        addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                val email = p0.toString()
                if (email.contains("@")) {
                    // Email valid (terdapat @), tidak menampilkan pesan error
                    error = null
                } else if (email.isNotEmpty()) {
                    // Email tidak valid (tidak terdapat @), menampilkan pesan error
                    error = context.getString(R.string.emailinvalid)
                }
            }

            override fun afterTextChanged(p0: Editable?) {
            }
        })
    }
}