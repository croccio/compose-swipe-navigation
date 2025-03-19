package it.croccio.compose.swipe.navigation.business

import androidx.compose.runtime.snapshots.SnapshotStateList
import it.croccio.compose.swipe.navigation.dto.Route

typealias ScreenStack = SnapshotStateList<Route>

interface INavigator {

    val backStack: ScreenStack

    var onBack: ((Route?) -> Unit)?

    fun navigate(route: Route)

    fun popBackStack(to: Route? = null)

}