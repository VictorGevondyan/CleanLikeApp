package am.victor.clean_like_app.ui.setup_profile_individual_provider.general_data.view

import android.graphics.Rect
import android.view.View
import androidx.recyclerview.widget.RecyclerView

class ChargerRVMarginItemDecoration(private val interval: Int) : RecyclerView.ItemDecoration() {

    override fun getItemOffsets(
        outRect: Rect, view: View,
        parent: RecyclerView, state: RecyclerView.State
    ) {
        with(outRect) {
            right = interval
        }
    }
}