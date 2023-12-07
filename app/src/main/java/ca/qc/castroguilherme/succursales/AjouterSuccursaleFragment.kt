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
import androidx.navigation.fragment.navArgs
import ca.qc.castroguilherme.succursales.databinding.FragmentAjouterSuccursaleBinding
import ca.qc.castroguilherme.succursales.model.CreateSuccursale
import com.google.android.material.snackbar.Snackbar

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [AjouterSuccursaleFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class AjouterSuccursaleFragment : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null

    private lateinit var binding: FragmentAjouterSuccursaleBinding
    private val args: AjouterSuccursaleFragmentArgs by navArgs()

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
        binding = FragmentAjouterSuccursaleBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val aut = args.auteur //Récupère le matricule de l'étudiant par la navigation

        //Création du ViewModel pour la succursale
        val succursaleRepository = SuccursaleRepository()
        val viewModelProviderFactory = SuccursaleViewModelProviderFactory(succursaleRepository)

        var viewModel =
            ViewModelProvider(this, viewModelProviderFactory).get(SuccursaleViewModel::class.java)


        binding.buttonAjouterSuccursale.setOnClickListener {

            //Variables qui serviront pour le corps de la requête
            val ville = binding.editTextVille.text
            val budget = binding.editTextBudget.text
            val auteur = aut.Aut

            //Objet qui sera utilisé pour le corps de la requête
            val createSuccursale = CreateSuccursale(auteur, ville.toString(), budget.toString())



            viewModel.postCreateSuccursale(createSuccursale)// Méthode POST pour créer la succursale

            viewModel.createSucReponse.observe(viewLifecycleOwner, Observer { createSucResponse ->
                //On affiche un snackbar si la requête fonctionne
                if (createSucResponse.statut == "OKI") {
                    Snackbar.make(view, getString(R.string.sucAjoute), Snackbar.LENGTH_LONG).show()
                    binding.editTextVille.setText("")
                    binding.editTextBudget.setText("")
                } else {
                    Snackbar.make(view, getString(R.string.sucPasAjoute), Snackbar.LENGTH_LONG)
                        .show()
                }


            })

        }


        binding.imageHome.setOnClickListener {

            findNavController().navigateUp()
        }
        binding.revenirLbl.setOnClickListener {

            findNavController().navigateUp()
        }

    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment AjouterSuccursaleFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            AjouterSuccursaleFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}