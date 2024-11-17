package com.siliconcupcake.thecloudgallery.core

import android.content.Context
import android.os.Build
import android.provider.MediaStore
import androidx.annotation.RequiresApi
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import com.siliconcupcake.thecloudgallery.core.Settings.PREFERENCE_NAME
import com.siliconcupcake.thecloudgallery.core.util.rememberPreference
import com.siliconcupcake.thecloudgallery.feature.presentation.util.Screen
import kotlinx.coroutines.flow.map

val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = PREFERENCE_NAME)

object Settings {

    const val PREFERENCE_NAME = "settings"

    object Misc {
        private val USER_CHOICE_MEDIA_MANAGER = booleanPreferencesKey("use_media_manager")

        @RequiresApi(Build.VERSION_CODES.S)
        @Composable
        fun rememberIsMediaManager() =
            rememberPreference(
                key = USER_CHOICE_MEDIA_MANAGER, defaultValue = MediaStore.canManageMedia(
                    LocalContext.current
                )
            )

        private val SECURE_MODE = booleanPreferencesKey("secure_mode")

        @Composable
        fun rememberSecureMode() =
            rememberPreference(key = SECURE_MODE, defaultValue = false)

        fun getSecureMode(context: Context) =
            context.dataStore.data.map { it[SECURE_MODE] ?: false }

        private val ALLOW_BLUR = booleanPreferencesKey("allow_blur")

        @Composable
        fun rememberAllowBlur() = rememberPreference(key = ALLOW_BLUR, defaultValue = true)

        private val AUDIO_FOCUS = booleanPreferencesKey("audio_focus")

        @Composable
        fun rememberAudioFocus() =
            rememberPreference(key = AUDIO_FOCUS, defaultValue = true)

        private val FULL_BRIGHTNESS_VIEW = booleanPreferencesKey("full_brightness_view")

        @Composable
        fun rememberFullBrightnessView() =
            rememberPreference(key = FULL_BRIGHTNESS_VIEW, defaultValue = false)

        private val AUTO_HIDE_ON_VIDEO_PLAY = booleanPreferencesKey("auto_hide_on_video_play")

        @Composable
        fun rememberAutoHideOnVideoPlay() =
            rememberPreference(key = AUTO_HIDE_ON_VIDEO_PLAY, defaultValue = true)
    }
}

sealed class SettingsType {
    data object Switch : SettingsType()
    data object Header : SettingsType()
    data object Default : SettingsType()
}

sealed class Position {
    data object Top : Position()
    data object Middle : Position()
    data object Bottom : Position()
    data object Alone : Position()
}

sealed class SettingsEntity(
    open val icon: ImageVector? = null,
    open val title: String,
    open val summary: String? = null,
    val type: SettingsType = SettingsType.Default,
    open val enabled: Boolean = true,
    open val isChecked: Boolean? = null,
    open val onCheck: ((Boolean) -> Unit)? = null,
    open val onClick: (() -> Unit)? = null,
    open val screenPosition: Position = Position.Alone
) {
    val isHeader = type == SettingsType.Header

    data class Header(
        override val title: String
    ) : SettingsEntity(
        title = title,
        type = SettingsType.Header
    )

    data class Preference(
        override val icon: ImageVector? = null,
        override val title: String,
        override val summary: String? = null,
        override val enabled: Boolean = true,
        override val screenPosition: Position = Position.Alone,
        override val onClick: (() -> Unit)? = null,
    ) : SettingsEntity(
        icon = icon,
        title = title,
        summary = summary,
        enabled = enabled,
        screenPosition = screenPosition,
        onClick = onClick,
        type = SettingsType.Default
    )

    data class SwitchPreference(
        override val icon: ImageVector? = null,
        override val title: String,
        override val summary: String? = null,
        override val enabled: Boolean = true,
        override val screenPosition: Position = Position.Alone,
        override val isChecked: Boolean = false,
        override val onCheck: ((Boolean) -> Unit)? = null,
    ) : SettingsEntity(
        icon = icon,
        title = title,
        summary = summary,
        enabled = enabled,
        isChecked = isChecked,
        onCheck = onCheck,
        screenPosition = screenPosition,
        type = SettingsType.Switch
    )
}