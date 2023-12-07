package ca.qc.castroguilherme.succursales
import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import ca.qc.castroguilherme.succursales.model.EnregistrementBody
import ca.qc.castroguilherme.succursales.model.EnregistrementReponse
import ca.qc.castroguilherme.succursales.model.Login
import ca.qc.castroguilherme.succursales.model.LoginResponse

import kotlinx.coroutines.launch

class LoginViewModel(private  val loginRepository: LoginRepository) : ViewModel() {

    val loginResponse: MutableLiveData<LoginResponse> = MutableLiveData()

    val enregistrementReponse: MutableLiveData<EnregistrementReponse> = MutableLiveData()

    fun postConnexion(loginRequest: Login) = viewModelScope.launch {
        Log.i("Loger", "I am here")

        try {

            val response = loginRepository.postConnexion(loginRequest)

            response.body()?.student?.let {"Student info: ${ response.body()?.student}"}


            if (response.isSuccessful) {
                Log.i("Studentinho", "Success: VM line 30 ${response.body()}")
                loginResponse.postValue(response.body())
                Log.i("Studentinho", "Success: VM line 32 ${response.body()}")
            } else {
                Log.e("Studentinho", "Error: ${response.errorBody()?.string()}")
            }
//            val student = response.body()?.student
//            val statut = response.body()?.statut
//            val errorMessage = response.body()?.error
        } catch (e: Exception) {
            Log.i("Loger", "ERROR HERE")
            Log.i(javaClass.simpleName, e.message.toString())
            Log.i("Loger", "ERROR ${e.message.toString()}")
        }
    }

    fun postEnregistrement(enregistrementBody: EnregistrementBody) = viewModelScope.launch {
        Log.i("EnregistrerResponse", "I am here")
        try {

            val response = loginRepository.postEnregistrement(enregistrementBody)

            response.body()?.student?.let {"Student info: ${ response.body()?.student}"}
            enregistrementReponse.postValue(response.body())

            if (response.isSuccessful) {
                Log.i("EnregistrerResponse", "Success: ${response.body()}")
            } else {
                Log.e("EnregistrerResponse", "Error: ${response.errorBody()?.string()}")
            }


            val student = response.body()?.student

            val statut = response.body()?.statut

            val errorMessage = response.body()?.error
        } catch (e: Exception) {
            Log.i("EnregistrerResponse", "ERROR HERE")
            Log.i(javaClass.simpleName, e.message.toString())
            Log.i("EnregistrerResponse", "ERROR ${e.message.toString()}")
        }


    }




}