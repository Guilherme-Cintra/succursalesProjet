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
import ca.qc.castroguilherme.succursales.databinding.FragmentEdit2Binding
import ca.qc.castroguilherme.succursales.model.Aut
import ca.qc.castroguilherme.succursales.model.CreateSuccursale
import ca.qc.castroguilherme.succursales.model.Succursale
import ca.qc.castroguilherme.succursales.room.FavViewModel
import ca.qc.castroguilherme.succursales.room.FavViewModelFactory
import com.google.android.material.snackbar.Snackbar

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [EditFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class EditFragment : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null

    private lateinit var binding: FragmentEdit2Binding
    private val args: EditFragmentArgs by navArgs()
    private lateinit var succursale: Succursale
    private lateinit var aut: Aut

    private val favViewModel: FavViewModel by lazy {
        ViewModelProvider(this, FavViewModelFactory(requireActivity().application)).get(
            FavViewModel::class.java
        )

    }
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
        binding = FragmentEdit2Binding.inflate(inflater, container, false)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        succursale = args.succursale
        aut = args.auteur

        val succursaleRepository = SuccursaleRepository()
        val viewModelProviderFactory = SuccursaleViewModelProviderFactory(succursaleRepository)
        var viewModel =
            ViewModelProvider(this, viewModelProviderFactory).get(SuccursaleViewModel::class.java)


        binding.nameSuccursaleTxt.setText(succursale.ville)
        binding.budgetSucText.setText(succursale.budget.toString())
        binding.imageHome.setOnClickListener {
            findNavController().navigateUp()
        }
        binding.revenirLbl.setOnClickListener {
            findNavController().navigateUp()
        }

        binding.button.setOnClickListener {
            var nom = binding.nameSuccursaleTxt.text.toString()
            var money = binding.budgetSucText.text.toString()

            val createSuccursale = CreateSuccursale(aut.Aut, nom, money)


            var updatedSuccursale = succursale
//            updatedSuccursale.ville = nom
            updatedSuccursale.budget = money.toInt()




            viewModel.postCreateSuccursale(createSuccursale)

            viewModel.createSucReponse.observe(viewLifecycleOwner, Observer { createSucResponse ->
                Log.i("CreateSuccFrag", "Response data: $createSucResponse")
                Log.i("CreateSuccFrag", "Response data: ${createSucResponse.statut}")
//                Log.i("CreateSuccFrag", "Response data: ${createSucResponse}")
                if (createSucResponse.statut == "OKM") {
                    Snackbar.make(view, getString(R.string.modifW), Snackbar.LENGTH_LONG).show()
                    favViewModel.update(updatedSuccursale)
                } else {
                    Snackbar.make(view, getString(R.string.sucPasAjoute), Snackbar.LENGTH_LONG)
                        .show()
                }


            })
        }
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment EditFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            EditFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}