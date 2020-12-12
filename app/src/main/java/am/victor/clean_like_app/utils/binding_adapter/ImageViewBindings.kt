package am.victor.clean_like_app.utils.binding_adapter

import android.graphics.drawable.Drawable
import android.widget.ImageView
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.CircleCrop
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import java.io.File

@BindingAdapter("app:srcCompat")
fun setImageResource(view: ImageView, resource: Int) {
    view.setImageResource(resource)
}

@BindingAdapter("photo_file", "circle")
fun setPhotoFromFile(view: ImageView, file: File?, circle: Boolean) {

    file?.also {
        val builder = Glide.with(view)
            .load(it)

        if (circle)
            builder.transform(CircleCrop())

        builder.into(view)
    }
}

@BindingAdapter(
    "image_source",
    "thumb_source",
    "circle",
    "placeholder",
    "center_crop",
    "corners",
    requireAll = false
)
fun setImage(
    view: ImageView,
    source: Any?,
    thumb: Any?,
    circle: Boolean?,
    placeholder: Drawable?,
    centerCrop: Boolean? = false,
    cornersRadius: Float? = 0F
) {

    val builder = Glide.with(view)
        .load(source)

    if (thumb != null)
        builder.thumbnail(
            Glide.with(view)
                .load(thumb)
        )

    if (circle == true)
        builder.circleCrop()

    if (centerCrop == true)
        builder.centerCrop()

    if (cornersRadius != null)
        builder.transform(RoundedCorners(cornersRadius.toInt()))

    if (placeholder != null)
        builder.placeholder(placeholder)

    builder.into(view)
}