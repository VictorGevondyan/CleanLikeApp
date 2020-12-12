package am.victor.clean_like_app.data.network.type_adapters

import com.google.gson.JsonDeserializationContext
import com.google.gson.JsonDeserializer
import com.google.gson.JsonElement
import am.victor.clean_like_app.data.network.ApiForm
import am.victor.clean_like_app.ui.confirmation.ConfirmationViewModel
import java.lang.reflect.Type

class VerificationTypeDeserializer : JsonDeserializer<ConfirmationViewModel.VerificationType> {

    override fun deserialize(
        json: JsonElement?,
        typeOfT: Type?,
        context: JsonDeserializationContext?
    ): ConfirmationViewModel.VerificationType {
        return when (json?.asString) {
            ApiForm.CHECK_CURRENT_EMAIL -> ConfirmationViewModel.VerificationType.CHECK_CURRENT_EMAIL
            ApiForm.CHECK_CHANGE_EMAIL -> ConfirmationViewModel.VerificationType.CHECK_CHANGE_EMAIL
            ApiForm.CHECK_NEW_EMAIL -> ConfirmationViewModel.VerificationType.CHECK_NEW_EMAIL
            ApiForm.RESET_PASSWORD -> ConfirmationViewModel.VerificationType.RESET_PASSWORD
            ApiForm.CHECK_CURRENT_PHONE -> ConfirmationViewModel.VerificationType.CHECK_CURRENT_PHONE
            ApiForm.CHECK_NEW_PHONE -> ConfirmationViewModel.VerificationType.CHECK_NEW_PHONE
            else -> throw IllegalArgumentException(VerificationTypeSerializer.UNKNOWN_VERIFICATION_TYPE)
        }
    }
}