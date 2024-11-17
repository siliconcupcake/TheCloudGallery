package com.siliconcupcake.thecloudgallery.feature.presentation.util

sealed class Screen(val route: String) {
    data object HomeScreen : Screen("home_screen")
    data object SettingsScreen : Screen("settings_screen")

    data object AlbumsScreen : Screen("albums_screen")
    data object AlbumViewScreen : Screen("album_view_screen") {
        fun albumAndName() = "$route?albumId={albumId}&albumName={albumName}"
    }

    operator fun invoke() = route
}
