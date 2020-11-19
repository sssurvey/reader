package com.haomins.reader.view.fragments

import android.content.Context
import android.graphics.Bitmap
import android.os.Bundle
import android.os.Handler
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.webkit.WebSettings
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.webkit.WebSettingsCompat
import androidx.webkit.WebViewFeature
import com.haomins.reader.R
import com.haomins.reader.ReaderApplication
import com.haomins.reader.view.activities.ArticleDetailActivity.Companion.ARTICLE_ITEM_ID
import com.haomins.reader.viewModels.ArticleDetailViewModel
import kotlinx.android.synthetic.main.fragment_article_detail.*
import javax.inject.Inject

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

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory

    private val articleDetailViewModel by viewModels<ArticleDetailViewModel> { viewModelFactory }
    private var articleId: String = ""

    private val handler by lazy {
        Handler()
    }

    private val contentDataObserver by lazy {
        Observer<ArticleDetailUiItem> {
            article_detail_title_text_view.text = it.title
            article_detail_author_text_view.text = it.author
            article_detail_update_time_text_view.text = it.updateTime
            article_detail_web_view.loadDataWithBaseURL(
                BASE_URL,
                (IMAGE_CSS_STYLE + it.contentHtmlData),
                MIME_TYPE,
                ENCODING,
                BASE_URL
            )
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
        }
    }

    override fun onAttach(context: Context) {
        (requireActivity().application as ReaderApplication).appComponent.viewModelComponent()
            .build().inject(this)
        super.onAttach(context)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_article_detail, container, false)
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
        article_detail_web_view.webViewClient = webViewClient
        article_detail_web_view.settings.apply {
            domStorageEnabled = true
            loadsImagesAutomatically = true
            setAppCacheEnabled(true)
            mixedContentMode = WebSettings.MIXED_CONTENT_ALWAYS_ALLOW
        }
    }

    private fun checkDarkModeSupport() {
        if (WebViewFeature.isFeatureSupported(WebViewFeature.FORCE_DARK)
            && articleDetailViewModel.isDarkModeEnabled()
        ) {
            context?.getColor(R.color.default_background)?.let {
                article_detail_web_view.setBackgroundColor(it)
            }
            WebSettingsCompat.setForceDark(
                article_detail_web_view.settings,
                WebSettingsCompat.FORCE_DARK_ON
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
                top_progress_bar?.let {
                    top_progress_bar.visibility = View.INVISIBLE
                }
            },
            PROGRESS_BAR_DELAY
        )
    }

    private fun showProgressBar() {
        top_progress_bar?.let {
            top_progress_bar.visibility = View.VISIBLE
        }
    }

}