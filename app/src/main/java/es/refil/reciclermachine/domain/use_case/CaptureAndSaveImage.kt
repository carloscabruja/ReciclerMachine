package es.refil.reciclermachine.domain.use_case

import android.content.Context
import es.refil.reciclermachine.domain.repository.CameraRepository

class CaptureAndSaveImage(private val cameraRepository: CameraRepository) {
    suspend operator fun invoke(context: Context) = cameraRepository.captureAndSaveImage(context)
}
