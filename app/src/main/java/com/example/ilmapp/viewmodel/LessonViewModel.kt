package com.example.ilmapp.viewmodel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.ilmapp.data.model.Lesson

class LessonViewModel : ViewModel() {

    private val _lessons = MutableLiveData<List<Lesson>>()
    val lessons: LiveData<List<Lesson>> get() = _lessons

    init {
        loadLessons()
    }

    private fun loadLessons() {
        _lessons.value = listOf(
            Lesson("Matematik Dersi", "14.45", 16),
            Lesson("Türkçe Dersi", "16.00", 11),
            Lesson("Fizik Dersi", "18.00", 13),
        )
    }
}
