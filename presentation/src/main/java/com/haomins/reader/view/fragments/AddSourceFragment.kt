package com.haomins.reader.view.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.widget.doOnTextChanged
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import com.haomins.reader.R
import com.haomins.reader.viewModels.AddSourceViewModel
import dagger.android.support.AndroidSupportInjection
import kotlinx.android.synthetic.main.fragment_add_source.*
import javax.inject.Inject

class AddSourceFragment : Fragment() {

    companion object {
        const val TAG = "AddSourceFragment"
    }

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory
    private lateinit var addSourceViewModel: AddSourceViewModel

    private val isSourceAddedObserver by lazy {
        Observer<Boolean> {
            if (it) {
                //successfully added
            } else {
                //not success
            }
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_add_source, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        AndroidSupportInjection.inject(this)
        addSourceViewModel = ViewModelProviders.of(this, viewModelFactory)[AddSourceViewModel::class.java]
        registerLiveDataObservers()
        configMediumFeedEditText()
        setOnclickListeners()
    }

    private fun registerLiveDataObservers() {
        addSourceViewModel.isSourceAdded.observe(viewLifecycleOwner, isSourceAddedObserver)
    }

    private fun setOnclickListeners() {
        add_feed_button.setOnClickListener { if (!feed_input_box.text.isNullOrEmpty()) addFeedOnClick() }
        add_medium_feed_button.setOnClickListener { if (!medium_feed_desc.text.isNullOrEmpty()) addMediumFeedOnClick() }
    }

    private fun addFeedOnClick() {
        addSourceViewModel.addSource(source = feed_input_box.text.toString())
    }

    private fun addMediumFeedOnClick() {
        addSourceViewModel.addMediumSource(source = medium_feed_input_box.text.toString())
    }

    private fun configMediumFeedEditText() {
        medium_feed_input_box.doOnTextChanged { text, _, _, after ->
            when (after > 0) {
                true -> medium_feed_desc.text = getString(R.string.medium_feed_sample_desc_template, text)
                false -> medium_feed_desc.text = getString(R.string.medium_feed_sample_desc)
            }
        }
    }
}