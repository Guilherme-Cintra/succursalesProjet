package ca.qc.castroguilherme.succursales.model


import com.google.gson.annotations.SerializedName

data class SucNbResp(
    @SerializedName("nbSuccursales")
    val nbSuccursales: String,
    @SerializedName("statut")
    val statut: String
)