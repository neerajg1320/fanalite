package com.fanalite.rulesapp.viewmodels

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.fanalite.rulesapp.models.RegexModel
import com.fanalite.rulesapp.repository.FanaliteAuthRepository
import com.fanalite.rulesapp.retrofitFanalite.models.LoginUserResponse
import com.fanalite.rulesapp.view.TAG
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class AuthenticationViewModel(application: Application): AndroidViewModel(application) {
    private val fanaliteAuthRepository = FanaliteAuthRepository()

    private var mutableToken:MutableLiveData<String> = MutableLiveData()
    val token:LiveData<String> = mutableToken

    fun loginUser(email:String, password:String) {
        Log.d(TAG, "AuthenticationViewModel:loginUser")
        viewModelScope.launch(Dispatchers.IO) {
            val response:LoginUserResponse? = fanaliteAuthRepository.loginUser(email, password)
            withContext(Dispatchers.Main) {
                // Inform the view
                if (response != null) {
                    mutableToken.setValue(response.accessToken)
                }
            }
        }
    }

}