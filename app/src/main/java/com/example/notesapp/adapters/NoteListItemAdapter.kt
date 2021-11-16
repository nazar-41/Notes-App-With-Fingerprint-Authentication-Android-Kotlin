package com.example.notesapp.adapters

import android.graphics.BitmapFactory
import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import com.example.notesapp.R
import com.example.notesapp.models.NotesModel

class NoteListItemAdapter() :
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private var list = ArrayList<NotesModel>()
    private var listener: OnItemClickListener? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return MyViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.item_note, parent, false)
        )
    }

    fun setData(noteList: List<NotesModel>) {
        list = noteList as ArrayList<NotesModel>
    }

    fun setOnClickListener(listener1: OnItemClickListener) {
        listener = listener1
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val model = list[position]
        if (holder is MyViewHolder) {
            holder.noteCreatedDate.text = model.date_created
            holder.noteCreatedTime.text = model.time_created

            if (model.note_text!!.length > 100) {
                holder.noteDesc.text = "${model.note_text!!.subSequence(0, 99)} ..."
            } else {
                holder.noteDesc.text = model.note_text
            }

            if (model.title!!.length > 40) {
                holder.noteTitle.text = "${model.title!!.subSequence(0, 39)} ..."
            } else {
                holder.noteTitle.text = model.title
            }

            if (model.color != null) {
                holder.noteCardView.setCardBackgroundColor(Color.parseColor(model.color))
            } else {
                holder.noteCardView.setCardBackgroundColor(Color.parseColor(R.color.white.toString()))
            }

            if (model.imagePath != null) {
                holder.noteImage.setImageBitmap(BitmapFactory.decodeFile(model.imagePath))
                holder.noteImage.visibility = View.VISIBLE
            } else {
                holder.noteImage.visibility = View.GONE
            }

            holder.noteCardView.setOnClickListener {
                listener!!.onClicked(model.id!!)
            }
        }
    }

    override fun getItemCount(): Int {
        return list.size
    }

    class MyViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val noteCardView: CardView = view.findViewById(R.id.cv_note)
        val noteTitle: TextView = view.findViewById(R.id.tv_item_title)
        val noteDesc: TextView = view.findViewById(R.id.tv_item_desc)
        val noteCreatedDate: TextView = view.findViewById(R.id.tv_item_created_date)
        val noteCreatedTime: TextView = view.findViewById(R.id.tv_item_created_time)
        val noteImage: ImageView = view.findViewById(R.id.img_item_image)
    }

    interface OnItemClickListener {
        fun onClicked(noteId: Int)
    }
}