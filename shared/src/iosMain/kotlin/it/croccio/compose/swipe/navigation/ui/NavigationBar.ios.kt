package it.croccio.compose.swipe.navigation.ui

import androidx.compose.foundation.layout.Row
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier

@Composable
actual fun NavigationBar(
    modifier: Modifier,
    title: @Composable (() -> Unit),
    menu: @Composable (() -> Unit),
    onNavigation: (@Composable (() -> Unit), @Composable (() -> Unit)) -> Unit
) {
    Row {

    }
}