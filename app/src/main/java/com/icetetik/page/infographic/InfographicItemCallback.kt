package com.icetetik.page.infographic

import com.icetetik.data.model.Infographic

interface InfographicItemCallback {
    fun onItemInfographicClick(infographic: Infographic)
}