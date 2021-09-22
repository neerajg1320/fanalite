package com.fanalite.rulesapp.viewmodels

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
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

    fun loginUser(email:String, password:String) {
        viewModelScope.launch(Dispatchers.IO) {
            val response:LoginUserResponse? = fanaliteAuthRepository.loginUser(email, password)
            Log.d(TAG, "response=${response}")

            withContext(Dispatchers.Main) {
                // Inform the view
            }
        }
    }

}