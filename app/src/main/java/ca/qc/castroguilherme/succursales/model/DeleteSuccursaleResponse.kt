package ca.qc.castroguilherme.succursales.model


import com.google.gson.annotations.SerializedName

data class DeleteSuccursaleResponse(
    @SerializedName("statut")
    val statut: String
)