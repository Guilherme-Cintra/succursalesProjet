package ca.qc.castroguilherme.succursales

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.NavHostFragment
import ca.qc.castroguilherme.succursales.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var loginViewModel: LoginViewModel

    private val navController by lazy {
        val navHostFragment = supportFragmentManager.findFragmentById(R.id.fragmentContainerView) as NavHostFragment
        navHostFragment.navController
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)



        val loginRepository = LoginRepository()
        val viewModelProviderFactory = LoginViewModelProviderRepository(loginRepository)

        try {
            val viewModelProvider = ViewModelProvider(navController.getViewModelStoreOwner(R.id.succursales_nav_graph),
                viewModelProviderFactory
            )
            loginViewModel = viewModelProvider.get(LoginViewModel::class.java)

        } catch (e: IllegalArgumentException){
            e.printStackTrace()
        }

    }
}