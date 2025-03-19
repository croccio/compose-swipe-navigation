package it.croccio.compose.swipe.navigation.business

import androidx.compose.runtime.mutableStateOf

open class OffsetPageNavigator() : SimpleNavigator() {

    var screenOffset = mutableStateOf(Pair<Int, Float>(0, 0f))

}