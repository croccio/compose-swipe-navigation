package it.croccio.compose.swipe.navigation.composable

import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import it.croccio.compose.swipe.navigation.business.INavigator
import it.croccio.compose.swipe.navigation.business.Navigator
import kotlinx.browser.document
import kotlinx.browser.window

@Composable
actual fun NavigationBar(
    state: NavigationBarState,
    navigator: INavigator,
) {

    val route by derivedStateOf { (navigator as? Navigator)?.backStack?.lastOrNull() }

    document.title = route?.title ?: ""

    window.onpopstate = { event ->
        val lastHistoryLength = (navigator as? Navigator)?.windowLastHistoryLength ?: 0
        if (window.history.length > lastHistoryLength) {
            (navigator as? Navigator)?.forward()
        } else {
            navigator.popBackStack()
        }

        (navigator as? Navigator)?.windowLastHistoryLength = window.history.length

    }

}