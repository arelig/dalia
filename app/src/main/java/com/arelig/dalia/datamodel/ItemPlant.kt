package com.arelig.dalia.datamodel

import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.arelig.dalia.R
import eu.davidea.flexibleadapter.FlexibleAdapter
import eu.davidea.flexibleadapter.items.AbstractFlexibleItem
import eu.davidea.flexibleadapter.items.IFlexible
import eu.davidea.viewholders.FlexibleViewHolder
import kotlinx.android.synthetic.main.layout_item_houseplant.view.*


class ItemPlant(var id: String, var name: String, var category: String) :
    AbstractFlexibleItem<ItemPlant.ItemPlantViewHolder>() {

    override fun equals(other: Any?): Boolean {
        if (other is ItemPlant) {
            val inItem: ItemPlant = other
            return this.id == inItem.id
        }
        return false
    }

    override fun createViewHolder(
        view: View?,
        adapter: FlexibleAdapter<IFlexible<*>?>?
    ): ItemPlantViewHolder? {
        return ItemPlantViewHolder(view, adapter)
    }

    override fun getLayoutRes(): Int {
        return R.layout.layout_item_houseplant
    }

    override fun hashCode(): Int {
        return id.hashCode()
    }

    override fun bindViewHolder(
        adapter: FlexibleAdapter<IFlexible<RecyclerView.ViewHolder>>?,
        holder: ItemPlantViewHolder?,
        position: Int,
        payload: MutableList<Any>?
    ) {
        //how do i populate the list from the database?
        holder?.imageView?.setImageResource(R.drawable.plant)
        holder?.textViewName?.text = "Im a name"
        holder?.textViewCategory?.text = "Im a category"
    }

//    override fun bindViewHolder(
//        adapter: FlexibleAdapter<IFlexible<RecyclerView.ViewHolder>>?,
//        holder: ItemPlantViewHolder?,
//        position: Int,
//        payload: MutableList<Any>?
//    ) {
//        //here is the problem
//        val currentItem = payload?.get(position) as ItemPlantViewHolder
//        holder?.imageView?.setImageResource(R.drawable.plant)
//        holder?.textViewName?.text = currentItem.textViewName.toString()
//        holder?.textViewCategory?.text = currentItem.textViewCategory.toString()
//    }

    class ItemPlantViewHolder(view: View?, adapter: FlexibleAdapter<*>?) :
        FlexibleViewHolder(view, adapter) {
        val imageView: ImageView = itemView.item_picture
        val textViewCategory: TextView = itemView.item_category
        val textViewName: TextView = itemView.item_name
    }
}



