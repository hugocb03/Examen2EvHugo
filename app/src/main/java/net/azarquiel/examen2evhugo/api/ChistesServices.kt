package net.azarquiel.examen2evhugo.api

import kotlinx.coroutines.Deferred
import net.azarquiel.examen2evhugo.model.Chiste
import net.azarquiel.examen2evhugo.model.Punto
import net.azarquiel.examen2evhugo.model.Respuesta
import net.azarquiel.examen2evhugo.model.Usuario
import retrofit2.Response
import retrofit2.http.*

interface ChistesServices {
    @GET("categorias")
    fun getCategorias(): Deferred<Response<Respuesta>>
    @GET("categoria/{idcategoria}/chistes")

    fun getChistes(@Path("idcategoria") idcategoria:Int): Deferred<Response<Respuesta>>

    @GET("chiste/{idchiste}/avgpuntos")
    fun getAvgChiste(@Path("idchiste") idchiste: Long): Deferred<Response<Respuesta>>

    @GET("img/{idcategoria}.png")
    fun getIMG(): Deferred<Response<Respuesta>>
    // nick y pass como variables en la url?nick=paco&pass=paco
    @GET("usuario")
    fun getUserByNickAndPass(
        @Query("nick") nick: String,
        @Query("pass") pass: String
    ): Deferred<Response<Respuesta>>

    // post con objeto en json
    @POST("usuario")
    fun saveUsuario(@Body usuario: Usuario): Deferred<Response<Respuesta>>
    // post con objeto en json
    @POST("chiste")
    fun savechiste(@Body chiste: Chiste): Deferred<Response<Respuesta>>

    @POST("chiste/{idchiste}/punto")
    fun savePuntoChiste(
        @Path("idchiste") idchiste: Long,
        @Body punto: Punto
    ): Deferred<Response<Respuesta>>
}
