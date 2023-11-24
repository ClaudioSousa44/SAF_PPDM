package br.senai.sp.jandira.saf.screens.telaCadastro.components

import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import br.senai.sp.jandira.saf.R
import coil.compose.rememberAsyncImagePainter
import coil.request.ImageRequest


@Composable
fun EditarFoto(fotoUri: MutableState<Uri?>) {


    val launcher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.GetContent()
    ){
        fotoUri.value = it
    }


    var painter = rememberAsyncImagePainter(
        ImageRequest.Builder(LocalContext.current).data(fotoUri.value).build()
    )
    Box(
        modifier = Modifier.size(150.dp)
    ){
        Icon(
            painter = painterResource(id = R.drawable.baseline_account_circle_24) ,
            contentDescription = "",
            tint = Color.White,
            modifier = Modifier.size(150.dp)
        )
        Image(
            painter = painter,
            contentDescription = "" ,
            modifier = Modifier
                .size(150.dp)
                .clip(shape = CircleShape)
                .clickable {
                    launcher.launch("image/*")
                },
            contentScale = ContentScale.Crop,
        )
    }

}