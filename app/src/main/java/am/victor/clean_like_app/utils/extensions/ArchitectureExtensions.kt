package am.victor.clean_like_app.utils.extensions

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

inline fun <reified VM : ViewModel> FragmentActivity.getViewModel() =
    ViewModelProvider(this).get(VM::class.java)

inline fun <reified VM : ViewModel> Fragment.getViewModel() =
    ViewModelProvider(this).get(VM::class.java)

inline fun <reified VM : ViewModel> Fragment.getParentActivityViewModel() =
    ViewModelProvider(this).get(VM::class.java)

inline fun <reified VM : ViewModel> Fragment.getParentFragmentViewModel() =
    ViewModelProvider(parentFragment!!).get(VM::class.java)
