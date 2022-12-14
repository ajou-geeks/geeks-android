package com.geeks.note.adapter

import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintSet.Layout
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.geeks.R
import com.geeks.model.NoteRoomModel

class NoteRoomAdapter internal constructor(var noteList: List<NoteRoomModel>)
    : RecyclerView.Adapter<NoteRoomAdapter.ListViewHolder>() {


    inner class ListViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bind(_list: NoteRoomModel) {

            if(_list.owner){
                itemView.findViewById<TextView>(R.id.content).gravity= Gravity.RIGHT
                itemView.findViewById<TextView>(R.id.time).gravity=Gravity.LEFT
                itemView.findViewById<View>(R.id.box).setBackgroundResource(R.color.skyblue)
            }

            itemView.findViewById<TextView>(R.id.content).text = _list.content
            itemView.findViewById<TextView>(R.id.time).text=_list.createdAt
                .substring(0 until 10).replace("-",". ")

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
            LayoutInflater.from(parent.context).inflate(R.layout.list_item_note_room, parent, false)
        )
    }

    // 반환된 ViewHolder에 데이터를 연결한다.
    override fun onBindViewHolder(holder: ListViewHolder, position: Int) {
        holder.bind(noteList[position])

        /*holder.itemView.setOnClickListener{
            itemClickListner.onClick(it,position)
        }*/

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