package ca.qc.castroguilherme.succursales.model


import com.google.gson.annotations.SerializedName

data class BudgetResponse(
    @SerializedName("statut")
    val statut: String,
    @SerializedName("succursale")
    val succursale: Succursale
)