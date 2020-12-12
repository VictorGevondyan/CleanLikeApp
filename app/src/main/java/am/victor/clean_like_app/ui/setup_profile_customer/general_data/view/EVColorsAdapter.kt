package am.victor.clean_like_app.ui.setup_profile_customer.general_data.view

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import am.victor.clean_like_app.data.network.models.ColorInfo
import am.victor.clean_like_app.databinding.RvItemEvColorBinding
import javax.inject.Inject

class EVColorsAdapter @Inject constructor(
    private val onColorSelected: (String) -> Unit
) : RecyclerView.Adapter<EVColorsAdapter.EVColorViewHolder>() {

    private var colorsList: List<ColorInfo> = emptyList()

    private var selectedPosition: Int = -1

    /*
     This is for handling RecyclerView specific lifecycle,
     when some operations cannot be performed during ViewHolder binding
     */
    private var onBind = false

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): EVColorViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = RvItemEvColorBinding.inflate(inflater, parent, false)
        return EVColorViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return colorsList.size
    }

    override fun onBindViewHolder(holder: EVColorViewHolder, position: Int) {
        holder.bind(position)
    }

    fun setData(colorsList: List<ColorInfo>, previousChosenColor: String) {

        this.colorsList = colorsList

        val previousChosenColorItem = colorsList.firstOrNull { it.name == previousChosenColor }
        val itemIndex = colorsList.indexOf(previousChosenColorItem)
        selectedPosition = itemIndex
        notifyDataSetChanged()

    }

    inner class EVColorViewHolder(val binding: RvItemEvColorBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(position: Int) {
            binding.apply {

                onBind = true

                val colorInfo = colorsList[position]

                colorRb.setOnCheckedChangeListener { _, isChecked ->

                    if (!onBind) {
                        selectedPosition = adapterPosition
                        notifyDataSetChanged()
                        onColorSelected(colorInfo.name)
                    }

                }

                // Set checked state for item
                colorInfo.chosen = selectedPosition == position

                color.setBackgroundColor(colorInfo.hexCode.toColorInt())

                this.colorInfo = colorInfo
                executePendingBindings()

                onBind = false

            }

        }

    }

}