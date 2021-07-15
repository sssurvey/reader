package com.haomins.reader.view.fragments

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModelProvider
import com.haomins.reader.R
import com.haomins.reader.ReaderApplication
import com.haomins.reader.viewModels.DisclosureViewModel
import kotlinx.android.synthetic.main.fragment_disclosure.*
import javax.inject.Inject

class DisclosureFragment : Fragment() {

    companion object {
        const val TAG = "DisclosureFragment"
    }

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory
    private val disclosureViewModel by viewModels<DisclosureViewModel> { viewModelFactory }

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
        return inflater.inflate(R.layout.fragment_disclosure, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        loadDisclosureContents()
    }

    private fun loadDisclosureContents() {
        with(disclosureViewModel) {
            new_disclosure_title.text = loadDisclosureTitle()
        }
    }

}