package com.example.myproject.utils

import com.example.myproject.R

enum class CategoryBackground(
    val categoryIndex: Int,
    val drawableResource: Int
) {
    CATEGORY_RANDO(0,  R.drawable.card_rando),
    CATEGORY_BILLARD(1,  R.drawable.card_billard),
    CATEGORY_PETANQUE(2,  R.drawable.card_petanque),
    CATEGORY_ESCALADE(3,  R.drawable.card_escalade),
    CATEGORY_VTT(4,  R.drawable.card_vtt),
    CATEGORY_BABY_FOOT(5,  R.drawable.card_baby_foot);


    companion object {

        fun getDrawableResource(categoryId: Int): Int? {
            for (category in values()) {
                if (category.categoryIndex == categoryId) {
                    return category.drawableResource
                }
            }
            return null
        }
    }
}

