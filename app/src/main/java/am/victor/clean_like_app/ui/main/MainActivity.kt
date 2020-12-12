package am.victor.clean_like_app.ui.main

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import am.victor.clean_like_app.R
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        backButton.setOnClickListener { finish() }
    }
}