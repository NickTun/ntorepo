package com.example.nto_minipigs.ui.screens.Main

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
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
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Search
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedCard
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.rememberDateRangePickerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.example.nto_minipigs.ui.theme.Nto_minipigsTheme
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.setValue
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import coil.compose.rememberAsyncImagePainter
import com.example.nto_minipigs.UserData

@Composable
fun MainScreen( viewModel: MainViewModel, dataStore: UserData) {
    Nto_minipigsTheme {
        val data = viewModel.data.observeAsState()
        val token = dataStore.getToken()
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
                    Column (
                        modifier = Modifier.fillMaxSize()
                    ) {
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(top = 40.dp)
                                .height(200.dp),
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
                        Box(
                            modifier = Modifier
                                .fillMaxSize()
                                .background(MaterialTheme.colorScheme.surfaceContainerLow, RoundedCornerShape(topEnd = 24.dp , topStart = 24.dp))
                        ) {
                            Column(
                                modifier = Modifier
                                    .fillMaxSize()
                                    .padding(horizontal = 20.dp)
                                    .padding(top = 8.dp)
                                    .verticalScroll(rememberScrollState())
                            ) {
                                for(i in 1..24) {
                                    OutlinedCard(
                                        modifier = Modifier
                                            .fillMaxWidth()
                                            .padding(top = 12.dp)
                                    ) {
                                        Text(
                                            text = "4am 141",
                                            style = MaterialTheme.typography.titleMedium,
                                            fontWeight = FontWeight.Light,
                                            modifier = Modifier
                                                .padding(start = 16.dp)
                                                .padding(top = 12.dp)
                                        )
                                        Text(
                                            text = "Dildos factory",
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
                                onClick = { },
                                shape = CircleShape,
                                modifier = Modifier
                                    .align(Alignment.BottomEnd)
                                    .size(80.dp)
                                    .aspectRatio(1f)
                                    .offset(x = -30.dp, y = -80.dp)
                                    .shadow(7.dp)
                            ) {
                               Icon(Icons.Outlined.Search)
                            }
                        }
                    }
                }
                null -> {}
            }
        }

        LaunchedEffect(Unit) {
            viewModel.Fetch(token)
        }
    }
}
