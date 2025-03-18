package it.croccio.compose.swipe.navigation.composable

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.padding
import androidx.compose.material.DropdownMenu
import androidx.compose.material.DropdownMenuItem
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.unit.dp
import it.croccio.compose.swipe.navigation.dto.MenuItem
import it.croccio.compose.swipe.navigation.expect.menuIcon
import org.jetbrains.compose.resources.painterResource

@Composable
fun NavigationMenu(
    modifier: Modifier = Modifier,
    menuItems: List<MenuItem>,
) {
    if (menuItems.isEmpty()) return

    val isMenuExpanded = remember { mutableStateOf(false) }

    Row(
        modifier = modifier
            .fillMaxHeight()
            .clickable { isMenuExpanded.value = !isMenuExpanded.value },
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Image(
            modifier = Modifier
                .padding(8.dp)
                .fillMaxHeight(),
            painter = painterResource(menuIcon),
            colorFilter = ColorFilter.tint(MaterialTheme.colors.onPrimary),
            contentDescription = "menu"
        )
        DropdownMenu(
            expanded = isMenuExpanded.value,
            onDismissRequest = { isMenuExpanded.value = false },
        ) {
            menuItems.forEach { menuItem ->
                DropdownMenuItem(
                    onClick = { isMenuExpanded.value = false }
                ) {
                    menuItem.render()
                }
            }
        }
    }
}