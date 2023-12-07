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
import ca.qc.castroguilherme.succursales.databinding.FragmentEnregistreNouvelEtudiantBinding
import ca.qc.castroguilherme.succursales.model.EnregistrementBody
import com.google.android.material.snackbar.Snackbar

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [EnregistreNouvelEtudiantFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class EnregistreNouvelEtudiantFragment : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null
    private lateinit var binding: FragmentEnregistreNouvelEtudiantBinding
//    private val loginViewModel: LoginViewModel by navGraphViewModels(R.id.succursales_nav_graph)

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
        binding = FragmentEnregistreNouvelEtudiantBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val loginRepository = LoginRepository()
        val viewModelProviderFactory = LoginViewModelProviderRepository(loginRepository)
        var loginViewModel =
            ViewModelProvider(this, viewModelProviderFactory).get(LoginViewModel::class.java)


        binding.enregistrerBtn.setOnClickListener {
            if (!binding.userNom.text.isEmpty() && !binding.matriculeEdit.text.isEmpty() &&
                !binding.motDePAsseEdit.text.isEmpty() && !binding.userPrenomEdt.text.isEmpty()
            ) {
                try {
                    var nom = binding.userNom.text.toString()
                    var mat = binding.matriculeEdit.text.toString()
                    var mdp = binding.motDePAsseEdit.text.toString()
                    var prenom = binding.userPrenomEdt.text.toString()
                    var matricule = mat.toInt()
                    var enregistrementBody = EnregistrementBody(matricule, mdp, nom, prenom)

                    loginViewModel.postEnregistrement(enregistrementBody)
                    loginViewModel.enregistrementReponse.observe(
                        viewLifecycleOwner,
                        Observer { response ->
                            Log.i("EnregistrerResponse", "Response data: $response")
                            if (response.student != null) {
                                var student = response.student
                                binding.userNom.setText("")
                                binding.matriculeEdit.setText("")
                                binding.motDePAsseEdit.setText("")
                                binding.userPrenomEdt.setText("")
                                Snackbar.make(
                                    view,
                                    getString(R.string.enregistrementReussi),
                                    Snackbar.LENGTH_LONG
                                ).show()


                            } else if (response.error == "Matricule existant !") {
                                Snackbar.make(
                                    view,
                                    getString(R.string.existeDeja),
                                    Snackbar.LENGTH_LONG
                                ).show()
                            }
                        })


                } catch (e: Exception) {
                    Log.i("EnregistrerResponse", "Error line 77 : ${e.message.toString()}")
                    Snackbar.make(view, getString(R.string.pbmEnregistrement), Snackbar.LENGTH_LONG)
                        .show()
//                Snackbar.make(view,e.message.toString(), Snackbar.LENGTH_LONG).show()
                }
            } else {
                Snackbar.make(view, getString(R.string.erreurChamps), Snackbar.LENGTH_LONG)
                    .show()
            }
        }
        binding.allerLogin.setOnClickListener {
            findNavController().navigate(R.id.action_enregistreNouvelEtudiantFragment_to_loginFragment)
        }
    }
    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment EnregistreNouvelEtudiantFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            EnregistreNouvelEtudiantFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}