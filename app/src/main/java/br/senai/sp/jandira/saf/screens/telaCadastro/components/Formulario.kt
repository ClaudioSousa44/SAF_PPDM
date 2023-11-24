package br.senai.sp.jandira.saf.screens.telaCadastro.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun Formulario(usuario: MutableState<String>, senha: MutableState<String>) {
    var passwordVisibilityState by remember {
        mutableStateOf(false)
    }

    Column (
        modifier = Modifier.fillMaxWidth()
    ){
        TextField(
            value = usuario.value ,
            onValueChange = {
                usuario.value = it
            },
            placeholder = {
                Text(text = "Usuário", fontSize = 15.sp, color = Color(0xFF4AA0E2), fontWeight = FontWeight.Bold)
            },
            colors = TextFieldDefaults.outlinedTextFieldColors(
                textColor = Color(0xFF4AA0E2),
                containerColor = Color.Transparent,
                unfocusedBorderColor = Color.White,
                focusedBorderColor = Color(0xFF4AA0E2)

            ),
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(20.dp))

        TextField(
            value = senha.value ,
            onValueChange = {
                senha.value = it
            },
            placeholder = {
                Text(text = "Senha", fontSize = 15.sp, color = Color(0xFF4AA0E2), fontWeight = FontWeight.Bold)
            },
            colors = TextFieldDefaults.outlinedTextFieldColors(
                textColor = Color(0xFF4AA0E2),
                containerColor = Color.Transparent,
                unfocusedBorderColor = Color.White,
                focusedBorderColor = Color(0xFF4AA0E2)

            ),
            trailingIcon = {
                IconButton(
                    onClick = {
                        passwordVisibilityState = !passwordVisibilityState
                    }
                ) {
                    Icon(
                        imageVector = if (passwordVisibilityState)

                            Icons.Default.VisibilityOff
                        else
                            Icons.Default.Visibility,
                        contentDescription = "",
                        tint = Color.White

                    )
                }
            },
            visualTransformation = if(!passwordVisibilityState)
                PasswordVisualTransformation()
            else
                VisualTransformation.None,
            modifier = Modifier.fillMaxWidth()
        )
    }


}