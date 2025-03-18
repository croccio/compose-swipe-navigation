package it.croccio.compose.swipe.navigation.composable

import androidx.compose.foundation.layout.offset
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import it.croccio.compose.swipe.navigation.business.Navigator
import kotlin.math.absoluteValue

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun NavigationBarTitle(
    modifier: Modifier,
    navigator: Navigator,
    animationOffset: Pair<Int, Float>,
) {

    fun textOffset() = when {
        navigator.backStack.lastIndex == animationOffset.first && animationOffset.second < 0 -> animationOffset.second.absoluteValue * 160 // si sta navigando alla pagina successiva
        navigator.backStack.lastIndex - 1 == animationOffset.first && animationOffset.second > 0 -> -animationOffset.second.absoluteValue * 160 // si sta navigando alla pagina precedente
        else -> 1f
    }.dp

    fun textAlpha() = when {
        animationOffset.first == 0 -> 1f
        else -> 1 - animationOffset.second.absoluteValue * 2
    }

    fun textContent() = when {
        navigator.backStack.lastIndex == animationOffset.first -> navigator.backStack.lastOrNull()?.title // si sta navigando alla pagina precedente
        else -> navigator.backStack.getOrNull(navigator.backStack.lastIndex - 1)?.title // si sta navigando alla pagina successiva
    } ?: ""

    Text(
        modifier = modifier
            .offset(textOffset())
            .alpha(textAlpha()),
        text = textContent(),
        fontWeight = FontWeight.SemiBold,
        textAlign = TextAlign.Center,
        color = MaterialTheme.colors.onPrimary,
        overflow = TextOverflow.Ellipsis,
        maxLines = 1,
    )
}