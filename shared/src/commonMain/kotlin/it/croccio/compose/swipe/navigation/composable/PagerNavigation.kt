package it.croccio.compose.swipe.navigation.composable

import androidx.compose.foundation.gestures.snapping.SnapPosition
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import it.croccio.compose.swipe.navigation.business.INavigator
import it.croccio.compose.swipe.navigation.business.OffsetPageNavigator
import it.croccio.compose.swipe.navigation.dto.Route
import it.croccio.compose.swipe.navigation.dto.Routes
import kotlinx.coroutines.launch

@Composable
fun PagerNavigation(
    modifier: Modifier = Modifier,
    navigator: INavigator,
    starterPage: Route,
    routes: Routes,
) {

    val navigator = remember { navigator }

    val pagerState = rememberPagerState(
        initialPage = 0,
        pageCount = { navigator.backStack.size },
    )

    val coroutineScope = rememberCoroutineScope()

    HorizontalPager(
        modifier = modifier
            .fillMaxSize(),
        state = pagerState,
        userScrollEnabled = true,
        snapPosition = SnapPosition.Center,
    ) { pageIndex ->
        routes
            .toScreen(navigator.backStack[pageIndex])
    }

    LaunchedEffect(pagerState.pageCount) {
        pagerState.animateScrollToPage(navigator.backStack.lastIndex)
    }

    LaunchedEffect(pagerState.currentPageOffsetFraction) {
        (navigator as? OffsetPageNavigator)?.screenOffset?.value =
            Pair(pagerState.currentPage, pagerState.currentPageOffsetFraction)
        if (pagerState.currentPageOffsetFraction == 0f && pagerState.currentPage < navigator.backStack.lastIndex) {
            navigator.backStack.removeRange(
                pagerState.currentPage + 1,
                pagerState.pageCount,
            )
        }
    }

    LaunchedEffect(Unit) {
        navigator.onBack = { popTo ->
            coroutineScope.launch {
                if (popTo != null) {
                    navigator.backStack.removeRange(
                        navigator.backStack.indexOf(popTo) + 1,
                        pagerState.pageCount
                    )
                } else {
                    pagerState.animateScrollToPage(navigator.backStack.lastIndex - 1)
                }
            }
        }
        navigator.navigate(starterPage)
    }
}