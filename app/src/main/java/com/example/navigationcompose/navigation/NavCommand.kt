package com.example.navigationcompose.navigation

import androidx.navigation.NavType
import androidx.navigation.navArgument

sealed class NavCommand(
    internal val feature: Feature,
    private val navArgs: List<NavArg> = emptyList()
) {
    class ScreenWithoutArguments(feature: Feature) : NavCommand(feature)

    class ScreenB(feature: Feature) :
        NavCommand(feature, listOf(NavArg.LuckyNumber)) {
        fun createDestinationRoute(luckyNumber: Int) =
            "${feature.route}/$luckyNumber"
    }

    class ScreenC(feature: Feature) :
        NavCommand(feature, listOf(NavArg.Message, NavArg.UnLuckyNumber)) {
        fun createDestinationRoute(message: String, unLuckyNumber: Int) =
            "${feature.route}/$message/$unLuckyNumber"

    }

    val route = run {
        val argValues = navArgs.map { "{${it.key}}" }
        listOf(feature.route)
            .plus(argValues)
            .joinToString("/")
    }

    val args = navArgs.map {
        navArgument(it.key) { type = it.navType }
    }

}

enum class NavArg(val key: String, val navType: NavType<*>) {
    LuckyNumber("luckyNumber", NavType.IntType),
    Message("message", NavType.StringType),
    UnLuckyNumber("unLuckyNumber", NavType.IntType),
}