package com.example.nto_minipigs.ui.screens.Login

import RetrofitClient
import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.nto_minipigs.Retrofit.Models.Auth
import com.example.nto_minipigs.UserData
import kotlinx.coroutines.launch

class LoginViewModel :ViewModel() {
    private val serviceApi  = RetrofitClient.apiService

    fun Authenticate(login: String, password: String, func: () -> Unit, dataStore: UserData) {
        viewModelScope.launch {
            try {
                val auth = Auth(login, password)
                val response = serviceApi.login(auth)
                if(response.isSuccessful) {
//                    dataStore.updateToken(response.body()?.token.toString())
                    dataStore.updateLogin(login)
                    dataStore.updatePassword(password)
                    Log.d("yenis", (response.body()?.admin == true).toString())
                    dataStore.updateAdmin(response.body()?.admin == true)
//                    dataStore.updateToken("")
                    func()
                }
            } catch (e: Exception) {
                Log.d("exception:", e.toString())
            }
        }
    }
}