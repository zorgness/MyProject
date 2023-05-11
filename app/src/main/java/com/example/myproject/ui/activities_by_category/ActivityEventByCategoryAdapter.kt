package com.example.myproject.ui.activities_by_category

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.myproject.R
import com.example.myproject.databinding.ItemRvActivityEventBinding
import com.example.myproject.dataclass.ActivityEventDto



class ActivityEventDiffUtil : DiffUtil.ItemCallback<ActivityEventDto>() {
    override fun areItemsTheSame(oldItem: ActivityEventDto, newItem: ActivityEventDto): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: ActivityEventDto, newItem: ActivityEventDto): Boolean {
        return oldItem == newItem
    }

}

class ActivityEventByCategoryAdapter():  ListAdapter<ActivityEventDto, ActivityEventByCategoryAdapter.ActivityEventViewHolder>(
    ActivityEventDiffUtil() ) {


    lateinit var context: Context
    private var onItemClick: ((ActivityEventDto)-> Unit)? = null

    fun setOnItemClick(callback: (ActivityEventDto) -> Unit) {
        onItemClick = callback
    }
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ActivityEventViewHolder {

        context = parent.context
        return LayoutInflater.from(parent.context).inflate(R.layout.item_rv_activity_event, parent, false)
            .run {
                ActivityEventViewHolder(this)
            }

    }

    override fun onBindViewHolder(holder: ActivityEventViewHolder, position: Int) {
        val activityEvent: ActivityEventDto = getItem(position)

        with(holder.binding) {
            tvNameItemRvActivityEvent.text = activityEvent.title

            cardActivityEvent.setOnClickListener {
                onItemClick?.invoke(activityEvent)
            }
        }





    }

    inner class ActivityEventViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val binding = ItemRvActivityEventBinding.bind(itemView)

    }
}

