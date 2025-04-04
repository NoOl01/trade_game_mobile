package com.example.trade_game.common

import android.content.Context
import android.webkit.WebView

class NoScrollWebView(context: Context) : WebView(context) {
    override fun onScrollChanged(l: Int, t: Int, oldl: Int, oldt: Int) {
        scrollTo(0, 0)
    }
}
