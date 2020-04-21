package com.haomins.reader.view.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import com.haomins.reader.R
import com.haomins.reader.viewModels.AddSourceViewModel
import dagger.android.support.AndroidSupportInjection
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
    }

    private fun registerLiveDataObservers() {
        addSourceViewModel.isSourceAdded.observe(viewLifecycleOwner, isSourceAddedObserver)
    }

}