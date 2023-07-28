package com.icetetik.data.model

data class Option(
    val value: Int = 0,
    val text: String = ""
)

data class OptionResponse(
    val options: ArrayList<Option> = ArrayList()
)
