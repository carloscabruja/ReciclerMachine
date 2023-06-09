package es.refil.reciclermachine.domain.use_case

data class UseCases(
    val startScanning: StartScanning,
    val addBottle: AddBottle,
    val captureAndSaveImage: CaptureAndSaveImage,
    val showCameraPreview: ShowCameraPreview
)
