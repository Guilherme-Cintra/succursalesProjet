package ca.qc.castroguilherme.succursales.room

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

class FavViewModelFactory(private val application: Application): ViewModelProvider.AndroidViewModelFactory(application){
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return if(modelClass.isAssignableFrom(FavViewModel::class.java)){
            FavViewModel(application) as T
        } else{
            throw IllegalArgumentException("Unkown ViewModel Class")
        }
    }
}