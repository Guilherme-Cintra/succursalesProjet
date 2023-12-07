package ca.qc.castroguilherme.succursales.model


import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName
import java.io.Serializable

@Entity(tableName = "fav_table")
data class Succursale(
    @ColumnInfo(name = "id")
    @SerializedName("_id")
    val id: String,
    @ColumnInfo(name = "AccessMDP")
    @SerializedName("AccessMDP")
    val accessMDP: Int,
    @PrimaryKey(autoGenerate = false)

    @ColumnInfo(name = "Ville")
    @SerializedName("Ville")
    var ville: String,
    @ColumnInfo(name = "Budget")
    @SerializedName("Budget")
    var budget: Int,
    @ColumnInfo(name = "__v")
    @SerializedName("__v")
    val v: Int
):Serializable