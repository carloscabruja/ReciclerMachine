package es.refil.reciclermachine.domain.use_case

import androidx.camera.view.PreviewView
import androidx.lifecycle.LifecycleOwner
import es.refil.reciclermachine.domain.repository.CameraRepository

class ShowCameraPreview(private val cameraRepository: CameraRepository) {
    suspend operator fun invoke(previewView: PreviewView, lifecicleOwner: LifecycleOwner) {
        cameraRepository.showCameraPreview(previewView, lifecicleOwner)
    }
}