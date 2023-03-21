package com.haomins.reader.view.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.widget.doOnTextChanged
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import com.haomins.reader.R
import com.haomins.reader.databinding.FragmentAddSourceBinding
import com.haomins.reader.utils.hideKeyboard
import com.haomins.reader.utils.showSnackbar
import com.haomins.reader.viewModels.AddSourceViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class AddSourceFragment : Fragment() {

    companion object {
        const val TAG = "AddSourceFragment"
    }

    private val addSourceViewModel by viewModels<AddSourceViewModel>()
    private lateinit var binding: FragmentAddSourceBinding

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

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentAddSourceBinding.inflate(inflater, container, false)
        return binding.root
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
        with(binding) {
            addFeedButton
                .setOnClickListener { if (!feedInputBox.text.isNullOrEmpty()) addFeedOnClick() }
            addMediumFeedButton
                .setOnClickListener { if (!mediumFeedDesc.text.isNullOrEmpty()) addMediumFeedOnClick() }
        }
    }

    private fun addFeedOnClick() {
        with(binding) {
            root.hideKeyboard()
            addSourceViewModel.addSource(source = feedInputBox.text.toString())
        }
    }

    private fun addMediumFeedOnClick() {
        with(binding) {
            root.hideKeyboard()
            addSourceViewModel.addMediumSource(source = mediumFeedInputBox.text.toString())
        }
    }

    private fun resetTextField() {
        with(binding) {
            feedInputBox.apply {
                text?.clear()
                clearFocus()
            }
            mediumFeedInputBox.apply {
                text?.clear()
                clearFocus()
            }
        }
    }

    private fun configMediumFeedEditText() {
        with(binding) {
            mediumFeedInputBox.doOnTextChanged { text, _, _, after ->
                when (after > 0) {
                    true ->
                        mediumFeedDesc.text =
                            getString(R.string.medium_feed_sample_desc_template, text)
                    false ->
                        mediumFeedDesc.text =
                            getString(R.string.medium_feed_sample_desc)
                }
            }
        }
    }
}