package com.streamverse.app.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.google.firebase.auth.FirebaseAuth
import com.streamverse.app.auth.GoogleAuth
import com.streamverse.app.data.ALL_SHOWS
import com.streamverse.app.data.User
import com.streamverse.app.ui.screens.HomeScreen
import com.streamverse.app.ui.screens.LoginScreen
import com.streamverse.app.ui.screens.ShowDetailScreen

object Routes {
    const val LOGIN  = "login"
    const val HOME   = "home"
    const val DETAIL = "detail/{showId}"
    fun detail(showId: String) = "detail/$showId"
}

@Composable
fun NavGraph() {
    val navController = rememberNavController()
    val context = LocalContext.current
    var currentUser by remember { mutableStateOf<User?>(null) }

    NavHost(navController = navController, startDestination = Routes.LOGIN) {

        composable(Routes.LOGIN) {
            LoginScreen(
                onLogin = { user ->
                    currentUser = user
                    navController.navigate(Routes.HOME) {
                        popUpTo(Routes.LOGIN) { inclusive = true }
                    }
                }
            )
        }

        composable(Routes.HOME) {
            HomeScreen(
                user      = currentUser,
                onShowClick = { show ->
                    navController.navigate(Routes.detail(show.id))
                },
                onLogout  = {
                    GoogleAuth.getClient(context).signOut()
                    FirebaseAuth.getInstance().signOut()
                    currentUser = null
                    navController.navigate(Routes.LOGIN) {
                        popUpTo(Routes.HOME) { inclusive = true }
                    }
                }
            )
        }

        composable(
            route     = Routes.DETAIL,
            arguments = listOf(navArgument("showId") { type = NavType.StringType })
        ) { backStack ->
            val showId = backStack.arguments?.getString("showId") ?: return@composable
            val show   = ALL_SHOWS.first { it.id == showId }
            ShowDetailScreen(
                show    = show,
                user    = currentUser,
                onBack  = { navController.popBackStack() }
            )
        }
    }
}
