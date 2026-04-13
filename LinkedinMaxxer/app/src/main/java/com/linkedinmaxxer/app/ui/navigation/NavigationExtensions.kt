package com.linkedinmaxxer.app.ui.navigation

import androidx.navigation.NavHostController

fun navigateAndPopBackstack(
    route: String,
    navController: NavHostController,
) {
    navController.navigate(route) {
        popUpTo(0)
        launchSingleTop = true
    }
}
