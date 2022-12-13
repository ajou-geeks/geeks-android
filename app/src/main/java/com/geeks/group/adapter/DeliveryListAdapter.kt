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

class DeliveryListAdapter internal constructor(var deliveryList: List<DeliveryModel>)
    : RecyclerView.Adapter<DeliveryListAdapter.ListViewHolder>() {


    inner class ListViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bind(_list: DeliveryModel) {
            val imageView: ImageView = itemView.findViewById<ImageView>(R.id.thumbnail)
            if(_list.thumbnailUrl!=null) {
                val newUrl=_list.thumbnailUrl
                Glide.with(itemView).load(newUrl).placeholder(R.drawable.ic_launcher_foreground)
                    .into(imageView)
            }
            itemView.findViewById<ImageView>(R.id.thumbnail).clipToOutline=true

            itemView.findViewById<TextView>(R.id.title).text = _list.name
            itemView.findViewById<TextView>(R.id.destination).text=_list.destination

            val expTime=_list.endTime.substring(11 until 16)

            itemView.findViewById<TextView>(R.id.price).text = "주문 예정 시간: $expTime"

        }
    }
    override fun getItemCount(): Int = deliveryList.size

    override fun getItemViewType(position: Int): Int {
        return position
    }

    // ViewHolder를 생성해 반환한다.
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ListViewHolder {
        return ListViewHolder(
            // 새로운 뷰를 생성해 뷰홀더에 인자로 넣어준다.
            LayoutInflater.from(parent.context).inflate(R.layout.list_item_product, parent, false)
        )
    }

    // 반환된 ViewHolder에 데이터를 연결한다.
    override fun onBindViewHolder(holder: ListViewHolder, position: Int) {
        holder.bind(deliveryList[position])

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