package com.szabist.audioguard

import android.Manifest
import android.annotation.SuppressLint
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Bundle
import android.webkit.*
import androidx.activity.OnBackPressedCallback
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.webkit.WebViewAssetLoader

/** AudioGuard: all analysis runs on the phone. The UI is bundled in assets/www and served locally. */
class MainActivity : AppCompatActivity() {
    private lateinit var web: WebView
    private var pendingPerm: PermissionRequest? = null
    private var fileCb: ValueCallback<Array<Uri>>? = null

    private val askMic = registerForActivityResult(ActivityResultContracts.RequestPermission()) { ok ->
        pendingPerm?.let { if (ok) it.grant(it.resources) else it.deny() }
        pendingPerm = null
    }
    private val pickFile = registerForActivityResult(ActivityResultContracts.GetContent()) { uri ->
        fileCb?.onReceiveValue(uri?.let { arrayOf(it) }); fileCb = null
    }

    @SuppressLint("SetJavaScriptEnabled")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        web = WebView(this); setContentView(web)
        val loader = WebViewAssetLoader.Builder()
            .addPathHandler("/assets/", WebViewAssetLoader.AssetsPathHandler(this)).build()
        web.settings.apply {
            javaScriptEnabled = true; domStorageEnabled = true
            mediaPlaybackRequiresUserGesture = false; allowFileAccess = false
        }
        web.webViewClient = object : WebViewClient() {
            override fun shouldInterceptRequest(v: WebView, r: WebResourceRequest) = loader.shouldInterceptRequest(r.url)
        }
        web.webChromeClient = object : WebChromeClient() {
            override fun onPermissionRequest(r: PermissionRequest) = runOnUiThread {
                if (ContextCompat.checkSelfPermission(this@MainActivity, Manifest.permission.RECORD_AUDIO) == PackageManager.PERMISSION_GRANTED)
                    r.grant(r.resources)
                else { pendingPerm = r; askMic.launch(Manifest.permission.RECORD_AUDIO) }
            }
            override fun onShowFileChooser(w: WebView, cb: ValueCallback<Array<Uri>>, p: FileChooserParams): Boolean {
                fileCb?.onReceiveValue(null); fileCb = cb; pickFile.launch("audio/*"); return true
            }
        }
        onBackPressedDispatcher.addCallback(this, object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                web.evaluateJavascript("(function(){if(stack.length>1){document.getElementById('back').click();return 1}return 0})()") {
                    if (it != "1") finish()
                }
            }
        })
        web.loadUrl("https://appassets.androidplatform.net/assets/www/index.html")
    }
    override fun onDestroy() { web.destroy(); super.onDestroy() }
}
