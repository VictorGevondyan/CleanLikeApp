package am.victor.clean_like_app.utils.extensions

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.inputmethod.InputMethodManager
import androidx.annotation.IdRes
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentTransaction
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

private const val EXTRA_VM = "extra_vm"
private const val EXTRA_IS_FROM_PARENT = "extra_is_from_parent"

fun FragmentActivity.showFragment(
    @IdRes container: Int, fragment: Fragment,
    addToBackStack: Boolean = false
) {
    if (addToBackStack) {
        supportFragmentManager.inTransaction {
            add(container, fragment, fragment.javaClass.name)
            addToBackStack(fragment.javaClass.name)
        }
    } else {
        supportFragmentManager.apply {
            popBackStack(null, FragmentManager.POP_BACK_STACK_INCLUSIVE)
            inTransaction { replace(container, fragment, fragment.javaClass.name) }
        }
    }
}

fun <T> Fragment.registerContract(
    clazz: Class<T>,
    fromParent: Boolean
) {
    this.arguments?.apply {
        putSerializable(EXTRA_VM, clazz)
        putBoolean(EXTRA_IS_FROM_PARENT, fromParent)
    }
}

fun Fragment.replaceCurrentFragment(fragment: Fragment, addToBackStack: Boolean = false) {
    requireActivity().showFragment(id, fragment, addToBackStack)
}

fun Fragment.finish() {
    requireActivity().supportFragmentManager.popBackStack()
}

fun Fragment.finishActivity() {
    requireActivity().finish()
}

/**
 * Allows calls like
 *
 * `supportFragmentManager.inTransaction { add(...) }`
 */
inline fun FragmentManager.inTransaction(func: FragmentTransaction.() -> FragmentTransaction) {
    beginTransaction().setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE).func().commit()
}

val Context.inputManager: InputMethodManager
    get() = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager

fun FragmentActivity.hideKeyboard() {

    if (this.currentFocus != null)
        inputManager.hideSoftInputFromWindow(this.currentFocus!!.windowToken, 0)
}

fun Fragment.hideKeyboard() {
    if (view != null && view!!.requestFocus())
        requireActivity().inputManager.hideSoftInputFromWindow(view!!.windowToken, 0)
}

/**
 * For Actvities, allows declarations like
 * ```
 * val myViewModel = viewModelProvider(myViewModelFactory)
 * ```
 */
inline fun <reified VM : ViewModel> FragmentActivity.viewModelProvider() =
    ViewModelProvider(this).get(VM::class.java)

/**
 * For Fragments, allows declarations like
 * ```
 * val myViewModel = viewModelProvider(myViewModelFactory)
 * ```
 */
inline fun <reified VM : ViewModel> Fragment.viewModelProvider() =
    ViewModelProvider(this).get(VM::class.java)

/**
 * Like [Fragment.viewModelProvider] for Fragments that want a [ViewModel] scoped to the Activity.
 */
inline fun <reified VM : ViewModel> Fragment.activityViewModelProvider() =
    ViewModelProvider(this).get(VM::class.java)

/**
 * Like [Fragment.viewModelProvider] for Fragments that want a [ViewModel] scoped to the parent
 * Fragment.
 */
inline fun <reified VM : ViewModel> Fragment.parentViewModelProvider() =
    ViewModelProvider(this).get(VM::class.java)

inline fun <reified T : Activity> Activity.launchActivityForResult(
    requestCode: Int,
    options: Bundle? = null,
    noinline init: Intent.() -> Unit = {}
) {
    val intent = newIntent<T>()
    intent.init()
    startActivityForResult(intent, requestCode, options)
}

inline fun <reified T : Activity> Context.launchActivity(
    options: Bundle? = null,
    noinline init: Intent.() -> Unit = {}
) {
    val intent = newIntent<T>()
    intent.init()
    startActivity(intent, options)
}

inline fun <reified T : Activity> Fragment.launchActivityForResult(
    requestCode: Int,
    options: Bundle? = null,
    noinline init: Intent.() -> Unit = {}
) {
    val intent = newIntent<T>()
    intent.init()
    startActivityForResult(intent, requestCode, options)
}

inline fun <reified T : Activity> Fragment.launchActivity(
    options: Bundle? = null,
    noinline init: Intent.() -> Unit = {}
) {
    val intent = newIntent<T>()
    intent.init()
    startActivity(intent, options)
}

/**Creates new [Intent] with given type*/
inline fun <reified T : Activity> Context.newIntent(): Intent = Intent(this, T::class.java)

/**Creates new [Intent] with given type*/
inline fun <reified T : Activity> Fragment.newIntent(): Intent = requireActivity().newIntent<T>()

val selectImageIntent = Intent(Intent.ACTION_GET_CONTENT).apply {
    type = "image/*"
    addCategory(Intent.CATEGORY_OPENABLE)
}

fun selectImage(requestCode: Int, activity: Activity? = null, fragment: Fragment? = null) {

    activity?.apply {
        if (selectImageIntent.resolveActivity(packageManager) != null) {
            startActivityForResult(selectImageIntent, requestCode)
        }
    }

    fragment?.apply {
        if (selectImageIntent.resolveActivity(requireActivity().packageManager) != null) {
            startActivityForResult(selectImageIntent, requestCode)
        }
    }


}

typealias EmptyCallback = () -> Unit
typealias BooleanCallback = (bool: Boolean) -> Unit