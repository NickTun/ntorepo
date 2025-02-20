package com.example.nto_minipigs

import android.content.Context
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
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

                val token = dataStore.getToken()

                Log.d("token", token.toString())

                NavHost(
                    navController = navController,
                    startDestination = if(token == "") Login else Main
//                    startDestination = Login
                ) {
                    composable<Login> { LoginScreen( onNavigateToMain = { navController.popBackStack(); navController.navigate(route = Main) }, viewModel = loginViewModel, dataStore = dataStore ) }
                    composable<Main> { MainScreen( viewModel = mainViewModel, dataStore = dataStore ) }
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

    companion object {
        val tokenKey = stringPreferencesKey("Bearer")
    }
}

@Serializable
object Login

@Serializable
object Main
