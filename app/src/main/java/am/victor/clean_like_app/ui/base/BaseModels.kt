package am.victor.clean_like_app.ui.base

data class NetworkError(
    val code: Int,
    val message: String,
    val title: String? = null
)

data class ApiError(
    val message: NetworkError,
    val code: Int
)