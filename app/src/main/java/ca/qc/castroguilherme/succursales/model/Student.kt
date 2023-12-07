package ca.qc.castroguilherme.succursales.model

import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class Student(
    @SerializedName("Matricule")
    val matricule: Int,
    @SerializedName("Nom")
    val nom: String,
    @SerializedName("Prenom")
    val prenom: String,
    @SerializedName("token")
    val token: String
):Serializable
