package com.example.nto_minipigs.ui.screens.QR

import android.app.Activity
import android.util.Log
import androidx.activity.result.ActivityResultLauncher
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.platform.LocalContext
import com.journeyapps.barcodescanner.CaptureManager
import com.journeyapps.barcodescanner.CompoundBarcodeView
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.viewinterop.AndroidView
import androidx.datastore.core.DataStore
import androidx.navigation.NavController
import com.example.nto_minipigs.QRResult
import com.example.nto_minipigs.UserData

@Composable
fun QRScreen(navController: NavController, viewModel: QRViewModel, dataStore: UserData) {
    var scanFlag by remember {
        mutableStateOf(false)
    }

    val login = dataStore.getLogin()
    val password = dataStore.getPassword()

    AndroidView(
        factory = { context ->
            CompoundBarcodeView(context).apply {
                val capture = CaptureManager(context as Activity, this)
                capture.initializeFromIntent(context.intent, null)
                this.setStatusText("")
                capture.decode()
                this.decodeContinuous { result ->
                    if (scanFlag) {
                        return@decodeContinuous
                    }
                    println("scanFlag true")
                    scanFlag = true
                    result.text?.let { barCodeOrQr ->
                        Log.d("penis", barCodeOrQr)
                        scanFlag = true
                        this.pause()

                        viewModel.Fetch(barCodeOrQr.toLong(), login, password)
                        navController.navigate(QRResult)
                    }
                    //If you don't put this scanFlag = false, it will never work again.
                    //you can put a delay over 2 seconds and then scanFlag = false to prevent multiple scanning
                }
                this.resume()
            }
        },
        modifier = Modifier
    )
}