package ca.qc.castroguilherme.succursales.model


import com.google.gson.annotations.SerializedName

data class VoirBudgetModel(
    @SerializedName("Aut")
    val aut: Int,
    @SerializedName("Ville")
    val ville: String
)