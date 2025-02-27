package com.example.nto_minipigs.ui.screens.QR

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.nto_minipigs.Retrofit.Models.Auth
import com.example.nto_minipigs.Retrofit.Models.Door
import com.example.nto_minipigs.Retrofit.Models.DoorResponse
import kotlinx.coroutines.launch
import okhttp3.Credentials

class QRViewModel: ViewModel() {
    private val serviceApi  = RetrofitClient.apiService

    private val _data = MutableLiveData<NetworkResponse<DoorResponse>>()
    val data : LiveData<NetworkResponse<DoorResponse>> = _data

    fun Fetch(id: Long, login:String, password:String) {
        _data.value = NetworkResponse.Loading

        viewModelScope.launch {
            if(id != null) {
                try {
                    var basic = Credentials.basic(login.toString(), password.toString())
                    val response = serviceApi.open(basic, id)
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