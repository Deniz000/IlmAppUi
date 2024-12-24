package com.example.ilmapp.adapter
import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.ilmapp.R
import com.example.ilmapp.data.model.Lesson

class LessonAdapter(private val lessonList: List<Lesson>) :
    RecyclerView.Adapter<LessonAdapter.LessonViewHolder>() {

    class LessonViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val title: TextView = itemView.findViewById(R.id.tvLessonTitle)
        val time: TextView = itemView.findViewById(R.id.tvLessonTime)
        val people: TextView = itemView.findViewById(R.id.tvLessonPeople)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): LessonViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.lesson_item, parent, false)
        return LessonViewHolder(view)
    }

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: LessonViewHolder, position: Int) {
        val lesson = lessonList[position]
        holder.title.text = lesson.title
        holder.time.text = lesson.time
        holder.people.text = "${lesson.peopleCount} people"
    }

    override fun getItemCount(): Int = lessonList.size
}
