package am.victor.clean_like_app.data.network.models

import com.google.gson.annotations.SerializedName

data class EVColorsResponse(
    @SerializedName("list")
    val evColorsList: List<ColorInfo>
)

data class ColorInfo(
    val id: Int,
    val name: String,
    val hexCode: String,
    var chosen: Boolean = false
)