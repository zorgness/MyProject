package com.example.myproject.ui.activities_list

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.myproject.R
import com.example.myproject.databinding.ItemRvActivityEventBinding
import com.example.myproject.dto.activities.ActivityEventDto
import com.example.myproject.utils.CategoryBackground
import com.example.myproject.utils.dateFormatter
import com.example.myproject.utils.myPicassoFun


class ActivityEventDiffUtil : DiffUtil.ItemCallback<ActivityEventDto>() {
    override fun areItemsTheSame(oldItem: ActivityEventDto, newItem: ActivityEventDto): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: ActivityEventDto, newItem: ActivityEventDto): Boolean {
        return oldItem == newItem
    }

}


class ActivitiesByCategoryAdapter():  ListAdapter<ActivityEventDto, ActivitiesByCategoryAdapter.ActivityEventViewHolder>(
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
            tvTitleActivity.text = activityEvent.title
            tvLocation.text = activityEvent.location
            tvDate.text = dateFormatter(activityEvent.startAt)
            myPicassoFun(activityEvent.creator.imageUrl, civUserImage)

            layoutActivityEvent
                .setBackgroundResource(
                    CategoryBackground
                        .getDrawableResource(activityEvent.category.id % 6)
                        ?: 0
                )

            cardActivityEvent.setOnClickListener {
                onItemClick?.invoke(activityEvent)
            }
        }

    }

    inner class ActivityEventViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val binding = ItemRvActivityEventBinding.bind(itemView)

    }
}

