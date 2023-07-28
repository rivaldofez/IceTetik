package com.icetetik.util

import com.icetetik.data.model.Option
import com.icetetik.data.model.Question

object DummyQuestion {

    fun generateOptions(): List<Option> {
        val a = listOf(
            Option(
                id = 0,
                text = "Tidak Pernah"
            ),
            Option(
                id = 1,
                text = "Kadang-kadang"
            ),
            Option(
                id = 2,
                text = "Lumayan Sering"
            ),
            Option(
                id = 3,
                text = "Sering sekali"
            ),

        )

        return a
    }

    fun generateQuestions() : List<Question> {
        val a = listOf(
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
        return a
    }


}