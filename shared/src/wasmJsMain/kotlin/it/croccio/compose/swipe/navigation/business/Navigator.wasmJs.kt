package it.croccio.compose.swipe.navigation.business

import it.croccio.compose.swipe.navigation.dto.Route
import kotlinx.browser.window

actual class Navigator : SimpleNavigator() {

    private val pathMap = mutableMapOf<String, Route>()

    var windowLastHistoryLength = 0

    actual override fun navigate(route: Route) {
        super.navigate(route)
        pathMap.clear()
        window.history.pushState(route.toPath().toJsString(), "", route.toPath())
    }

    fun forward() {
        pathMap[window.location.href]?.let { backStack.add(it) }
    }

    actual override fun popBackStack(to: Route?) {
        to?.let { route ->
            (backStack.indexOf(route)..backStack.lastIndex).forEach {
                pathMap[window.location.href] = backStack[it]
            }
        }?: run {
            pathMap[window.location.href] = backStack.last()
        }
        super.popBackStack(to)
    }

}