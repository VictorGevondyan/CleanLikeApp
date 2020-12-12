package am.victor.clean_like_app.ui.legal_docs

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import am.victor.clean_like_app.R
import am.victor.clean_like_app.ui.base.RxViewModel
import am.victor.clean_like_app.utils.navigation.Navigation

class LegalDocsViewModel(
    private val navigation: Navigation
) : RxViewModel() {

    private val _docURL = MutableLiveData<String>()
    val docURL: LiveData<String>
        get() = _docURL

    private val _screenTitleResourceID = MutableLiveData<Int>()
    val screenTitleResourceID: LiveData<Int>
        get() = _screenTitleResourceID

    private var screen: LegalDocsFragment.Screen? = null

    fun onCreate(screen: LegalDocsFragment.Screen) {
        this.screen = screen
        if (screen == LegalDocsFragment.Screen.PRIVACY)
            _screenTitleResourceID.value = R.string.privacy_policy
        else
            _screenTitleResourceID.value = R.string.terms_and_conditions
    }

    fun onBackButtonClick() {
        navigation.finish()
    }

    override fun clearUseCases() {

    }
}