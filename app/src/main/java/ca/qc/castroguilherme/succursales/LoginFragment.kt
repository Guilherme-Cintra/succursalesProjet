package ca.qc.castroguilherme.succursales

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.navigation.navGraphViewModels
import ca.qc.castroguilherme.succursales.databinding.FragmentLoginBinding
import ca.qc.castroguilherme.succursales.model.Login
import ca.qc.castroguilherme.succursales.model.Student
import ca.qc.castroguilherme.succursales.room.FavViewModel
import ca.qc.castroguilherme.succursales.room.FavViewModelFactory
import com.google.android.material.snackbar.Snackbar

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [LoginFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class LoginFragment : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null
    private  lateinit var binding: FragmentLoginBinding
    private lateinit var matricule :String
    private var mat = 0
    private lateinit var motdepasse :String
//    private val loginViewModel: LoginViewModel by navGraphViewModels(R.id.succursales_nav_graph)



    private lateinit var studentinho: Student

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentLoginBinding.inflate(inflater, container, false)
        return binding.root
    }



    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

//        val loginRepository = LoginRepository()
//        val viewModelProviderFactory = LoginViewModelProviderRepository(loginRepository)
//
//        val viewModelProvider = ViewModelProvider(this, viewModelProviderFactory)
//
//        loginViewModel = viewModelProvider.get(LoginViewModel::class.java)


        val loginRepository = LoginRepository()
        val viewModelProviderFactory = LoginViewModelProviderRepository(loginRepository)
        var loginViewModel =
            ViewModelProvider(this, viewModelProviderFactory).get(LoginViewModel::class.java)


        binding.buttonLogin.setOnClickListener {
            try {
                matricule = binding.userNameEditText.text.toString().trim()
                Log.i("Studentinho", "matriculeTxt : ${matricule}")
                motdepasse = binding.editTextText2.text.toString().trim()
                Log.i("Studentinho", "mot de passe : ${motdepasse}")
                mat = matricule.toInt()
                Log.i("Loger", "Matricule - ${matricule}, MDP - ${motdepasse}")
                val loginRequest = Login(mat,motdepasse)

                loginViewModel.postConnexion(loginRequest)


                loginViewModel.loginResponse.observe(viewLifecycleOwner, Observer { response ->
                    Log.i("Loger", "Response data: $response")
                    if (response.statut == "OK"){
                        studentinho = response.student!!
                        Log.i("AffectStudent", "Après reponse :  ${studentinho}")
                        Log.i("Studentinho", "student dans la réponse : ${response.student}")
                        val bundle = Bundle().apply {
                            putSerializable("studentinho", studentinho)
                            Log.i("Studentinho", "nom dans la réponse : ${studentinho.prenom}")
                        }
                        if(findNavController().currentDestination?.id == R.id.loginFragment) {
                            findNavController().navigate(
                                R.id.action_loginFragment_to_accueilFragment,
                                bundle
                            )
                        } else {
                            findNavController().navigate(R.id.loginFragment)
                            findNavController().navigate(
                                R.id.action_loginFragment_to_accueilFragment, bundle)
                        }
                    }
                })
//                if (studentinho != null) {
//                    val bundle = Bundle().apply {
//                        putSerializable("studentinho", studentinho)
//                        Log.i("Studentinho", "nom dans la réponse : ${studentinho.prenom}")
//                    }
//                    findNavController().navigate(R.id.action_loginFragment_to_accueilFragment, bundle)
//                }
            } catch (e: Exception){
                Log.i("ErroLogin", "${e.message}")
                Snackbar.make(view,getString(R.string.errorMessageLogin), Snackbar.LENGTH_LONG).show()
            }


        }

        binding.enregistrementLink.setOnClickListener {
            findNavController().navigate(R.id.enregistreNouvelEtudiantFragment)
        }
//        if (studentinho != null) {
//            Log.i("AffectStudent", "Avant reponse :  ${studentinho}")
//        }
    }

    override fun onViewStateRestored(savedInstanceState: Bundle?) {
        super.onViewStateRestored(savedInstanceState)



    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment LoginFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            LoginFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }

    }
}