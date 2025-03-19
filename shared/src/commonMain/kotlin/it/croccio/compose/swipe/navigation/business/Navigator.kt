package it.croccio.compose.swipe.navigation.business

import it.croccio.compose.swipe.navigation.dto.Route

expect class Navigator: INavigator {
    override val backStack: ScreenStack
    override var onBack: ((Route?) -> Unit)?
    override fun navigate(route: Route)
    override fun popBackStack(to: Route?)
}