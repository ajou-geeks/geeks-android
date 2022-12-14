package com.geeks.note.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.geeks.R
import com.geeks.group.adapter.ProductListAdapter
import com.geeks.model.NoteListModel
import com.geeks.model.ProductModel

class NoteListAdapter internal constructor(var noteList: List<NoteListModel>)
    : RecyclerView.Adapter<NoteListAdapter.ListViewHolder>() {


    inner class ListViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bind(_list: NoteListModel) {
            val imageView: ImageView = itemView.findViewById<ImageView>(R.id.thumbnail)
            /*if(_list.otherInfo.profileImage!=null) {
                val newUrl=_list.otherInfo.profileImage
                Glide.with(itemView).load(newUrl).placeholder(R.drawable.ic_launcher_foreground)
                    .into(imageView)
            }*/
            itemView.findViewById<ImageView>(R.id.thumbnail).clipToOutline=true

            itemView.findViewById<TextView>(R.id.name).text = _list.otherInfo.nickname
            itemView.findViewById<TextView>(R.id.chat).text=_list.recentNoteContent

        }
    }
    override fun getItemCount(): Int = noteList.size

    override fun getItemViewType(position: Int): Int {
        return position
    }

    // ViewHolder를 생성해 반환한다.
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ListViewHolder {
        return ListViewHolder(
            // 새로운 뷰를 생성해 뷰홀더에 인자로 넣어준다.
            LayoutInflater.from(parent.context).inflate(R.layout.list_item_note, parent, false)
        )
    }

    // 반환된 ViewHolder에 데이터를 연결한다.
    override fun onBindViewHolder(holder: ListViewHolder, position: Int) {
        holder.bind(noteList[position])

        holder.itemView.setOnClickListener{
            itemClickListner.onClick(it,position)
        }

        /*val layoutParams = holder.itemView.layoutParams
        layoutParams.height = 600
        holder.itemView.requestLayout()*/
    }

    interface ItemClickListener{
        fun onClick(view: View, position: Int)
    }
    //를릭 리스너
    private lateinit var itemClickListner: ItemClickListener

    fun setItemClickListener(itemClickListener: ItemClickListener) {
        this.itemClickListner = itemClickListener
    }
}