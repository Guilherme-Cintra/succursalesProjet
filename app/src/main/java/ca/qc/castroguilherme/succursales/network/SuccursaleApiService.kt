package ca.qc.castroguilherme.succursales.network




import ca.qc.castroguilherme.succursales.model.Aut
import ca.qc.castroguilherme.succursales.model.BudgetResponse
import ca.qc.castroguilherme.succursales.model.CreateSucReponse
import ca.qc.castroguilherme.succursales.model.CreateSuccursale
import ca.qc.castroguilherme.succursales.model.DeleteAllBody
import ca.qc.castroguilherme.succursales.model.DeleteBody
import ca.qc.castroguilherme.succursales.model.DeleteSuccursaleResponse
import ca.qc.castroguilherme.succursales.model.EnregistrementBody
import ca.qc.castroguilherme.succursales.model.EnregistrementReponse
import ca.qc.castroguilherme.succursales.model.Login
import ca.qc.castroguilherme.succursales.model.LoginResponse
import ca.qc.castroguilherme.succursales.model.SucNbResp
import ca.qc.castroguilherme.succursales.model.SuccursaleResponse
import ca.qc.castroguilherme.succursales.model.VoirBudgetModel
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.HTTP
import retrofit2.http.POST
import retrofit2.http.Path


interface SuccursaleApiService {

    @POST("students/Connexion")
    suspend fun postConnexion(
        @Body loginRequest: Login
    ): Response<LoginResponse>

    @POST("succursales/Succursale-Liste")
    suspend fun postAuteurListe(
        @Body aut : Aut
    ): Response<SuccursaleResponse>

    @POST("students/Enregistrement")
    suspend fun postEnregistrement(
        @Body enregistrementBody: EnregistrementBody
    ): Response<EnregistrementReponse>

    @POST("/succursales/Succursale-Ajout")
    suspend fun postCreateSuccursale(
        @Body createSuccursale: CreateSuccursale
    ): Response<CreateSucReponse>

    @HTTP(method = "DELETE", path = "/succursales/Succursale-Retrait", hasBody = true)
    suspend fun deleteSuccursale(
       @Body deleteBody: DeleteBody
    ): Response<DeleteSuccursaleResponse>

    @HTTP(method = "DELETE", path = "/succursales/Succursale-Suppression", hasBody = true)
    suspend fun deleteAll(
        @Body aut: DeleteAllBody
    ): Response<DeleteSuccursaleResponse>

    @POST("/succursales/Succursale-Budget")
    suspend fun postVoirBudget(
        @Body voirBudgetModel: VoirBudgetModel
    ): Response<BudgetResponse>

    @POST("/succursales/Succursale-Compte")
    suspend fun nombreSuccursales(
        @Body aut: Aut
    ) : Response<SucNbResp>
}