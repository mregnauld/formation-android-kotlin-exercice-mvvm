package com.formationandroid.architecturemvvm.repositories

import android.content.Context
import androidx.lifecycle.MutableLiveData
import com.formationandroid.architecturemvvm.R
import com.formationandroid.architecturemvvm.helpers.ReseauHelper
import com.formationandroid.architecturemvvm.models.RetourWSPlanetes
import com.formationandroid.architecturemvvm.models.RetrofitSingleton
import com.formationandroid.architecturemvvm.models.WSInterface
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import kotlin.random.Random

class MainRepository
{

    // Retrofit :
    private val serviceRetrofit = RetrofitSingleton.retrofit.create(WSInterface::class.java)


    /**
     * Récupère les informations d'une planète au hasard.
     */
    fun getLiveDataPlanete(liveDataPlanete: MutableLiveData<String>, liveDataErreur: MutableLiveData<String>, context: Context)
    {
        // vérification de l'état de la connexion internet :
        if (!ReseauHelper.estConnecte(context))
        {
            liveDataErreur.value = context.getString(R.string.main_aucun_reseau)
            return
        }

        // appel au webservice :
        val call = serviceRetrofit.getPlanets()
        call.enqueue(object : Callback<RetourWSPlanetes>
        {
            override fun onResponse(call: Call<RetourWSPlanetes>, response: Response<RetourWSPlanetes>)
            {
                if (response.isSuccessful)
                {
                    val retourWSPlanetes = response.body()
                    retourWSPlanetes?.results?.let { liste ->

                        // récupération d'une planète au hasard :
                        val planete = liste[Random.nextInt(0, liste.size)]

                        // création du texte :
                        val stringBuilder = StringBuilder()
                        stringBuilder.append(context.getString(R.string.main_planete_nom, planete.name))
                        stringBuilder.append(context.getString(R.string.main_planete_rotation, planete.rotation_period))
                        stringBuilder.append(context.getString(R.string.main_planete_periode, planete.orbital_period))
                        stringBuilder.append(context.getString(R.string.main_planete_diametre, planete.diameter))
                        stringBuilder.append(context.getString(R.string.main_planete_climat, planete.climate))
                        stringBuilder.append(context.getString(R.string.main_planete_gravite, planete.gravity))
                        stringBuilder.append(context.getString(R.string.main_planete_terrain, planete.terrain))
                        stringBuilder.append(context.getString(R.string.main_planete_eau, planete.surface_water))
                        stringBuilder.append(context.getString(R.string.main_planete_population, planete.population))

                        // affichage final :
                        liveDataPlanete.value = stringBuilder.toString()
                    }
                }
            }

            override fun onFailure(call: Call<RetourWSPlanetes>, t: Throwable)
            {
                liveDataErreur.value = t.message
            }

        })
    }

}