package am.victor.clean_like_app.ui.base

import android.os.Bundle
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.google.android.material.textfield.TextInputEditText
import com.messapps.market_app.utils.KeyboardUtil

abstract class BaseActivity : AppCompatActivity(), Base {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(layoutID)
        setupUI(findViewById<ViewGroup>(android.R.id.content).getChildAt(0))
        initialize(savedInstanceState)
        setListeners()
    }

    fun showFragment(
        container: Int,
        fragment: Fragment
    ) {

        val transaction = supportFragmentManager.beginTransaction()

        transaction
            .add(container, fragment, fragment::class.java.name)
            .addToBackStack(fragment::class.java.name)
            .commit()
    }

    fun showInitialFragment(container: Int, fragment: Fragment) {

        supportFragmentManager.beginTransaction()
            .replace(container, fragment, fragment::class.java.name)
            .commit()
    }

    private fun setupUI(view: View) {

        if (view !is TextInputEditText && view !is EditText) {
            view.setOnTouchListener { _, _ ->
                KeyboardUtil.hideFrom(this)
                false
            }
        }

        if (view is ViewGroup) {
            for (i in 0 until view.childCount) {
                val innerView = view.getChildAt(i)
                setupUI(innerView)
            }
        }
    }

    override fun onBackPressed() {

    }
}