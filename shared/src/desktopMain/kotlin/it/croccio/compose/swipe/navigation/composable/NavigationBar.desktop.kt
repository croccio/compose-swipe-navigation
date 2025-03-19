package it.croccio.compose.swipe.navigation.composable

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import it.croccio.compose.swipe.navigation.business.Navigator
import kotlinx.coroutines.launch

@Composable
actual fun NavigationBar(
    state: NavigationBarState,
    navigator: Navigator,
) {

    val backStack by derivedStateOf { navigator.backStack }
    val listState = rememberLazyListState()
    val coroutineScope = rememberCoroutineScope()
    val menuItems = remember { state.menuItems }

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .background(MaterialTheme.colors.primary)
            .height(48.dp)
            .padding(8.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
    ) {

        LazyRow(
            modifier = Modifier
                .fillMaxHeight()
                .weight(1f),
            state = listState,
            verticalAlignment = Alignment.CenterVertically,
        ) {
            items(backStack) { route ->
                BreadcrumbItem(
                    route = route,
                    isFirst = backStack.indexOf(route) == 0,
                    isLast = backStack.indexOf(route) == backStack.lastIndex,
                ) {
                    navigator.popBackStack(route)
                }
            }
            coroutineScope.launch {
                listState.animateScrollToItem(backStack.lastIndex)
            }
        }

        NavigationMenu(
            menuItems = menuItems
        )

    }

}