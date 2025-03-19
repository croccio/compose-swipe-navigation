package it.croccio.compose.swipe.navigation.composable

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.offset
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import io.github.croccio.csnavigation.shared.generated.resources.Res
import io.github.croccio.csnavigation.shared.generated.resources.back_button
import it.croccio.compose.swipe.navigation.business.INavigator
import org.jetbrains.compose.resources.painterResource
import kotlin.math.absoluteValue

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun BackButton(
    modifier: Modifier,
    navigator: INavigator,
    animationOffset: Pair<Int, Float>,
) {

    fun backButtonAlpha() = when {
        animationOffset.first == 0 -> animationOffset.second * 2 // si sta navigando dalla/sulla prima pagina
        navigator.backStack.size == 1 -> 0f // c'è solo la prima pagina
        navigator.backStack.size > 1 -> 1f // c'è più di una pagina
        navigator.backStack.lastIndex == animationOffset.first && animationOffset.second < 0 -> 1 - animationOffset.second.absoluteValue * 2 // si sta navigando alla pagina successiva
        else -> 1f
    }

    fun backTextOffset() = when {
        navigator.backStack.lastIndex == animationOffset.first && animationOffset.second < 0 -> animationOffset.second.absoluteValue * 40 // si sta navigando alla pagina successiva
        navigator.backStack.lastIndex - 1 == animationOffset.first && animationOffset.second > 0 -> -animationOffset.second.absoluteValue * 40 // si sta navigando alla pagina precedente
        else -> 1f
    }.dp

    fun backTextAlpha() = when {
        animationOffset.first == 0 -> 0f
        else -> 1 - animationOffset.second.absoluteValue * 2
    }

    fun backTextContent() = when {
        navigator.backStack.lastIndex == animationOffset.first -> navigator.backStack.getOrNull(
            navigator.backStack.lastIndex - 1
        )?.title // si sta navigando alla pagina precedente
        else -> navigator.backStack.getOrNull(navigator.backStack.lastIndex - 2)?.title // si sta navigando alla pagina successiva
    } ?: ""

    Row(
        modifier = modifier
            .alpha(backButtonAlpha())
            .fillMaxHeight()
            .clickable(
                onClick = { navigator.popBackStack() },
            ),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Image(
            painter = painterResource(Res.drawable.back_button),
            contentDescription = "back",
            colorFilter = ColorFilter.tint(MaterialTheme.colors.onPrimary),
            contentScale = ContentScale.Crop,
        )

        Text(
            modifier = Modifier
                .offset(backTextOffset())
                .alpha(backTextAlpha()),
            text = backTextContent(),
            color = MaterialTheme.colors.onPrimary,
            textAlign = TextAlign.Start,
            maxLines = 1,
            overflow = TextOverflow.Ellipsis,
        )
    }
}