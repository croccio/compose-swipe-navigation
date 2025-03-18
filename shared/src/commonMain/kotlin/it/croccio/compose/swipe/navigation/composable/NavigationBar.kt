package it.croccio.compose.swipe.navigation.composable

import androidx.compose.runtime.Composable
import androidx.compose.runtime.Stable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import it.croccio.compose.swipe.navigation.business.Navigator
import it.croccio.compose.swipe.navigation.dto.MenuItem

@Stable
class NavigationBarState(
    menuItems: List<MenuItem>,
) {
    var menuItems by mutableStateOf(menuItems)
}

@Composable
fun rememberNavigationBarState(
    menuItems: List<MenuItem> = listOf(),
): NavigationBarState {
    return remember { NavigationBarState(menuItems) }
}

@Composable
expect fun NavigationBar(
    state: NavigationBarState,
    navigator: Navigator,
)