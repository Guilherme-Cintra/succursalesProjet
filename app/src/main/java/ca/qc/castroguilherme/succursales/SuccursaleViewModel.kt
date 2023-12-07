package ca.qc.castroguilherme.succursales
import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import ca.qc.castroguilherme.succursales.model.Aut
import ca.qc.castroguilherme.succursales.model.BudgetResponse
import ca.qc.castroguilherme.succursales.model.CreateSucReponse
import ca.qc.castroguilherme.succursales.model.CreateSuccursale
import ca.qc.castroguilherme.succursales.model.DeleteAllBody
import ca.qc.castroguilherme.succursales.model.DeleteBody
import ca.qc.castroguilherme.succursales.model.DeleteSuccursaleResponse
import ca.qc.castroguilherme.succursales.model.SucNbResp
import ca.qc.castroguilherme.succursales.model.SuccursaleResponse
import ca.qc.castroguilherme.succursales.model.VoirBudgetModel

import kotlinx.coroutines.launch

class SuccursaleViewModel(private val succursaleRepository: SuccursaleRepository) : ViewModel() {





    val succursaleResponse: MutableLiveData<SuccursaleResponse> = MutableLiveData()
    val createSucReponse: MutableLiveData<CreateSucReponse> = MutableLiveData()
    val deleteSucReponse: MutableLiveData<DeleteSuccursaleResponse> = MutableLiveData()
    val deleteAllSucReponse: MutableLiveData<DeleteSuccursaleResponse> = MutableLiveData()
    val budgetResponse: MutableLiveData<BudgetResponse> = MutableLiveData()

    val nbResponse: MutableLiveData<SucNbResp> = MutableLiveData()
    fun postAuteurListe(aut: Aut) = viewModelScope.launch {
        try {
            val response = succursaleRepository.postAuteurListe(aut)
            if (response.isSuccessful) {
                response.body()?.succursales?.let { Log.i(javaClass.simpleName, it.joinToString { "$it\n" }) }
                succursaleResponse.postValue(response.body())
                Log.i("SucVM", "Success: ${response.body()}")
            } else {
                Log.e("SucVM", "Error: response is not succesful ${response.errorBody()?.string()}")
            }
            val succursales = response.body()?.succursales
            val statut = response.body()?.statut
        } catch (e: Exception) {

            Log.i("SucVM", "line 44 VM ERROR HERE")
            Log.i(javaClass.simpleName, e.message.toString())
            Log.i("SucVM", "line 46 VM ERROR ${e.message.toString()}")
        }
    }

    fun postCreateSuccursale(createSuccursale: CreateSuccursale) = viewModelScope.launch {
        try {

            val response = succursaleRepository.postCreateSuccursale(createSuccursale)

            if (response.isSuccessful) {
                Log.i("CreateSuccVM", "Success: ${response.body()}")
                response.body()?.statut.let { Log.i(javaClass.simpleName,  "$it") }
                createSucReponse.postValue(response.body())
            } else {
                Log.e("CreateSuccVM", "Error: response is not succesful ${response.errorBody()?.string()}")
            }

        } catch (e: Exception) {

            Log.i(javaClass.simpleName, e.message.toString())
            Log.i("SucCREATEVM", "ERROR ${e.message.toString()}")
        }
    }

    fun deleteSuccursale(deleteBody: DeleteBody) = viewModelScope.launch {
        try {
            val response = succursaleRepository.deleteSuccursale(deleteBody)
            response.body()?.statut.let {
                Log.i(javaClass.simpleName,  "$it")

            }

            if (response.isSuccessful) {
                Log.i("DeleteVM", "Success: ${response.body()}")
                deleteSucReponse.postValue(response.body())
            } else {
                Log.e("DeleteVM", "Error: response is not succesful line 93 ${response.errorBody()?.string()}")
            }
        } catch (e: Exception) {
            Log.i("DeleteVM", "ERROR HERE")
            Log.i(javaClass.simpleName, e.message.toString())
            Log.i("DeleteVM", "ERROR ${e.message.toString()}")
        }
    }

    fun  deleteAll(aut: DeleteAllBody) = viewModelScope.launch {
        try {
            val response = succursaleRepository.deleteAll(aut)
            response.body()?.statut.let {
                Log.i(javaClass.simpleName,  "$it")
            }
            if (response.isSuccessful){
                Log.i("DeleteALL", "Success: line 105 ${response.body()}")
                Log.i("DeleteALL", "Success: line 106 ${response.errorBody()}")
                deleteAllSucReponse.postValue(response.body())
            } else {
                Log.e("DeleteALL", "Error: response is not succesful line 108 ${response.errorBody()?.string()}")
            }
        }
        catch (e: Exception) {
            Log.i("DeleteALL", "ERROR HERE")
            Log.i(javaClass.simpleName, e.message.toString())
            Log.i("DeleteALL", "ERROR ${e.message.toString()}")
        }
    }

    fun postVoirBudget(voirBudgetModel: VoirBudgetModel) = viewModelScope.launch {
        try {
            val response = succursaleRepository.postVoirBudget(voirBudgetModel)
            response.body()?.statut.let {
                Log.i(javaClass.simpleName,  "$it")
            }

            if (response.isSuccessful){
                budgetResponse.postValue(response.body())
            }else {
                Log.e("VoirBudget", "Error: response is not succesful line 133 ${response.errorBody()?.string()}")
            }
        }    catch (e: Exception) {
            Log.i("VoirBudget", "ERROR HERE")
            Log.i(javaClass.simpleName, e.message.toString())
            Log.i("VoirBudget", "ERROR ${e.message.toString()}")
        }
    }

    fun nombreSuccursale(aut:Aut) = viewModelScope.launch {
        try {
            val response = succursaleRepository.nombreSuccursales(aut)
            response.body()?.statut.let {
                Log.i(javaClass.simpleName,  "$it")

                if (response.isSuccessful){
                    Log.i("nbSucLog", "Success: line 105 ${response.body()}")
                    Log.i("nbSucLog", "Success: line 106 ${response.errorBody()}")
                    nbResponse.postValue(response.body())
                } else {
                    Log.e("DeleteALL", "Error: response is not succesful line 108 ${response.errorBody()?.string()}")
                }
            }
        } catch (e: Exception) {
            Log.i("VoirBudget", "ERROR HERE")
            Log.i(javaClass.simpleName, e.message.toString())
            Log.i("VoirBudget", "ERROR ${e.message.toString()}")
        }
    }
}