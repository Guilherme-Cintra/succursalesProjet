package ca.qc.castroguilherme.succursales.room

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import ca.qc.castroguilherme.succursales.model.Succursale

@Database
    (entities = arrayOf(Succursale::class), version = 1, exportSchema = false)
abstract class FavRoomDatabase: RoomDatabase() {
    abstract fun succursaleDAO(): SuccursaleDAO

    companion object{
        //Annotation qui previent multiples instances de la BD ouvrant en même temps
        @Volatile
        private var INSTANCE: FavRoomDatabase? = null

        fun getDatabase(context: Context): FavRoomDatabase {
            val tempInstance = INSTANCE
            if (tempInstance != null){
                return tempInstance
            }
            INSTANCE = Room.databaseBuilder(
                context.applicationContext,
                FavRoomDatabase::class.java,
                "Fav_database"
            ).build()
            return INSTANCE as FavRoomDatabase
        }
    }
}