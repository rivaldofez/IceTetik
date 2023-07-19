package com.icetetik.authentication.signup

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.viewModels
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.icetetik.authentication.signin.SignInActivity
import com.icetetik.data.model.Option
import com.icetetik.data.model.OptionResponse
import com.icetetik.data.model.Question
import com.icetetik.data.model.QuestionResponse
import com.icetetik.databinding.ActivitySignUpBinding
import com.icetetik.util.DummyQuestion
import com.icetetik.util.UiState
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SignUpActivity : AppCompatActivity() {
    private lateinit var binding: ActivitySignUpBinding
    private val viewModel: SignUpViewModel by viewModels()


    private lateinit var firebaseAuth: FirebaseAuth
    private lateinit var firestore: FirebaseFirestore


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySignUpBinding.inflate(layoutInflater)
        setContentView(binding.root)

        firebaseAuth = FirebaseAuth.getInstance()
        firestore = FirebaseFirestore.getInstance()


//        val mapData = HashMap<String, Any>()
////            mapData["question-data"] = DummyQuestion.generateQuestions()
//        mapData["option-data"] = DummyQuestion.generateOptions()

//        val optionData = OptionResponse(options = DummyQuestion.generateOptions())
//
//        firestore.collection("apps").document("options")
//            .set(optionData)
//            .addOnCompleteListener {
//                Toast.makeText(this, "Success", Toast.LENGTH_SHORT)
//            }
//            .addOnFailureListener {
//                Toast.makeText(this, "Failed", Toast.LENGTH_SHORT)
//            }
//
//
//        val questionData = QuestionResponse(questions = DummyQuestion.generateQuestions())
//
//        firestore.collection("apps").document("questions")
//            .set(questionData)
//            .addOnCompleteListener {
//                Toast.makeText(this, "Success", Toast.LENGTH_SHORT)
//            }
//            .addOnFailureListener {
//                Toast.makeText(this, "Failed", Toast.LENGTH_SHORT)
//            }

        binding.btnToLogin.setOnClickListener {
            val intent = Intent(this, SignInActivity::class.java)
            startActivity(intent)
//
//            firestore.collection("apps")
//                .document("questions")
//                .get()
//                .addOnSuccessListener { snapshot ->
//                    val mapData = snapshot.data
//                    val question = mapData?.get("question-datas") as List<Question>?
//                    val option = mapData?.get("options-data") as List<Option>
//
//                    Log.d("Teston", question.toString())
//                    Log.d("Teston", option.toString())
//
//                }




        }

        binding.btnSignup.setOnClickListener {
            val email = binding.edtEmail.text.toString()
            val pass = binding.edtPassword.text.toString()
            val confirmPass = binding.edtRetypePassword.text.toString()

            if (email.isNotEmpty() && pass.isNotEmpty() && confirmPass.isNotEmpty()) {
                if (pass == confirmPass) {

                    viewModel.signUpUser(name = "Rivaldo Fernandes", email = email, password = pass)


//                    firebaseAuth.createUserWithEmailAndPassword(email, pass).addOnCompleteListener {
//                        if (it.isSuccessful){
//
//                            val user = User(
//                                name = "Rivaldo Fernandes",
//                                email = email,
//                                password = pass,
//                                avatar = ""
//                            )
//                            firestore.collection("user").document(email).set(user)
//                                .addOnCompleteListener {
//                                    Toast.makeText(this, "Success added", Toast.LENGTH_SHORT).show()
//                                }
//                            val intent = Intent(this, SignInActivity::class.java)
//                            startActivity(intent)
//                        } else {
//                            Toast.makeText(this, "failed to create because ${it.exception.toString()}", Toast.LENGTH_SHORT).show()
//                        }
//                    }
                } else {
                    Toast.makeText(this, "Pass not match", Toast.LENGTH_SHORT).show()
                }
            } else {
                Toast.makeText(this, "Empty fields not allowed", Toast.LENGTH_SHORT).show()
            }
        }

        viewModel.signUpUser.observe(this) { state ->
            when (state) {
                is UiState.Loading -> {
                    Toast.makeText(this, "Loading", Toast.LENGTH_SHORT).show()
                }

                is UiState.Failure -> {
                    Toast.makeText(this, state.error, Toast.LENGTH_SHORT).show()
                }

                is UiState.Success -> {
                    Toast.makeText(this, state.data, Toast.LENGTH_SHORT).show()
                    val intent = Intent(this, SignInActivity::class.java)
                    startActivity(intent)
                }
            }
        }

    }
}