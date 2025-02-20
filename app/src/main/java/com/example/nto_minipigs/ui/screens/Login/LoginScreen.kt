package com.example.nto_minipigs.ui.screens.Login

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.example.nto_minipigs.ui.theme.Nto_minipigsTheme
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.unit.dp
import com.example.nto_minipigs.UserData

@Composable
fun LoginScreen(onNavigateToMain: () -> Unit, viewModel: LoginViewModel, dataStore: UserData) {
    Nto_minipigsTheme {
        val cor = rememberCoroutineScope()
        Surface {
            Column(
                modifier = Modifier.fillMaxSize(),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally,
            ) {
                Text(
                    text = "MiniPigs",
                    style = MaterialTheme.typography.displayMedium,
                    modifier = Modifier.padding(bottom = 36.dp)
                )

                var login by remember { mutableStateOf("") }
                var password by remember { mutableStateOf("") }

                OutlinedTextField(
                    value = login,
                    onValueChange = { login = it },
                    label = { Text("Login") },
                    modifier = Modifier.padding(bottom = 4.dp),
                    shape = RoundedCornerShape(12.dp)
                )

                OutlinedTextField(
                    value = password,
                    onValueChange = { password = it },
                    label = { Text("Password") },
                    modifier = Modifier.padding(bottom = 12.dp),
                    shape = RoundedCornerShape(12.dp),
                )

                Button(
                    onClick = { viewModel.Authenticate(login, password, onNavigateToMain, dataStore) },
                    shape = RoundedCornerShape(12.dp),
                    ) {
                    Text(text = "Sign In", style = MaterialTheme.typography.bodyLarge)
                }
            }
        }
    }
}
