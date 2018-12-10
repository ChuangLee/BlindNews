package com.lichuang.blindenglish

import android.content.Intent
import android.graphics.Bitmap
import android.media.session.MediaSession
import android.os.Bundle
import android.os.Handler
import android.os.Parcelable
import android.support.v7.app.AppCompatActivity
import android.util.Log
import android.view.KeyEvent
import android.view.MenuItem
import android.view.View
import android.view.Window
import android.webkit.WebResourceError
import android.webkit.WebResourceRequest
import android.webkit.WebView
import android.webkit.WebViewClient
import android.widget.ProgressBar

class OnlineEnglishLoader : AppCompatActivity() {
    companion object {
        const val MEDIA_INFO = "mediaInfo"
    }

    private val tag = ":OnlineEnglishLoader"
    private var mSession: MediaSession? = null
    private var handler: Handler? = null
    private lateinit var mediaInfo: MediaItem
    private lateinit var web: WebView

    override fun onCreate(savedInstanceState: Bundle?) {
        this.requestWindowFeature(Window.FEATURE_NO_TITLE)
        super.onCreate(savedInstanceState)
        setContentView(R.layout.online_loader)
        mediaInfo = intent.getSerializableExtra(MEDIA_INFO) as MediaItem
        title = mediaInfo.title.split(": ").last()
        supportActionBar?.setHomeButtonEnabled(true)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        findViewById<View>(android.R.id.content).keepScreenOn = true

        web = findViewById(R.id.webview)
        web.settings.loadWithOverviewMode = true
        web.settings.useWideViewPort = true
        web.settings.setAppCacheEnabled(true)
        web.loadUrl(mediaInfo.urlString)

        val progressBar = findViewById<ProgressBar>(R.id.progressBar)
        val webViewClient = object : WebViewClient() {
            override fun onPageStarted(view: WebView?, url: String?, favicon: Bitmap?) {
                progressBar.visibility = View.VISIBLE
                super.onPageStarted(view, url, favicon)
            }

            override fun shouldOverrideUrlLoading(view: WebView?, request: WebResourceRequest?): Boolean {
                progressBar.visibility = View.VISIBLE
                return super.shouldOverrideUrlLoading(view, request)
            }

            override fun onPageFinished(view: WebView?, url: String?) {
                progressBar.visibility = View.INVISIBLE
                super.onPageFinished(view, url)
            }

            override fun onReceivedError(view: WebView?, request: WebResourceRequest?, error: WebResourceError?) {
                progressBar.visibility = View.INVISIBLE
                Log.w(tag, " onReceivedError:" + error.toString())
                super.onReceivedError(view, request, error)
            }
        }
        web.webViewClient = webViewClient
        handler = Handler()
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            android.R.id.home -> {
                this.finish()
                return true
            }
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onPause() {
        web.onPause()
        super.onPause()
    }

    override fun onResume() {
        createMediaSession()
        web.onResume()
        super.onResume()
    }

    override fun onDestroy() {
        releaseMediaSession()
        web.destroy()
        super.onDestroy()
    }


    override fun onBackPressed() {
        val web = findViewById<WebView>(R.id.webview)
        if (web.canGoBack()) {
            web.goBack()
            return
        }
        super.onBackPressed()
    }

    private fun createMediaSession() {
        if (this.mSession === null) {
            mSession = MediaSession(this, tag)
            mSession?.setCallback(mSessionCallback)
            mSession?.isActive = true
        }
    }

    private fun releaseMediaSession() {
        Log.v(tag, "releaseMediaSession() mSession=$mSession")
        mSession?.setCallback(null)
        mSession?.isActive = false
        mSession?.release()
        mSession = null
    }

    private val mSessionCallback = object : MediaSession.Callback() {
        override fun onMediaButtonEvent(mediaIntent: Intent): Boolean {
            if (Intent.ACTION_MEDIA_BUTTON == mediaIntent.action) {
                val event = mediaIntent.getParcelableExtra<Parcelable>(Intent.EXTRA_KEY_EVENT) as KeyEvent
                if (KeyEvent.ACTION_DOWN == event.action) {
                    Log.d(tag, "SessionCallback event= $event")
                    when (event.keyCode) {
                        KeyEvent.KEYCODE_MEDIA_PREVIOUS -> this@OnlineEnglishLoader.onRewind()
                        KeyEvent.KEYCODE_MEDIA_NEXT -> onPausePlay()
                        KeyEvent.KEYCODE_MEDIA_PLAY_PAUSE -> onPausePlay()
                        KeyEvent.KEYCODE_MEDIA_PAUSE -> onPausePlay()
                        KeyEvent.KEYCODE_MEDIA_PLAY -> onPausePlay()
                    }
                    return true
                }
            }
            return false
        }
    }

    private fun onPausePlay() {
        runJS(mediaInfo.replayJS)
    }

    private fun onRewind() {
        runJS(mediaInfo.rewindJS)
    }

    private fun runJS(jsString: String?) {
        val web = findViewById<WebView>(R.id.webview)
        web.evaluateJavascript(jsString, null)
    }
}

