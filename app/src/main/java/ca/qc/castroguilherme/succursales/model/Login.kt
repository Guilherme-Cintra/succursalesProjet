package ca.qc.castroguilherme.succursales.model

import com.google.gson.annotations.SerializedName

data class Login(
    @SerializedName("Mat")
    val mat: Int,
    @SerializedName("MDP")
    val mDP: String
)
