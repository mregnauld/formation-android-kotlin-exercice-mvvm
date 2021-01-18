package com.formationandroid.architecturemvvm.viewmodels

import android.content.Context
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.formationandroid.architecturemvvm.repositories.MainRepository

class MainViewModel : ViewModel()
{

    // Repository :
    private lateinit var mainRepository: MainRepository

    // LiveDatas :
    val liveDataPlanete: MutableLiveData<String> = MutableLiveData()
    val liveDataErreur: MutableLiveData<String> = MutableLiveData()


    /**
     * Initialisation.
     */
    fun init(mainRepository: MainRepository)
    {
        // vérification que l'initialisation n'a pas déjà été faite :
        if (this::mainRepository.isInitialized) return

        // initialisation et premier chargement :
        this.mainRepository = mainRepository
    }

    /**
     * Listener clic bouton chargement.
     */
    fun clicBoutoPlanete(context: Context)
    {
        mainRepository.getLiveDataPlanete(liveDataPlanete, liveDataErreur, context)
    }

}