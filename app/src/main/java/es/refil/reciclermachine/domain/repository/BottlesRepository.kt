package es.refil.reciclermachine.domain.repository

import es.refil.reciclermachine.domain.model.Bottle
import es.refil.reciclermachine.domain.model.Response

typealias AddBottleResponse = Response<Boolean>

interface BottlesRepository {
    suspend fun addBottleToFirestore(bottle: Bottle): AddBottleResponse
}