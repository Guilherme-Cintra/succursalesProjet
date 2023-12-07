package ca.qc.castroguilherme.succursales

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import ca.qc.castroguilherme.succursales.databinding.FragmentAccueilBinding
import ca.qc.castroguilherme.succursales.model.Aut
import ca.qc.castroguilherme.succursales.model.DeleteAllBody
import ca.qc.castroguilherme.succursales.model.DeleteBody
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
 * Use the [AccueilFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class AccueilFragment : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null

    private lateinit var binding: FragmentAccueilBinding

    private val args: AccueilFragmentArgs by navArgs()

    private lateinit var student: Student

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
        binding = FragmentAccueilBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        student = args.studentinho

        //Affocher le nom et le prénom de l'étudiant actuel
        binding.nomStudent.text = "${student.prenom} ${student.nom}"

        //Création du ViewModel
        val succursaleRepository = SuccursaleRepository()
        val viewModelProviderFactory = SuccursaleViewModelProviderFactory(succursaleRepository)
        var viewModel =
            ViewModelProvider(this, viewModelProviderFactory).get(SuccursaleViewModel::class.java)


        var succursaleAdapter = SuccursaleAdapter()
        binding.succursalesRV.adapter = succursaleAdapter

        //Corps de la fonction est le matricule de l'étudiant
        val aut = Aut(student.matricule.toString())


        viewModel.nombreSuccursale(aut) // Trouver le nombre de succursales de l'étudiant

        viewModel.nbResponse.observe(viewLifecycleOwner, Observer { resp ->
            if (resp.statut == "OK"){
                //si la réponse marche on affiche le nombre de succursales
                binding.nombreSucc.setText(resp.nbSuccursales.toString())
            }
        })

        //POST pour recevoir la liste de succursales
        viewModel.postAuteurListe(aut)

        viewModel.succursaleResponse.observe(viewLifecycleOwner, Observer { succursaleResponse ->
            if (succursaleResponse.succursales != null) {
                succursaleAdapter.setSuccursales(succursaleResponse.succursales)
                Log.i("SucVM", "item count : ${succursaleAdapter.itemCount}")
            }
        })



        //Afficher la liste de succursales en cliquant sur le bouton ce qui le rend visible
        binding.buttonLister.setOnClickListener {
            binding.succursalesRV.visibility = View.VISIBLE
        }

        //Aller au fragment d'ajout d'une nouvelle succursale en passant le matricule de l'étudiant dans un bundle
        binding.imageAjout.setOnClickListener {
            val bundle = Bundle().apply {
                putSerializable("auteur", aut)
            }
            findNavController().navigate(
                R.id.action_accueilFragment_to_ajouterSuccursaleFragment,
                bundle
            )
        }

        //Si on clique sur le favoris on l'ajoute à la base de données locale dans les favoris
        succursaleAdapter.onFavClickListener = {
            favViewModel.insert(it)
            Snackbar.make(view, getString(R.string.ajoutFav), Snackbar.LENGTH_LONG)
                .show()
        }


//        Delete succursale avec la methode DELETE qui prend un objet DelteBody comme corps
//        On l'éfface aussi da la base de données locale des favoris
        succursaleAdapter.onDeleteClickListener = {

            var ville = it.ville
            var aut = student.matricule
            var deleteBody = DeleteBody(aut, ville)

            viewModel.deleteSuccursale(deleteBody)




            viewModel.deleteSucReponse.observe(viewLifecycleOwner, Observer { deleteSucResponse ->
                if (deleteSucResponse.statut != "PASOK") { //si le statut est différent de PASOK on affiche le succès dans un Snackbar
                    Snackbar.make(view, getString(R.string.sucEfface), Snackbar.LENGTH_LONG)
                        .show()


                    favViewModel.delete(it) //Effacer dans la BD locale


                    //Code pour mettre à jour le nombre de succursales après effacement
                    var auteur = Aut(aut.toString())
                    viewModel.postAuteurListe(auteur)
                    viewModel.succursaleResponse.observe(
                        viewLifecycleOwner,
                        Observer { succursaleResponse ->
                            if (succursaleResponse.succursales != null) {
                                succursaleAdapter.setSuccursales(succursaleResponse.succursales)

                                val aut = Aut(student.matricule.toString())
                                viewModel.nombreSuccursale(aut)
                                viewModel.nbResponse.observe(viewLifecycleOwner, Observer { resp ->
                                    if (resp.statut == "OK"){
                                        binding.nombreSucc.setText(resp.nbSuccursales.toString())
                                    }
                                })



                            } else {
                                succursaleAdapter.setSuccursales(emptyList())
                            }
                        })
                }
            })


        }

        //Naviguer au fragment de modification en passant le matricule de l'étudiant et la succursale en question
        succursaleAdapter.onEditClickListener = {
            val bundle = Bundle()
            bundle.putSerializable("succursale", it)
            bundle.putSerializable("auteur", aut)
            findNavController().navigate(R.id.action_accueilFragment_to_editFragment, bundle)
        }

        //code pour mettre le texte affichant le nombre de succursale à jour. Se met à jour à chaque changement
        viewModel.succursaleResponse.observe(viewLifecycleOwner, Observer {
                succursaleResponse ->
            if (succursaleResponse.succursales == null) {
                binding.nombreSucc.text = "0"
            } else {
                val aut = Aut(student.matricule.toString())
                viewModel.nombreSuccursale(aut)
                viewModel.nbResponse.observe(viewLifecycleOwner, Observer { resp ->
                    if (resp.statut == "OK"){
                        binding.nombreSucc.setText(resp.nbSuccursales.toString())
                    }
                })
            }


        })


        //En cliquant sur le bouton supprimer toutes on appele la méthode de suppresion et on efface les données locales des favoris
        binding.buttonDeleteAll.setOnClickListener {
            var autentif = DeleteAllBody(student.matricule)//Corps de la requête
            viewModel.deleteAll(autentif)
            viewModel.deleteAllSucReponse.observe(viewLifecycleOwner, Observer {
                    deleteSucResponse ->

                if (deleteSucResponse.statut == "OK") {
                    //Afficher un snackbar si la requête fonctionne
                    Snackbar.make(view, getString(R.string.deleteAll), Snackbar.LENGTH_LONG)
                        .show()
                    succursaleAdapter.setSuccursales(emptyList())
                    binding.nombreSucc.text = "0"
                    favViewModel.deleteAll() //Efface la BD locale
                } else {
                    Snackbar.make(view, getString(R.string.error), Snackbar.LENGTH_LONG)
                        .show() //En cas d'erreur on l'affiche
                }
            })
        }

        //Voyager dans le fragment des favoris avec le matricule de l'étudiant
        binding.imageFavories.setOnClickListener {
            val bundle = Bundle()
            bundle.putSerializable("auteur", aut)
            findNavController().navigate(R.id.action_accueilFragment_to_favorisFragment, bundle)
        }

        //Voyager dans le fragment pour voir le budget d'une succursale
        binding.textView7.setOnClickListener {
            val bundle = Bundle()
            bundle.putSerializable("auteurr", aut)
            findNavController().navigate(R.id.action_accueilFragment_to_budgetFragment, bundle)
        }
        binding.imgBudget.setOnClickListener {
            val bundle = Bundle()
            bundle.putSerializable("auteurr", aut)
            findNavController().navigate(R.id.action_accueilFragment_to_budgetFragment, bundle)
        }

        //Se déconnecter (voyage à la vue de login)
        binding.imageDeconcter.setOnClickListener {
            Log.i("currentFrag", "${findNavController().currentDestination}")
            findNavController().navigate(R.id.action_accueilFragment_to_loginFragment)
        }
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment AccueilFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            AccueilFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}