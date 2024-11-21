package com.example.yourapp.views

import android.annotation.SuppressLint
import android.content.Context
import android.text.InputType
import android.util.AttributeSet
import android.view.MotionEvent
import android.widget.EditText
import com.example.ilmapp.R

class PasswordEditText(context: Context, attrs: AttributeSet?) : androidx.appcompat.widget.AppCompatEditText(context, attrs) {

    init {
        setPasswordVisibility(this, false)
    }

    @SuppressLint("ClickableViewAccessibility")
     fun setPasswordVisibility(editTextPassword: EditText, isVisible:Boolean) {
         var isPasswordVisible = isVisible
        editTextPassword.setOnTouchListener { _, event ->
            if (event.action == MotionEvent.ACTION_UP) {
                val drawableEnd = editTextPassword.compoundDrawables[2] // Sağdaki Drawable
                if (drawableEnd != null) {
                    val drawableWidth = drawableEnd.bounds.width()
                    val touchX = editTextPassword.width - editTextPassword.paddingEnd - drawableWidth
                    if (event.rawX >= touchX) { // Sağdaki Drawable'a tıklandı
                        isPasswordVisible = !isPasswordVisible
                        editTextPassword.inputType = if (isPasswordVisible) {
                            InputType.TYPE_CLASS_TEXT
                        } else {
                            InputType.TYPE_CLASS_TEXT or InputType.TYPE_TEXT_VARIATION_PASSWORD
                        }
                        editTextPassword.setCompoundDrawablesWithIntrinsicBounds(
                            0, 0,
                            if (isPasswordVisible) R.drawable.hide_password else R.drawable.show_password,
                            0
                        )
                        editTextPassword.setSelection(editTextPassword.text?.length ?: 0)
                        return@setOnTouchListener true
                    }
                }
            }
            false
        }
    }
}
