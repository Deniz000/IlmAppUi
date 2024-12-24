package com.example.ilmapp.data.model


data class ButtonGroup(
    val textButton: TextButton,
    val iconButton: IconButton
)

data class IconButton(
    var background: Int,
    var drawableStart: Int,
)

data class TextButton(
    var text: String,
    var textColor: Int
)
