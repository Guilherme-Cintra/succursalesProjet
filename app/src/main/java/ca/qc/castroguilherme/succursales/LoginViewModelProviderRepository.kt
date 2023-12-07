package ca.qc.castroguilherme.succursales

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

class LoginViewModelProviderRepository(private  val loginRepository: LoginRepository):ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return if (modelClass.isAssignableFrom(LoginViewModel::class.java)){
            LoginViewModel(loginRepository) as T
        } else {
            throw java.lang.IllegalArgumentException("Unknown viewModel class")
        }
    }
}