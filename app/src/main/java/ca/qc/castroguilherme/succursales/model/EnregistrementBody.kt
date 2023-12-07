package ca.qc.castroguilherme.succursales.model


import com.google.gson.annotations.SerializedName

data class EnregistrementBody(
    @SerializedName("Mat")
    val mat: Int,
    @SerializedName("MDP")
    val mDP: String,
    @SerializedName("Nom")
    val nom: String,
    @SerializedName("Prenom")
    val prenom: String
)