package ca.qc.castroguilherme.succursales.model

import com.google.gson.annotations.SerializedName



data class LoginResponse(
    @SerializedName("statut")
    val statut: String,
    @SerializedName("student")
    val student: Student?,
    @SerializedName("error")
    val error: String?
)