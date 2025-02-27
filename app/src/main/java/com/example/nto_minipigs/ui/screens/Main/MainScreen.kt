package com.example.nto_minipigs.ui.screens.Main

import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.outlined.Logout
import androidx.compose.material.icons.outlined.QrCode
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedCard
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import coil.compose.rememberAsyncImagePainter
import com.example.nto_minipigs.Login
import com.example.nto_minipigs.QR
import com.example.nto_minipigs.UserData
import com.example.nto_minipigs.ui.theme.Nto_minipigsTheme
import kotlinx.coroutines.launch


@Composable
fun MainScreen( viewModel: MainViewModel, dataStore: UserData, navController: NavController) {
    Nto_minipigsTheme {
        val data = viewModel.data.observeAsState()
        val token = dataStore.getToken()
        val login = dataStore.getLogin()
        val password = dataStore.getPassword()
        val coroutineScope = rememberCoroutineScope()
        Surface {
            when(val result = data.value){
                is NetworkResponse.Error -> {
                    Text(text = result.message)
                }
                NetworkResponse.Loading -> {
                    Column(
                        modifier = Modifier.fillMaxSize(),
                        verticalArrangement = Arrangement.Center,
                        horizontalAlignment = Alignment.CenterHorizontally,
                    ) {
                        CircularProgressIndicator()
                    }
                }
                is NetworkResponse.Success -> {
                    Log.d("yenis", result.data.entries.toString())
                    Column (
                        modifier = Modifier.fillMaxSize()
                    ) {
                        Box(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(top = 40.dp)
                                .height(200.dp),
                        ) {
                            IconButton(
                                onClick = { coroutineScope.launch {
                                    dataStore.updateToken("");
                                    dataStore.updateLogin("");
                                    dataStore.updateAdmin(false);
                                    dataStore.updatePassword("") };
                                    navController.popBackStack();
                                    navController.navigate(Login) },
                                modifier = Modifier
                                    .align(Alignment.TopEnd)
                                    .size(20.dp)
                                    .aspectRatio(1f)
                                    .offset(x = -30.dp, y = 40.dp)
                            ) {
                                Icon(
                                Icons.AutoMirrored.Outlined.Logout,
                                contentDescription = null,
                                tint = MaterialTheme.colorScheme.error,
                                modifier = Modifier
                                    .fillMaxSize()
                                    .shadow(7.dp)
                            ) }


                            Row(
                                modifier = Modifier.fillMaxSize(),
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Column (
                                    verticalArrangement = Arrangement.Center,
                                    horizontalAlignment = Alignment.CenterHorizontally,
                                    modifier = Modifier
                                        .padding(start = 30.dp)
                                        .size(120.dp)
                                        .aspectRatio(1f)
                                        .background(MaterialTheme.colorScheme.primaryContainer, CircleShape)
                                ) {
                                    Image(
                                        painter = rememberAsyncImagePainter(result.data.photo),
                                        contentDescription = null,
                                        contentScale = ContentScale.Fit,
                                        modifier = Modifier.size(105.dp).clip(CircleShape)
                                    )
                                }

                                Column(
                                    modifier = Modifier
                                        .padding(horizontal = 25.dp)
                                ) {
                                    Text(
                                        text = result.data.lastVisit,
                                        style = MaterialTheme.typography.titleSmall,
                                        fontWeight = FontWeight.Light
                                    )
                                    Text(
                                        text = result.data.name,
                                        style = MaterialTheme.typography.titleMedium,
                                        fontWeight = FontWeight.Medium
                                    )
                                    Text(
                                        text = result.data.position,
                                        style = MaterialTheme.typography.titleSmall,
                                        fontWeight = FontWeight.Normal
                                    )
                                }
                            }
                        }

                        Box(
                            modifier = Modifier
                                .fillMaxSize()
                                .background(MaterialTheme.colorScheme.surfaceContainerLow, RoundedCornerShape(topEnd = 24.dp , topStart = 24.dp))
                        ) {
                            LazyColumn(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(horizontal = 20.dp)
                                    .padding(top = 8.dp)
                            ) {
                                items(
                                    items = result.data.entries,
                                    key = { item -> item.id }
                                ) { entry ->
                                    OutlinedCard(
                                        modifier = Modifier
                                            .fillMaxWidth()
                                            .padding(top = 12.dp)
                                    ) {
                                        Text(
                                            text = entry.time,
                                            style = MaterialTheme.typography.titleMedium,
                                            fontWeight = FontWeight.Light,
                                            modifier = Modifier
                                                .padding(start = 16.dp)
                                                .padding(top = 12.dp)
                                        )
                                        Text(
                                            text = entry.place.name,
                                            style = MaterialTheme.typography.titleMedium,
                                            fontWeight = FontWeight.Normal,
                                            modifier = Modifier
                                                .padding(start = 16.dp)
                                                .padding(bottom = 12.dp)
                                        )
                                    }
                                }
                            }
                            Button(
                                onClick =  { navController.navigate(QR) },
                                shape = CircleShape,
                                modifier = Modifier
                                    .align(Alignment.BottomEnd)
                                    .size(80.dp)
                                    .aspectRatio(1f)
                                    .offset(x = -30.dp, y = -80.dp)
                                    .shadow(7.dp)
                            ) {
                                Icon(Icons.Outlined.QrCode, contentDescription = null, Modifier.size(60.dp))
                            }
                        }
                    }
                }
                null -> {}
            }
        }

        LaunchedEffect(Unit) {
            viewModel.Fetch(login, password)
        }
    }
}
