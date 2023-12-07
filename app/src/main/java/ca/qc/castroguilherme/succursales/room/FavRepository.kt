package ca.qc.castroguilherme.succursales.room

import androidx.lifecycle.LiveData
import ca.qc.castroguilherme.succursales.model.Succursale

class FavRepository(private val succursaleDAO: SuccursaleDAO) {

    //ROOM

    val allSuccursales: LiveData<List<Succursale>> = succursaleDAO.getSuccursales()

    suspend fun insert(succursale: Succursale) {
        succursaleDAO.insert(succursale)
    }

    suspend fun deleteAll(){
        succursaleDAO.deleteAll()
    }

    suspend fun delete(succursale: Succursale){
        succursaleDAO.deleteSuccursale(succursale)
    }

    suspend fun update(succursale: Succursale){
        succursaleDAO.updateSuccursale(succursale)
    }
}