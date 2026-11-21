> This branch only holds these notes. The change itself is on the branch `config-page-app-theme`.
>
> - [Read the diff](https://github.com/Toasbi/mobileapp/compare/master...config-page-app-theme)
> - [Open the pull request](https://github.com/coredevices/mobileapp/compare/master...Toasbi:mobileapp:config-page-app-theme?quick_pull=1&title=Expose%20the%20app%27s%20theme%20to%20settings%20pages&body=A%20settings%20page%20sees%20the%20app%27s%20theme%20only%20through%20%60prefers-color-scheme%60%2C%20which%20knows%20light%20and%20dark.%20The%20Black%20theme%20reports%20as%20dark%2C%20so%20a%20page%20with%20a%20black%20look%20of%20its%20own%20cannot%20follow%20it.%20One%20example%20is%20a%20watchface%27s%20settings%20page%20with%20light%2C%20dark%20and%20black%20themes%3A%20its%20Auto%20setting%20can%20match%20Light%20and%20Dark%2C%20but%20never%20Black.%0A%0AThis%20adds%20%60Pebble.appTheme%60%20to%20the%20page-side%20%60Pebble%60%20API%3A%20%60%27light%27%60%2C%20%60%27dark%27%60%20or%20%60%27black%27%60%2C%20the%20theme%20the%20app%20shows%20when%20the%20page%20opens%20%28System%20resolved%20to%20light%20or%20dark%29.%20The%20script%20that%20already%20defines%20%60Pebble.%2A%60%20at%20document%20start%20sets%20it%2C%20so%20a%20page%20can%20read%20it%20in%20its%20first%20script%2C%20before%20it%20renders.%0A%0A%60%60%60js%0Avar%20theme%20%3D%20window.Pebble%20%26%26%20Pebble.appTheme%3B%20%2F%2F%20%27light%27%2C%20%27dark%27%20or%20%27black%27%3B%20undefined%20in%20older%20apps%0Aif%20%28theme%20%3D%3D%3D%20%27black%27%29%20document.documentElement.classList.add%28%27black%27%29%3B%0A%2F%2F%20light%20and%20dark%20can%20keep%20coming%20from%20prefers-color-scheme%0A%60%60%60%0A%0AHow%20it%20works%3A%0A-%20%60CONFIG_PAGE_SHIM_JS%60%20becomes%20%60configPageShimJs%28colorScheme%29%60%2C%20which%20writes%20the%20value%20in.%0A-%20%60WatchappSettingsScreen%60%20passes%20it%20%60currentColorScheme%28%29%60%2C%20the%20resolved%20scheme%20the%20rest%20of%20the%20UI%20uses%2C%20and%20hands%20the%20script%20to%20%60webViewFactory%60%2C%20which%20injects%20it%20where%20it%20injected%20the%20constant%20before%2C%20on%20Android%20and%20iOS.%0A-%20%60prefers-color-scheme%60%20is%20unchanged.%0A%0AThe%20value%20is%20fixed%20when%20the%20page%20opens%20and%20is%20not%20updated%20while%20it%20is%20open.%20Black%20can%20only%20be%20chosen%20in%20the%20app%27s%20own%20settings%2C%20so%20it%20cannot%20start%20or%20stop%20while%20a%20settings%20page%20is%20open.%20A%20page%20that%20wants%20live%20light%20and%20dark%20changes%20keeps%20listening%20to%20%60prefers-color-scheme%60.%0A%0ATesting%3A%0A-%20New%20unit%20test%20%60ConfigPageAppThemeTest%60%20%28commonTest%29%3A%20each%20colour%20scheme%20puts%20the%20right%20value%20in%20the%20script.%20It%20fails%20when%20a%20scheme%20maps%20to%20the%20wrong%20value.%0A-%20On%20Linux%20with%20JDK%2017%3A%20%60.%2Fgradlew%20%3Apebble%3AcompileAndroidMain%20%3Apebble%3AtestAndroidHostTest%60%20passes%2C%20all%20201%20tests%20in%20the%20module.%0A-%20Not%20tried%20in%20the%20app%20on%20a%20phone.%20The%20iOS%20side%20was%20not%20compiled%20%28Apple%20targets%20don%27t%20build%20on%20Linux%29%3B%20it%20only%20gains%20the%20new%20parameter.%0A%0ANot%20in%20this%20PR%3A%0A-%20On%20iOS%20the%20settings%20page%27s%20%60WKWebView%60%20does%20not%20set%20%60overrideUserInterfaceStyle%60%20the%20way%20%60PebbleWebview%60%20does%2C%20so%20as%20far%20as%20I%20can%20tell%20from%20the%20code%2C%20%60prefers-color-scheme%60%20there%20follows%20the%20phone%27s%20setting%20rather%20than%20the%20app%27s.%20I%20left%20that%20alone%20and%20can%20send%20it%20separately.%0A-%20Docs%3A%20the%20Settings%20pages%20section%20of%20the%20plugin%20guide%20%28sdk-docs%2C%20%60source%2F_guides%2Fplugins%2Fpreview.md%60%29%20would%20want%20a%20line%20about%20%60Pebble.appTheme%60.%20I%20can%20send%20that%20once%20this%20is%20merged.%0A%0AI%20used%20AI%20%28Claude%20Code%29%20to%20write%20this%20change.%20I%20have%20read%20and%20reviewed%20the%20diff.%0A%0A%F0%9F%A4%96%20Generated%20with%20%5BClaude%20Code%5D%28https%3A%2F%2Fclaude.com%2Fclaude-code%29%0A%0Ahttps%3A%2F%2Fclaude.ai%2Fcode%2Fsession_01Uurh61TBUFaMFM6x7KGqnB%0A), with the title and text below already filled in. Use it after the to-dos.

# Settings pages learn the app's theme: notes for the upstream PR

## What this branch is

`config-page-app-theme` holds one small change to the Pebble app (Core Devices' mobileapp), prepared as a pull request for them. It lets a watch app's settings page read the app's theme, Black included, as `Pebble.appTheme` (`'light'`, `'dark'` or `'black'`). Today a settings page only learns light or dark, through `prefers-color-scheme`, and the app's Black theme reports as dark, so my watchface's settings page cannot follow Black on Auto. Nothing has been opened upstream yet: the steps below are yours.

## Your to-dos

- [ ] **Read the diff** (one commit, 5 files, +39 and -6 lines), so you can honestly say you reviewed and understand it:
  - `pebble/src/commonMain/kotlin/coredevices/pebble/config/ConfigPageBridge.kt`: the script the app runs in every settings page before the page's own script (`CONFIG_PAGE_SHIM_JS`, which defines `Pebble.sendMessage`, `Pebble.openUrl` and the rest) becomes the function `configPageShimJs(colorScheme)` and gains one line, `global.Pebble.appTheme = '...'`. The small `pageTheme` mapping at the end of the file turns the app's three colour schemes (Light, Grey, Black) into `'light'`, `'dark'` and `'black'`.
  - `pebble/src/commonMain/kotlin/coredevices/pebble/ui/WatchappSettingsScreen.kt`: the settings page screen reads `currentColorScheme()`, the theme the app's own screens use, with System already resolved to light or dark, and passes the built script to `webViewFactory`.
  - `pebble/src/androidMain/kotlin/coredevices/pebble/ui/WatchappSettingsScreen.android.kt` and `pebble/src/iosMain/kotlin/coredevices/pebble/ui/WatchappSettingsScreen.ios.kt`: each takes the script as a new `shimJs` parameter and injects it exactly where it injected the constant before.
  - `pebble/src/commonTest/kotlin/coredevices/pebble/config/ConfigPageAppThemeTest.kt`: the new test.
  - What to check: nothing else in the script changes; `prefers-color-scheme` is untouched; the value is fixed when the page opens (the web view is built once per page) and is not updated while it is open.
- [ ] **Make yourself the commit's author.** The commit is authored "Claude <noreply@anthropic.com>". Their CLA check wants every commit author to be a GitHub account that signed it, under a legally identifiable name. With your name, and an email linked to your GitHub account, set in git:
  ```
  git clone <your fork's URL> mobileapp
  cd mobileapp
  git checkout config-page-app-theme
  git commit --amend --reset-author
  git push --force-with-lease origin config-page-app-theme
  ```
  The editor that opens lets you keep or drop the two lines at the end of the message: `Co-Authored-By: Claude ...` (their own merged PRs carry such lines too) and a `Claude-Session` link.
- [ ] **Sign the CLA**: https://cla-assistant.io/coredevices/libpebble3 (or wait for the bot's comment on the PR).
- [ ] **Test it, if you can.** It needs a build of the app you make yourself; nothing here could run it on a phone.
  - Android needs JDK 17, the Android SDK (Android Studio installs it), a phone with USB debugging, and Chrome on a computer. In your clone: `cp androidApp/src/google-services-dummy.json androidApp/src/google-services.json`; if `git describe --tags` finds no tag, run `git tag 1.0.0`; then `./gradlew :androidApp:assembleDebug` and install `androidApp/build/outputs/apk/debug/androidApp-debug.apk` with `adb install`.
  - Careful: the self-built app has the store app's id but a different signature, so Android only installs it after you uninstall the store app, and that deletes the store app's data: watch pairing, sign-in, and your watchfaces' saved settings. A spare phone avoids this.
  - In the app: Settings, turn on "Show debug options", then "PKJS Debugger". Set the app theme to Light, open any watchface's or plugin's settings page, open `chrome://inspect` on the computer, inspect the page and type `Pebble.appTheme` in its console: expect `'light'`. Close the page, switch the app to Dark, then to Black, opening the page again each time: expect `'dark'`, then `'black'`. `matchMedia('(prefers-color-scheme: dark)').matches` should still be `true` for both Dark and Black, as before.
  - iOS needs a Mac with Xcode (their README has the steps). On a Mac, `./gradlew :pebble:compileKotlinIosSimulatorArm64` at least compiles the iOS side, which nothing here could. In the app, the settings page can be inspected from Safari's Develop menu.
  - Put what you actually tried into the PR's Testing list before you post it.
- [ ] **Open the PR**: base `coredevices/mobileapp` `master`, compare your fork's `config-page-app-theme`. Paste the title and body below.
- [ ] **Docs follow-up, after it is merged**: a short PR to `coredevices/sdk-docs` adding `Pebble.appTheme` to `source/_guides/plugins/preview.md`: a sentence and the snippet in the "Settings pages" section, and a line in its "Changelog" under the app version that ships it. The maintainers may prefer to write it themselves; offer it in the PR thread.
- [ ] **Tell me afterwards**: the PR link, whether they changed the name or the values, and which Pebble app version ships it. Then the watchface side gets added (Auto shows Black when the app is on Black), and its hint can name that version.

## PR title and body

````markdown
Title: Expose the app's theme to settings pages

A settings page sees the app's theme only through `prefers-color-scheme`, which knows light and dark. The Black theme reports as dark, so a page with a black look of its own cannot follow it. One example is a watchface's settings page with light, dark and black themes: its Auto setting can match Light and Dark, but never Black.

This adds `Pebble.appTheme` to the page-side `Pebble` API: `'light'`, `'dark'` or `'black'`, the theme the app shows when the page opens (System resolved to light or dark). The script that already defines `Pebble.*` at document start sets it, so a page can read it in its first script, before it renders.

```js
var theme = window.Pebble && Pebble.appTheme; // 'light', 'dark' or 'black'; undefined in older apps
if (theme === 'black') document.documentElement.classList.add('black');
// light and dark can keep coming from prefers-color-scheme
```

How it works:
- `CONFIG_PAGE_SHIM_JS` becomes `configPageShimJs(colorScheme)`, which writes the value in.
- `WatchappSettingsScreen` passes it `currentColorScheme()`, the resolved scheme the rest of the UI uses, and hands the script to `webViewFactory`, which injects it where it injected the constant before, on Android and iOS.
- `prefers-color-scheme` is unchanged.

The value is fixed when the page opens and is not updated while it is open. Black can only be chosen in the app's own settings, so it cannot start or stop while a settings page is open. A page that wants live light and dark changes keeps listening to `prefers-color-scheme`.

Testing:
- New unit test `ConfigPageAppThemeTest` (commonTest): each colour scheme puts the right value in the script. It fails when a scheme maps to the wrong value.
- On Linux with JDK 17: `./gradlew :pebble:compileAndroidMain :pebble:testAndroidHostTest` passes, all 201 tests in the module.
- Not tried in the app on a phone. The iOS side was not compiled (Apple targets don't build on Linux); it only gains the new parameter.

Not in this PR:
- On iOS the settings page's `WKWebView` does not set `overrideUserInterfaceStyle` the way `PebbleWebview` does, so as far as I can tell from the code, `prefers-color-scheme` there follows the phone's setting rather than the app's. I left that alone and can send it separately.
- Docs: the Settings pages section of the plugin guide (sdk-docs, `source/_guides/plugins/preview.md`) would want a line about `Pebble.appTheme`. I can send that once this is merged.

I used AI (Claude Code) to write this change. I have read and reviewed the diff.

🤖 Generated with [Claude Code](https://claude.com/claude-code)

https://claude.ai/code/session_01Uurh61TBUFaMFM6x7KGqnB
````

## What was compiled and run here

On Linux, with JDK 17 and Android SDK platform 37 installed for the purpose, at the branch's commit:

- Compiled: `:pebble:compileAndroidMain`, the pebble module's common and Android code for Android, which includes both changed common files and the Android file, and the modules it depends on.
- Ran: `:pebble:testAndroidHostTest`, all of the module's unit tests: 41 test classes, 201 tests, no failures, the new one included. To check the new test bites, `Grey` was mapped to `'grey'` for one run: the test failed; with the mapping restored it passes.
- Not compiled: the iOS file. Gradle disables the Apple targets on Linux.
- Not compiled: `:pebble:compileCommonMainKotlinMetadata`. On this machine it stops earlier, in `:util`, on code this branch does not touch (`util/src/commonMain/kotlin/coredevices/util/transcription/CactusTranscriptionService.kt:370`, "Check for instance is always 'false'").
- Not run: the app itself, on a phone or an emulator.
- Maven Central rate-limited this machine (HTTP 429), so the build took many retries; that has nothing to do with the change.

## The watchface side (not done yet)

Auto keeps following `prefers-color-scheme`, plus one rule: when `Pebble.appTheme` is exactly `'black'`, Auto shows the Black look. The settings engine's `resolveTheme` gets the app theme as one more argument and checks it just before it falls back to `prefersLight ? 'light' : 'dark'`; the code that applies the theme passes it in, read from `window.Pebble`. The Weather tab's own theme lookup passes the same value, through one shared helper so the two cannot disagree, and its first-render fallback makes the same check. It fails safe: without the value (an older Pebble app, the desktop preview, any other phone) nothing changes, and the explicit Light, Dark and Black settings ignore it. The settings row's hint and the README line about Auto get updated, with tests for Auto with and without the value.
