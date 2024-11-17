package com.siliconcupcake.thecloudgallery.core.presentation.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.tv.material3.ExperimentalTvMaterial3Api
import androidx.tv.material3.Icon
import androidx.tv.material3.MaterialTheme
import androidx.tv.material3.Text
import com.siliconcupcake.thecloudgallery.R
import com.siliconcupcake.thecloudgallery.feature.presentation.util.Screen

@Composable
fun AppBar(
    navController: NavHostController
) {
    val entry by navController.currentBackStackEntryAsState()
    val routeValue = entry?.destination?.route

    val title = if (routeValue == Screen.HomeScreen()) "Home" else ""
//        stringResource(R.string.tv_compose)
//    else
//        components.find { it.routeValue == routeValue }?.title
//            ?: foundations.find { it.routeValue == routeValue }?.title ?: ""
    val description =
        if (routeValue == Screen.HomeScreen()) "Component Catalog" else null
    val isMainIconMagnified = description != null

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(start = 54.dp, top = 40.dp, end = 38.dp, bottom = 16.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically,
    ) {
        HeadlineContent(
            title = title,
            description = description,
            isMainIconMagnified = isMainIconMagnified
        )
    }
}

@OptIn(ExperimentalTvMaterial3Api::class)
@Composable
private fun HeadlineContent(
    title: String,
    description: String? = null,
    isMainIconMagnified: Boolean,
) {
    Row(
        horizontalArrangement = Arrangement.spacedBy(16.dp),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Box(
            modifier = Modifier
                .size(if (isMainIconMagnified) 64.dp else 40.dp)
                .background(
                    MaterialTheme.colorScheme.secondaryContainer.copy(0.4f),
                    shape = CircleShape
                ), contentAlignment = Alignment.Center
        ) {
            Icon(
                modifier = Modifier
                    .padding(if (isMainIconMagnified) 12.dp else 8.dp)
                    .size(if (isMainIconMagnified) 38.dp else 22.dp),
                painter = painterResource(id = R.drawable.ic_tv),
                contentDescription = null
            )
        }

        Column(modifier = Modifier.padding(start = 16.dp)) {
            Text(
                text = title,
                maxLines = 1,
                style = MaterialTheme.typography.headlineMedium,
                overflow = TextOverflow.Ellipsis,
            )
            if (description != null) {
                Text(
                    text = description,
                    style = MaterialTheme.typography.titleMedium,
                    color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.6f)
                )
            }
        }
    }
}
