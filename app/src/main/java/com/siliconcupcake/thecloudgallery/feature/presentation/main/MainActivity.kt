package com.siliconcupcake.thecloudgallery.feature.presentation.main

import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.selection.selectableGroup
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Settings
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.unit.dp
import androidx.navigation.compose.rememberNavController
import androidx.tv.material3.ExperimentalTvMaterial3Api
import androidx.tv.material3.Icon
import androidx.tv.material3.ModalNavigationDrawer
import androidx.tv.material3.NavigationDrawerItem
import androidx.tv.material3.Surface
import androidx.tv.material3.Text
import com.siliconcupcake.thecloudgallery.core.presentation.components.NavigationGraph
import com.siliconcupcake.thecloudgallery.ui.theme.TheCloudGalleryTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    @OptIn(ExperimentalTvMaterial3Api::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            TheCloudGalleryTheme {
                val navController = rememberNavController()
                val bottomBarState = rememberSaveable { mutableStateOf(true) }

                var selectedIndex by remember { mutableIntStateOf(0) }
                val items =
                    listOf(
                        "Home" to Icons.Default.Home,
                        "Settings" to Icons.Default.Settings
                    )

                Surface(
                    Modifier.fillMaxSize(),
                    shape = RectangleShape,
                ) {
                    ModalNavigationDrawer(
                        drawerContent = {
                            Column(
                                Modifier
                                    .background(Color.Gray)
                                    .fillMaxHeight()
                                    .padding(12.dp)
                                    .selectableGroup(),
                                horizontalAlignment = Alignment.Start,
                                verticalArrangement = Arrangement.spacedBy(10.dp)
                            ) {
                                items.forEachIndexed { index, item ->
                                    val (text, icon) = item

                                    NavigationDrawerItem(
                                        selected = selectedIndex == index,
                                        onClick = { selectedIndex = index },
                                        leadingContent = {
                                            Icon(
                                                imageVector = icon,
                                                contentDescription = null,
                                            )
                                        }
                                    ) {
                                        Text(text)
                                    }
                                }
                            }
                        },
                        scrimBrush = Brush.horizontalGradient(
                            listOf(
                                Color.DarkGray,
                                Color.Transparent
                            )
                        )
                    ) {
                        Column(Modifier.fillMaxSize()) {
                            NavigationGraph(
                                navController = navController,
                                bottomBarState = bottomBarState
                            )
                        }
                    }
                }
            }
        }
    }
}