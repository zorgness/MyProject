package com.example.myproject.ui.validation_list

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.myproject.R
import com.example.myproject.databinding.ItemValidationBinding
import com.example.myproject.dto.booking.BookingFullDto
import com.example.myproject.utils.myPicassoFun


class ValidationListDiffUtil : DiffUtil.ItemCallback<BookingFullDto>() {
    override fun areItemsTheSame(oldItem: BookingFullDto, newItem: BookingFullDto): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: BookingFullDto, newItem: BookingFullDto): Boolean {
        return oldItem == newItem
    }

}


class ValidationListAdapter():  ListAdapter<BookingFullDto, ValidationListAdapter.ValidationListViewHolder>(
    ValidationListDiffUtil() ) {


    lateinit var context: Context
    private var onItemClick: ((BookingFullDto)-> Unit)? = null

    fun setOnItemClick(callback: (BookingFullDto) -> Unit) {
        onItemClick = callback
    }
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ValidationListViewHolder {

        context = parent.context
        return LayoutInflater.from(parent.context).inflate(R.layout.item_validation, parent, false)
            .run {
                ValidationListViewHolder(this)
            }

    }

    override fun onBindViewHolder(holder: ValidationListViewHolder, position: Int) {
        val booking: BookingFullDto = getItem(position)

        with(holder.binding) {

             myPicassoFun(booking.userAccount.imageUrl, civUserImage)

             tvUsername.text = booking.userAccount.username

             btnValidate.setOnClickListener {
                onItemClick?.invoke(booking)
             }

            btnReject.setOnClickListener {
                onItemClick?.invoke(booking)
            }
        }

    }

    inner class ValidationListViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val binding = ItemValidationBinding.bind(itemView)

    }
}
