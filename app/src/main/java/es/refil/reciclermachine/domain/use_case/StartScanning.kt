package es.refil.reciclermachine.domain.use_case

import es.refil.reciclermachine.domain.repository.CameraRepository

class StartScanning(private val cameraRepository: CameraRepository) {
    operator fun invoke() = cameraRepository.startScanning()
}
