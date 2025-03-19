package it.croccio.compose.swipe.navigation

import it.croccio.compose.swipe.navigation.business.SimpleNavigator
import it.croccio.compose.swipe.navigation.dto.Route
import it.croccio.compose.swipe.navigation.dto.RouteArgument
import it.croccio.compose.swipe.navigation.dto.RouteWithArgument
import kotlin.test.Test
import kotlin.test.assertTrue

class SimpleNavigatorTest {

    data object SimpleRoute : Route("Route")

    data class ArgumentedRoute(override val argument: Argument) : RouteWithArgument("ArgumentedRoute", argument) {
        data class Argument(val data: String) : RouteArgument()
    }

    @Test
    fun `WHEN a navigator is created THEN the stack is empty`() {
        val navigator = SimpleNavigator()

        assertTrue { navigator.backStack.isEmpty() }
    }

    @Test
    fun `WHEN navigate to a new route THEN the stack contains that route`() {
        val navigator = SimpleNavigator()

        val firstRoute = SimpleRoute
        val secondRoute = ArgumentedRoute(ArgumentedRoute.Argument("data"))

        navigator.navigate(route = firstRoute)
        assertTrue { navigator.backStack[0] == firstRoute }

        navigator.navigate(secondRoute)
        assertTrue { navigator.backStack[1] == secondRoute }
    }

    @Test
    fun `WHEN navigate back THEN the route is removed by stack`() {
        val navigator = SimpleNavigator()

        val firstRoute = SimpleRoute
        val secondRoute = ArgumentedRoute(ArgumentedRoute.Argument("data"))

        navigator.navigate(route = firstRoute)
        navigator.navigate(secondRoute)
        assertTrue { navigator.backStack.size == 2 }

        navigator.popBackStack()
        assertTrue { navigator.backStack[0] == firstRoute }
        assertTrue { navigator.backStack.size == 1 }

    }

    @Test
    fun `WHEN navigate back and there is only one entry in the stack THEN the route is not removed`() {
        val navigator = SimpleNavigator()

        val firstRoute = SimpleRoute
        val secondRoute = ArgumentedRoute(ArgumentedRoute.Argument("data"))

        navigator.navigate(route = firstRoute)
        navigator.navigate(secondRoute)

        navigator.popBackStack()
        navigator.popBackStack()
        navigator.popBackStack()

        assertTrue { navigator.backStack.size == 1 }

    }

    @Test
    fun `WHEN navigate back THEN back override is called`() {
        val navigator = SimpleNavigator()

        navigator.onBack = { navigator.backStack.clear() }

        val firstRoute = SimpleRoute
        val secondRoute = ArgumentedRoute(ArgumentedRoute.Argument("data"))

        navigator.navigate(route = firstRoute)
        navigator.navigate(secondRoute)

        navigator.popBackStack()

        assertTrue { navigator.backStack.isEmpty() }

    }

}