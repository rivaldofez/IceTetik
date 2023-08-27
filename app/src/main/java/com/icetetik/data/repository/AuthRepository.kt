package com.icetetik.data.repository

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException
import com.google.firebase.auth.FirebaseAuthUserCollisionException
import com.google.firebase.auth.FirebaseAuthWeakPasswordException
import com.google.firebase.firestore.FirebaseFirestore
import com.icetetik.data.model.User
import com.icetetik.util.FireStoreCollection
import com.icetetik.util.UiState

class AuthRepository(
    private val auth: FirebaseAuth,
    private val database: FirebaseFirestore
) {

    fun deleteAccount(result: (UiState<String>) -> Unit) {
        val currentUser = auth.currentUser
        currentUser?.delete()
            ?.addOnCompleteListener {
                if (it.isSuccessful) {
                    result.invoke(UiState.Success("Berhasil reset password, silakan cek email kamu untuk mengatur password yang baru"))
                } else {
                    result.invoke(UiState.Failure("Terjadi kesalahan saat memproses reset password, silakan coba lagi"))
                }
            }
            ?.addOnFailureListener {
                result.invoke(
                    UiState.Failure(
                        it.localizedMessage
                    )
                )
            }
    }

    fun resetPasswordUser(email: String, result: (UiState<String>) -> Unit) {
        auth.sendPasswordResetEmail(email)
            .addOnCompleteListener {
                if (it.isSuccessful) {
                    result.invoke(UiState.Success("Berhasil reset password, silakan cek email kamu untuk mengatur password yang baru"))
                } else {
                    result.invoke(UiState.Failure("Terjadi kesalahan saat memproses reset password, silakan coba lagi"))
                }
            }
            .addOnFailureListener {
                result.invoke(
                    UiState.Failure(
                        it.localizedMessage
                    )
                )
            }
    }


    fun signUpUser(user: User, result: (UiState<String>) -> Unit) {
        auth.createUserWithEmailAndPassword(user.email, user.password)
            .addOnCompleteListener {
                if (it.isSuccessful) {
                    saveUserInfo(user) { state ->
                        when (state) {
                            is UiState.Success -> {
                                result.invoke(UiState.Success("User berhasil terdaftar"))
                            }

                            is UiState.Failure -> {
                                result.invoke(UiState.Failure(state.error))
                            }

                            is UiState.Loading -> {
                                result.invoke(UiState.Loading)
                            }
                        }
                    }
                } else {
                    try {
                        throw it.exception ?: java.lang.Exception("Autentikasi tidak valid")
                    } catch (e: FirebaseAuthWeakPasswordException) {
                        result.invoke(UiState.Failure("Autentikasi gagal, password minimal mengandung 8 karakter"))
                    } catch (e: FirebaseAuthInvalidCredentialsException) {
                        result.invoke(UiState.Failure("Autentikasi gagal, email kamu tidak valid"))
                    } catch (e: FirebaseAuthUserCollisionException) {
                        result.invoke(UiState.Failure("Autentikasi gagal, email sudah terdaftar."))
                    } catch (e: Exception) {
                        result.invoke(UiState.Failure(e.message))
                    }
                }
            }
    }


    fun signInUser(email: String, password: String, result: (UiState<String>) -> Unit) {
        auth.signInWithEmailAndPassword(email, password)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    result.invoke(UiState.Success("Sign in berhasil"))
                } else {
                    result.invoke(UiState.Failure("Autentikasi gagal, periksa kembali email dan password kamu"))
                }
            }
            .addOnFailureListener {
                result.invoke(UiState.Failure("Autentikasi gagal, periksa kembali email dan password kamu"))
            }
    }

    fun signOutUser(result: (UiState<String>) -> Unit) {
        auth.signOut()
        val currentUser = auth.currentUser
        if (currentUser == null) {
            result.invoke(UiState.Success("Berhasl Sign Out"))
        } else {
            result.invoke(
                UiState.Failure(
                    "Tidak dapat Sign Out, silakan coba lagi"
                )
            )
        }
    }


    fun getUserSession(result: (String?) -> Unit) {
        val currentUser = auth.currentUser
        if (currentUser == null) {
            result.invoke(null)
        } else {
            result.invoke(
                currentUser.email
            )
        }
    }

    fun getUserInfo(userEmail: String, result: (UiState<User?>) -> Unit) {
        database.collection((FireStoreCollection.USER)).document(userEmail)
            .get()
            .addOnSuccessListener { snapshot ->
                val dataResult = snapshot.toObject(User::class.java)
                if (dataResult == null) {
                    result.invoke(UiState.Success(null))
                } else {
                    result.invoke(UiState.Success(dataResult))
                }
            }
            .addOnFailureListener {
                result.invoke(
                    UiState.Failure(
                        it.localizedMessage
                    )
                )
            }

    }

    fun saveUserInfo(user: User, result: (UiState<String>) -> Unit) {
        val document = database.collection(FireStoreCollection.USER).document(user.email)
        document.set(user)
            .addOnSuccessListener {
                result.invoke(
                    UiState.Success("Data user berhasil disimpan")
                )
            }
            .addOnFailureListener {
                result.invoke(
                    UiState.Failure(it.localizedMessage)
                )
            }
    }


}