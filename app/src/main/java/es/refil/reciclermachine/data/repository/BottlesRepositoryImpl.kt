package es.refil.reciclermachine.data.repository

import com.google.firebase.firestore.CollectionReference
import es.refil.reciclermachine.domain.model.Bottle
import es.refil.reciclermachine.domain.model.Response
import es.refil.reciclermachine.domain.repository.AddBottleResponse
import es.refil.reciclermachine.domain.repository.BottlesRepository
import kotlinx.coroutines.tasks.await
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class BottlesRepositoryImpl @Inject constructor(
    private val bottlesRef: CollectionReference
) : BottlesRepository {
    override suspend fun addBottleToFirestore(bottle: Bottle): AddBottleResponse {
        return try {
            bottle.code?.let { bottlesRef.document(it).set(bottle).await() }
            Response.Success(true)
        } catch (e: Exception) {
            Response.Failure(e)
        }
    }
}