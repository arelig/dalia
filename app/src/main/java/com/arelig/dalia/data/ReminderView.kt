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
import kotlinx.android.synthetic.main.layout_item_reminder.view.*

class ReminderView(val id: String?, private var myReminder: Reminder?) :
    AbstractFlexibleItem<ReminderView.ReminderViewHolder>(),
    IFlexible<ReminderView.ReminderViewHolder> {

    class ReminderViewHolder(view: View?, adapter: FlexibleAdapter<IFlexible<*>?>?) :
        FlexibleViewHolder(view, adapter) {
        val imageView: ImageView = itemView.reminder_picture_view
        val textViewName: TextView = itemView.reminder_name_view
        val textViewWateringFreq: TextView = itemView.reminder_watering_freq_view
        val textViewLastWatered: TextView = itemView.reminder_last_watered_view
    }

    override fun equals(other: Any?): Boolean {
        return myReminder == other
    }

    override fun getLayoutRes(): Int {
        return R.layout.layout_item_reminder
    }

    override fun createViewHolder(
        view: View?,
        adapter: FlexibleAdapter<IFlexible<*>?>?
    ): ReminderViewHolder {
        return ReminderViewHolder(view, adapter)

    }

    override fun hashCode(): Int {
        return myReminder.hashCode()
    }

    override fun bindViewHolder(
        adapter: FlexibleAdapter<IFlexible<RecyclerView.ViewHolder>>?,
        holder: ReminderViewHolder?,
        position: Int,
        payloads: MutableList<Any>?
    ) {
        holder?.textViewName?.text = myReminder?.name
        holder?.textViewWateringFreq?.text = "Water every " + myReminder?.wateringFreq + " days"
        holder?.textViewLastWatered?.text = "Last watered: " + myReminder?.lastWatered

        when (myReminder?.category.toString()) {
            "Flowery" ->
                holder?.imageView?.setImageResource(R.drawable.prof_flowery_1)
            "Foliage" ->
                holder?.imageView?.setImageResource(R.drawable.prof_foliage_1)
            "Succulent" ->
                holder?.imageView?.setImageResource(R.drawable.prof_succulent_1)
            "Cacti" ->
                holder?.imageView?.setImageResource(R.drawable.prof_cacti_1)
        }
    }


}