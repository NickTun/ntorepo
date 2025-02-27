package com.example.nto_minipigs.ui.screens.Main

import RetrofitClient
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.nto_minipigs.Retrofit.Models.User
import kotlinx.coroutines.launch
import okhttp3.Credentials

class MainViewModel: ViewModel() {
    private val serviceApi  = RetrofitClient.apiService

    private val _data = MutableLiveData<NetworkResponse<User>>()
    val data : LiveData<NetworkResponse<User>> = _data

    fun Fetch(login: String?, password: String?) {
        _data.value = NetworkResponse.Loading
        var basic = Credentials.basic(login.toString(), password.toString())

        viewModelScope.launch {
            if(basic != "") {
                try {
                    val response = serviceApi.info(basic)
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