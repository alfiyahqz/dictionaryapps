package com.alfiyah.dictionaryapps.detail

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.alfiyah.dictionaryapps.R
import com.alfiyah.dictionaryapps.adapter.ItemAdapter
import com.alfiyah.dictionaryapps.model.Item
import com.alfiyah.dictionaryapps.utils.ViewModelFactory
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import io.grpc.InternalChannelz.id
import kotlinx.android.synthetic.main.activity_detail.*
import kotlinx.android.synthetic.main.fragment_dictionary.*
import java.util.*
import kotlin.collections.ArrayList

class DetailActivity : AppCompatActivity() {

    private lateinit var item: Item
    private lateinit var viewModel: DetailViewModel
    private lateinit var factory: ViewModelFactory

    var id: String = ""

    private var itemAdapter: ItemAdapter? = null
    private var mItem: MutableList<Item>? = mutableListOf()
    private var idList: List<String>? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail)

        val mInten = intent
        id = mInten.getStringExtra("id").toString()


        item = intent.getParcelableExtra(DICTIONARY_KEY) ?: Item()

        factory = ViewModelFactory.getInstance(this)
        factory.getHelper.open()
        viewModel = ViewModelProvider(this, factory)[DetailViewModel::class.java]

        viewModel.checkIfDataBookmarked(item.id)
        viewModel.isBookmarked.observe(this) { isBookmarked ->
            setBookmarkIcon(isBookmarked)
        }

        bookmark_btn.setOnClickListener {
            if (viewModel.isBookmarked.value != null) {
                if (viewModel.isBookmarked.value!!) {
                    //If bookmark status is true then delete
                    deleteDic()
                } else {
                    //If bookmark status is false then insert
                    insertDic()
                }
            }
        }

        share_btn.setOnClickListener {
            val note: String = item.word + "\n" + item.term + " (KamusDB-SQL)"
            val note2: String = item.word + "\n" + item.term + "\nSyntax : "+ item.syntax + "\nContoh : "+ item.exp_syntax + "\n"+ item.exp_desc +" (KamusDB-SQL)"
            val nInten = Intent()
            nInten.action = Intent.ACTION_SEND
            if(item.syntax == ""){
                nInten.putExtra(Intent.EXTRA_TEXT, note)
            }else{
                nInten.putExtra(Intent.EXTRA_TEXT, note2)
            }

            nInten.type = "text/plain"
            startActivity(Intent.createChooser(nInten,"Share with"))
        }
        setViewData()
    }

    override fun onDestroy() {
        super.onDestroy()
        factory.getHelper.close()
    }

    @Suppress("SENSELESS_COMPARISON")
    private fun setViewData() {
        tv_word.text = item.word
        tv_term.text = item.term
        tv_syntax.text = item.syntax
        tv_exp_syntax.text = item.exp_syntax
        tv_exp_desc.text = item.exp_desc

        if (item.syntax != ""){
            tv_stx.text = "Syntax :"
        }

        if (item.exp_syntax != ""){
            tv_exp.text = "Contoh :"
        }

        back_btn.setOnClickListener { finish() }
    }

    private fun setBookmarkIcon(state: Boolean) {
        Log.d("isBookmarked", state.toString())
        if (state) {
            bookmark_btn.setImageDrawable(ContextCompat.getDrawable(this, R.drawable.ic_bookmark))
        } else {
            bookmark_btn.setImageDrawable(
                ContextCompat.getDrawable(
                    this,
                    R.drawable.ic_bookmark_outline
                )
            )
        }
    }

    private fun insertDic() {
        viewModel.insertDictionary(item).observe(this) { isSuccessful ->
            if (isSuccessful) {
                //Code if insert into DB is successfull
                //Snackbar.make(detail_container, "Insert successful", Snackbar.LENGTH_SHORT).show()
                Toast.makeText(this, "Saved successful", Toast.LENGTH_SHORT).show()
            } else {
                //Code if insert into DB is fail
                //Snackbar.make(detail_container, "Insert fail", Snackbar.LENGTH_SHORT).show()
                Toast.makeText(this, "Failed to save", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun deleteDic() {
        viewModel.deleteDictionary(item.id).observe(this) { isSuccessful ->
            if (isSuccessful) {
                //Code if delete into DB is successfull
                //Snackbar.make(detail_container, "Delete successful", Snackbar.LENGTH_SHORT).show()
                Toast.makeText(this, "Delete successful", Toast.LENGTH_SHORT).show()
            } else {
                //Code if delete into DB is fail
                //Snackbar.make(detail_container, "Delete fail", Snackbar.LENGTH_SHORT).show()
                Toast.makeText(this, "Failed to delete", Toast.LENGTH_SHORT).show()
            }
        }
    }

    companion object {
        const val DICTIONARY_KEY = "dictionary_key"
    }
}