package com.example.nto_minipigs.ui.screens.Main

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.nto_minipigs.Retrofit.Models.Auth
import com.example.nto_minipigs.Retrofit.Models.User
import com.example.nto_minipigs.UserData
import kotlinx.coroutines.launch

class MainViewModel: ViewModel() {
    private val serviceApi  = RetrofitClient.apiService

    private val _data = MutableLiveData<NetworkResponse<User>>()
    val data : LiveData<NetworkResponse<User>> = _data

    fun Fetch(token: String?) {
        _data.value = NetworkResponse.Loading

        viewModelScope.launch {
            if(token != null) {
                try {
                    val response = serviceApi.info(token)
                    if(response.isSuccessful) {
                        response.body()?.let {
                            _data.value = NetworkResponse.Success(it)
                        }
                    } else {
                        _data.value = NetworkResponse.Error(response.message())
                    }
                } catch(e: Exception) {
                    _data.value = NetworkResponse.Error(e.toString())
                }
            }
        }
    }
}

sealed class NetworkResponse<out T> {
    data class Success<out T>(val data: T) : NetworkResponse<T>()
    data class Error(val message: String) : NetworkResponse<Nothing>()
    object Loading : NetworkResponse<Nothing>()
}