package com.example.myproject.utils

import com.example.myproject.R


sealed class CategoryBackground(val categoryIndex: Int, val drawableResource: Int) {
    object RANDO : CategoryBackground(0, R.drawable.card_rando)
    object BILLARD : CategoryBackground(1, R.drawable.card_billard)
    object PETANQUE : CategoryBackground(2, R.drawable.card_petanque)
    object ESCALADE : CategoryBackground(3, R.drawable.card_escalade)
    object VTT : CategoryBackground(4, R.drawable.card_vtt)
    object BABYFOOT : CategoryBackground(5, R.drawable.card_baby_foot)

    companion object {
        fun getDrawableResource(categoryId: Int): Int? {
            return when (categoryId) {
                RANDO.categoryIndex -> RANDO.drawableResource
                BILLARD.categoryIndex -> BILLARD.drawableResource
                PETANQUE.categoryIndex -> PETANQUE.drawableResource
                ESCALADE.categoryIndex -> ESCALADE.drawableResource
                VTT.categoryIndex -> VTT.drawableResource
                BABYFOOT.categoryIndex -> BABYFOOT.drawableResource
                else -> null
            }
        }
    }
}



