package com.example.ilmapp.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.ilmapp.R
import com.example.ilmapp.data.model.ButtonGroup
import com.example.ilmapp.data.model.IconButton
import com.example.ilmapp.data.model.TextButton

class ButtonViewModel : ViewModel() {
    private val _buttonGroups = MutableLiveData<List<ButtonGroup>>()
    val buttonGroups: LiveData<List<ButtonGroup>> get() = _buttonGroups

    init {
        loadButtonGroups()
    }

    private fun loadButtonGroups() {
        _buttonGroups.value = listOf(
            ButtonGroup(
                textButton = TextButton(
                    text = "Resources",
                    textColor = R.color.orange
                ),
                iconButton = IconButton(
                    background = R.drawable.icon_button_pencil,
                    drawableStart = R.drawable.pencil_icon
                )
            ),
            ButtonGroup(
                textButton = TextButton(
                    text = "Courses",
                    textColor = R.color.dark_green
                ),
                iconButton = IconButton(
                    background = R.drawable.icon_button_resource,
                    drawableStart = R.drawable.resources_icon
                )
            ), ButtonGroup(
                textButton = TextButton(
                    text = "People",
                    textColor = R.color.blue
                ),
                iconButton = IconButton(
                    background = R.drawable.icon_button_people,
                    drawableStart = R.drawable.people_icon
                )
            ),
            ButtonGroup(
                textButton = TextButton(
                    text = "Calendar",
                    textColor = R.color.purple
                ),
                iconButton = IconButton(
                    background = R.drawable.icon_button_calendar,
                    drawableStart = R.drawable.calendar_icon
                )
            ),
            ButtonGroup(
                textButton = TextButton(
                    text = "Statistic",
                    textColor = R.color.red
                ),
                iconButton = IconButton(
                    background = R.drawable.icon_button_statistic,
                    drawableStart = R.drawable.statistic_icon
                )
            ),
        )
    }

    fun updateButtonGroup(index: Int, newTextButton: TextButton, newIconButton: IconButton) {
        _buttonGroups.value = _buttonGroups.value?.toMutableList()?.apply {
            this[index] = ButtonGroup(newTextButton, newIconButton)
        }
    }
}
