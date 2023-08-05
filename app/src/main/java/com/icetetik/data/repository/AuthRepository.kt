package com.icetetik.data.repository

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException
import com.google.firebase.auth.FirebaseAuthUserCollisionException
import com.google.firebase.auth.FirebaseAuthWeakPasswordException
import com.google.firebase.firestore.FirebaseFirestore
import com.icetetik.data.model.User
import com.icetetik.util.Extension.showShortToast
import com.icetetik.util.FireStoreCollection
import com.icetetik.util.UiState

class AuthRepository(
    private val auth: FirebaseAuth,
    private val database: FirebaseFirestore
) {


    fun resetPasswordUser(email: String, result: (UiState<String>) -> Unit) {
        auth.sendPasswordResetEmail(email)
            .addOnCompleteListener {
                if (it.isSuccessful) {
                    result.invoke(UiState.Success("Successfully reset password, please check your email to setup new password"))
                } else {
                    result.invoke(UiState.Failure("Reset password failed, please try again later"))
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
                                result.invoke(UiState.Success("User Successfully Registered!"))
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
                        throw it.exception ?: java.lang.Exception("Invalid authentication")
                    } catch (e: FirebaseAuthWeakPasswordException) {
                        result.invoke(UiState.Failure("Authentication failed, Password should be at least 8 characters"))
                    } catch (e: FirebaseAuthInvalidCredentialsException) {
                        result.invoke(UiState.Failure("Authentication failed, Invalid email entered"))
                    } catch (e: FirebaseAuthUserCollisionException) {
                        result.invoke(UiState.Failure("Authentication failed, Email already registered."))
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
                    result.invoke(UiState.Success("Login successfully!"))
                } else {
                    result.invoke(UiState.Failure("Authentication failed, Check email and password"))
                }
            }
            .addOnFailureListener {
                result.invoke(UiState.Failure("Authentication failed, Check email and password"))
            }
    }

    fun signOutUser(result: (UiState<String>) -> Unit) {
        auth.signOut()
        val currentUser = auth.currentUser
        if (currentUser == null) {
            result.invoke(UiState.Success("Successfully Sign Out"))
        } else {
            result.invoke(
                UiState.Failure(
                    "Cannot Sign Out, please try again."
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
                    UiState.Success("User has been save successfully")
                )
            }
            .addOnFailureListener {
                result.invoke(
                    UiState.Failure(it.localizedMessage)
                )
            }
    }


}