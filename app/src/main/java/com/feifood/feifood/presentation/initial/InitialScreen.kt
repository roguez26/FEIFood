package com.feifood.feifood.presentation.initial

import android.widget.Button
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.feifood.feifood.R
import org.w3c.dom.Text

@Preview
@Composable
fun InitialScreen(navigateToLogin: () -> Unit = {}, navigateToSignup: () -> Unit = {}) {
    Column(
        modifier = Modifier
            .fillMaxSize().background(
            brush = Brush.verticalGradient(
                colors = listOf(
                    Color(0xFF740938).copy(alpha = 0.8f), // Primer color con transparencia
                    Color(0xFFD32F2F).copy(alpha = 0.8f)  // Segundo color con transparencia
                )
            )
        ) .padding(16.dp)

    ) {


        Spacer(modifier = Modifier.weight(0.5f))

        Surface(
            modifier = Modifier
                .width(320.dp) // Define el ancho deseado
                .height(500.dp) // Define la altura deseada
                .clip(RoundedCornerShape(20.dp))
                .align(Alignment.CenterHorizontally), // Centra el rectángulo en la pantalla
            color = Color.White, // Color de fondo del rectángulo
            shadowElevation = 8.dp
        ) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(30.dp),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Spacer(modifier = Modifier.weight(0.5f))
                Image(
                    painter = painterResource(id = R.drawable.feifood),
                    contentDescription = "Logo",
                    modifier = Modifier
                        .size(120.dp)
                        .clip(CircleShape) // Imagen redonda
                )
                Spacer(modifier = Modifier.weight(0.1f))

                Text(
                    text = "FEIFood",
                    fontSize = 26.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color.Black,
                )
                Spacer(modifier = Modifier.weight(0.5f))
                Text(
                    text = "Bienvenido a FEIFood: Un lugar donde puedes encontrar los mejores productos que ofrece nuestra facultad.",
                    fontSize = 10.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color.Black,
                )

                Spacer(modifier = Modifier.weight(0.3f))

                Button(
                    onClick = {navigateToSignup()},  shape = RoundedCornerShape(8.dp), modifier = Modifier.
                    fillMaxWidth().
                    padding(10.dp), colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF740938))
                ) {
                    Text(text = "Registrarse", color = Color.White)
                }

                Button(
                    onClick = {navigateToLogin()},  shape = RoundedCornerShape(8.dp), modifier = Modifier.
                    fillMaxWidth().
                    padding(10.dp), colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFD32F2F))
                ) {
                    Text(text = "Iniciar sesión", color = Color.White)
                }
            }
        }
        Spacer(modifier = Modifier.weight(1f))


    }
}