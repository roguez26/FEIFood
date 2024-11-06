package com.feifood.feifood.presentation.home

import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Color.Companion.Black
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.feifood.feifood.R
import com.google.firebase.auth.FirebaseAuth
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.res.colorResource
import com.feifood.feifood.Constants
import com.feifood.feifood.model.Product
import com.feifood.feifood.model.User
import com.feifood.feifood.presentation.createPostButton
import com.feifood.feifood.presentation.createSubtitle
import com.feifood.feifood.presentation.createTitle
import com.feifood.feifood.presentation.login.LoginScreen
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.tasks.await

@Composable
fun HomeScreen( navigateToPostCreation: () -> Unit) {
    var products:List<Product> by remember { mutableStateOf(emptyList<Product>()) }
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(Constants.LIGHT_RED))
            .padding(12.dp),
        verticalArrangement = Arrangement.Top
    ) {

        createTitle("FEIFood", Color.White)

        Surface(
            modifier = Modifier
                .fillMaxWidth() // Define el ancho deseado
                .height(670.dp) // Define la altura deseada
                .clip(RoundedCornerShape(16.dp))
                .align(Alignment.CenterHorizontally),
            color = Color(0xFFF8F8F8)// Centra el rectángulo en la pantalla,

        ) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(15.dp)
                    .padding(top = 5.dp),
                verticalArrangement = Arrangement.Top,
            ) {
               createSubtitle("Publicaciones", Black)
                Button (
                    onClick = {navigateToPostCreation()}, // Acción al hacer clic
                    modifier = Modifier
                        .width(180.dp) // Define el ancho deseado
                        .height(70.dp) // Define la altura deseada
                        .padding(1.dp)
                        .padding(top = 14.dp).padding(bottom = 10.dp), // Espacio alrededor del botón
                    colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF4CAF50)), // Color de fondo del botón
                    shape = RoundedCornerShape(8.dp), // Bordes redondeados
                ) {
                    Row(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(2.dp), // Espacio interno del botón
                        verticalAlignment = Alignment.CenterVertically // Centra verticalmente el contenido.
                    ) {
                        Text(
                            text = "Crear publicación",
                            fontSize = 13.sp,
                            fontWeight = FontWeight.Bold,
                            color = Color.White,
                            modifier = Modifier.padding(end = 5.dp) // Espacio entre el texto y el ícono
                        )
                        Image(
                            painter = painterResource(id = R.drawable.add_icon),  // Cambia "add_icon" al nombre de tu archivo sin la extensión
                            contentDescription = "Descripción del ícono",  // Proporciona una descripción accesible
                            modifier = Modifier.size(50.dp),  // Ajusta el tamaño según sea necesario
                            colorFilter = ColorFilter.tint(Color.White) // Cambia el color del ícono
                        )
                    }
                }
                //
                LaunchedEffect (Unit) {
                    // Simulamos la carga de productos
                    products = loadProducts()
                    Log.d("HomeScreen", "Productos cargados: $products")
                }

                // Usamos LazyColumn para mostrar la lista de productos
                LazyColumn(
                    modifier = Modifier
                        .fillMaxSize().clip(RoundedCornerShape(8.dp))
                        .background(Color(0xFFECECEC)) // Coloca el fondo verde
                ) {
                    items(products) { product ->
                            createPostButton(product)
                    }
                }
                }

            }
        }
}

suspend fun loadProducts(): List<Product> {
    val db = Firebase.firestore
    return try {
        val snapshot = db.collection(Constants.COLLECTION_PRODUCT).get().await()
        Log.d("LoadProducts", "Cantidad de documentos recuperados: ${snapshot.size()}")

        snapshot.documents.mapNotNull { document ->
            val product = document.toObject(Product::class.java)
            Log.d("LoadProducts", "Producto cargado: $product") // Log para cada producto
            product
        }
    } catch (e: Exception) {
        Log.e("LoadProducts", "Error al cargar productos: ${e.message}")
        emptyList()
    }
}