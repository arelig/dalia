package com.arelig.dalia.datamodel

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.arelig.dalia.R
import kotlinx.android.synthetic.main.layout_item_houseplant.view.*


class ItemPlantAdapter(private val plantList: List<ItemPlant>) :
    RecyclerView.Adapter<ItemPlantAdapter.PlantViewHolder>() {

    class PlantViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val imageView: ImageView = itemView.item_picture
        val textViewName: TextView = itemView.item_name
        val textViewCategory: TextView = itemView.item_category
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PlantViewHolder {
        val itemView = LayoutInflater.from(parent.context)
            .inflate(R.layout.layout_item_houseplant, parent, false)
        return PlantViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: PlantViewHolder, position: Int) {
        val currentItem = plantList[position]
        holder.imageView.setImageResource(R.drawable.plant)
        holder.textViewName.text = currentItem.name
        holder.textViewCategory.text = currentItem.category


    }

    override fun getItemCount() = plantList.size
}