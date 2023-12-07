package ca.qc.castroguilherme.succursales

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

class SuccursaleViewModelProviderFactory(private  val succursaleRepository: SuccursaleRepository):ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return if (modelClass.isAssignableFrom(SuccursaleViewModel::class.java)){
            SuccursaleViewModel(succursaleRepository) as T
        } else {
            throw java.lang.IllegalArgumentException("Unknown viewModel class")
        }
    }
}