package am.victor.clean_like_app.data.network

import com.google.gson.Gson
import com.google.gson.GsonBuilder
import am.victor.clean_like_app.victorApplication
import am.victor.clean_like_app.data.local.PreferenceHelper
import am.victor.clean_like_app.data.network.type_adapters.VerificationTypeDeserializer
import am.victor.clean_like_app.data.network.type_adapters.VerificationTypeSerializer
import am.victor.clean_like_app.ui.confirmation.ConfirmationViewModel
import com.readystatesoftware.chuck.ChuckInterceptor
import io.reactivex.schedulers.Schedulers
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.create
import java.util.concurrent.TimeUnit

object NetworkWrapper {

    private const val API_DATE_FORMAT = "MM/dd/yyyy"

    fun getClient(): Retrofit {
        return Retrofit.Builder()
            .baseUrl(_root_ide_package_.am.victor.clean_like_app.BuildConfig.BASE_URL)
            .addCallAdapterFactory(RxJava2CallAdapterFactory.createWithScheduler(Schedulers.io()))
            .addConverterFactory(GsonConverterFactory.create(provideGson()))
            .addConverterFactory(EnumConverterFactory())
            .client(provideOkHttpClient())
            .build()
    }

    private fun provideGson(): Gson {
        return GsonBuilder()
            .setLenient()
            .setDateFormat(API_DATE_FORMAT)
            .registerTypeAdapter(
                ConfirmationViewModel.VerificationType::class.java, VerificationTypeSerializer()
            )
            .registerTypeAdapter(
                ConfirmationViewModel.VerificationType::class.java, VerificationTypeDeserializer()
            )
            .create()
    }

    private fun provideOkHttpClient(): OkHttpClient {
        return OkHttpClient.Builder()
            .addInterceptor(provideHeadersInterceptor())
            .addInterceptor(ChuckInterceptor(victorApplication.getContext()))
            .connectTimeout(60 * 1000, TimeUnit.MILLISECONDS)
            .writeTimeout(60 * 1000, TimeUnit.MILLISECONDS)
            .readTimeout(60 * 1000, TimeUnit.MILLISECONDS)
            .build()
    }

    private fun provideHeadersInterceptor(): Interceptor {
        return Interceptor {

            val builder = it.request().newBuilder().apply {
                header(ApiHeaders.X_API_KEY, _root_ide_package_.am.victor.clean_like_app.BuildConfig.API_KEY)
                header(ApiHeaders.X_DEVICE_TYPE.first, ApiHeaders.X_DEVICE_TYPE.second)
                header(ApiHeaders.X_DEVICE_ID, PreferenceHelper.deviceId)

                PreferenceHelper.apply {
                    sessionID?.also { value ->
                        header(ApiHeaders.X_SESSION_ID, value)
                    }
                }
            }

            it.proceed(builder.build())
        }
    }

    fun getGoogleClient(): Retrofit {
        return Retrofit.Builder()
            .baseUrl(_root_ide_package_.am.victor.clean_like_app.BuildConfig.GOOGLE_BASE_URL)
            .addCallAdapterFactory(RxJava2CallAdapterFactory.createWithScheduler(Schedulers.io()))
            .addConverterFactory(GsonConverterFactory.create(provideGson()))
            .addConverterFactory(EnumConverterFactory())
            .client(provideOkHttpClient())
            .build()
    }
}

/**
 * create Services for retrofit in easy way
 * Allows calls like
 *
 * `createService<AuthService>()`
 */
inline fun <reified T> createService(): T = NetworkWrapper.getClient().create()
inline fun <reified T> createGoogleService(): T = NetworkWrapper.getGoogleClient().create()