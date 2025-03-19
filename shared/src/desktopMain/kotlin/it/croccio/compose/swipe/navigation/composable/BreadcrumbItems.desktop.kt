package it.croccio.compose.swipe.navigation.composable

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import io.github.croccio.csnavigation.shared.generated.resources.Res
import io.github.croccio.csnavigation.shared.generated.resources.breadcrumb_separator
import io.github.croccio.csnavigation.shared.generated.resources.home
import it.croccio.compose.swipe.navigation.dto.Route
import org.jetbrains.compose.resources.painterResource

@Composable
fun BreadcrumbItem(
    modifier: Modifier = Modifier,
    route: Route,
    isFirst: Boolean,
    isLast: Boolean,
    onClick: () -> Unit,
) {

    Row(
        modifier = modifier
            .clickable { onClick() },
        verticalAlignment = Alignment.CenterVertically,
    ) {
        when {
            isFirst -> BreadcrumbHome(isFirst = isFirst, isLast = isLast)
            !isFirst && isLast -> BreadcrumbLastItem(title = route.title)
            else -> BreadcrumbCenterItem(title = route.title)
        }
    }

}

@Composable
fun BreadcrumbHome(
    modifier: Modifier = Modifier,
    isFirst: Boolean,
    isLast: Boolean,
) {
    Image(
        modifier = modifier
            .padding(8.dp)
            .size(18.dp),
        painter = painterResource(Res.drawable.home),
        contentDescription = "breadcrumbs home",
        colorFilter = ColorFilter.tint(MaterialTheme.colors.onPrimary),
    )
    if (isFirst && !isLast) {
        BreadcrumbSeparator()
    }
}

@Composable
fun BreadcrumbSeparator(
    modifier: Modifier = Modifier,
) {
    Image(
        modifier = modifier
            .size(18.dp),
        painter = painterResource(Res.drawable.breadcrumb_separator),
        contentDescription = "breadcrumbs separator",
        colorFilter = ColorFilter.tint(MaterialTheme.colors.onPrimary),
    )
}

@Composable
fun BreadcrumbTextItem(
    modifier: Modifier = Modifier,
    title: String,
    fontWeight: FontWeight,
) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Text(
            modifier = modifier,
            text = title,
            color = MaterialTheme.colors.onPrimary,
            style = MaterialTheme.typography.body2,
            fontWeight = fontWeight,
        )
    }
}

@Composable
fun BreadcrumbCenterItem(
    title: String,
) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
    ) {
        BreadcrumbTextItem(
            title = title,
            fontWeight = FontWeight.Light
        )
        BreadcrumbSeparator()
    }
}

@Composable
fun BreadcrumbLastItem(
    title: String,
) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
    ) {
        BreadcrumbTextItem(
            title = title,
            fontWeight = FontWeight.Normal
        )
    }
}