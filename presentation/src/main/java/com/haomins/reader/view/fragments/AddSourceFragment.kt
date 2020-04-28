package com.haomins.reader.view.fragments

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.widget.doOnTextChanged
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.haomins.reader.R
import com.haomins.reader.ReaderApplication
import com.haomins.reader.utils.hideKeyboard
import com.haomins.reader.utils.showSnackbar
import com.haomins.reader.viewModels.AddSourceViewModel
import kotlinx.android.synthetic.main.fragment_add_source.*
import javax.inject.Inject

class AddSourceFragment : Fragment() {

    companion object {
        const val TAG = "AddSourceFragment"
    }

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory
    private val addSourceViewModel by viewModels<AddSourceViewModel> { viewModelFactory }

    private val isSourceAddedObserver by lazy {
        Observer<Pair<AddSourceViewModel.AddSourceStatus, String>> {
            when (it.first) {
                AddSourceViewModel.AddSourceStatus.SUCCESS -> {
                    showSnackbar(it.second)
                    view?.hideKeyboard()
                    resetTextField()
                }
                AddSourceViewModel.AddSourceStatus.FAIL -> {
                    showSnackbar(it.second)
                    view?.hideKeyboard()
                }
                AddSourceViewModel.AddSourceStatus.DEFAULT -> Unit
            }
        }
    }

    override fun onAttach(context: Context) {
        (requireActivity().application as ReaderApplication).appComponent.viewModelComponent().build().inject(this)
        super.onAttach(context)
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
        view?.hideKeyboard()
        addSourceViewModel.addSource(source = feed_input_box.text.toString())
    }

    private fun addMediumFeedOnClick() {
        view?.hideKeyboard()
        addSourceViewModel.addMediumSource(source = medium_feed_input_box.text.toString())
    }

    private fun resetTextField() {
        feed_input_box.apply {
            text?.clear()
            clearFocus()
        }
        medium_feed_input_box.apply {
            text?.clear()
            clearFocus()
        }
    }

    private fun configMediumFeedEditText() {
        medium_feed_input_box.doOnTextChanged { text, _, _, after ->
            when (after > 0) {
                true -> medium_feed_desc.text =
                    getString(R.string.medium_feed_sample_desc_template, text)
                false -> medium_feed_desc.text = getString(R.string.medium_feed_sample_desc)
            }
        }
    }
}