package com.haomins.reader.view.fragments.articles

import android.content.Intent
import android.graphics.Bitmap
import android.net.Uri
import android.os.Bundle
import android.os.Handler
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.webkit.WebResourceRequest
import android.webkit.WebSettings
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.webkit.ServiceWorkerWebSettingsCompat.CacheMode
import androidx.webkit.WebSettingsCompat
import androidx.webkit.WebViewFeature
import com.haomins.reader.R
import com.haomins.reader.databinding.FragmentArticleDetailBinding
import com.haomins.reader.view.activities.ArticleDetailActivity.Companion.ARTICLE_ITEM_ID
import com.haomins.reader.viewModels.ArticleDetailViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ArticleDetailFragment : Fragment() {

    companion object {
        const val TAG = "ArticleDetailFragment"

        private const val PROGRESS_BAR_DELAY = 1500L
        private const val BASE_URL = ""
        private const val MIME_TYPE = "text/html"
        private const val ENCODING = "UTF-8"
        private const val IMAGE_CSS_STYLE =
            "<style> img { display: block; max-width: 100%; height: auto; } </style>"
    }

    data class ArticleDetailUiItem(
        val title: String,
        val author: String,
        val updateTime: String,
        val contentHtmlData: String
    )

    private val articleDetailViewModel by viewModels<ArticleDetailViewModel>()
    private var articleId: String = ""

    private lateinit var binding: FragmentArticleDetailBinding
    private val handler by lazy {
        Handler()
    }

    private val contentDataObserver by lazy {
        Observer<ArticleDetailUiItem> {
            with(binding) {
                articleDetailTitleTextView.text = it.title
                articleDetailAuthorTextView.text = it.author
                articleDetailUpdateTimeTextView.text = it.updateTime
                articleDetailWebView.loadDataWithBaseURL(
                    BASE_URL,
                    (IMAGE_CSS_STYLE + it.contentHtmlData),
                    MIME_TYPE,
                    ENCODING,
                    BASE_URL
                )
            }

        }
    }

    private val webViewClient by lazy {
        object : WebViewClient() {

            override fun onPageStarted(view: WebView?, url: String?, favicon: Bitmap?) {
                super.onPageStarted(view, url, favicon)
                showProgressBar()
            }

            override fun onPageFinished(view: WebView?, url: String?) {
                super.onPageFinished(view, url)
                hideProgressBar()
            }

            override fun shouldOverrideUrlLoading(
                view: WebView,
                request: WebResourceRequest
            ): Boolean {
                launchExternalBrowser(request.url)
                return true
            }

        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentArticleDetailBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        loadArticleId(arguments)
        showArticle()
        registerLiveDataObservers()
        configWebView()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        handler.removeCallbacksAndMessages(null)
    }

    private fun configWebView() {
        checkDarkModeSupport()
        with(binding) {
            articleDetailWebView.webViewClient = webViewClient
            articleDetailWebView.settings.apply {
                domStorageEnabled = true
                loadsImagesAutomatically = true
                cacheMode = WebSettings.LOAD_DEFAULT
                mixedContentMode = WebSettings.MIXED_CONTENT_ALWAYS_ALLOW
            }
        }
    }

    private fun checkDarkModeSupport() {
        if (WebViewFeature.isFeatureSupported(WebViewFeature.ALGORITHMIC_DARKENING)
            && articleDetailViewModel.isDarkModeEnabled()
        ) {
            context?.getColor(R.color.default_background)?.let {
                binding.articleDetailWebView.setBackgroundColor(it)
            }
            WebSettingsCompat.setAlgorithmicDarkeningAllowed(
                binding.articleDetailWebView.settings,
                true
            )
        }
    }

    private fun registerLiveDataObservers() {
        articleDetailViewModel.contentDataForDisplay.observe(
            viewLifecycleOwner,
            contentDataObserver
        )
    }

    private fun loadArticleId(bundle: Bundle?) {
        bundle?.let {
            it.getString(ARTICLE_ITEM_ID)?.let { id ->
                articleId = id
            }
        }
    }

    private fun showArticle() {
        articleDetailViewModel.loadArticleDetail(articleId)
    }

    private fun hideProgressBar() {
        handler.postDelayed(
            {
                binding.topProgressBar.bottomLoadingProgressBar.visibility = View.INVISIBLE
            },
            PROGRESS_BAR_DELAY
        )
    }

    private fun showProgressBar() {
        binding.topProgressBar.bottomLoadingProgressBar.visibility = View.VISIBLE
    }

    private fun launchExternalBrowser(url: Uri) {
        startActivity(Intent(Intent.ACTION_VIEW, url))
    }

}