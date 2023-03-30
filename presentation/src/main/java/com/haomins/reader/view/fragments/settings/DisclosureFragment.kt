package com.haomins.reader.view.fragments.settings

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.haomins.reader.databinding.FragmentDisclosureBinding
import com.haomins.reader.viewModels.DisclosureViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class DisclosureFragment : Fragment() {

    companion object {
        const val TAG = "DisclosureFragment"
    }

    private val disclosureViewModel by viewModels<DisclosureViewModel>()
    private lateinit var binding: FragmentDisclosureBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentDisclosureBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        loadDisclosureContents()
    }

    private fun loadDisclosureContents() {
        with(disclosureViewModel) {
            binding.newsDisclosureTitle.text = loadDisclosureTitle()
            loadDisclosure { disclosureData ->
                binding.newsDisclosureContent.text = disclosureData.disclosureContent
                binding.newsDisclosureContactEmail.text = disclosureData.contactEmail
                binding.newsDisclosureContactWebsite.text = disclosureData.website
            }
        }
    }

}