package net.azarquiel.examen2evhugo.model

import java.io.Serializable

data class Categorias(var id: Int, var nombre: String):Serializable
data class Chiste(var id: Long, var idcategoria:Int, var contenido:String):Serializable
data class Punto(var id:Int, var idchiste:Long, var puntos: Int)
data class Usuario (var id: Int, var nick: String, var pass: String):Serializable


data class Respuesta (
    var categorias:List<Categorias>,
    var chistes:List<Chiste>,
    var usuario: Usuario,
    var punto: Punto,
    var chiste:Chiste,
    val avg: Int,
    val msg: String
)