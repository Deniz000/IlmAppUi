package com.example.ilmapp.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.recyclerview.widget.RecyclerView
import com.example.ilmapp.R
import com.example.ilmapp.data.model.ButtonGroup

class ButtonAdapter(
    private val items: List<ButtonGroup>,
) : RecyclerView.Adapter<ButtonAdapter.ButtonViewHolder>() {

    inner class ButtonViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val textButton: Button = itemView.findViewById(R.id.btn_resource_name)
        private val iconButton: Button = itemView.findViewById(R.id.btn_resource_icon)

        fun bind(position: Int) {
            val buttonGroup = items[position]
            val textData = buttonGroup.textButton
            val iconData = buttonGroup.iconButton

            textButton.text = textData.text
            textButton.setTextColor(itemView.context.getColor(textData.textColor))

            iconButton.setBackgroundResource(iconData.background)
            iconButton.setCompoundDrawablesWithIntrinsicBounds(iconData.drawableStart, 0, 0, 0)

        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ButtonViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.button_recycler_layout, parent, false)
        return ButtonViewHolder(view)
    }

    override fun onBindViewHolder(holder: ButtonViewHolder, position: Int) {
        holder.bind(position)
    }

    override fun getItemCount(): Int = items.size
}
