package com.example.feature.mypage.components

import android.graphics.Bitmap
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.SheetState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.unit.dp
import com.google.zxing.BarcodeFormat
import com.google.zxing.qrcode.QRCodeWriter
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ShowQRModal(
    data: String,
    scope: CoroutineScope,
    sheetState: SheetState,
    isSheetOpen: MutableState<Boolean>,
) {
    ModalBottomSheet(
        onDismissRequest = {
            scope.launch {
                sheetState.hide()
                isSheetOpen.value = false
            }
        },
        sheetState = sheetState,
    ) {
        Box(
            modifier = Modifier
                .padding(12.dp)
                .fillMaxWidth()
                .height(400.dp),
            contentAlignment = Alignment.Center,
        ) {
            val qrBitmap = generateQRCode(data)
            if (qrBitmap != null) {
                Image(
                    bitmap = qrBitmap.asImageBitmap(),
                    contentDescription = "QRコード",
                    modifier = Modifier
                        .padding(16.dp)
                        .fillMaxWidth()
                        .height(300.dp),
                )
            } else {
                Text("QRコードの生成に失敗しました。")
            }
        }
    }
}

private fun generateQRCode(data: String): Bitmap? {
    return try {
        val writer = QRCodeWriter()
        val bitMatrix = writer.encode(data, BarcodeFormat.QR_CODE, 512, 512)
        val width = bitMatrix.width
        val height = bitMatrix.height
        val bitmap = Bitmap.createBitmap(width, height, Bitmap.Config.RGB_565)
        for (x in 0 until width) {
            for (y in 0 until height) {
                bitmap.setPixel(x, y, if (bitMatrix[x, y]) android.graphics.Color.BLACK else android.graphics.Color.WHITE)
            }
        }
        bitmap
    } catch (e: Exception) {
        e.printStackTrace()
        null
    }
}
