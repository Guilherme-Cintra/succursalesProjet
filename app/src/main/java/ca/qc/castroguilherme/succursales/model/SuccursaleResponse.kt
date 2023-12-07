package ca.qc.castroguilherme.succursales.model


import com.google.gson.annotations.SerializedName

data class SuccursaleResponse(
    @SerializedName("statut")
    val statut: String,
    @SerializedName("succursales")
    val succursales: List<Succursale>
)