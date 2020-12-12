package am.victor.clean_like_app.data.network.type_adapters

import com.google.gson.JsonElement
import com.google.gson.JsonPrimitive
import com.google.gson.JsonSerializationContext
import com.google.gson.JsonSerializer
import am.victor.clean_like_app.data.network.ApiForm
import am.victor.clean_like_app.ui.confirmation.ConfirmationViewModel
import java.lang.reflect.Type

class VerificationTypeSerializer : JsonSerializer<ConfirmationViewModel.VerificationType> {

    companion object {
        const val UNKNOWN_VERIFICATION_TYPE = "Unknown verification type."
    }

    override fun serialize(
        src: ConfirmationViewModel.VerificationType?,
        typeOfSrc: Type?,
        context: JsonSerializationContext?
    ): JsonElement {
        return JsonPrimitive(
            when (src) {
                ConfirmationViewModel.VerificationType.CHECK_CURRENT_EMAIL -> ApiForm.CHECK_CURRENT_EMAIL
                ConfirmationViewModel.VerificationType.CHECK_CHANGE_EMAIL -> ApiForm.CHECK_CHANGE_EMAIL
                ConfirmationViewModel.VerificationType.CHECK_NEW_EMAIL -> ApiForm.CHECK_NEW_EMAIL
                ConfirmationViewModel.VerificationType.RESET_PASSWORD -> ApiForm.RESET_PASSWORD
                ConfirmationViewModel.VerificationType.CHECK_CURRENT_PHONE -> ApiForm.CHECK_CURRENT_PHONE
                ConfirmationViewModel.VerificationType.CHECK_NEW_PHONE -> ApiForm.CHECK_NEW_PHONE
                else -> throw IllegalArgumentException(UNKNOWN_VERIFICATION_TYPE)
            }
        )
    }
}