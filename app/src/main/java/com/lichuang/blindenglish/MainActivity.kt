package com.lichuang.blindenglish

import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.RecyclerView
import android.view.Window
import android.view.WindowManager


class MainActivity : AppCompatActivity() {

    private val tag = ":MainActivity"
    private lateinit var recycleView: RecyclerView

    override fun onCreate(savedInstanceState: Bundle?) {
        this.requestWindowFeature(Window.FEATURE_NO_TITLE)
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        recycleView = findViewById(R.id.list)
        recycleView.adapter =
                MediaAdapter({ clickedItem -> mediaItemClicked(clickedItem) }, DataProvider.getMediaItems())
    }

    private fun mediaItemClicked(clickedItem: MediaItem) {
        var intent = Intent(this, OnlineEnglishLoader::class.java)
        intent.putExtra(OnlineEnglishLoader.MEDIA_INFO, clickedItem)
        startActivity(intent)
    }

    override fun onResume() {
        super.onResume()
        window.addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
    }
}

