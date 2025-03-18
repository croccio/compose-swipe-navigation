package it.croccio.compose.swipe.navigation.dto

import androidx.compose.runtime.Composable

data class MenuItem(
    val render: @Composable () -> Unit,
)