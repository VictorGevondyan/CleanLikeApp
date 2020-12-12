package am.victor.clean_like_app.use_cases

import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable

abstract class BaseUseCaseWithoutParams<Observer> {

    private val compositeDisposable: CompositeDisposable = CompositeDisposable()

    abstract fun execute(observer: Observer, doOnEvent: (() -> Unit)? = null)

    protected fun addDisposable(disposable: Disposable) {
        this.compositeDisposable.add(disposable)
    }

    fun clear() {
        if (!this.compositeDisposable.isDisposed)
            this.compositeDisposable.dispose()
    }
}