# AudioGuard prototype – files and how to run

audioguard-prototype/
- app/index.html          Mobile app prototype (installable PWA, English + Urdu)
- app/manifest.webmanifest, sw.js, icon-192.png, icon-512.png   (make it installable and offline)
- website/index.html      Project website (open app via the button)
- README.md               This guide

## 1. Quickest: laptop
Double-click website/index.html or app/index.html (Chrome/Edge). Demo sources work everywhere.
For the microphone, serve it: open a terminal in this folder and run `python -m http.server 8000`, then open http://localhost:8000/app/ (localhost allows the mic).

## 2. Phone (needs HTTPS for the microphone)
Option A (2 minutes): go to app.netlify.com/drop, drag the `app` folder. You get an https link. Open it on your phone.
Option B: GitHub Pages. Create a repo, upload the `app` folder contents, Settings > Pages > main branch.
Option C: the hosted link already given in chat.

## 3. Make it a real mobile app
- Install on home screen (PWA): Android Chrome > menu > Install app. iPhone Safari > Share > Add to Home Screen. It then opens full-screen with its own icon and works offline.
- Real Android APK (Capacitor), on the laptop with Node.js and Android Studio:
  1. npm init -y && npm i @capacitor/core @capacitor/cli @capacitor/android
  2. npx cap init AudioGuard com.szabist.audioguard --web-dir app
  3. npx cap add android
  4. Add <uses-permission android:name="android.permission.RECORD_AUDIO"/> to AndroidManifest.xml
  5. npx cap sync && npx cap open android, then Build > Build APK, and install the APK on the phone.

## 4. Presentation flow
Home > Live Analysis (Demo: mixed, watch genuine > amber > red warning) > Verify File (Demo voice note, tap red segments) > Urdu Demo (4 Urdu samples) > tap the اردو button to switch the whole app to Urdu.

## 5. Honest notes
- Detection uses a lightweight acoustic scorer standing in for the trained RawNet2/AASIST-lite model. Demo buttons use scripted scores.
- The watermarking feature was removed from this prototype as requested.

## 6. Build the APK online with GitHub (no Android Studio needed)
1. Make a free account at github.com and create a new repository (name: audioguard).
2. Upload the CONTENTS of this folder (AudioGuardAndroid, app, website, README.md) with Add file > Upload files.
3. Add file > Create new file. Name it exactly `.github/workflows/build-apk.yml` and paste the content of that file from this zip. Commit.
4. Open the Actions tab, open the "Build AudioGuard APK" run, wait about 5 minutes for the green tick.
5. Scroll down to Artifacts, download AudioGuard-APK (a zip), unzip it to get app-debug.apk, send it to your phone and install.
If the run shows a red cross, open it, copy the error text and send it to Claude.

## 7. Languages used
- Kotlin: native Android shell (MainActivity.kt: permissions, file picker, back button)
- HTML, CSS, JavaScript: the whole interface and the audio analysis (assets/www/index.html)
- Gradle (Kotlin DSL) and XML: build setup and AndroidManifest/resources
