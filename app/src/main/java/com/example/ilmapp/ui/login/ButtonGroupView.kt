package com.example.ilmapp.ui.login

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.Button
import android.widget.LinearLayout
import com.example.ilmapp.R

class ButtonGroupView @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : LinearLayout(context, attrs, defStyleAttr) {
    init {
        LayoutInflater.from(context).inflate(R.layout.social_button_group, this, true)
    }

    val googleButton: Button
        get() = findViewById(R.id.btn_enter_with_google)

    val facebookButton: Button
        get() = findViewById(R.id.btn_enter_with_facebook)
}
