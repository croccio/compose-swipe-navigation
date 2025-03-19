package it.croccio.compose.swipe.navigation.composable

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.asPaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBars
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import it.croccio.compose.swipe.navigation.business.INavigator
import it.croccio.compose.swipe.navigation.business.OffsetPageNavigator

@Composable
actual fun NavigationBar(
    state: NavigationBarState,
    navigator: INavigator,
) {

    val statusBarHeight = WindowInsets.statusBars.asPaddingValues().calculateTopPadding()
    val navBarHeight = 44.dp
    val totalHeight = navBarHeight + statusBarHeight

    val animationOffset by remember {
        (navigator as? OffsetPageNavigator)?.screenOffset ?: mutableStateOf(Pair(0, 0f))
    }

    val menuItems = remember { state.menuItems }

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .height(totalHeight)
            .background(MaterialTheme.colors.primary)
            .padding(top = statusBarHeight),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween,
    ) {

        BackButton(
            modifier = Modifier
                .weight(0.5f)
                .wrapContentWidth(),
            navigator = navigator,
            animationOffset = animationOffset,
        )

        NavigationBarTitle(
            modifier = Modifier
                .weight(1f),
            navigator = navigator,
            animationOffset = animationOffset,
        )

        Box(
            modifier = Modifier
                .weight(0.5f),
            contentAlignment = Alignment.CenterEnd
        ) {
            NavigationMenu(
                modifier = Modifier
                    .wrapContentWidth(),
                menuItems = menuItems,
            )
        }
    }
}