package com.feifood.feifood.presentation.login

import android.app.AlertDialog
import android.app.Dialog
import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.material3.rememberSwipeToDismissBoxState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.feifood.feifood.Constants
import com.feifood.feifood.CurrentSession
import com.feifood.feifood.model.User
import com.feifood.feifood.presentation.createLabeledTextField
import com.feifood.feifood.presentation.createMainButton
import com.feifood.feifood.presentation.createTitle
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import java.time.format.TextStyle
import java.util.Collections

@Composable
fun MyErrorDialog(show:Boolean, onDismiss: () -> Unit) {
    if (show) {
        AlertDialog(onDismissRequest = {onDismiss()},
            confirmButton = {},
            title = {Text(text = "Usuario incorrecto")},
            text = {Text(text = "La contraseña o el usuario son incorrectos")},
            dismissButton = {TextButton(onClick = {onDismiss()}) {Text(text = "Aceptar")} },
            )
    }

}

@Composable
fun LoginScreen( navigateToHome: () -> Unit) {
    var email:String by remember { mutableStateOf("")}
    var password:String by remember { mutableStateOf("") }
    var showDialog by remember { mutableStateOf(false) }

    Column(
        modifier = Modifier
            .fillMaxSize().background(
                brush = Brush.verticalGradient(
                    colors = listOf(
                        Color(Constants.DARK_RED).copy(alpha = 0.8f), // Primer color con transparencia
                        Color(Constants.LIGHT_RED).copy(alpha = 0.8f)  // Segundo color con transparencia
                    )
                )
            ) .padding(16.dp)

    ) {
        Spacer(modifier = Modifier.weight(0.5f))

        Surface(
            modifier = Modifier
                .width(320.dp) // Define el ancho deseado
                .height(500.dp) // Define la altura deseada
                .clip(RoundedCornerShape(30.dp))
                .align(Alignment.CenterHorizontally), // Centra el rectángulo en la pantalla
            color = Color.White, // Color de fondo del rectángulo
            shadowElevation = 8.dp
        ) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(30.dp),
                verticalArrangement = Arrangement.Top,
            ) {

                createTitle("Iniciar sesión", Color.Black)
                createLabeledTextField(
                    label = "Correo electrónico",
                    value = email,
                    onValueChange = {email = it}
                )
                createLabeledTextField(
                    label = "Contraseña",
                    value = password,
                    onValueChange = {password = it}
                )

                var show by rememberSaveable { mutableStateOf(false) }
                Spacer(modifier = Modifier.weight(0.5f))

                createMainButton("Iniciar sesión") {
                    val auth = FirebaseAuth.getInstance()
                    auth.signInWithEmailAndPassword(email, password).addOnCompleteListener { task ->
                        if (task.isSuccessful) {
                            // Se intenta obtener el usuario después de iniciar sesión
                            fetchUserFromDatabase(email) { user ->
                                // Este bloque se ejecuta después de intentar obtener el usuario de la base de datos
                                if (user != null) {
                                    CurrentSession.currentUser = user // Guarda el usuario en el singleton
                                     // Navega a la pantalla de inicio
                                    navigateToHome()
                                    println(user.name)

                                } else {
                                    show = true
                                }
                            }
                        } else {
                            show = true // Muestra un error de inicio de sesión
                        }
                    }
                }

                MyErrorDialog(show, {show = false})
            }
            }
        Spacer(modifier = Modifier.weight(0.5f))

    }

}

fun fetchUserFromDatabase(email: String, onComplete: (User?) -> Unit) {
    val db = Firebase.firestore
    db.collection(Constants.COLLECTION_USER) // Asegúrate de que el nombre de la colección sea correcto
        .whereEqualTo("email", email)
        .get()
        .addOnSuccessListener { result ->
            if (!result.isEmpty) {
                val user = result.documents[0].toObject(User::class.java)
                onComplete(user) // Devuelve el usuario encontrado
            } else {
                onComplete(null) // No se encontró el usuario
            }
        }
        .addOnFailureListener { exception ->
            // Maneja el error, puedes logear o mostrar un mensaje
            onComplete(null) // Retorna null en caso de error
        }
}
