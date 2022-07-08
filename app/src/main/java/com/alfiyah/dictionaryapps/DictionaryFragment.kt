package com.alfiyah.dictionaryapps

import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.alfiyah.dictionaryapps.adapter.ItemAdapter
import com.alfiyah.dictionaryapps.detail.DetailActivity
import com.alfiyah.dictionaryapps.model.Item
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import io.grpc.InternalChannelz.id
import kotlinx.android.synthetic.main.fragment_dictionary.*
import java.util.*
import kotlin.collections.ArrayList

class DictionaryFragment : Fragment() {
    private var itemAdapter: ItemAdapter? = null
    private var mItem: MutableList<Item>? = mutableListOf()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_dictionary, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        recyclerViewConfig()

    }

    private fun recyclerViewConfig() {
        rv_word.setHasFixedSize(true)
        rv_word.layoutManager = LinearLayoutManager(rv_word.context)
        mItem = ArrayList()
        itemAdapter = context?.let {ItemAdapter(it, mItem as ArrayList<Item>, true)}
        rv_word?.adapter = itemAdapter

        itemAdapter?.onClick = { item ->
            val intent = Intent(activity, DetailActivity::class.java).apply {
                putExtra(DetailActivity.DICTIONARY_KEY, item)

            }
            startActivity(intent)
        }
        no_result.text = ""
        searchItem()
        retrievedItem()
    }

    private fun searchItem() {
        search.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {

            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                if(search.toString() == ""){
                    retrievedItem()
                }else{
                    retrievedItem()
                    getSearchItem(s.toString().toLowerCase())

                }
            } //end of onTextChanged

            override fun afterTextChanged(s: Editable?) {

            }
        })
    }

    private fun getSearchItem(input: String) {
        val query = FirebaseDatabase.getInstance().getReference()
            .child("Dictionary")
            .orderByChild("key")
            .startAt(input)
            .endAt(input + "\uf8ff")
        query.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(s: DataSnapshot) {
                mItem?.clear()
                for (snapshot in s.children){
                    val item = snapshot.getValue(Item::class.java)
                    if (item != null) {
                        mItem?.add(item)
                        no_result.text = ""
                    }

                }//end for

                itemAdapter?.notifyDataSetChanged()
            }

            override fun onCancelled(error: DatabaseError) {

            }

        })
    }

    private fun retrievedItem() {
        val itemRef = FirebaseDatabase.getInstance().getReference().child("Dictionary")
            .orderByChild("key")
        itemRef.addValueEventListener(object : ValueEventListener{
            override fun onDataChange(s: DataSnapshot) {
                if(search?.text.toString() == ""){
                    mItem?.clear()
                    no_result.text = ""

                    for(snapshot in s.children){
                        val item = snapshot.getValue(Item::class.java)
                        if(item != null){
                            mItem?.add(item)

                        }
                    }//end for

                    itemAdapter?.notifyDataSetChanged()

                }//end if
                else{
                    no_result.text = "No Result Found"
                }
            }

            override fun onCancelled(error: DatabaseError) {

            }
        })
    }

}