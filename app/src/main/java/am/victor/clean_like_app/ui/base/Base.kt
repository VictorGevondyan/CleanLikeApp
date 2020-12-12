package am.victor.clean_like_app.ui.base

import android.os.Bundle

interface Base {

    val layoutID: Int

    fun initialize(savedInstanceState: Bundle?) {}
    fun setListeners() {}
}