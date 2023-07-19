package com.icetetik.util


data class Question(
    val id: Int,
    val text: String
)

data class Option(
    val id: Int,
    val text: String
)

object DummyQuestion {

    fun generateOptions(): List<Option> {
        val a = listOf(
            Option(
                id = 1,
                text = "Tidak Pernah"
            ),
            Option(
                id = 2,
                text = "Kadang-kadang"
            ),
            Option(
                id = 3,
                text = "Lumayan Sering"
            ),
            Option(
                id = 4,
                text = "Sering sekali"
            ),

        )

        return a
    }

    fun generateQuestions() : List<Question> {
        val a = listOf(
            Question(
                id = 1,
                text = "Menjadi marah karena hal-hal kecil/sepele"
            ),
            Question(
                id = 2,
                text = "Mulut terasa kering"
            ),
            Question(
                id = 3,
                text = "Tidak dapat melihat hal yang positif dari suatu kejadian\n"
            ),
            Question(
                id = 4,
                text = "Merasakan gangguan dalam bernapas (napas cepat, sulit bernapas)"
            ),
            Question(
                id = 5,
                text = "Merasa sepertinya tidak kuat lagi untuk melakukan suatu kegiatan"
            ),
            Question(
                id = 6,
                text = "Cenderung bereaksi berlebihan pada situasi"
            ),
            Question(
                id = 7,
                text = "Kelemahan pada anggota tubuh"
            ),
            Question(
                id = 8,
                text = "Kesulitan untuk relaksasi/bersantai"
            ),
            Question(
                id = 9,
                text = "Cemas yang berlebihan dalam suatu situasi namun bisa lega jika hal/situasi itu berakhir"
            ),
            Question(
                id = 10,
                text = "Pesimis"
            ),
            Question(
                id = 11,
                text = "Mudah merasa kesal"
            ),
            Question(
                id = 12,
                text = "Merasa banyak menghabiskan energi karena cemas"
            ),
            Question(
                id = 13,
                text = "Merasa sedih dan depresi"
            ),
            Question(
                id = 14,
                text = "Tidak sabaran"
            ),
            Question(
                id = 15,
                text = "Kelelahan"
            ),
            Question(
                id = 16,
                text = "Kehilangan minat pada banyak hal (misal: makan, ambulasi, sosialisasi)"
            ),
            Question(
                id = 17,
                text = "Merasa diri tidak layak"
            ),
            Question(
                id = 18,
                text = "Mudah tersinggung"
            ),
            Question(
                id = 19,
                text = "Berkeringat (misal: tangan berkeringat) tanpa stimulasi oleh cuaca maupun latihan fisik"
            ),
            Question(
                id = 20,
                text = "Ketakutan tanpa alasan yang jelas"
            ),Question(
                id = 21,
                text = "Merasa hidup tidak berharga"
            ),Question(
                id = 22,
                text = "Sulit untuk beristirahat"
            ),
            Question(
                id = 23,
                text = "Kesulitan dalam menelan"
            ),
            Question(
                id = 24,
                text = "Tidak dapat menikmati hal-hal yang saya lakukan"
            ),
            Question(
                id = 25,
                text = "Perubahan kegiatan jantung dan denyut nadi tanpa stimulasi oleh latihan fisik\n"
            ),
            Question(
                id = 26,
                text = "Merasa hilang harapan dan putus asa"
            ),
            Question(
                id = 27,
                text = "Mudah marah"
            ),
            Question(
                id = 28,
                text = "Mudah panik"
            ),
            Question(
                id = 29,
                text = "Kesulitan untuk tenang setelah sesuatu yang mengganggu"
            ),
            Question(
                id = 30,
                text = "Takut diri terhambat oleh tugas-tugas yang tidak biasa dilakukan"
            ),
            Question(
                id = 31,
                text = "Sulit untuk antusias pada banyak hal"
            ),
            Question(
                id = 32,
                text = "Sulit mentoleransi gangguan-gangguan terhadap hal yang sedang dilakukan"
            ),
            Question(
                id = 33,
                text = "Berada pada keadaan tegang"
            ),
            Question(
                id = 34,
                text = "Merasa tidak berharga"
            ),
            Question(
                id = 35,
                text = "Tidak dapat memaklumi hal apapun yang menghalangi anda untuk menyelesaikan hal yang sedang Anda lakukan"
            ),
            Question(
                id = 36,
                text = "Ketakutan"
            ),
            Question(
                id = 37,
                text = "Tidak ada harapan untuk masa depan"
            ),
            Question(
                id = 38,
                text = "Merasa hidup tidak berarti"
            ),
            Question(
                id = 39,
                text = "Mudah gelisah"
            ),
            Question(
                id = 40,
                text = "Khawatir dengan situasi saat diri Anda mungkin menjadi panik dan mempermalukan diri sendiri"
            ),
            Question(
                id = 41,
                text = "Gemetar"
            ),
            Question(
                id = 42,
                text = "Sulit untuk meningkatkan inisiatif dalam melakukan sesuatu"
            ),
        )



        return a
    }


}