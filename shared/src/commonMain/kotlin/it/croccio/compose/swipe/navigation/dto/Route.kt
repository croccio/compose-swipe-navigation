package it.croccio.compose.swipe.navigation.dto

import androidx.compose.runtime.Composable

open class RouteArgument

abstract class Route(
    open val title: String,
)

abstract class RouteWithArgument(
    override val title: String,
    open val argument: RouteArgument,
) : Route(title)

interface Routes {

    @Composable
    fun toScreen(route: Route)

}