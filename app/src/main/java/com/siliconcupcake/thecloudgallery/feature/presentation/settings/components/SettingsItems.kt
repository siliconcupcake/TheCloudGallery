package com.siliconcupcake.thecloudgallery.feature.presentation.settings.components

import android.content.res.Configuration
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.BoxScope
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Settings
import androidx.tv.material3.Icon
import androidx.tv.material3.ListItem
import androidx.tv.material3.ListItemDefaults
import androidx.tv.material3.MaterialTheme
import androidx.tv.material3.Switch
import androidx.tv.material3.Text
import androidx.tv.material3.surfaceColorAtElevation
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.siliconcupcake.thecloudgallery.core.Position
import com.siliconcupcake.thecloudgallery.core.SettingsEntity
import com.siliconcupcake.thecloudgallery.core.SettingsEntity.Header
import com.siliconcupcake.thecloudgallery.core.SettingsEntity.Preference
import com.siliconcupcake.thecloudgallery.core.SettingsEntity.SwitchPreference
import com.siliconcupcake.thecloudgallery.core.SettingsType
import com.siliconcupcake.thecloudgallery.ui.theme.TheCloudGalleryTheme

@Composable
fun SettingsItem(
    item: SettingsEntity
) {
    var checked by remember(item.isChecked) {
        mutableStateOf(item.isChecked ?: false)
    }

    val icon: @Composable (BoxScope) -> Unit = {
        require(item.icon != null) { "Icon at this stage cannot be null" }
        Icon(
            imageVector = item.icon!!,
            modifier = Modifier.size(24.dp),
            contentDescription = null
        )
    }

    val summary: @Composable () -> Unit = {
        require(!item.summary.isNullOrEmpty()) { "Summary at this stage cannot be null or empty" }
        Text(text = item.summary!!)
    }

    val switch: @Composable () -> Unit = {
        Switch(checked = checked, onCheckedChange = null)
    }

    val shape = remember(item.screenPosition) {
        when (item.screenPosition) {
            Position.Alone -> RoundedCornerShape(16.dp)
            Position.Bottom -> RoundedCornerShape(
                topStart = 4.dp,
                topEnd = 4.dp,
                bottomStart = 16.dp,
                bottomEnd = 16.dp
            )

            Position.Middle -> RoundedCornerShape(
                topStart = 4.dp,
                topEnd = 4.dp,
                bottomStart = 4.dp,
                bottomEnd = 4.dp
            )

            Position.Top -> RoundedCornerShape(
                topStart = 16.dp,
                topEnd = 16.dp,
                bottomStart = 4.dp,
                bottomEnd = 4.dp
            )
        }
    }

    val paddingModifier =
        when (item.screenPosition) {
            Position.Alone -> Modifier.padding(bottom = 16.dp)
            Position.Bottom -> Modifier.padding(top = 1.dp, bottom = 16.dp)
            Position.Middle -> Modifier.padding(vertical = 1.dp)
            Position.Top -> Modifier.padding(bottom = 1.dp)
        }

    val supportingContent: (@Composable () -> Unit)? = when (item.type) {
        SettingsType.Default, SettingsType.Switch ->
            if (!item.summary.isNullOrEmpty()) summary else null

        SettingsType.Header -> null
    }

    val trailingContent: (@Composable () -> Unit)? = when (item.type) {
        SettingsType.Switch -> switch
        else -> null
    }

    if (item.isHeader) {
        Text(
            text = item.title,
            color = MaterialTheme.colorScheme.primary,
            style = MaterialTheme.typography.titleSmall,
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 40.dp, vertical = 8.dp)
                .padding(bottom = 8.dp)
        )
    } else {
        val alpha by animateFloatAsState(
            targetValue = if (item.enabled) 1f else 0.4f,
            label = "alpha"
        )
        ListItem(
            headlineContent = {
                Text(
                    text = item.title,
                    style = MaterialTheme.typography.titleMedium,
                    modifier = Modifier.padding(bottom = 2.dp)
                )
            },
            supportingContent = supportingContent,
            trailingContent = trailingContent,
            leadingContent = if (item.icon != null) icon else null,
            modifier = Modifier
                .then(paddingModifier)
                .padding(horizontal = 16.dp)
                .clip(shape)
                .background(
                    color = MaterialTheme.colorScheme.surfaceColorAtElevation(2.dp)
                )
                .padding(8.dp)
                .fillMaxWidth()
                .alpha(alpha),
            colors = ListItemDefaults.colors(containerColor = Color.Transparent),
            selected = false,
            onClick = {
                if (item.enabled)
                    if (item.type == SettingsType.Switch) {
                        item.onCheck?.let {
                            checked = !checked
                            it(checked)
                        }
                    } else item.onClick?.invoke()
            }
        )
    }
}

@Preview(
    uiMode = Configuration.UI_MODE_NIGHT_YES or Configuration.UI_MODE_TYPE_NORMAL
)
@Composable
fun SettingsItemGroupPreview() =
    TheCloudGalleryTheme {
        Column(
            modifier = Modifier.wrapContentHeight()
        ) {
            SettingsItem(
                item = Preference(
                    title = "Preview Alone Title",
                    summary = "Preview Summary"
                )
            )
            SettingsItem(
                item = Header(
                    title = "Preview Header Title"
                )
            )
            SettingsItem(
                item = Preference(
                    icon = Icons.Outlined.Settings,
                    title = "Preview Top Title",
                    summary = "Preview Summary",
                    screenPosition = Position.Top
                )
            )
            SettingsItem(
                item = SwitchPreference(
                    title = "Preview Middle Title",
                    summary = "Preview Summary\nSecond Line\nThird Line",
                    screenPosition = Position.Middle
                )
            )
            SettingsItem(
                item = Preference(
                    title = "Preview Bottom Title",
                    summary = "Preview Summary",
                    screenPosition = Position.Bottom
                )
            )
        }
    }