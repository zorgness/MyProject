package com.example.myproject.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.navigation.findNavController
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.myproject.R
import com.example.myproject.databinding.ItemRvCategoryBinding
import com.example.myproject.dataclass.CategoryDto
import com.example.myproject.ui.category.CategoryFragmentDirections
import com.squareup.picasso.Callback
import com.squareup.picasso.Picasso
import java.lang.Exception

class CategoryDiffUtil : DiffUtil.ItemCallback<CategoryDto>() {
    override fun areItemsTheSame(oldItem: CategoryDto, newItem: CategoryDto): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: CategoryDto, newItem: CategoryDto): Boolean {
        return oldItem == newItem
    }

}


class CategoryAdapter(

) :
    ListAdapter<CategoryDto, CategoryAdapter.CategoryViewHolder>(CategoryDiffUtil()) {


    lateinit var context: Context
    private var onItemClick: ((Int)-> Unit)? = null


    fun setOnItemClick(callback: (Int) -> Unit) {
        onItemClick = callback
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): CategoryAdapter.CategoryViewHolder {

        context = parent.context
        return LayoutInflater.from(parent.context).inflate(R.layout.item_rv_category, parent, false)
            .run {
                CategoryViewHolder(this)
            }

    }

    override fun onBindViewHolder(holder: CategoryAdapter.CategoryViewHolder, position: Int) {

        val category: CategoryDto = getItem(position)


        with(holder.binding) {
            tvNameItemRvCategory.text = category.name.uppercase()

            val img = ImageView(context)
            Picasso.get()
                .load(category.urlImage).error(R.drawable.ic_launcher_background)
                .centerInside()
                .resize(300, 300)
                .into(img, object : Callback {
                    override fun onSuccess() {
                        categoryLayout.background = img.drawable
                    }

                    override fun onError(e: Exception?) {
                        //TODO("Not yet implemented")
                    }


                })


            categoryLayout.setOnClickListener {
                onItemClick?.invoke(category.id)
            }


        }
    }


    inner class CategoryViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val binding = ItemRvCategoryBinding.bind(itemView)

    }

}