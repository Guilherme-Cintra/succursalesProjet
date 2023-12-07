package ca.qc.castroguilherme.succursales.room

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Entity
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import ca.qc.castroguilherme.succursales.model.Succursale
import retrofit2.http.DELETE

@Dao
interface SuccursaleDAO {
    //Prendre tous les étudiants
    @Query("Select * from fav_table ORDER BY ville ASC")
    fun getSuccursales() : LiveData<List<Succursale>>

    //Insert
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insert(succursale: Succursale)

    @Query("DELETE FROM fav_table")
    fun deleteAll()

    @Delete
    fun deleteSuccursale(succursale: Succursale)

    @Update
    fun updateSuccursale(succursale: Succursale)


}