package ca.qc.castroguilherme.succursales.model

import com.google.gson.annotations.SerializedName

data class CreateSuccursale(
    @SerializedName("Aut")
    val Aut: String,
    @SerializedName("Ville")
    val ville: String,
    @SerializedName("Budget")
    val budget: String
)
