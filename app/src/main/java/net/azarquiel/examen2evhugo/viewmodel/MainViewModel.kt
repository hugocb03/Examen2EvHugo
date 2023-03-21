package net.azarquiel.examen2evhugo.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.Dispatchers.Main
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import net.azarquiel.examen2evhugo.api.MainRepository
import net.azarquiel.examen2evhugo.model.Categorias
import net.azarquiel.examen2evhugo.model.Chiste

import net.azarquiel.examen2evhugo.model.Punto
import net.azarquiel.examen2evhugo.model.Usuario

// ……

/**
 * Created by pacopulido on 23/02/2021.
 */
class MainViewModel : ViewModel() {


    private var repository: MainRepository = MainRepository()

    fun getCategorias(): MutableLiveData<List<Categorias>> {
        val categorias = MutableLiveData<List<Categorias>>()
        GlobalScope.launch(Main) {
            categorias.value = repository.getCategorias()
        }
        return categorias
    }
    fun getChistes(idcategoria:Int): MutableLiveData<List<Chiste>> {
        val chiste = MutableLiveData<List<Chiste>>()
        GlobalScope.launch(Main) {
            chiste.value = repository.getChistes(idcategoria)
        }
        return chiste
    }

    fun getAvgChiste(idchiste:Long): MutableLiveData<Int> {
        val avg = MutableLiveData<Int>()
        GlobalScope.launch(Main) {
            avg.value = repository.getAvgChiste(idchiste)
        }
        return avg
    }

    fun getUserByNickAndPass(nick:String, pass:String):MutableLiveData<Usuario> {
        val usuarioresponse= MutableLiveData<Usuario>()
        GlobalScope.launch(Main) {
            usuarioresponse.value = repository.getUserByNickAndPass(nick, pass)
        }
        return usuarioresponse
    }

    fun saveUsuario(usuario: Usuario):MutableLiveData<Usuario> {
        val usuarioresponse= MutableLiveData<Usuario>()
        GlobalScope.launch(Main) {
            usuarioresponse.value = repository.saveUsuario(usuario)
        }
        return usuarioresponse
    }

    fun savePuntoChiste(idchiste: Long, punto: Punto): MutableLiveData<Punto> {
        val responsepunto = MutableLiveData<Punto>()
        GlobalScope.launch(Main) {
            responsepunto.value = repository.savePuntoChiste(idchiste, punto)
        }
        return responsepunto
    }

    fun saveChiste(chiste:Chiste):MutableLiveData<Chiste> {
        val comentarioResponse= MutableLiveData<Chiste>()
        GlobalScope.launch(Main) {
            comentarioResponse.value = repository.savechiste(chiste)
        }
        return comentarioResponse
    }


}
