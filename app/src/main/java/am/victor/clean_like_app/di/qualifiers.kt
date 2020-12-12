package am.victor.clean_like_app.di

import javax.inject.Qualifier

@Qualifier
@Retention(AnnotationRetention.SOURCE)
annotation class ApplicationContext

@Qualifier
@Retention(AnnotationRetention.SOURCE)
annotation class ActivityContext

@Qualifier
@Retention(AnnotationRetention.SOURCE)
annotation class CreateAccountEmailConfirmation

@Qualifier
@Retention(AnnotationRetention.SOURCE)
annotation class CreateAccountPhoneConfirmation

@Qualifier
@Retention(AnnotationRetention.SOURCE)
annotation class ResetPasswordEmailConfirmation

@Qualifier
@Retention(AnnotationRetention.SOURCE)
annotation class ResetPasswordPhoneConfirmation