package com.arelig.dalia.data

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


class PlantView(val id: String?, private var myPlant: Plant?) :
    AbstractFlexibleItem<PlantView.PlantViewHolder>(),
    IFlexible<PlantView.PlantViewHolder> {


    override fun equals(other: Any?): Boolean {
        return myPlant == other
    }

    override fun createViewHolder(
        view: View?,
        adapter: FlexibleAdapter<IFlexible<*>?>?
    ): PlantViewHolder? {
        return PlantViewHolder(view, adapter)
    }

    override fun getLayoutRes(): Int {
        return R.layout.layout_item_houseplant
    }

    override fun hashCode(): Int {
        return myPlant.hashCode()
    }

    override fun bindViewHolder(
        adapter: FlexibleAdapter<IFlexible<RecyclerView.ViewHolder>>?,
        holder: PlantViewHolder?,
        position: Int,
        payload: MutableList<Any>?
    ) {
        holder?.imageView?.setImageResource(R.drawable.plant)
        holder?.textViewName?.text = myPlant?.name
        holder?.textViewCategory?.text = myPlant?.category
    }

    class PlantViewHolder(view: View?, adapter: FlexibleAdapter<IFlexible<*>?>?) :
        FlexibleViewHolder(view, adapter) {
        val imageView: ImageView = itemView.item_picture
        val textViewName: TextView = itemView.item_name
        val textViewCategory: TextView = itemView.item_category

    }


}



