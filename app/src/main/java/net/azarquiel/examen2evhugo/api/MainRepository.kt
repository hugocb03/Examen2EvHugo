package net.azarquiel.examen2evhugo.api


import net.azarquiel.examen2evhugo.model.*

class MainRepository() {
    val service = WebAccess.chistesServices

    suspend fun getCategorias(): List<Categorias> {
        val webResponse = service.getCategorias().await()
        if (webResponse.isSuccessful) {
            return webResponse.body()!!.categorias
        }
        return emptyList()
    }
    suspend fun getChistes(idcategoria:Int): List<Chiste> {
        val webResponse = service.getChistes(idcategoria).await()
        if (webResponse.isSuccessful) {
            return webResponse.body()!!.chistes
        }
        return emptyList()
    }

    suspend fun saveUsuario(usuario: Usuario): Usuario? {
        val webResponse = service.saveUsuario(usuario).await()
        if (webResponse.isSuccessful) {
            return webResponse.body()!!.usuario
        }
        return null
    }

    suspend fun getUserByNickAndPass(nick:String, pass:String): Usuario? {
        val webResponse = service.getUserByNickAndPass(nick, pass).await()
        if (webResponse.isSuccessful) {
            return webResponse.body()!!.usuario
        }
        return null
    }

    suspend fun getAvgChiste(idchiste:Long): Int {
        val webResponse = service.getAvgChiste(idchiste).await()
        if (webResponse.isSuccessful) {
            return webResponse.body()!!.avg
        }
        return 0
    }

    suspend fun savePuntoChiste(idchiste: Long, punto: Punto): Punto? {
        var puntoresponse: Punto? = null
        val webResponse = service.savePuntoChiste(idchiste,punto).await()
        if (webResponse.isSuccessful) {
            puntoresponse = webResponse.body()!!.punto
        }
        return puntoresponse
    }
    suspend fun savechiste(chiste: Chiste): Chiste {
        val webResponse = service.savechiste(chiste).await()
        if (webResponse.isSuccessful) {
            return webResponse.body()!!.chiste
        }
        return chiste
    }

}
