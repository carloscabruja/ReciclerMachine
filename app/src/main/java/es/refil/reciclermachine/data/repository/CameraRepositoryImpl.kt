package es.refil.reciclermachine.data.repository

import android.content.ContentValues
import android.content.Context
import android.net.Uri
import android.os.Build
import android.provider.MediaStore
import androidx.camera.core.CameraSelector
import androidx.camera.core.ImageCapture
import androidx.camera.core.ImageCaptureException
import androidx.camera.core.Preview
import androidx.camera.lifecycle.ProcessCameraProvider
import androidx.camera.view.PreviewView
import androidx.core.content.ContextCompat
import androidx.lifecycle.LifecycleOwner
import com.google.mlkit.vision.codescanner.GmsBarcodeScanner
import es.refil.reciclermachine.domain.repository.CameraRepository
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.*
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class CameraRepositoryImpl @Inject constructor(
    private val scanner: GmsBarcodeScanner,
    private val cameraProvider: ProcessCameraProvider,
    private val cameraSelector: CameraSelector,
    private val preview: Preview,
    private val imageCapture: ImageCapture
) : CameraRepository {

    override fun startScanning(): Flow<String?> {
        return callbackFlow {
            scanner.startScan()
                .addOnSuccessListener {
                    launch {
                        send(it.rawValue)
                    }
                }.addOnFailureListener {
                    it.printStackTrace()
                }
            awaitClose { }
        }

    }

    override suspend fun captureAndSaveImage(context: Context): Flow<Uri?> {
        return callbackFlow {
            val name = SimpleDateFormat("yyyyMMdd_HHmmss", Locale.getDefault()).format(Date())
            val contentValues = ContentValues().apply {
                put(MediaStore.MediaColumns.DISPLAY_NAME, name)
                put(MediaStore.MediaColumns.MIME_TYPE, "image/jpeg")

                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
                    put(MediaStore.MediaColumns.RELATIVE_PATH, "Pictures/RecyclerMachine")
                }
            }

            val outputOptions = ImageCapture.OutputFileOptions
                .Builder(
                    context.contentResolver,
                    MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
                    contentValues
                ).build()

            imageCapture.takePicture(
                outputOptions,
                ContextCompat.getMainExecutor(context),
                object : ImageCapture.OnImageSavedCallback {
                    override fun onImageSaved(outputFileResults: ImageCapture.OutputFileResults) {
                        launch { send(outputFileResults.savedUri) }
                    }

                    override fun onError(exception: ImageCaptureException) {
                        exception.printStackTrace()
                    }
                }
            )
            awaitClose { }
        }
    }

    override suspend fun showCameraPreview(
        previewView: PreviewView,
        lifecicleOwner: LifecycleOwner
    ) {
        preview.setSurfaceProvider(previewView.surfaceProvider)
        try {
            cameraProvider.unbindAll()
            cameraProvider.bindToLifecycle(lifecicleOwner, cameraSelector, preview, imageCapture)
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }
}