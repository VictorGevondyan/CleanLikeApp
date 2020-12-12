package am.victor.clean_like_app.ui.base

sealed class State<out R> {
    data class Success<out T>(val data: T) : State<T>()
    data class Error(val exception: Throwable) : State<Nothing>()
    object Loading : State<Nothing>()
    object HideLoading : State<Nothing>()

    override fun toString(): String {
        return when (this) {
            is Success<*> -> "Success[data=$data]"
            is Error -> "Error[exception=$exception]"
            Loading -> "Loading"
            HideLoading -> "HideLoading"
        }
    }
}

val State<*>.succeeded
    get() = this is State.Success && data != null