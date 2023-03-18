package es.refil.reciclermachine.domain.repository

import android.content.Context
import android.net.Uri
import androidx.camera.view.PreviewView
import androidx.lifecycle.LifecycleOwner
import kotlinx.coroutines.flow.Flow

interface CameraRepository {
    fun startScanning(): Flow<String?>
    suspend fun captureAndSaveImage(context: Context): Flow<Uri?>
    suspend fun showCameraPreview(previewView: PreviewView, lifecicleOwner: LifecycleOwner)
}