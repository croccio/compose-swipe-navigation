package it.croccio.compose.swipe.navigation.dto

import androidx.compose.runtime.Composable
import it.croccio.compose.swipe.navigation.utils.DynamicLookupSerializer
import kotlinx.serialization.Serializable
import kotlinx.serialization.properties.Properties

fun toParams(any: @Serializable Any): String =
    Properties
        .encodeToMap(DynamicLookupSerializer(), any)
        .map { (key, value) ->
            "$key=$value"
        }
        .joinToString("&")

interface Route {
    val title: String
    val basePath: String?
        get() = "/${this::class.simpleName}"

    fun toPath(): String =
        basePath ?: "/${this::class.simpleName}"

}

interface RouteWithArgument<T : Any> : Route {
    val argument: T

    override fun toPath(): String =
        "${super.toPath()}?${toParams(argument)}"
}

interface Routes {

    @Composable
    fun toScreen(route: Route)

}