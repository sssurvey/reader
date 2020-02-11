package com.haomins.reader.fragments.list

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.VisibleForTesting
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.haomins.reader.R
import kotlinx.android.synthetic.main.fragment_source_list_title.*
import kotlinx.android.synthetic.main.source_title_recycler_view_item.view.*

class SourceTitleListFragment : Fragment() {

    companion object {
        const val TAG = "SourceTitleListFragment"
    }

    private lateinit var sourceTitleListAdapter: SourceTitleListAdapter
    private val recyclerLayoutManager by lazy {
        LinearLayoutManager(context)
    }

    @VisibleForTesting
    private val testTitleData by lazy {
        ArrayList<String>()
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_source_list_title, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupTestEnv()

        source_title_recycler_view.apply {
            setHasFixedSize(true)
            layoutManager = recyclerLayoutManager
            adapter = sourceTitleListAdapter
        }
    }

    @VisibleForTesting
    private fun setupTestEnv() {
        populateArr()
        sourceTitleListAdapter = SourceTitleListAdapter(testTitleData)
    }

    @VisibleForTesting
    private fun populateArr() {
        for (i in 0..99) {
            testTitleData.add("This is title ${i} Very long Testing for old reader ...")
        }
    }
}

class SourceTitleListAdapter(private val sourceTitleList: ArrayList<String>): RecyclerView.Adapter<SourceTitleListAdapter.CustomViewHolder>() {

    class CustomViewHolder(val viewHolder: View):RecyclerView.ViewHolder(viewHolder)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CustomViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.source_title_recycler_view_item, parent, false)
        return CustomViewHolder(itemView)
    }

    override fun getItemCount(): Int {
        return sourceTitleList.size
    }

    override fun onBindViewHolder(holder: CustomViewHolder, position: Int) {
        //TODO: holder.viewHolder.source_icon_image_view = add for image view
        holder.viewHolder.source_title_text_view.text = sourceTitleList[position]
    }

}