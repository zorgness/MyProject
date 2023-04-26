package com.example.myproject.adapter

import android.content.Context
import android.graphics.Bitmap
import android.graphics.drawable.BitmapDrawable
import android.graphics.drawable.Drawable
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.navigation.Navigation
import androidx.navigation.findNavController
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.myproject.R
import com.example.myproject.databinding.ItemRvCategoryBinding
import com.example.myproject.dataclass.CategoryDto
import com.example.myproject.fragment.CategoryFragment
import com.example.myproject.fragment.CategoryFragmentDirections
import com.squareup.picasso.Callback
import com.squareup.picasso.Picasso
import de.hdodenhof.circleimageview.CircleImageView
import java.lang.Exception
import  com.squareup.picasso.Target

class CategoryDiffUtil : DiffUtil.ItemCallback<CategoryDto>() {
    override fun areItemsTheSame(oldItem: CategoryDto, newItem: CategoryDto): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: CategoryDto, newItem: CategoryDto): Boolean {
        return oldItem == newItem
    }

}


class CategoryAdapter() :
    ListAdapter<CategoryDto, CategoryAdapter.CategoryViewHolder>(CategoryDiffUtil()) {


    lateinit var context: Context

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


            categoryLayout.setOnClickListener {view->
                val navDir = CategoryFragmentDirections.actionCategoryFragmentToActivityByCategoryFragment(category.id)
                view.findNavController().navigate(navDir)
            }


        }
    }


    inner class CategoryViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val binding = ItemRvCategoryBinding.bind(itemView)

    }

}