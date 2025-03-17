package it.croccio.compose.swipe.navigation.ui

import androidx.compose.foundation.layout.RowScope
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier

@Composable
expect fun NavigationBar(
    modifier: Modifier,
    title: @Composable () -> Unit,
    actions: @Composable RowScope.() -> Unit = {},
    onBackClick: (() -> Unit)? = null,
    onNavigation: ((
        title: @Composable () -> Unit,
        menu: @Composable () -> Unit,
    ) -> Unit)? = null,
)