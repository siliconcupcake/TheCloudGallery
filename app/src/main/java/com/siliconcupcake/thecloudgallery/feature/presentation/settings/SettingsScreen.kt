package com.siliconcupcake.thecloudgallery.feature.presentation.settings

import android.content.res.Configuration
import android.os.Build
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.siliconcupcake.thecloudgallery.R
import com.siliconcupcake.thecloudgallery.core.Position
import com.siliconcupcake.thecloudgallery.core.Settings
import com.siliconcupcake.thecloudgallery.core.Settings.Misc.rememberAudioFocus
import com.siliconcupcake.thecloudgallery.core.Settings.Misc.rememberAutoHideOnVideoPlay
import com.siliconcupcake.thecloudgallery.core.Settings.Misc.rememberFullBrightnessView
import com.siliconcupcake.thecloudgallery.core.SettingsEntity
import com.siliconcupcake.thecloudgallery.feature.presentation.settings.components.SettingsAppHeader
import com.siliconcupcake.thecloudgallery.feature.presentation.settings.components.SettingsItem
import com.siliconcupcake.thecloudgallery.feature.presentation.util.restartApplication
import com.siliconcupcake.thecloudgallery.ui.theme.TheCloudGalleryTheme
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@Composable
fun SettingsScreen() {
    val settingsList = rememberSettingsList()
    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
    ) {
        item { SettingsAppHeader() }
        items(
            items = settingsList,
            key = { it.title + it.type.toString() }
        ) { SettingsItem(it) }
    }

}

@Composable
fun rememberSettingsList(): SnapshotStateList<SettingsEntity> {
    val context = LocalContext.current
    val scope = rememberCoroutineScope()
    var secureMode by Settings.Misc.rememberSecureMode()
    val secureModePref = remember(secureMode) {
        SettingsEntity.SwitchPreference(
            title = context.getString(R.string.secure_mode_title),
            summary = context.getString(R.string.secure_mode_summary),
            isChecked = secureMode,
            onCheck = { secureMode = it },
            screenPosition = Position.Middle
        )
    }

    val shouldAllowBlur = remember { Build.VERSION.SDK_INT >= Build.VERSION_CODES.S }
    var allowBlur by Settings.Misc.rememberAllowBlur()
    val allowBlurPref = remember(allowBlur) {
        SettingsEntity.SwitchPreference(
            title = context.getString(R.string.fancy_blur),
            summary = context.getString(R.string.fancy_blur_summary),
            isChecked = allowBlur,
            onCheck = { allowBlur = it },
            enabled = shouldAllowBlur,
            screenPosition = Position.Middle
        )
    }

    var audioFocus by rememberAudioFocus()
    val audioFocusPref = remember(audioFocus) {
        SettingsEntity.SwitchPreference(
            title = context.getString(R.string.take_audio_focus_title),
            summary = context.getString(R.string.take_audio_focus_summary),
            isChecked = audioFocus,
            onCheck = {
                scope.launch {
                    audioFocus = it
                    delay(50)
                    context.restartApplication()
                }
            },
            screenPosition = Position.Middle
        )
    }

    var fullBrightnessView by rememberFullBrightnessView()
    val fullBrightnessViewPref = remember(fullBrightnessView) {
        SettingsEntity.SwitchPreference(
            title = context.getString(R.string.full_brightness_view_title),
            summary = context.getString(R.string.full_brightness_view_summary),
            isChecked = fullBrightnessView,
            onCheck = { fullBrightnessView = it },
            screenPosition = Position.Middle
        )
    }

    var autoHideOnVideoPlay by rememberAutoHideOnVideoPlay()
    val autoHideOnVideoPlayPref = remember(autoHideOnVideoPlay) {
        SettingsEntity.SwitchPreference(
            title = context.getString(R.string.auto_hide_on_video_play),
            summary = context.getString(R.string.auto_hide_on_video_play_summary),
            isChecked = autoHideOnVideoPlay,
            onCheck = { autoHideOnVideoPlay = it },
            screenPosition = Position.Bottom
        )
    }

    return remember(
        arrayOf(
            secureMode
        )
    ) {
        mutableStateListOf<SettingsEntity>().apply {
            /** ********************* **/
            /** ********************* **/
            add(SettingsEntity.Header(title = context.getString(R.string.settings_general)))
            /** General Section Start **/
            /** General Section Start **/
            add(secureModePref)
            /** ********************* **/
            /** ********************* **/
            add(SettingsEntity.Header(title = context.getString(R.string.customization)))
            /** Customization Section Start **/
            /** Customization Section Start **/
            add(allowBlurPref)
            add(audioFocusPref)
            add(fullBrightnessViewPref)
            add(autoHideOnVideoPlayPref)
            /** ********************* **/
            /** ********************* **/

        }
    }
}

@Preview(
    uiMode = Configuration.UI_MODE_NIGHT_YES or Configuration.UI_MODE_TYPE_NORMAL
)
@Composable
fun SettingsScreenPreview() =
    TheCloudGalleryTheme {
        SettingsScreen()
    }