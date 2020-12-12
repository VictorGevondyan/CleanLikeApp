package am.victor.clean_like_app.ui.setup_profile_customer.general_data.view

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import am.victor.clean_like_app.R
import am.victor.clean_like_app.data.network.models.EVMakesResponse
import am.victor.clean_like_app.data.network.models.EvMakeItem
import am.victor.clean_like_app.data.network.models.PageInfo
import am.victor.clean_like_app.databinding.RvItemEvMakeBinding
import am.victor.clean_like_app.databinding.RvItemEvMakeEmptyBinding
import am.victor.clean_like_app.databinding.RvItemEvMakeTypeBinding
import am.victor.clean_like_app.ui.setup_profile_customer.general_data.viewmodel.EVMakesViewModel
import javax.inject.Inject

class SearchEVMakeAdapter @Inject constructor(
    private val evMakesViewModel: EVMakesViewModel
) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private var isSearch: Boolean = false

    private var makeData: EVMakesResponse =
        EVMakesResponse(PageInfo(0, 0, 0, 0), listOf(), listOf())

    /*
     This is for handling RecyclerView specific lifecycle,
     when some operations cannot be performed during ViewHolder binding
     */

    private var onBind = false

    companion object {

        const val EMPTY = 0
        const val HEADER = 1
        const val FILLED = 2

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {

        val inflater = LayoutInflater.from(parent.context)
        return when (viewType) {

            EMPTY -> {
                val binding = RvItemEvMakeEmptyBinding.inflate(inflater, parent, false)
                EmptyVH(binding)
            }
            HEADER -> {
                val binding = RvItemEvMakeTypeBinding.inflate(inflater, parent, false)
                HeaderVH(binding)
            }
            else -> {
                val binding = RvItemEvMakeBinding.inflate(inflater, parent, false)
                return FilledVH(binding)
            }

        }

    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {

        when (getItemViewType(position)) {

            EMPTY -> {
                (holder as EmptyVH).bind()
            }
            HEADER -> {
                (holder as HeaderVH).bind()
            }
            FILLED -> {

                // When we perform search, headers are disappeared. So we need to normalize item displaying
                val normalizerCoefficient = if (isSearch) 1 else 0
                val popularsSize = makeData.popular.size
                var item: EvMakeItem? = null
                if (position > 0 - normalizerCoefficient && position < popularsSize + 1 - normalizerCoefficient) {
                    item = makeData.popular[position - 1 + normalizerCoefficient]
                } else if (position > popularsSize + 1 - 2 * normalizerCoefficient) {
                    item = makeData.list[position - (popularsSize + 2 - 2 * normalizerCoefficient)]
                }

                (holder as FilledVH).bind(item)

            }

        }

    }

    override fun getItemCount(): Int {

        val normalizerCoefficient = if (isSearch) 2 else 0

        val makeDataSize = makeData.list.size + makeData.popular.size
        return if (makeDataSize == 0) {
            1
        } else {
            makeDataSize + 2 - normalizerCoefficient
        }

    }

    override fun getItemViewType(position: Int): Int {

        return if (makeData.list.size + makeData.popular.size == 0) {
            EMPTY
        } else if (!isSearch && (position == 0 || position == makeData.popular.size + 1)) {
            HEADER
        } else {
            FILLED
        }

    }

    fun setData(
        responseSearchPair: Pair<EVMakesResponse, Boolean>,
        previousChosenMake: String
    ) {

        makeData = responseSearchPair.first
        this.isSearch = responseSearchPair.second

        val previousChosenMakeItem = makeData.popular.firstOrNull { it.name == previousChosenMake }
            ?: makeData.list.firstOrNull { it.name == previousChosenMake }
        previousChosenMakeItem?.isChosen = true

        notifyDataSetChanged()

    }

    inner class EmptyVH(val binding: RvItemEvMakeEmptyBinding) :

        RecyclerView.ViewHolder(binding.root) {

        fun bind() {

            val context = binding.root.context
            binding.apply {
                this.placeholder = context.getString(R.string.no_results_found)
                executePendingBindings()
            }

        }

    }

    inner class HeaderVH(val binding: RvItemEvMakeTypeBinding) :

        RecyclerView.ViewHolder(binding.root) {

        fun bind() {

            binding.apply {

                val context = binding.root.context
                if (adapterPosition == 0) {
                    textMakeType.text = context.getString(R.string.most_popular)
                } else if (adapterPosition == makeData.popular.size + 1) {
                    textMakeType.text = context.getString(R.string.other)
                }
                executePendingBindings()

            }

        }

    }

    inner class FilledVH(val binding: RvItemEvMakeBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(item: EvMakeItem?) {
            binding.apply {

                onBind = true

                binding.root.setOnClickListener {

                    if (!onBind) {

                        val previousClickedItem =
                            makeData.popular.firstOrNull { it.isChosen }
                                ?: makeData.list.firstOrNull { it.isChosen }
                        previousClickedItem?.isChosen = false
                        item?.isChosen = true
                        notifyDataSetChanged()
                        evMakesViewModel.onMakeClicked(item?.name ?: "")

                    }

                }

                textMake.text = item?.name
                // Set checked state for item
                isSelected = item?.isChosen
                executePendingBindings()

                onBind = false

            }
        }
    }

}