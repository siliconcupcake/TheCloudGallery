package com.siliconcupcake.thecloudgallery.core.presentation.components

import androidx.compose.foundation.layout.Column
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.LocalLifecycleOwner
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import com.siliconcupcake.thecloudgallery.feature.presentation.common.ChanneledViewModel
import com.siliconcupcake.thecloudgallery.feature.presentation.settings.SettingsScreen
import com.siliconcupcake.thecloudgallery.feature.presentation.util.Screen
import kotlinx.coroutines.Dispatchers

@Composable
fun NavigationGraph(
    navController: NavHostController,
    bottomBarState: MutableState<Boolean>
) {
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val navPipe = hiltViewModel<ChanneledViewModel>()
    navPipe
        .initWithNav(navController, bottomBarState)
        .collectAsStateWithLifecycle(LocalLifecycleOwner.current, context = Dispatchers.Main.immediate)

    NavHost(navController = navController, startDestination = Screen.SettingsScreen()) {
        destinations.forEach { destination ->
            composable(destination.routeName) {
                destination.composable { AppBar(navController) }
            }
        }
    }
}

enum class NavGraph(
    val routeName: String,
    val composable: @Composable (appBar: @Composable () -> Unit) -> Unit
) {
    Home(
        routeName = Screen.HomeScreen(),
        composable = { appBar ->
            Column {
                appBar()
            }
        }
    ),
    Settings(
        routeName = Screen.SettingsScreen(),
        composable = { appBar ->
            Column {
                appBar()
                SettingsScreen()
            }
        }
    );
}

val destinations = listOf(
    NavGraph.Home,
    NavGraph.Settings,
)
