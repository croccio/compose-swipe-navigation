package it.croccio.compose.swipe.navigation.business

import it.croccio.compose.swipe.navigation.dto.Route

open class SimpleNavigator() : Navigator {

    override val backStack = ScreenStack()

    override var onBack: ((to: Route?) -> Unit)? = null

    override fun navigate(route: Route) {
        backStack.add(route)
    }

    override fun popBackStack(to: Route?) {
        if (backStack.size > 1) {
            onBack?.invoke(to) ?: backStack.removeLast()
        }
    }

}