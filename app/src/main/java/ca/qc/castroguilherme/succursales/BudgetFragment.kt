package ca.qc.castroguilherme.succursales

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import ca.qc.castroguilherme.succursales.databinding.FragmentBudgetBinding
import ca.qc.castroguilherme.succursales.model.Aut
import ca.qc.castroguilherme.succursales.model.VoirBudgetModel
import com.google.android.material.snackbar.Snackbar

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [BudgetFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class BudgetFragment : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null

    private lateinit var binding: FragmentBudgetBinding

    private val args: BudgetFragmentArgs by navArgs()
    private lateinit var aut : Aut

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
        binding = FragmentBudgetBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        aut = args.auteurr
        val succursaleRepository = SuccursaleRepository()
        val viewModelProviderFactory = SuccursaleViewModelProviderFactory(succursaleRepository)

        var viewModel =
            ViewModelProvider(this, viewModelProviderFactory).get(SuccursaleViewModel::class.java)


        binding.budgetVille.visibility = View.GONE

        binding.chercherBtn.setOnClickListener {

            var ville = binding.nomVille.text.toString()

            val voirBudgetModel = VoirBudgetModel(aut.Aut.toInt(), ville)

            viewModel.postVoirBudget(voirBudgetModel)


            viewModel.budgetResponse.observe(viewLifecycleOwner, Observer { response ->
                if (response.statut == "OK") {
                    binding.budgetVille.visibility = View.VISIBLE
                    binding.budgetVille.setText("${response.succursale.budget.toString()} $")
                } else{
                    Snackbar.make(view, getString(R.string.errorTrouve), Snackbar.LENGTH_LONG)
                        .show()
                    binding.budgetVille.visibility = View.GONE
                }
            })
        }

        binding.imageView6.setOnClickListener {
            findNavController().navigateUp()
        }
        binding.textView11.setOnClickListener {
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
         * @return A new instance of fragment BudgetFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            BudgetFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}