package ca.qc.castroguilherme.succursales.room

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import androidx.room.Query
import ca.qc.castroguilherme.succursales.model.Succursale
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class FavViewModel(application: Application) : AndroidViewModel(application) {

    private val repository: FavRepository

    val allSuccursales: LiveData<List<Succursale>>

    init {
        val succursaleDAO = FavRoomDatabase.getDatabase(application).succursaleDAO()
        repository = FavRepository(succursaleDAO)
        allSuccursales = repository.allSuccursales
    }

    fun insert(succursale: Succursale) = viewModelScope.launch(Dispatchers.IO) { repository.insert(succursale)
    }

    fun deleteAll() = viewModelScope.launch(Dispatchers.IO) { repository.deleteAll() }
    fun delete(succursale: Succursale) = viewModelScope.launch ( Dispatchers.IO){ repository.delete(succursale)}
    fun update(succursale: Succursale) = viewModelScope.launch(Dispatchers.IO) { repository.update(succursale) }
}