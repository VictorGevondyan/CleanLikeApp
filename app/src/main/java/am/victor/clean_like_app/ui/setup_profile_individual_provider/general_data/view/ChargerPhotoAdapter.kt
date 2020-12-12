package am.victor.clean_like_app.ui.setup_profile_individual_provider.general_data.view

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import am.victor.clean_like_app.databinding.RvItemChargerPhotoesBinding
import am.victor.clean_like_app.ui.setup_profile_individual_provider.ChargerPhoto
import am.victor.clean_like_app.ui.setup_profile_individual_provider.general_data.viewmodel.IPSetupGeneralDataViewModel

class ChargerPhotoAdapter(
    private val ipSetupGeneralDataViewModel: IPSetupGeneralDataViewModel
) : RecyclerView.Adapter<ChargerPhotoAdapter.ChargerPhotoVH>() {

    private val chargerPhotosList: ArrayList<ChargerPhoto> =
        arrayListOf(ChargerPhoto(0, null, false))

    inner class ChargerPhotoVH(val binding: RvItemChargerPhotoesBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(position: Int) {

            binding.apply {

                val chargerPhoto = chargerPhotosList[position]
                this.chargerPhoto = chargerPhoto

                if (position == chargerPhotosList.size - 1) {
                    layoutPhoto.setOnClickListener {
                        ipSetupGeneralDataViewModel.onClickAddPhoto()
                    }
                } else if (position < chargerPhotosList.size - 1) {
                    buttonDeletePhoto.setOnClickListener {
                        ipSetupGeneralDataViewModel.onClickDeletePhoto(position)
                    }
                }

            }

        }

    }

    fun setData(image: Any) {
        chargerPhotosList.add(
            chargerPhotosList.size - 1,
            ChargerPhoto(chargerPhotosList.size - 1, image, true)
        )
        notifyDataSetChanged()
    }

    fun update(position: Int) {
        chargerPhotosList.removeAt(position)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ChargerPhotoVH {
        val inflater = LayoutInflater.from(parent.context)
        val binding = RvItemChargerPhotoesBinding.inflate(inflater, parent, false)
        return ChargerPhotoVH(binding)
    }

    override fun onBindViewHolder(holder: ChargerPhotoVH, position: Int) {
        holder.bind(position)
    }

    override fun getItemCount(): Int {
        return if (chargerPhotosList.size < 5) {
            chargerPhotosList.size
        } else {
            5
        }
    }
}