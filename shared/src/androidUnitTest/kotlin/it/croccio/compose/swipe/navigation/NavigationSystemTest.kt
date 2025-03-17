@file:OptIn(ExperimentalTestApi::class)

package it.croccio.compose.swipe.navigation

import android.app.Application
import android.content.ComponentName
import androidx.compose.material.Button
import androidx.compose.material.Card
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.test.ExperimentalTestApi
import androidx.compose.ui.test.assertTextEquals
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.performClick
import androidx.compose.ui.test.runComposeUiTest
import androidx.core.app.ComponentActivity
import androidx.test.core.app.ApplicationProvider
import org.junit.Rule
import org.junit.rules.TestWatcher
import org.junit.runner.Description
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import org.robolectric.Shadows
import kotlin.test.Test

@RunWith(RobolectricTestRunner::class)
class NavigationSystemTest {

    data object SimpleRoute : Route()

    data class ArgumentedRoute(override val argument: Argument) : RouteWithArgument(argument) {
        data class Argument(val data: String) : RouteArgument()
    }

    @get:Rule(order = 1)
    val addActivityToRobolectricRule = object : TestWatcher() {
        override fun starting(description: Description?) {
            super.starting(description)
            val appContext: Application = ApplicationProvider.getApplicationContext()
            Shadows.shadowOf(appContext.packageManager).addActivityIfNotPresent(
                ComponentName(
                    appContext.packageName,
                    ComponentActivity::class.java.name,
                )
            )
        }
    }

    @Test
    fun `WHEN navigation system is created THEN the started page is shown`() = runComposeUiTest {
        setContent {
            TestNavigationSystem()
        }

        // Starter Page
        onNodeWithTag("title").assertTextEquals("SimpleRoute")

    }

    @Test
    fun `WHEN navigation system is created THEN the navigation navigate to the expected page with right parameter`() =
        runComposeUiTest {
            setContent {
                TestNavigationSystem()
            }

            // Starter Page
            onNodeWithTag("navigateButton").performClick()

            // Second Page
            onNodeWithTag("title").assertTextEquals("data")

        }

    @Test
    fun `WHEN navigation system is created THEN the back action navigate to the expected page`() =
        runComposeUiTest {
            setContent {
                TestNavigationSystem()
            }

            // Starter Page
            onNodeWithTag("navigateButton").performClick()
            // Second Page
            onNodeWithTag("navigateButton").performClick()

            // Starter Page
            onNodeWithTag("title").assertTextEquals("SimpleRoute")

        }

    @Composable
    private fun TestNavigationSystem() {
        val navigator = PagerNavigator()
        NavigationSystem(
            modifier = Modifier,
            navigator = navigator,
            starterPage = SimpleRoute,
            routes = object : Routes {
                @Composable
                override fun toScreen(route: Route) {
                    when (route) {
                        is SimpleRoute -> Card {
                            Text(
                                "SimpleRoute",
                                modifier = Modifier.testTag("title")
                            )
                            Button(modifier = Modifier.testTag("navigateButton"), onClick = {
                                navigator.navigate(
                                    ArgumentedRoute(ArgumentedRoute.Argument("data"))
                                )
                            }) { }
                        }

                        is ArgumentedRoute -> Card {
                            Text(
                                route.argument.data,
                                modifier = Modifier.testTag("title")
                            )
                            Button(modifier = Modifier.testTag("navigateButton"), onClick = {
                                navigator.popBackStack()
                            }) { }
                        }
                    }
                }

            }
        )
    }

}