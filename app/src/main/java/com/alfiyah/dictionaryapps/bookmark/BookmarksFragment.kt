package com.alfiyah.dictionaryapps.bookmark

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.alfiyah.dictionaryapps.R
import com.alfiyah.dictionaryapps.adapter.ItemAdapter
import com.alfiyah.dictionaryapps.detail.DetailActivity
import com.alfiyah.dictionaryapps.model.Item
import com.alfiyah.dictionaryapps.utils.ViewModelFactory
import com.google.android.material.snackbar.Snackbar
import kotlinx.android.synthetic.main.fragment_bookmarks.*


class BookmarksFragment : Fragment() {

    private lateinit var viewModel: BookmarkViewModel
    private lateinit var itemAdapter: ItemAdapter
    private lateinit var list: ArrayList<Item>

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val factory = ViewModelFactory.getInstance(requireContext())
        viewModel = ViewModelProvider(this, factory)[BookmarkViewModel::class.java]
        list = ArrayList()
        itemAdapter = ItemAdapter(requireContext(), list, true)

        return inflater.inflate(R.layout.fragment_bookmarks, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.getAllBookmark.observe(viewLifecycleOwner) { list ->
            if (list != null){
                if (list.isNotEmpty()) {
                    this.list.clear()
                    this.list.addAll(list)
                } else {
                    this.list.clear()
//                    Snackbar.make(view, "Bookmark is empty", Snackbar.LENGTH_SHORT).show()
//                    Toast.makeText(context, "Bookmark is empty", Toast.LENGTH_SHORT).show()
                    layout_empty.visibility = View.VISIBLE
                }
                itemAdapter.notifyDataSetChanged()
            }

        }
        rvSetup()

        itemAdapter.onClick = { item ->
            val intent = Intent(activity, DetailActivity::class.java).apply {
                putExtra(DetailActivity.DICTIONARY_KEY, item)
            }
            startActivity(intent)
        }
    }

    override fun onResume() {
        super.onResume()
        viewModel.queryBookmark()
    }

    private fun rvSetup() {
        rv_bookmark.apply {
            layoutManager = LinearLayoutManager(requireContext())
            hasFixedSize()
            adapter = itemAdapter
        }
    }

}