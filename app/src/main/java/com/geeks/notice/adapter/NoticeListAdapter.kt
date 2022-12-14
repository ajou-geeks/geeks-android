package com.geeks.notice.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.geeks.R
import com.geeks.model.NoticeModel

class NoticeListAdapter internal constructor(var noticeList: List<NoticeModel>)
    : RecyclerView.Adapter<NoticeListAdapter.ListViewHolder>() {


    inner class ListViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bind(_list: NoticeModel) {
            val imageView: ImageView = itemView.findViewById<ImageView>(R.id.thumbnail)

            itemView.findViewById<TextView>(R.id.title).text = _list.title
            itemView.findViewById<TextView>(R.id.`object`).text=_list._object
            itemView.findViewById<TextView>(R.id.content).text=_list.content
            itemView.findViewById<TextView>(R.id.time).text=_list.createdAt
                .substring(0 until 10).replace("-",". ")

        }
    }
    override fun getItemCount(): Int = noticeList.size

    override fun getItemViewType(position: Int): Int {
        return position
    }

    // ViewHolder를 생성해 반환한다.
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ListViewHolder {
        return ListViewHolder(
            // 새로운 뷰를 생성해 뷰홀더에 인자로 넣어준다.
            LayoutInflater.from(parent.context).inflate(R.layout.list_item_notice, parent, false)
        )
    }

    // 반환된 ViewHolder에 데이터를 연결한다.
    override fun onBindViewHolder(holder: ListViewHolder, position: Int) {
        holder.bind(noticeList[position])

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