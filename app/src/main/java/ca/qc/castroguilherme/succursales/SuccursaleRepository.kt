package ca.qc.castroguilherme.succursales


import androidx.lifecycle.LiveData
import ca.qc.castroguilherme.succursales.model.Aut
import ca.qc.castroguilherme.succursales.model.CreateSuccursale
import ca.qc.castroguilherme.succursales.model.DeleteAllBody
import ca.qc.castroguilherme.succursales.model.DeleteBody
import ca.qc.castroguilherme.succursales.model.Succursale
import ca.qc.castroguilherme.succursales.model.VoirBudgetModel
import ca.qc.castroguilherme.succursales.network.RetrofitInstance

class SuccursaleRepository() {

    suspend fun postAuteurListe(aut: Aut) = RetrofitInstance.retrofitService.postAuteurListe(aut)

    suspend fun postCreateSuccursale(createSuccursale: CreateSuccursale) = RetrofitInstance.retrofitService.postCreateSuccursale(createSuccursale)

    suspend fun deleteSuccursale(deleteBody: DeleteBody) = RetrofitInstance.retrofitService.deleteSuccursale(deleteBody)

    suspend fun deleteAll(aut: DeleteAllBody) = RetrofitInstance.retrofitService.deleteAll(aut)

    suspend fun postVoirBudget(voirBudgetModel: VoirBudgetModel) = RetrofitInstance.retrofitService.postVoirBudget(voirBudgetModel)

    suspend fun nombreSuccursales(aut: Aut) = RetrofitInstance.retrofitService.nombreSuccursales(aut)
}