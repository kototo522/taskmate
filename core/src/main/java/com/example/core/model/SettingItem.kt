package com.example.core.model

data class SettingItem(
    val title: String,
    val subTitle: String = "",
    val icon: Int,
    val onClick: () -> Unit = {},
)
