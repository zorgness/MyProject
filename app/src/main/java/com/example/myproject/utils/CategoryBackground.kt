package com.example.myproject.utils

import com.example.myproject.R

enum class CategoryBackground(
    val categoryId: Int,
    val drawableResource: Int
) {
    CATEGORY_BILLARD(1,  R.drawable.card_billard),
    CATEGORY_PETANQUE(2,  R.drawable.card_petanque),
    CATEGORY_ESCALADE(3,  R.drawable.card_escalade),
    CATEGORY_VTT(4,  R.drawable.card_vtt),
    CATEGORY_BABY_FOOT(5,  R.drawable.card_baby_foot),
    CATEGORY_RANDO(6,  R.drawable.card_rando);

    companion object {

        fun getDrawableResource(categoryId: Int): Int? {
            for (category in values()) {
                if (category.categoryId == categoryId) {
                    return category.drawableResource
                }
            }
            return null
        }
    }
}

