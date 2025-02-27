package com.example.nto_minipigs

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.nto_minipigs.ui.screens.Login.LoginScreen
import com.example.nto_minipigs.ui.screens.Login.LoginViewModel
import com.example.nto_minipigs.ui.screens.Main.MainScreen
import com.example.nto_minipigs.ui.screens.Main.MainViewModel
import com.example.nto_minipigs.ui.theme.Nto_minipigsTheme
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.runBlocking
import kotlinx.serialization.Serializable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.datastore.preferences.core.booleanPreferencesKey
import com.example.nto_minipigs.ui.screens.QR.QRResultScreen
import com.example.nto_minipigs.ui.screens.QR.QRScreen
import com.example.nto_minipigs.ui.screens.QR.QRViewModel

class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        enableEdgeToEdge()
        setContent {
            Nto_minipigsTheme {
                val dataStore = remember {
                    UserData(applicationContext)
                }

                val navController = rememberNavController()
                val loginViewModel = ViewModelProvider(this)[LoginViewModel::class.java]
                val mainViewModel = ViewModelProvider(this)[MainViewModel::class.java]
                val qrViewModel = ViewModelProvider(this)[QRViewModel::class.java]

                val token = dataStore.getLogin()

                NavHost(
                    navController = navController,
                    startDestination = if(token == "") Login else Main
                ) {
                    composable<Login> { LoginScreen( onNavigateToMain = { navController.popBackStack(); navController.navigate(route = Main) }, viewModel = loginViewModel, dataStore = dataStore ) }
                    composable<Main> { MainScreen( viewModel = mainViewModel, dataStore = dataStore, navController = navController ) }
                    composable<QR> { QRScreen( navController = navController, viewModel = qrViewModel, dataStore = dataStore) }
                    composable<QRResult> { QRResultScreen( navController = navController, viewModel = qrViewModel ) }
                }
            }
        }
    }
}

class UserData(private val context: Context) {
    val Context.dataStore by preferencesDataStore("user_data")

    fun getToken():String {
        return runBlocking { context.dataStore.data.map { preferences ->
            preferences[tokenKey]
        }.first().toString()}
    }

    suspend fun updateToken(data:String) =
        context.dataStore.edit { settings ->
            settings[tokenKey] = data
        }

    fun getLogin():String {
        return runBlocking { context.dataStore.data.map { preferences ->
            preferences[loginKey]
        }.first().toString()}
    }

    suspend fun updateLogin(data:String) =
        context.dataStore.edit { settings ->
            settings[loginKey] = data
        }

    fun getPassword():String {
        return runBlocking { context.dataStore.data.map { preferences ->
            preferences[passwordKey]
        }.first().toString()}
    }

    suspend fun updatePassword(data:String) =
        context.dataStore.edit { settings ->
            settings[passwordKey] = data
        }

    fun getAdmin():Boolean {
        return runBlocking {
            context.dataStore.data.map { preferences ->
                preferences[adminKey]
            }.first() == true
        }
    }

    suspend fun updateAdmin(data:Boolean) =
        context.dataStore.edit { settings ->
            settings[adminKey] = data
        }

    companion object {
        val tokenKey = stringPreferencesKey("Bearer")
        val loginKey = stringPreferencesKey("Login")
        val passwordKey = stringPreferencesKey("Password")
        val adminKey = booleanPreferencesKey("IsAdmin")
    }
}

@Serializable
object Login

@Serializable
object Main

@Serializable
object QR

@Serializable
object QRResult
