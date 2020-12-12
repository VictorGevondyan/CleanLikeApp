package am.victor.clean_like_app.ui.base

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import am.victor.clean_like_app.utils.extensions.getNetworkError
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable

abstract class RxViewModel : ViewModel() {

    private val _compositeDisposable by lazy { CompositeDisposable() }

    private val _errors = MutableLiveData<Event<NetworkError>>()
    val errors: LiveData<Event<NetworkError>>
        get() = _errors

    private val _loading = MutableLiveData<Boolean>()
    val loading: LiveData<Boolean>
        get() = _loading

    private val _showSnackBar = MutableLiveData<Event<String>>()
    val showSnackBar: LiveData<Event<String>>
        get() = _showSnackBar

    open fun showLoading() {
        _loading.postValue(true)
    }

    open fun hideLoading() {
        _loading.postValue(false)
    }

    protected open fun onError(error: Throwable) {
        onError(error.getNetworkError())
    }

    protected open fun onError(error: NetworkError) {
        _errors.postValue(Event(error))
    }

    protected fun showSnackBar(message: String) {
        _showSnackBar.postValue(Event(message))
    }

    protected fun addDisposable(action: () -> Disposable) {
        _compositeDisposable.add(action.invoke())
    }

    protected abstract fun clearUseCases()

    override fun onCleared() {
        if (!_compositeDisposable.isDisposed)
            _compositeDisposable.dispose()

        clearUseCases()
    }
}