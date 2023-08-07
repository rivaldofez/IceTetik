package com.icetetik.page.settings.faq

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.icetetik.R
import com.icetetik.databinding.ActivityFaqBinding
import com.icetetik.page.infographic.DetailInfographicActivity
import com.icetetik.util.KeyParcelable

class FaqActivity : AppCompatActivity() {
    private lateinit var binding: ActivityFaqBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityFaqBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setButtonActions()
    }

    private fun navigateToDetailFaq(title: String, answer: String){
        val intent =  Intent(this@FaqActivity, DetailFaqActivity::class.java)
        intent.putExtra(KeyParcelable.TITLE_FAQ, title)
        intent.putExtra(KeyParcelable.ANSWER_FAQ, answer)
        startActivity(intent)
    }

    private fun setButtonActions() {
        binding.apply {
            var title = ""
            var answer = ""

            btnAboutIcetetik.setOnClickListener {
                title = btnAboutIcetetik.text.toString()
                answer = "Ice-tetik merupakan inovasi produk berupa es krim probiotik dengan penambahan ekstrak pisang muli Lampung yang kaya vitamin B kompleks. Pengayaan vitamin B pada produk bertujuan untuk meredakan stres, kecemasan dan depresi."

                navigateToDetailFaq(title, answer)
            }

            btnFeature.setOnClickListener {
                title = btnFeature.text.toString()
                answer = "Fitur aplikasi dalam ICETETiK HEALING terdiri dari \n" +
                        "1.\tkalender mood : fungsinya memantau mood kita setiap hari sehingga kita bisa melihat dan menghargai hari-hari yang telah berlalu dan kenangan mood setiap hari, dalam kalender mood juga terdapat rekap mood untuk melihat jumlah mood kita dalam sebulan\n" +
                        "2.\tKuesioner DASS 42 berfungsi melakukan self assessment sehingga kita bisa melihat pada skor berapa tingkat kecemasan kita saat ini\n" +
                        "3.\tlatihan pernapasan bersama mintik\n" +
                        "4.\tcurhat bersama mintik (journaling)\n" +
                        "5.\tteman sebelum tidur\n" +
                        "6.\tvideo dan infografis yang edukatif\n"

                navigateToDetailFaq(title, answer)
            }

            btnCooperation.setOnClickListener {
                title = btnCooperation.text.toString()
                answer = "cara bekerja sama dengan kami bisa menghubungi kami melalui\n" +
                        "CP berikut\n" +
                        "email : icetetikhealing@gmail.com\n" +
                        "WA  \n"

                navigateToDetailFaq(title, answer)
            }

            btnMarket.setOnClickListener {
                title = btnMarket.text.toString()
                answer = "tidak bisa. saat ini produk Ice-Tetik dapat dibeli dengan memesan secara langsung di wilayah Bandar Lampung atau melalui contact person kami. "

                navigateToDetailFaq(title, answer)
            }

            btnSupport.setOnClickListener {
                title = btnSupport.text.toString()
                answer = "Cara mendukung ICETETIK cukup mudah hanya dengan mendownload aplikasi di playstore dan login, anda sudah menjadi bagian dari nICEtizen. Anda juga bisa follow di \n" +
                        "ig @ICETETIK.co https://www.instagram.com/reel/CutJv13hb_J/?igshid=MzRlODBiNWFlZA==\n" +
                        "\n" +
                        "youtube https://youtu.be/kRNo28BJMas\n" +
                        "tiktok \n"

                navigateToDetailFaq(title, answer)
            }

            btnIosSupport.setOnClickListener {
                title = btnIosSupport.text.toString()
                answer = "tidak tersedia untuk sementara ini"

                navigateToDetailFaq(title, answer)
            }

        }
    }
}