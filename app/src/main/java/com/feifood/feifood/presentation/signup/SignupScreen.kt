package com.feifood.feifood.presentation.signup

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
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.feifood.feifood.Constants
import com.feifood.feifood.model.User
import com.feifood.feifood.presentation.createLabeledTextField
import com.feifood.feifood.presentation.createMainButton
import com.feifood.feifood.presentation.createTitle
import com.feifood.feifood.presentation.login.MyErrorDialog
import com.google.firebase.auth.oAuthProvider
import com.google.firebase.auth.FirebaseAuth
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase


@Composable
fun MyErrorDialog(show:Boolean, onDismiss: () -> Unit) {
    if (show) {
        AlertDialog(onDismissRequest = {onDismiss()},
            confirmButton = {},
            title = {Text(text = "Usuario incorrecto")},
            text = {Text(text = "La contraseña o el usuario son incorrectos")},
            dismissButton = { TextButton(onClick = {onDismiss()}) {Text(text = "Aceptar")} },
        )
    }

}

@Composable
fun MySuccessDialog(show:Boolean, onDismiss: () -> Unit) {
    if (show) {
        AlertDialog(onDismissRequest = {onDismiss()},
            confirmButton = {},
            title = {Text(text = "Usuario registrado")},
            text = {Text(text = "El usuario ha sido registrado")},
            dismissButton = { TextButton(onClick = {onDismiss()}) {Text(text = "Aceptar")} },
        )
    }

}

@Composable
fun SignupScreen() {
    var email: String by remember { mutableStateOf("") }
    var password: String by remember { mutableStateOf("") }
    var name: String by remember { mutableStateOf("") }
    var paternalSurname: String by remember { mutableStateOf("") }
    var maternalSurname: String by remember { mutableStateOf("") }
    var phoneNumber: String by remember { mutableStateOf("") }
    var showDialog by remember { mutableStateOf(false) }

    Column(
        modifier = Modifier
            .fillMaxSize().background(
                brush = Brush.verticalGradient(
                    colors = listOf(
                        Color(0xFF740938).copy(alpha = 0.8f), // Primer color con transparencia
                        Color(0xFFD32F2F).copy(alpha = 0.8f)  // Segundo color con transparencia
                    )
                )
            ).padding(16.dp)

    ) {
        Spacer(modifier = Modifier.weight(0.5f))

        Surface(
            modifier = Modifier
                .width(320.dp) // Define el ancho deseado
                .height(1000.dp) // Define la altura deseada
                .clip(RoundedCornerShape(30.dp))
                .align(Alignment.CenterHorizontally), // Centra el rectángulo en la pantalla
            color = Color.White, // Color de fondo del rectángulo
            shadowElevation = 8.dp
        ) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(30.dp),
                verticalArrangement = Arrangement.Top
            ) {
                createTitle("Registro", Color.Black)

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
                createLabeledTextField(
                    label = "Nombre",
                    value = name,
                    onValueChange = {name = it}
                )
                createLabeledTextField(
                    label = "Apellido paterno",
                    value = paternalSurname,
                    onValueChange = {paternalSurname = it}
                )
                createLabeledTextField(
                    label = "Apellido materno",
                    value = maternalSurname,
                    onValueChange = {maternalSurname = it}
                )
                var showError by rememberSaveable { mutableStateOf(false) }
                var showSuccess by rememberSaveable { mutableStateOf(false) }
                var result:Boolean = false
                createMainButton("Registrar") {

                    var user: User = User (
                        "",
                        name,

                         paternalSurname,
                         maternalSurname,
                         "",
                        email,
                         phoneNumber,
                         password,
                        )

                        saveUser(user) { success, userId ->
                            result = success
                        }
                    }
            }
        }
    }
}

fun saveUser(user: User, onComplete: (Boolean, String?) -> Unit) {
    val db = Firebase.firestore
    val auth = FirebaseAuth.getInstance()
    auth.createUserWithEmailAndPassword(user.email, user.password).addOnCompleteListener { task ->
        if (task.isSuccessful) {
            // Obtener el usuario autenticado
            val firebaseUser = auth.currentUser
            val userId = firebaseUser?.uid // Obtener el ID del usuario

            // Guardar el usuario en Firestore
            if (userId != null) {
                // Aquí puedes actualizar el objeto User para incluir el userId
                val userWithId = user.copy(userId = userId)

                db.collection(Constants.COLLECTION_USER).add(userWithId).addOnCompleteListener { task2 ->
                    if (task2.isSuccessful) {
                        onComplete(true, userId) // Usuario guardado exitosamente
                    } else {
                        onComplete(false, null) // Error al guardar en Firestore
                    }
                }
            } else {
                onComplete(false, null) // No se pudo obtener el ID del usuario
            }
        } else {
            onComplete(false, null) // Error al crear el usuario
        }
    }
}
