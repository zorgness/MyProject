package com.example.myproject.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.myproject.R
import com.example.myproject.databinding.ItemRvCategoryBinding
import com.example.myproject.dataclass.CategoryDto
import com.squareup.picasso.Picasso
import de.hdodenhof.circleimageview.CircleImageView


class CategoryAdapter() :
    RecyclerView.Adapter<CategoryAdapter.CategoryViewHolder>() {


    private var categories: MutableList<CategoryDto> = mutableListOf()

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): CategoryAdapter.CategoryViewHolder {

        return LayoutInflater.from(parent.context).inflate(R.layout.item_rv_category, parent, false).run {
            CategoryViewHolder( this)
        }

    }

    override fun onBindViewHolder(holder: CategoryAdapter.CategoryViewHolder, position: Int) {

        categories[position].let { category->

            with(holder.binding) {
                tvNameItemRvCategory.text = category.name
                Picasso.get()
                    .load(category.urlImage).error(R.drawable.ic_launcher_background)
                    .resize(300, 300)
                    .into(civImageItemRvCategory)

                /*  //DELETE COUNTRY
                  ivTrash.setOnClickListener {
                      onDeleteItemCallback?.invoke(country)
                  }

                  //UPDATE COUNTRY
                  tvName.setOnClickListener {
                      onUpdateItemCallback?.invoke(country)
                  }*/
            }
        }
    }

    fun setCategories(list: List<CategoryDto>) {
        categories.clear()
        categories.addAll(list)
        notifyDataSetChanged()
    }

    override fun getItemCount(): Int {
        return categories.size
    }

    inner class CategoryViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val binding = ItemRvCategoryBinding.bind(itemView)

    }

}