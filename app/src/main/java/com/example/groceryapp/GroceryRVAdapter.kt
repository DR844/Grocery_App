package com.example.groceryapp

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.grocery_rv_item.view.*


class GroceryRVAdapter(
    var list: List<GroceryItems>,
    val groceryItemClickListener: GroceryItemClickListener
) :
    RecyclerView.Adapter<GroceryRVAdapter.MyViewHolder>() {

    inner class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val nameTv = itemView.tvItemName
        val quantityTv = itemView.tvQuantity
        val rateTv = itemView.tvRate
        val totalAmountTv = itemView.tvTotalAmount
        val headingTv = itemView.tvHeading
        val deleteIv = itemView.ibDelete
    }

    override fun getItemCount(): Int {
        return list.size
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val view =
            LayoutInflater.from(parent.context).inflate(R.layout.grocery_rv_item, parent, false)
        return MyViewHolder(view)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        holder.nameTv.text = list[position].itemName
        holder.quantityTv.text = list[position].itemQuantity.toString()
        holder.rateTv.text = "Rs. " + list[position].itemPrice.toString()
        val itemTotal: Int = list[position].itemPrice * list[position].itemQuantity
        holder.totalAmountTv.text = "Rs. " + itemTotal
        holder.deleteIv.setOnClickListener {
            groceryItemClickListener.onItemClick(list[position])
        }


    }


    interface GroceryItemClickListener {
        fun onItemClick(groceryItems: GroceryItems)
    }
}
