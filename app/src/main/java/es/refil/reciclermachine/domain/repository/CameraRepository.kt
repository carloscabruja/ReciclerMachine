package es.refil.reciclermachine.domain.repository

import kotlinx.coroutines.flow.Flow

interface CameraRepository {
    fun startScanning(): Flow<String?>
}