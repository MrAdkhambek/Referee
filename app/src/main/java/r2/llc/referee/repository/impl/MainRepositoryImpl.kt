package r2.llc.referee.repository.impl

import kotlinx.coroutines.delay
import r2.llc.referee.main.Model
import r2.llc.referee.repository.MainRepository
import javax.inject.Inject


class MainRepositoryImpl @Inject constructor() : MainRepository {

    override suspend fun loadItems(): Model {
        delay(2000)
        return Model(
            list = (1..100).map {
                "Item $it"
            }
        )
    }
}