package it.croccio.compose.swipe.navigation.business

import androidx.compose.runtime.mutableStateOf

class PageNavigator() : SimpleNavigator() {

    var screenOffset = mutableStateOf(Pair<Int, Float>(0, 0f))

}