package am.victor.clean_like_app.data.network.models

import com.google.gson.annotations.SerializedName

data class EVMakesResponse(

    @field:SerializedName("pageInfo")
    val pageInfo: PageInfo,

    @field:SerializedName("list")
    val list: List<EvMakeItem>,

    @field:SerializedName("popular")
    val popular: List<EvMakeItem>
)

data class PageInfo(

    @field:SerializedName("totalItems")
    val totalItems: Int,

    @field:SerializedName("perPage")
    val perPage: Int,

    @field:SerializedName("totalPages")
    val totalPages: Int,

    @field:SerializedName("currentPage")
    val currentPage: Int
)

data class EvMakeItem(

    @field:SerializedName("name")
    val name: String,

    @field:SerializedName("isPopular")
    val isPopular: Boolean,

    @field:SerializedName("id")
    val id: Int,

    var isChosen: Boolean

)