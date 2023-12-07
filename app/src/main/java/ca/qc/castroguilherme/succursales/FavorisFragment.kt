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
import ca.qc.castroguilherme.succursales.databinding.FragmentFavorisBinding
import ca.qc.castroguilherme.succursales.model.Aut
import ca.qc.castroguilherme.succursales.model.DeleteBody
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
 * Use the [FavorisFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class FavorisFragment : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null
    private lateinit var binding: FragmentFavorisBinding
    private val args:FavorisFragmentArgs by navArgs()
    private lateinit var auteur: Aut
    var succursalesDeCurrentUser = mutableListOf<Succursale>()

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
        binding = FragmentFavorisBinding.inflate(inflater, container, false)
        return binding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val succursaleRepository = SuccursaleRepository()
        val viewModelProviderFactory = SuccursaleViewModelProviderFactory(succursaleRepository)

        var viewModel =
            ViewModelProvider(this, viewModelProviderFactory).get(SuccursaleViewModel::class.java)

        auteur = args.auteur
        var succursaleAdapter = SuccursaleAdapter()


        binding.favRV.adapter = succursaleAdapter

        succursaleAdapter
//        succursaleAdapter.onEditClickListener.


        binding.revenirLbl.setOnClickListener {
            findNavController().navigateUp()
        }

        binding.imageHome.setOnClickListener {
            findNavController().navigateUp()
        }





        favViewModel.allSuccursales.observe(viewLifecycleOwner, Observer { succursales ->
            succursalesDeCurrentUser.clear()
            succursales?.forEach {
                Log.i("nb", " taille dans oberver avant : ${succursalesDeCurrentUser.size}")
                if (it.accessMDP == auteur.Aut.toInt()){
                    succursalesDeCurrentUser.add(it)
                    Log.i("nb", " taille dans oberver apres : ${succursalesDeCurrentUser.size}")
                }
            }
            succursaleAdapter.setSuccursales(succursalesDeCurrentUser)
            if (succursalesDeCurrentUser.size == 0) {
                binding.RienAfficher.visibility = View.VISIBLE
            } else {
                binding.RienAfficher.visibility = View.INVISIBLE
            }
        })





        succursaleAdapter.onDeleteClickListener = {

            var deleteBody = DeleteBody(auteur.Aut.toInt(), it.ville)
            viewModel.deleteSuccursale(deleteBody)

            viewModel.deleteSucReponse.observe(viewLifecycleOwner, Observer { deleteSucResponse ->
                Log.i("DeleteSucResp", "line 111 : ${deleteSucResponse.statut}")
                if (deleteSucResponse.statut != "PASOK") {
                    Snackbar.make(view, getString(R.string.sucEfface), Snackbar.LENGTH_LONG)
                        .show()


                    favViewModel.delete(it)


                    favViewModel.allSuccursales.observe(viewLifecycleOwner, Observer { succursales ->
                        succursalesDeCurrentUser.clear()
                        succursales?.forEach {
                            Log.i("nb", " taille dans oberver avant : ${succursalesDeCurrentUser.size}")
                            if (it.accessMDP == auteur.Aut.toInt()){
                                succursalesDeCurrentUser.add(it)
                                Log.i("nb", " taille dans oberver apres : ${succursalesDeCurrentUser.size}")
                            }
                        }
                        succursaleAdapter.setSuccursales(succursalesDeCurrentUser)
                    })

                }
            })

//                favViewModel.delete(it)
        }

        succursaleAdapter.onFavClickListener = {
            favViewModel.delete(it)
            Snackbar.make(view, getString(R.string.sucNoFav), Snackbar.LENGTH_LONG)
                .show()
        }

        succursaleAdapter.onEditClickListener = {
            val bundle = Bundle()
            bundle.putSerializable("auteur", auteur)
            bundle.putSerializable("succursale", it)
            findNavController().navigate(R.id.action_favorisFragment_to_editFragment, bundle)
        }

    }



    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment FavorisFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            FavorisFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}