package am.victor.clean_like_app.ui.on_boarding

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.viewpager.widget.PagerAdapter
import am.victor.clean_like_app.R
import kotlinx.android.synthetic.main.view_on_boarding_slide.view.*

class OnBoardingAdapter(
    private val context: Context
) : PagerAdapter() {

    override fun instantiateItem(container: ViewGroup, position: Int): Any {

        val resources = context.resources

        val inflater = LayoutInflater.from(context)
        val view = inflater.inflate(
            R.layout.view_on_boarding_slide,
            container,
            false
        )

        val titleRes = when (position) {
            0 -> R.string.get_access_to_ev_chargers_anytime
            1 -> R.string.share_your_charger_and_help_ev_travelers
            else -> R.string.contribute_to_a_cleaner_environment
        }
        val descriptionRes = when (position) {
            0 -> R.string.no_matter_what_time_it_is_you_can_always_access_a_charger_at_your_favorite_locations
            1 -> R.string.have_a_level_2_ev_charger_and_willing_to_help_travelers
            else -> R.string.every_time_when_we_use_electricity_instead_of_gasoline
        }

        val imageRes = when (position) {
            0 -> R.drawable.img_slider_1
            1 -> R.drawable.img_slider_2
            else -> R.drawable.img_slider_3
        }

        view.sliderTitleTV.text = resources.getString(titleRes)
        view.sliderDescriptionTV.text = resources.getString(descriptionRes)
        view.sliderIV.setImageResource(imageRes)

        container.addView(view)

        return view
    }

    override fun destroyItem(container: ViewGroup, position: Int, `object`: Any) {
        container.removeView(`object` as View)
    }

    override fun isViewFromObject(view: View, `object`: Any): Boolean = view == `object`

    override fun getCount(): Int = 3
}