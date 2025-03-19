package it.croccio.compose.swipe.navigation.composable

import androidx.compose.runtime.Composable
import it.croccio.compose.swipe.navigation.business.INavigator

@Composable
actual fun NavigationBar(
    state: NavigationBarState,
    navigator: INavigator,
){
    // Kept empty intentionally, no need on web
}