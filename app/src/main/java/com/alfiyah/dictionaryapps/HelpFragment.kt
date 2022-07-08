package com.alfiyah.dictionaryapps
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.ViewGroup.LayoutParams.WRAP_CONTENT
import android.widget.ImageView
import android.widget.LinearLayout
import androidx.core.content.ContextCompat
import androidx.core.view.get
import androidx.viewpager2.widget.ViewPager2
import com.alfiyah.dictionaryapps.adapter.HelpSlideAdapter
import kotlinx.android.synthetic.main.fragment_help.*

class HelpFragment : Fragment() {

    private val helpSlideAdapter = HelpSlideAdapter(
        listOf(
            HelpSlide(
                R.drawable.pg1,
                "Geser Kiri",
                "Geser halaman sesuai petunjuk untuk melihat fitur yang tersedia pada aplikasi KamusDB-SQL!"
            ),
            HelpSlide(
                R.drawable.pg2,
                "Menu Utama",
                "Tersedia 5 menu utama yang dapat digunakan untuk mengakses fitur dalam aplikasi KamusDB-SQL."
            ),
            HelpSlide(
                R.drawable.pg3,
                "Cari Istilah",
                "Menu Dictionary menyediakan bar pencarian yang dapat digunakan untuk mencari istilah maupun perintah."
            ),
            HelpSlide(
                R.drawable.pg4,
                "Bagi Istilah",
                "Halaman Detail menyediakan fitur sharing yang digunakan untuk membagi istilah maupun perintah KamusDB-SQL melalui platform sosial media."
            ),
            HelpSlide(
                R.drawable.pg5,
                "Simpan Istilah",
                "Halaman Detail menyediakan fitur bookmark yang digunakan untuk menyimpan istilah untuk diakses secara offline. Daftar istilah yang disimpan dapat dilihat pada Menu Bookmarks."
            ),
            HelpSlide(
                R.drawable.pg6,
                "Kirim Masukan dan Saran",
                "Menu Request and Suggestion menyediakan fitur masukan dan saran yang telah diintegrasikan dengan e-mail admin KamusDB-SQL."
            )
        )
    )


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_help, container, false)

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        vp_slider.adapter = helpSlideAdapter
        setUpIndicators()
        setCurrentInd(0)
        vp_slider.registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback(){

            override fun onPageSelected(position: Int) {
                super.onPageSelected(position)
                setCurrentInd(position)
            }
        })
    }

    private fun setCurrentInd(index: Int) {
        val childCount = containerInd.childCount
        for (i in 0 until childCount){
            val imageView = containerInd[i] as ImageView
            if (i == index){
                imageView.setImageDrawable(
                    context?.let {
                        ContextCompat.getDrawable(
                            it, R.drawable.indicator_active
                        )
                    }
                )
            } else  {
                imageView.setImageDrawable(context?.let { ContextCompat.getDrawable(it, R.drawable.indicator_inactive) })
            }
        }
    }

    private fun setUpIndicators() {
        val indicators = arrayOfNulls<ImageView>(helpSlideAdapter.itemCount)
        val layoutParams: LinearLayout.LayoutParams = LinearLayout.LayoutParams(WRAP_CONTENT, WRAP_CONTENT)
        layoutParams.setMargins(8,0,8,0)
        for (i in indicators.indices){
            indicators[i] = ImageView(context)
            indicators[i].apply {
                this?.setImageDrawable(ContextCompat.getDrawable(context, R.drawable.indicator_inactive))
                this?.layoutParams = layoutParams
            }
            containerInd.addView(indicators[i])
        }
    }


}