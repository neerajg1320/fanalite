package com.fanalite.rulesapp.repository

import android.util.Log
import com.fanalite.rulesapp.models.RegexModel
import com.fanalite.rulesapp.view.TAG
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*
import kotlinx.coroutines.tasks.await


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

    suspend fun getAllData(): List<RegexModel>? {
        val regexList = mutableListOf<RegexModel>()

        val snapshot: DataSnapshot = regexRef.get().await()
        snapshot.children.forEach { child ->
            regexList.add(child.getValue(RegexModel::class.java)!!)
        }

        return regexList
    }

    fun generateId():String {
        return regexRef.push().key!!
    }

    suspend fun insertData(data: RegexModel) {
        regexRef.child(data.id).setValue(data).await()
    }

    suspend fun updateData(data: RegexModel) {
        regexRef.child(data.id).setValue(data).await()
    }

    suspend fun deleteData(data: RegexModel) {
        regexRef.child(data.id).removeValue().await()
    }

    suspend fun deleteAll() {
        regexRef.removeValue().await()
    }
}