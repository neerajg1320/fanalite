package com.fanalite.rulesapp.repository

import android.util.Log
import androidx.lifecycle.LiveData
import com.fanalite.rulesapp.models.RegexModel
import com.fanalite.rulesapp.view.TAG
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*


class RegexRemoteRepository {
    private var authentication: FirebaseAuth? = null
    private var database: FirebaseDatabase
    private var regexRef: DatabaseReference

    init {
        authentication = FirebaseAuth.getInstance()
        database = FirebaseDatabase.getInstance()
        regexRef = database.getReference("regexModels")
    }

    fun createUser(email:String, password:String) {
        authentication?.let {
            it.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener { task: Task<AuthResult> ->
                    if (!task.isSuccessful) {
                        Log.d(TAG, "Registration Failed with ${task.exception}")
                        //_registrationStatus.postValue(ResultOf.Success("Registration Failed with ${task.exception}"))
                    } else {
                        //_registrationStatus.postValue(ResultOf.Success("UserCreated"))
                        Log.d(TAG, "Registration Successful")
                    }
                    //loading.postValue(false)
                }
        }
    }

    fun loginUser(email:String, password:String) {
        authentication?.let {
            it.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener { task: Task<AuthResult> ->
                    if (!task.isSuccessful) {
                        Log.d(TAG, "SignIn Failed with ${task.exception}")
                        //_registrationStatus.postValue(ResultOf.Success("Registration Failed with ${task.exception}"))
                    } else {
                        //_registrationStatus.postValue(ResultOf.Success("UserCreated"))
                        Log.d(TAG, "SignIn Successful")
                    }
                    //loading.postValue(false)
                }
        }
    }

    fun currentUser() = authentication?.currentUser


    fun logoutUser() {
        authentication?.let {
            it.signOut()
        }
    }

    fun generateId():String? {
        return regexRef.push().key
    }

    fun insertData(id:String?, data: RegexModel): String? {
        var newId = id?:generateId()

        newId?.let {
            regexRef.child(it).setValue(data).addOnCompleteListener {
                Log.d(TAG, "Firebase: Complete")
            }.addOnSuccessListener {
                Log.d(TAG, "Firebase: Success")
            }.addOnFailureListener {
                Log.d(TAG, "Firebase: Failure")
            }.addOnCanceledListener {
                Log.d(TAG, "Firebase: Cancelled")
            }
        }

        return id
    }

    // Here we have return a List<RegexModel>
    fun getData(id: String): Task<DataSnapshot> {
        return regexRef.child(id).get().addOnSuccessListener {
            Log.d(TAG, "Firebase: snapshot: $it")
        }.addOnFailureListener {
            Log.e(TAG, " Firebase: exception: ${it.localizedMessage}")
        }
    }

    fun getAllData(): Task<DataSnapshot> {
        return regexRef.get().addOnSuccessListener {
            Log.d(TAG, "Firebase: snapshot: $it")
        }.addOnFailureListener {
            Log.e(TAG, " Firebase: exception: ${it.localizedMessage}")
        }

    }



    fun updateData(regexModel: RegexModel) {

    }

    fun deleteData(regexModel: RegexModel) {
        regexRef.child(regexModel.id)
    }

}