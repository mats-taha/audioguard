# AudioGuard Android app: build and install

This is a native Android project (Kotlin). The AudioGuard interface is bundled inside the app (app/src/main/assets/www), so it runs fully on the phone with no internet. Microphone permission is requested natively.

## Build the APK (about 10 minutes the first time)
1. Install Android Studio (developer.android.com/studio). Leave all defaults.
2. File > Open > choose the AudioGuardAndroid folder. Wait for "Gradle sync" to finish (needs internet once). Accept any prompt to install SDK 34 or update Gradle.
3. Build > Build Bundle(s) / APK(s) > Build APK(s). Click "locate" when done:
   AudioGuardAndroid/app/build/outputs/apk/debug/app-debug.apk
4. Send app-debug.apk to your phone (WhatsApp, Drive, USB) and tap it. Allow "Install unknown apps" when asked.

## Run directly from Android Studio
Enable Developer options and USB debugging on the phone, plug in USB, press the green Run button, pick your phone.

## Using it in the demo
Open AudioGuard. Live Analysis > Microphone > Continue > Allow. Demo buttons work without the mic. Tap اردو for Urdu.

## Edit the UI later
Change app/src/main/assets/www/index.html and rebuild.

## No laptop, only phone?
Upload the `app` folder (one level up) to app.netlify.com/drop, open the https link in Android Chrome, menu > Install app.
