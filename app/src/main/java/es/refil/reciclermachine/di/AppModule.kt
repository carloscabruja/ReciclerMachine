package es.refil.reciclermachine.di

import android.app.Application
import android.content.Context
import androidx.camera.core.AspectRatio
import androidx.camera.core.CameraSelector
import androidx.camera.core.ImageCapture
import androidx.camera.core.ImageCapture.FLASH_MODE_ON
import androidx.camera.core.Preview
import androidx.camera.lifecycle.ProcessCameraProvider
import com.google.firebase.firestore.CollectionReference
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.google.mlkit.vision.barcode.common.Barcode
import com.google.mlkit.vision.codescanner.GmsBarcodeScanner
import com.google.mlkit.vision.codescanner.GmsBarcodeScannerOptions
import com.google.mlkit.vision.codescanner.GmsBarcodeScanning
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import es.refil.reciclermachine.core.Constants.BOTTLES
import es.refil.reciclermachine.data.repository.BottlesRepositoryImpl
import es.refil.reciclermachine.data.repository.CameraRepositoryImpl
import es.refil.reciclermachine.domain.repository.BottlesRepository
import es.refil.reciclermachine.domain.repository.CameraRepository
import es.refil.reciclermachine.domain.use_case.*
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    /** Barcode Scanner */
    @Provides
    fun provideContext(app: Application): Context {
        return app.applicationContext
    }

    @Provides
    fun provideBarCodeOptions(): GmsBarcodeScannerOptions {
        return GmsBarcodeScannerOptions.Builder()
            .setBarcodeFormats(Barcode.FORMAT_ALL_FORMATS)
            .build()
    }

    @Provides
    fun provideBarCodeScanner(
        context: Context,
        options: GmsBarcodeScannerOptions
    ): GmsBarcodeScanner {
        return GmsBarcodeScanning.getClient(context, options)
    }

    /** Firebase */
    @Provides
    fun provideBooksRef() = Firebase.firestore.collection(BOTTLES)

    /** CameraX */
    @Provides
    @Singleton
    fun provideCameraSelector(): CameraSelector {
        return CameraSelector.Builder()
            .requireLensFacing(CameraSelector.LENS_FACING_BACK)
            .build()
    }

    @Provides
    @Singleton
    fun provideCameraProvider(application: Application): ProcessCameraProvider {
        return ProcessCameraProvider.getInstance(application).get()
    }

    @Provides
    @Singleton
    fun provideCameraPreview(): Preview {
        return Preview.Builder().build()
    }

    @Provides
    @Singleton
    fun provideImageCapture(): ImageCapture {
        return ImageCapture.Builder()
            .setFlashMode(FLASH_MODE_ON)
            .setTargetAspectRatio(AspectRatio.RATIO_16_9)
            .build()
    }

    /** Repositories */
    @Provides
    fun provideCameraRepository(
        scanner: GmsBarcodeScanner,
        cameraProvider: ProcessCameraProvider,
        cameraSelector: CameraSelector,
        preview: Preview,
        imageCapture: ImageCapture
    ): CameraRepository {
        return CameraRepositoryImpl(
            scanner,
            cameraProvider,
            cameraSelector,
            preview,
            imageCapture
        )
    }

    @Provides
    fun provideBottlesRepository(
        bottlesRef: CollectionReference
    ): BottlesRepository = BottlesRepositoryImpl(bottlesRef)

    /** Use Cases */
    @Provides
    fun provideUseCases(
        CameraRepository: CameraRepository,
        BottlesRepository: BottlesRepository
    ) = UseCases(
        startScanning = StartScanning(CameraRepository),
        addBottle = AddBottle(BottlesRepository),
        captureAndSaveImage = CaptureAndSaveImage(CameraRepository),
        showCameraPreview = ShowCameraPreview(CameraRepository)
    )
}