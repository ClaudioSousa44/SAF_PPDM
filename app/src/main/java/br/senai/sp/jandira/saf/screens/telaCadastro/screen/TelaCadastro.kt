package br.senai.sp.jandira.saf.screens.telaCadastro.screen

import android.net.Uri
import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.LifecycleCoroutineScope
import br.senai.sp.jandira.saf.screens.telaCadastro.components.EditarFoto
import br.senai.sp.jandira.saf.screens.telaCadastro.components.Formulario
import br.senai.sp.jandira.saf.service.RetrofitHelper
import br.senai.sp.jandira.saf.service.UsuarioService
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference
import com.google.gson.JsonObject
import kotlinx.coroutines.launch


@Composable
fun TelaCadastro(lifecycleCoroutineScope: LifecycleCoroutineScope) {

    lateinit var storageRef: StorageReference
    lateinit var fibaseFirestore: FirebaseFirestore
    storageRef = FirebaseStorage.getInstance().reference.child("images")
    fibaseFirestore = FirebaseFirestore.getInstance()

    lateinit var usuarioService: UsuarioService
    usuarioService = RetrofitHelper.getInstance().create(UsuarioService::class.java)

    val context = LocalContext.current

    var fotoUri = remember {
        mutableStateOf<Uri?>(null)

    }

    var usuario =  remember {
        mutableStateOf("")
    }

    var senha =  remember {
        mutableStateOf("")
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(
                brush = Brush.horizontalGradient(
                    colors = listOf(
                        Color(0xFF82C5E4),
                        Color(0xFFA5F6C8)
                    )
                )
            )
            .padding(horizontal = 40.dp),
         horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.SpaceEvenly
    ) {
        
            Text(text = "Symbian", color = Color.White, fontSize = 50.sp)
        
            EditarFoto(fotoUri = fotoUri)
            Formulario(usuario, senha)



            Button(
                onClick = {
                    storageRef = storageRef.child(System.currentTimeMillis().toString())
                    fotoUri.let {
                        it.value?.let {
                            storageRef.putFile(it).addOnCompleteListener {task->

                                if (task.isSuccessful){
                                    storageRef.downloadUrl.addOnSuccessListener { uri ->

                                        val map = HashMap<String, Any>()
                                        map["pic"] = uri.toString()
                                        fibaseFirestore.collection("images").add(map)
                                        lifecycleCoroutineScope.launch {
                                            val body = JsonObject().apply {
                                                addProperty("login", usuario.value)
                                                addProperty("senha", senha.value)
                                                addProperty("imagem", uri.toString())
                                            }

                                            val result = usuarioService.cadastrarUsuario(body)

                                            if(result.isSuccessful){
                                                Toast.makeText(
                                                    context,
                                                    "Usuario cadastrado com sucesso",
                                                    Toast.LENGTH_SHORT
                                                ).show()
                                            }else{
                                                Log.e("erro", "TelaCadastro: ${result.body()}", )
                                            }
                                        }



                                    }
                                }
                            }
                        }

                    }
                          },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(50.dp),
                colors = ButtonDefaults.buttonColors(Color.White)
            ) {
                Text(text = "Cadastrar", color = Color(0xFF4AA0E2), fontSize = 20.sp, fontWeight = FontWeight.Bold)
            }

    }
}

