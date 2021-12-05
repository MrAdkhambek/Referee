package r2.llc.referee.repository

import r2.llc.referee.main.Model

interface MainRepository {

    suspend fun loadItems(): Model
}