package am.victor.clean_like_app.ui.search_location

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import am.victor.clean_like_app.databinding.RvItemStringBinding
import am.victor.clean_like_app.utils.extensions.hideKeyboard

class PlacesPredictionsAdapter(private val onLocationChosen: (String) -> Unit) :
    RecyclerView.Adapter<PlacesPredictionsAdapter.VH>() {

    private val data = mutableListOf<String>()

    inner class VH(val binding: RvItemStringBinding) : RecyclerView.ViewHolder(binding.root) {

        init {
            binding.root.apply {
                setOnClickListener {
                    onLocationChosen(data[adapterPosition])
                    hideKeyboard()
                }
            }
        }

        fun bind(placeName: String) {
            binding.apply {
                this.name = placeName
                executePendingBindings()
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VH {
        val inflater = LayoutInflater.from(parent.context)
        val binding = RvItemStringBinding.inflate(inflater, parent, false)
        return VH(binding)
    }

    override fun getItemCount(): Int = data.size

    override fun onBindViewHolder(holder: VH, position: Int) {
        holder.bind(data[position])
    }

    fun addItem(placeName: String) {
        this.data.add(placeName)
        notifyDataSetChanged()
    }

    fun clearData() {
        this.data.clear()
        notifyDataSetChanged()
    }

}