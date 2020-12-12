package am.victor.clean_like_app.utils

import android.os.CountDownTimer
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleObserver
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.OnLifecycleEvent
import java.text.DecimalFormat

class Timer(
    millis: Long,
    lifecycleOwner: LifecycleOwner,
    onTick: (String) -> Unit,
    onFinish: () -> Unit
) : LifecycleObserver {

    private var started = false
    private val lifecycle = lifecycleOwner.lifecycle

    init {
        lifecycle.addObserver(this)
    }

    private val timer = object : CountDownTimer(millis, 1_000) {
        override fun onFinish() {
            if (lifecycle.currentState.isAtLeast(Lifecycle.State.STARTED)) {
                onFinish()
            }
        }

        override fun onTick(millisUntilFinished: Long) {
            if (lifecycle.currentState.isAtLeast(Lifecycle.State.STARTED)) {

                val minutes = DecimalFormat("00").format(millisUntilFinished / (60 * 1_000) % 60)
                val seconds = DecimalFormat("00").format(millisUntilFinished / 1_000 % 60)

                onTick("$minutes:$seconds")
            }
        }
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_START)
    fun start() {
        if (!started && timer != null) {
            started = true
            timer.start()
        }
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_DESTROY)
    fun stop() {
        timer.cancel()
    }
}