package am.victor.clean_like_app.utils.extensions

import android.animation.Animator
import android.animation.ObjectAnimator
import android.view.View

fun View.fadeOut(onAnimationEnd: (() -> Unit)? = null) {
    val animator = ObjectAnimator.ofFloat(this, View.ALPHA, 1f, 0f)
    animator.apply {
        duration = 400
        addListener(object : Animator.AnimatorListener {
            override fun onAnimationRepeat(animation: Animator?) {

            }

            override fun onAnimationEnd(animation: Animator?) {
                this@fadeOut.visibility = View.GONE
                onAnimationEnd?.invoke()
            }

            override fun onAnimationCancel(animation: Animator?) {

            }

            override fun onAnimationStart(animation: Animator?) {
            }
        })
    }

    animator.start()
}

fun View.fadeIn() {
    val animator = ObjectAnimator.ofFloat(this, View.ALPHA, 0f, 1f)
    animator.apply {
        duration = 400
        addListener(object : Animator.AnimatorListener {
            override fun onAnimationRepeat(animation: Animator?) {

            }

            override fun onAnimationEnd(animation: Animator?) {

            }

            override fun onAnimationCancel(animation: Animator?) {

            }

            override fun onAnimationStart(animation: Animator?) {
                this@fadeIn.visibility = View.VISIBLE
            }
        })
    }

    animator.start()
}