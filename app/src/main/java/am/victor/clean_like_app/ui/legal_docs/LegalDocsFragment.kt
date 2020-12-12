package am.victor.clean_like_app.ui.legal_docs

import android.os.Bundle
import am.victor.clean_like_app.R
import am.victor.clean_like_app.databinding.FragmentLegalDocsBinding
import am.victor.clean_like_app.di.component.FragmentComponent
import am.victor.clean_like_app.ui.base.BindableFragment
import javax.inject.Inject

class LegalDocsFragment : BindableFragment<LegalDocsViewModel, FragmentLegalDocsBinding>() {

    companion object {

        private const val EXTRA_SCREEN = "extra_screen"

        fun newInstance(screen: Screen) = LegalDocsFragment().apply {

            val arguments = Bundle().apply {
                putSerializable(EXTRA_SCREEN, screen)
            }

            this.arguments = arguments
        }
    }

    @Inject
    override lateinit var viewModel: LegalDocsViewModel

    override fun setupBinding(binding: FragmentLegalDocsBinding) {
        binding.model = viewModel
    }

    override val layoutID: Int
        get() = R.layout.fragment_legal_docs

    override fun initialize(savedInstanceState: Bundle?) {
        super.initialize(savedInstanceState)

        viewModel.onCreate(arguments?.getSerializable(EXTRA_SCREEN) as Screen)

        webView.apply {
            settings.javaScriptEnabled = true
            loadUrl("https://policies.google.com/terms?hl=en-US")
        }
    }

    override fun observeFields() {
        viewModel.apply {
            docURL.observe(viewLifecycleOwner, {
                webView.loadUrl(it)
            })
        }
    }

    enum class Screen {
        PRIVACY, TERMS
    }

    override fun injectDependencies(fragmentComponent: FragmentComponent) {
        fragmentComponent.inject(this)
    }
}