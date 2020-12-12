package am.victor.clean_like_app.use_cases

import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable

abstract class BaseUseCase<Params, Observer> {

    private val compositeDisposable: CompositeDisposable = CompositeDisposable()

    protected fun addDisposable(disposable: Disposable) {
        this.compositeDisposable.add(disposable)
    }

    abstract fun execute(params: Params, observer: Observer, doOnEvent: (() -> Unit)? = null)

    fun clear() {
        if (!this.compositeDisposable.isDisposed)
            this.compositeDisposable.dispose()
    }
}