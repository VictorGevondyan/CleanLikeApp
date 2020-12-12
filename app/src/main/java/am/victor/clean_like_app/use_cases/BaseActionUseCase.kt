package am.victor.clean_like_app.use_cases

abstract class BaseActionUseCase<V> {
    abstract fun execute(): V
}