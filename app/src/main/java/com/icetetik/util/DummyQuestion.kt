package com.icetetik.util

import com.icetetik.data.model.Option
import com.icetetik.data.model.Question
import com.icetetik.data.model.Video

object DummyQuestion {

    fun generateVideos(): List<Video> {
        return listOf(
            Video(
                id = "Mn-rqGhNnSM",
                title = "Good Food Good Mood: Makanan untuk Kesehatan Mentalmu",
                url = "https://youtu.be/Mn-rqGhNnSM"
            ),
            Video(
                id = "jiQ69d5guJA",
                title = "Awas! Dampak Negatif Teknologi Bagi Kesehatan Mental",
                url = "https://youtu.be/jiQ69d5guJA"
            ),
            Video(
                id = "nZBrFyINjM0",
                title = "Cara Mendukung Teman dan Keluarga yang Memiliki Masalah Kesehatan Mental\n",
                url = "https://youtu.be/nZBrFyINjM0"
            ),
            Video(
                id = "Y1vw5ByB41M",
                title = "Tanda-tanda dan Gejala Gangguan Mental ",
                url = "https://youtu.be/Y1vw5ByB41M"
            ),
            Video(
                id = "nkkgImx8fN0",
                title = "Mengenali perbedaan antara stress, Kecemasan, dan Depresi",
                url = "https://youtu.be/nkkgImx8fN0"
            ),
            Video(
                id = "XXqp0QswChU",
                title = "Kenali Stres dan Tandanya Pada Diri Sendiri",
                url = "https://youtu.be/XXqp0QswChU"
            ),
            Video(
                id = "hQ9O2ubuUKk",
                title = "Pemicu Stres, Dampaknya Bagi Tubuh dan Penanggulangannya.",
                url = "https://youtu.be/hQ9O2ubuUKk"
            ),
            Video(
                id = "Yt33Sj_a1u0",
                title = "Kisah Inspiratif : Depresi Namun Bangkit Kembali.",
                url = "https://youtu.be/Yt33Sj_a1u0"
            ),
            Video(
                id = "kRNo28BJMas",
                title = "Patah Hati? Cari Solusinya Di Video Ini",
                url = "https://youtu.be/kRNo28BJMas"
            ),
        )
    }

    fun generateOptions(): List<Option> {
        return listOf(
            Option(
                value = 0,
                text = "Tidak Pernah"
            ),
            Option(
                value = 1,
                text = "Kadang-kadang"
            ),
            Option(
                value = 2,
                text = "Lumayan Sering"
            ),
            Option(
                value = 3,
                text = "Sering sekali"
            ),

        )
    }

    fun generateQuestions() : List<Question> {
        return listOf(
            Question(
                id = 1,
                text = "Menjadi marah karena hal-hal kecil/sepele",
                category = "Stress"
            ),
            Question(
                id = 2,
                text = "Mulut terasa kering",
                category = "Kecemasan"
            ),
            Question(
                id = 3,
                text = "Tidak dapat melihat hal yang positif dari suatu kejadian\n",
                category = "Depresi"
            ),
            Question(
                id = 4,
                text = "Merasakan gangguan dalam bernapas (napas cepat, sulit bernapas)",
                category = "Kecemasan"
            ),
            Question(
                id = 5,
                text = "Merasa sepertinya tidak kuat lagi untuk melakukan suatu kegiatan",
                category = "Depresi"
            ),
            Question(
                id = 6,
                text = "Cenderung bereaksi berlebihan pada situasi",
                category = "Stress"
            ),
            Question(
                id = 7,
                text = "Kelemahan pada anggota tubuh",
                category = "Kecemasan"
            ),
            Question(
                id = 8,
                text = "Kesulitan untuk relaksasi/bersantai",
                category = "Stress"
            ),
            Question(
                id = 9,
                text = "Cemas yang berlebihan dalam suatu situasi namun bisa lega jika hal/situasi itu berakhir",
                category = "Kecemasan"
            ),
            Question(
                id = 10,
                text = "Pesimis",
                category = "Depresi"
            ),
            Question(
                id = 11,
                text = "Mudah merasa kesal",
                category = "Stress"
            ),
            Question(
                id = 12,
                text = "Merasa banyak menghabiskan energi karena cemas",
                category = "Stress"
            ),
            Question(
                id = 13,
                text = "Merasa sedih dan depresi",
                category = "Depresi"
            ),
            Question(
                id = 14,
                text = "Tidak sabaran",
                category = "Stress"
            ),
            Question(
                id = 15,
                text = "Kelelahan",
                category = "Kecemasan"
            ),
            Question(
                id = 16,
                text = "Kehilangan minat pada banyak hal (misal: makan, ambulasi, sosialisasi)",
                category = "Depresi"
            ),
            Question(
                id = 17,
                text = "Merasa diri tidak layak",
                category = "Depresi"
            ),
            Question(
                id = 18,
                text = "Mudah tersinggung",
                category = "Stress"
            ),
            Question(
                id = 19,
                text = "Berkeringat (misal: tangan berkeringat) tanpa stimulasi oleh cuaca maupun latihan fisik",
                category = "Kecemasan"
            ),
            Question(
                id = 20,
                text = "Ketakutan tanpa alasan yang jelas",
                category = "Kecemasan"
            ),Question(
                id = 21,
                text = "Merasa hidup tidak berharga",
                category = "Depresi"
            ),Question(
                id = 22,
                text = "Sulit untuk beristirahat",
                category = "Stress"
            ),
            Question(
                id = 23,
                text = "Kesulitan dalam menelan",
                category = "Kecemasan"
            ),
            Question(
                id = 24,
                text = "Tidak dapat menikmati hal-hal yang saya lakukan",
                category = "Depresi"
            ),
            Question(
                id = 25,
                text = "Perubahan kegiatan jantung dan denyut nadi tanpa stimulasi oleh latihan fisik\n",
                category = "Kecemasan"
            ),
            Question(
                id = 26,
                text = "Merasa hilang harapan dan putus asa",
                category = "Depresi"
            ),
            Question(
                id = 27,
                text = "Mudah marah",
                category = "Stress"
            ),
            Question(
                id = 28,
                text = "Mudah panik",
                category = "Kecemasan"
            ),
            Question(
                id = 29,
                text = "Kesulitan untuk tenang setelah sesuatu yang mengganggu",
                category = "Stress"
            ),
            Question(
                id = 30,
                text = "Takut diri terhambat oleh tugas-tugas yang tidak biasa dilakukan",
                category = "Kecemasan"
            ),
            Question(
                id = 31,
                text = "Sulit untuk antusias pada banyak hal",
                category = "Depresi"
            ),
            Question(
                id = 32,
                text = "Sulit mentoleransi gangguan-gangguan terhadap hal yang sedang dilakukan",
                category = "Stress"
            ),
            Question(
                id = 33,
                text = "Berada pada keadaan tegang",
                category = "Stress"
            ),
            Question(
                id = 34,
                text = "Merasa tidak berharga",
                category = "Depresi"
            ),
            Question(
                id = 35,
                text = "Tidak dapat memaklumi hal apapun yang menghalangi anda untuk menyelesaikan hal yang sedang Anda lakukan",
                category = "Stress"
            ),
            Question(
                id = 36,
                text = "Ketakutan",
                category = "Kecemasan"
            ),
            Question(
                id = 37,
                text = "Tidak ada harapan untuk masa depan",
                category = "Depresi"
            ),
            Question(
                id = 38,
                text = "Merasa hidup tidak berarti",
                category = "Depresi"
            ),
            Question(
                id = 39,
                text = "Mudah gelisah",
                category = "Stress"
            ),
            Question(
                id = 40,
                text = "Khawatir dengan situasi saat diri Anda mungkin menjadi panik dan mempermalukan diri sendiri",
                category = "Kecemasan"
            ),
            Question(
                id = 41,
                text = "Gemetar",
                category = "Kecemasan"
            ),
            Question(
                id = 42,
                text = "Sulit untuk meningkatkan inisiatif dalam melakukan sesuatu",
                category = "Depresi"
            ),
        )
    }
}