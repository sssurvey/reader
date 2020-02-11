package com.haomins.reader.fragments.list

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.haomins.reader.R

class SourceTitleListFragment : Fragment() {

    companion object {
        const val TAG = "SourceTitleListFragment"
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_source_list_title, container, false)
    }
}