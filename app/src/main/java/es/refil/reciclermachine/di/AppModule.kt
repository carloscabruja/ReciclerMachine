package es.refil.reciclermachine.di

import android.app.Application
import android.content.Context
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
import es.refil.reciclermachine.domain.use_case.AddBottle
import es.refil.reciclermachine.domain.use_case.StartScanning
import es.refil.reciclermachine.domain.use_case.UseCases

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

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

    @Provides
    fun provideBooksRef() = Firebase.firestore.collection(BOTTLES)

    @Provides
    fun provideCameraRepository(scanner: GmsBarcodeScanner): CameraRepository {
        return CameraRepositoryImpl(scanner)
    }

    @Provides
    fun provideBottlesRepository(
        bottlesRef: CollectionReference
    ): BottlesRepository = BottlesRepositoryImpl(bottlesRef)

    @Provides
    fun provideUseCases(
        CameraRepository: CameraRepository,
        BottlesRepository: BottlesRepository
    ) = UseCases(
        startScanning = StartScanning(CameraRepository),
        addBottle = AddBottle(BottlesRepository)
    )
}