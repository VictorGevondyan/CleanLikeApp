package am.victor.clean_like_app.di.module

import android.content.Context
import androidx.lifecycle.ViewModelProvider
import am.victor.clean_like_app.di.ActivityContext
import am.victor.clean_like_app.ui.base.BaseDialog
import am.victor.clean_like_app.ui.setup_profile_customer.general_data.view.EVColorsAdapter
import am.victor.clean_like_app.ui.setup_profile_customer.general_data.viewmodel.ChooseEVColorDialogViewModel
import am.victor.clean_like_app.use_cases.ChooseEVColorUseCase
import am.victor.clean_like_app.utils.ViewModelProviderFactory
import dagger.Module
import dagger.Provides

@Module
class DialogModule(private val dialog: BaseDialog) {

    @ActivityContext
    @Provides
    fun provideContext(): Context = dialog.requireContext()

    @Provides
    fun provideChooseEVColorViewModel(
        chooseEVColorUseCase: ChooseEVColorUseCase
    ): ChooseEVColorDialogViewModel =
        ViewModelProvider(
            dialog,
            ViewModelProviderFactory(ChooseEVColorDialogViewModel::class) {
                ChooseEVColorDialogViewModel(chooseEVColorUseCase)
            }
        ).get(ChooseEVColorDialogViewModel::class.java)

    @Provides
    fun provideEvColorsAdapter(chooseEVColorDialogViewModel: ChooseEVColorDialogViewModel): EVColorsAdapter =
        EVColorsAdapter { chooseEVColorDialogViewModel.onColorSelected(it) }

}