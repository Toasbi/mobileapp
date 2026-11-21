package coredevices.pebble.config

import theme.CoreAppColorScheme
import kotlin.test.Test
import kotlin.test.assertTrue

class ConfigPageAppThemeTest {
    @Test
    fun thePageSeesTheAppsTheme() {
        mapOf(
            CoreAppColorScheme.Light to "light",
            CoreAppColorScheme.Grey to "dark",
            CoreAppColorScheme.Black to "black",
        ).forEach { (colorScheme, theme) ->
            assertTrue(configPageShimJs(colorScheme).contains("global.Pebble.appTheme = '$theme';"), colorScheme.name)
        }
    }
}
