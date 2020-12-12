package am.victor.clean_like_app.di.component

import am.victor.clean_like_app.di.DialogScope
import am.victor.clean_like_app.di.module.DialogModule
import am.victor.clean_like_app.ui.setup_profile_customer.general_data.view.ChooseEVColorDialog
import dagger.Component

@DialogScope
@Component(dependencies = [ActivityComponent::class], modules = [DialogModule::class])
interface DialogComponent {

    fun inject(dialog: ChooseEVColorDialog)

}