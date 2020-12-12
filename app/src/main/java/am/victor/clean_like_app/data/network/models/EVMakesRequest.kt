package am.victor.clean_like_app.data.network.models

import com.google.gson.annotations.SerializedName

data class EVMakesRequest(
    val pageIndex: Int,
    @SerializedName("search")
    val searchQuery: String
)