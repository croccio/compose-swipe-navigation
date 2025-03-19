package it.croccio.compose.swipe.navigation.business

import it.croccio.compose.swipe.navigation.dto.Route

actual class Navigator : SimpleNavigator() {

    override val backStack: ScreenStack
        get() = super.backStack
    override var onBack: ((Route?) -> Unit)?
        get() = super.onBack
        set(value) {}

    override fun navigate(route: Route) {
        super.navigate(route)
    }

    override fun popBackStack(to: Route?) {
        super.popBackStack(to)
    }

}