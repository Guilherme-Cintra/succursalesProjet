package ca.qc.castroguilherme.succursales

import ca.qc.castroguilherme.succursales.model.EnregistrementBody
import ca.qc.castroguilherme.succursales.model.Login
import ca.qc.castroguilherme.succursales.network.RetrofitInstance

class LoginRepository() {

    suspend fun postConnexion(loginRequest: Login) = RetrofitInstance.retrofitService.postConnexion(loginRequest)

    suspend fun postEnregistrement(enregistrementBody: EnregistrementBody) = RetrofitInstance.retrofitService.postEnregistrement(enregistrementBody)
}