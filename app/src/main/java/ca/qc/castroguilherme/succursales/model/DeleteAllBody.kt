package ca.qc.castroguilherme.succursales.model


import com.google.gson.annotations.SerializedName

data class DeleteAllBody(
    @SerializedName("Aut")
    val aut: Int
)