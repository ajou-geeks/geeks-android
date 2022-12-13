package com.geeks.group.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.geeks.R
import com.geeks.model.DeliveryModel
import com.geeks.model.RoommateModel

class RoommateListAdapter internal constructor(var roommateList: List<RoommateModel>)
    : RecyclerView.Adapter<RoommateListAdapter.ListViewHolder>() {


    inner class ListViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bind(_list: RoommateModel) {
            val imageView: ImageView = itemView.findViewById<ImageView>(R.id.thumbnail)
            if (_list.profileImage != null) {
                val newUrl = _list.profileImage
                Glide.with(itemView).load(newUrl).placeholder(R.drawable.ic_launcher_foreground)
                    .into(imageView)
            }
            itemView.findViewById<ImageView>(R.id.thumbnail).clipToOutline = true

            itemView.findViewById<TextView>(R.id.name).text = _list.nickname
            itemView.findViewById<TextView>(R.id.score).text = _list.score.toString()

            itemView.findViewById<TextView>(R.id.rank).text = position.toString()

        }
    }

    override fun getItemCount(): Int = roommateList.size

    override fun getItemViewType(position: Int): Int {
        return position
    }

    // ViewHolder를 생성해 반환한다.
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ListViewHolder {
        return ListViewHolder(
            // 새로운 뷰를 생성해 뷰홀더에 인자로 넣어준다.
            LayoutInflater.from(parent.context).inflate(R.layout.list_item_roommate, parent, false)
        )
    }

    // 반환된 ViewHolder에 데이터를 연결한다.
    override fun onBindViewHolder(holder: ListViewHolder, position: Int) {
        holder.bind(roommateList[position])

        holder.itemView.setOnClickListener {
            itemClickListner.onClick(it, position)
        }

        /*val layoutParams = holder.itemView.layoutParams
        layoutParams.height = 600
        holder.itemView.requestLayout()*/
    }

    interface ItemClickListener {
        fun onClick(view: View, position: Int)
    }

    //를릭 리스너
    private lateinit var itemClickListner: ItemClickListener

    fun setItemClickListener(itemClickListener: ItemClickListener) {
        this.itemClickListner = itemClickListener
    }
}
