package com.alfiyah.dictionaryapps

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import kotlinx.android.synthetic.*
import kotlinx.android.synthetic.main.fragment_request.*


class RequestFragment : Fragment() {
    // TODO: Rename and change types of parameters
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_request, container, false)

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        var addresses = arrayOf("kamusdbsql@gmail.com")

        textFormat1.setOnClickListener {
            val subject = "Tidak Menemukan Istilah"
            val message = "Nama:\nIstilah yang tidak saya temukan:"

            sendEmail(addresses, subject, message)
        }

        textFormat2.setOnClickListener {
            val subject = "Saran Definisi"
            val message = "Nama:\nIstilah:\nSaran definisi:\nSumber:"

            sendEmail(addresses, subject, message)
        }

        textFormat3.setOnClickListener {
            val subject = "Definisi Perlu Perbaikan"
            val message = "Nama:\nIstilah:\nPerbaikan definisi:"

            sendEmail(addresses, subject, message)
        }

        textFormat4.setOnClickListener {
            val subject = "Halo KamusDB-SQL"
            val message = "Nama:\n"

            sendEmail(addresses, subject, message)
        }
    }

    private fun sendEmail(addresses: Array<String>, subject: String, message: String) {
        val mIntent = Intent(Intent.ACTION_SEND).apply {
            data = Uri.parse("mailto:")
            type = "message/rfc822"
            putExtra(Intent.EXTRA_EMAIL, addresses)
            putExtra(Intent.EXTRA_SUBJECT, subject)
            putExtra(Intent.EXTRA_TEXT, message)
        }

        startActivity(Intent.createChooser(mIntent, "Complete action using"))

        try {

        }
        catch (e: Exception){
//            Toast.makeText(this, e.message, Toast.LENGTH_LONG).show()
        }
    }

}