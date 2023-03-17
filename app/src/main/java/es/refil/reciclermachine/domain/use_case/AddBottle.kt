package es.refil.reciclermachine.domain.use_case

import es.refil.reciclermachine.domain.model.Bottle
import es.refil.reciclermachine.domain.repository.BottlesRepository

class AddBottle(private val repo: BottlesRepository) {
    suspend operator fun invoke(bottle: Bottle) = repo.addBottleToFirestore(bottle)
}
