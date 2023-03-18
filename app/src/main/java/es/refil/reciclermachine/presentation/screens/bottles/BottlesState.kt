package es.refil.reciclermachine.presentation.screens.bottles

import android.net.Uri

data class BottlesState(
    val barCode: String = "no barcode scanned ...",
    val user: String = "no user detected ...",
    val imageUriDetected: Uri = Uri.EMPTY,
    val openDialog: Boolean = false,
    val isCameraVisible: Boolean = false,
)
