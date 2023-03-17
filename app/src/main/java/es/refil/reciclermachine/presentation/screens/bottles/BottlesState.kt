package es.refil.reciclermachine.presentation.screens.bottles

data class BottlesState(
    val barCode: String = "no barcode scanned ...",
    val openDialog: Boolean = false,
)
