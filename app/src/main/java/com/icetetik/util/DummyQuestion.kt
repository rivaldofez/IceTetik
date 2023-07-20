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
                id = 0,
                text = "Menjadi marah karena hal-hal kecil/sepele"
            ),
            Question(
                id = 1,
                text = "Mulut terasa kering"
            ),
            Question(
                id = 2,
                text = "Tidak dapat melihat hal yang positif dari suatu kejadian\n"
            ),
            Question(
                id = 3,
                text = "Merasakan gangguan dalam bernapas (napas cepat, sulit bernapas)"
            ),
            Question(
                id = 4,
                text = "Merasa sepertinya tidak kuat lagi untuk melakukan suatu kegiatan"
            ),
            Question(
                id = 5,
                text = "Cenderung bereaksi berlebihan pada situasi"
            ),
            Question(
                id = 6,
                text = "Kelemahan pada anggota tubuh"
            ),
            Question(
                id = 7,
                text = "Kesulitan untuk relaksasi/bersantai"
            ),
            Question(
                id = 8,
                text = "Cemas yang berlebihan dalam suatu situasi namun bisa lega jika hal/situasi itu berakhir"
            ),
            Question(
                id = 9,
                text = "Pesimis"
            ),
            Question(
                id = 10,
                text = "Mudah merasa kesal"
            ),
            Question(
                id = 11,
                text = "Merasa banyak menghabiskan energi karena cemas"
            ),
            Question(
                id = 12,
                text = "Merasa sedih dan depresi"
            ),
            Question(
                id = 13,
                text = "Tidak sabaran"
            ),
            Question(
                id = 14,
                text = "Kelelahan"
            ),
            Question(
                id = 15,
                text = "Kehilangan minat pada banyak hal (misal: makan, ambulasi, sosialisasi)"
            ),
            Question(
                id = 16,
                text = "Merasa diri tidak layak"
            ),
            Question(
                id = 17,
                text = "Mudah tersinggung"
            ),
            Question(
                id = 18,
                text = "Berkeringat (misal: tangan berkeringat) tanpa stimulasi oleh cuaca maupun latihan fisik"
            ),
            Question(
                id = 19,
                text = "Ketakutan tanpa alasan yang jelas"
            ),Question(
                id = 20,
                text = "Merasa hidup tidak berharga"
            ),Question(
                id = 21,
                text = "Sulit untuk beristirahat"
            ),
            Question(
                id = 22,
                text = "Kesulitan dalam menelan"
            ),
            Question(
                id = 23,
                text = "Tidak dapat menikmati hal-hal yang saya lakukan"
            ),
            Question(
                id = 24,
                text = "Perubahan kegiatan jantung dan denyut nadi tanpa stimulasi oleh latihan fisik\n"
            ),
            Question(
                id = 25,
                text = "Merasa hilang harapan dan putus asa"
            ),
            Question(
                id = 26,
                text = "Mudah marah"
            ),
            Question(
                id = 27,
                text = "Mudah panik"
            ),
            Question(
                id = 28,
                text = "Kesulitan untuk tenang setelah sesuatu yang mengganggu"
            ),
            Question(
                id = 29,
                text = "Takut diri terhambat oleh tugas-tugas yang tidak biasa dilakukan"
            ),
            Question(
                id = 30,
                text = "Sulit untuk antusias pada banyak hal"
            ),
            Question(
                id = 31,
                text = "Sulit mentoleransi gangguan-gangguan terhadap hal yang sedang dilakukan"
            ),
            Question(
                id = 32,
                text = "Berada pada keadaan tegang"
            ),
            Question(
                id = 33,
                text = "Merasa tidak berharga"
            ),
            Question(
                id = 34,
                text = "Tidak dapat memaklumi hal apapun yang menghalangi anda untuk menyelesaikan hal yang sedang Anda lakukan"
            ),
            Question(
                id = 35,
                text = "Ketakutan"
            ),
            Question(
                id = 36,
                text = "Tidak ada harapan untuk masa depan"
            ),
            Question(
                id = 37,
                text = "Merasa hidup tidak berarti"
            ),
            Question(
                id = 38,
                text = "Mudah gelisah"
            ),
            Question(
                id = 39,
                text = "Khawatir dengan situasi saat diri Anda mungkin menjadi panik dan mempermalukan diri sendiri"
            ),
            Question(
                id = 40,
                text = "Gemetar"
            ),
            Question(
                id = 41,
                text = "Sulit untuk meningkatkan inisiatif dalam melakukan sesuatu"
            ),
        )



        return a
    }


}