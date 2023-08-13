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
                answer = "Ice-tetik merupakan inovasi produk berupa es krim probiotik dari bahan susu sapi pilihan yang diperkaya dengam ekstrak buah pisang muli Lampung, membantu mencukupi kebutuhan vitamin B harian untuk mencegah salah satu dari faktor penyebab munculnya gejala berupa stres, kecemasan dan depresi. Diproses dengan teknologi probiotik yang memiliki manfaat baik untuk kesehatan Anda. \n\nIce-tetik hadir dengan aplikasi Healing dengan berbagai fitur menarik sebagai penunjang dan solusi untuk upaya peningkatan derajat kesehatan mental di Indonesia."

                navigateToDetailFaq(title, answer)
            }

            btnFeature.setOnClickListener {
                title = btnFeature.text.toString()
                answer = "Fitur aplikasi dalam ICETETiK HEALING terdiri dari \n" +
                        "\n1.Kalender yang dilengkapi mood tracker dan jurnal harian\n" +
                        "\n2.Rekap statistik mood bulanan\n" +
                        "\n3.Kuesioner DASS-42 untuk mengukur tingkat stres, kecemasan, dan depresi ditambah saran/tips dari hasil kuesioner \n" +
                        "\n4.Media infografik dan video edukasi\n" +
                        "\n5.Fitur instruksi relaksasi bersama Mintik\n"

                navigateToDetailFaq(title, answer)
            }

            btnCooperation.setOnClickListener {
                title = btnCooperation.text.toString()
                answer = "Cara bekerja sama dengan kami bisa menghubungi kami melalui: \n" +
                        "\nCP berikut:\n" +
                        "Email : icetetikhealing@gmail.com\n" +
                        "WA : 0895-6221-10517\n"

                navigateToDetailFaq(title, answer)
            }

            btnMarket.setOnClickListener {
                title = btnMarket.text.toString()
                answer = "Tidak bisa. saat ini produk Ice-Tetik dapat dibeli dengan memesan secara langsung di wilayah Bandar Lampung atau melalui contact person kami. "

                navigateToDetailFaq(title, answer)
            }

            btnSupport.setOnClickListener {
                title = btnSupport.text.toString()
                answer = "Dukung kami dengan memberikan rating sempurna pada google playstore memfollow akun sosial media kami, menyebarluaskan informasi produk dan aplikasi kami. Anda juga membantu kami dengan membeli produk eskrim ice-tetik dan secara rutin menggunakan aplikasi ini. Kami akan sangat senang apabila produk dan aplikasi kami bermanfaat untuk Anda."

                navigateToDetailFaq(title, answer)
            }

            btnIosSupport.setOnClickListener {
                title = btnIosSupport.text.toString()
                answer = "Tidak tersedia untuk sementara ini"

                navigateToDetailFaq(title, answer)
            }

        }
    }
}