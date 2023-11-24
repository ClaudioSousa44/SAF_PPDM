package br.senai.sp.jandira.saf.service

import com.google.gson.JsonObject
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST

interface UsuarioService {

    @POST("usuario/cadastrarUsuario")
    suspend fun cadastrarUsuario(@Body body:JsonObject): Response<JsonObject>
}