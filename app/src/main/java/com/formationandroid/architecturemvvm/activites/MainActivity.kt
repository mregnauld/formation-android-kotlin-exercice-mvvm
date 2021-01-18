package com.formationandroid.architecturemvvm.activites

import android.app.Activity
import android.os.Bundle
import android.view.View
import android.widget.ProgressBar
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.formationandroid.architecturemvvm.R
import com.formationandroid.architecturemvvm.repositories.MainRepository
import com.formationandroid.architecturemvvm.viewmodels.MainViewModel

class MainActivity : AppCompatActivity()
{

	// Vues :
	private lateinit var textViewPlanete: TextView
	private lateinit var progressBarAttente: ProgressBar

	// ViewModel :
	private lateinit var mainViewModel: MainViewModel


	override fun onCreate(savedInstanceState: Bundle?)
	{
		// init :
		super.onCreate(savedInstanceState)
		setContentView(R.layout.activity_main)

		// vues :
		textViewPlanete = findViewById(R.id.texte_planete)
		progressBarAttente = findViewById(R.id.progressbar_attente)

		// view model et observer :
		mainViewModel = ViewModelProvider(this).get(MainViewModel::class.java)
		mainViewModel.init(MainRepository())
		mainViewModel.liveDataPlanete.observe(this, { t ->
			textViewPlanete.text = t
			progressBarAttente.visibility = View.GONE
		})
		mainViewModel.liveDataErreur.observe(this, { t ->
			textViewPlanete.text = t
			progressBarAttente.visibility = View.GONE
		})
	}

	/**
	 * Listener clic bouton planète.
	 */
	@Suppress("UNUSED_PARAMETER")
	fun onClickBoutonPlanete(view: View?)
	{
		progressBarAttente.visibility = View.VISIBLE
		textViewPlanete.text = ""
		mainViewModel.clicBoutoPlanete(this)
	}

}