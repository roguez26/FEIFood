package com.feifood.feifood.presentation.PostCreation

import android.app.TimePickerDialog
import android.icu.util.Calendar
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
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
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.Alignment
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import com.feifood.feifood.CurrentSession
import com.feifood.feifood.R
import com.feifood.feifood.model.Product
import com.feifood.feifood.model.dataccess.ProductManager
import com.feifood.feifood.presentation.createAddProduct
import com.feifood.feifood.presentation.createBackButton
import com.feifood.feifood.presentation.createExposedDropdownMenuBox
import com.feifood.feifood.presentation.createLabeledTextField
import com.feifood.feifood.presentation.createLabeledTextFieldDisabled
import com.feifood.feifood.presentation.createMainButton
import com.feifood.feifood.presentation.createPostButton
import com.feifood.feifood.presentation.createSubtitle
import com.feifood.feifood.presentation.createTimeButton
import com.feifood.feifood.presentation.createTitle

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PostCreationScreen(navigateToHome: () -> Unit, navigateToProductRegistration: () -> Unit) {
    val context = LocalContext.current
    val postTypes = listOf("Venta", "Apartados")
    var expanded by remember { mutableStateOf(false) }
    var selectedType by remember { mutableStateOf(postTypes[0]) }
    var selectedStartTime by remember { mutableStateOf("") }
    var selectedEndTime by remember { mutableStateOf("") }
    var showStartPicker by remember { mutableStateOf(false) }
    var showEndPicker by remember { mutableStateOf(false) }
    var location: String by remember { mutableStateOf("") }
    var description: String by remember { mutableStateOf("") }

    val productManager = ProductManager()
    var products by remember { mutableStateOf<List<Product>>(emptyList()) }

    LaunchedEffect(CurrentSession.currentUser?.userId) {
        val userId = CurrentSession.currentUser?.userId
        if (userId != null) {
            ProductManager().getProductsByUserId(userId) { productList ->
                products = productList
            }
        } else {
            products = emptyList()
        }
    }

    if (showStartPicker) {
        showTimePicker(
            onTimeSelected = { selectedTime ->
                selectedStartTime = selectedTime
                showStartPicker = false
            },
            onDismiss = { showStartPicker = false }
        )
    }

    if (showEndPicker) {
        showTimePicker(
            onTimeSelected = { selectedTime ->
                selectedEndTime = selectedTime
                showEndPicker = false
            },
            onDismiss = { showEndPicker = false }
        )
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
            .padding(16.dp),
        verticalArrangement = Arrangement.Top
    ) {
        Row {
            createBackButton { navigateToHome() }
            createTitle("Crear publicación", Color.Black)
        }

        createExposedDropdownMenuBox(
            expanded = expanded,
            onExpandedChange = { expanded = !expanded },
            selectedItem = selectedType,
            onItemSelected = { selectedType = it },
            options = postTypes,
            itemLabel = { it.toString() }
        )

        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(16.dp),
            modifier = Modifier.fillMaxWidth()
        ) {
            Column(modifier = Modifier.width(70.dp)) {
                createLabeledTextFieldDisabled(
                    label = "Inicio",
                    value = selectedStartTime,
                    onValueChange = { selectedStartTime = it }
                )
            }
            createTimeButton { showStartPicker = true }
            Column(modifier = Modifier.width(70.dp)) {
                createLabeledTextFieldDisabled(
                    label = "Cierre",
                    value = selectedEndTime,
                    onValueChange = { selectedEndTime = it }
                )
            }
            createTimeButton { showEndPicker = true }
        }

        createLabeledTextField(
            label = "Ubicacion",
            value = location,
            onValueChange = { location = it }
        )
        createLabeledTextField(
            label = "Algo que quieras añadir",
            value = description,
            onValueChange = { description = it }
        )

        createSubtitle("Productos", Color.Black)

        // Cambié la altura del botón
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .background(Color.Yellow)
                .height(47.dp)
                .fillMaxWidth() // Hacer que el botón ocupe todo el ancho disponible
        ) {
            Button(
                onClick = { navigateToProductRegistration() },
                modifier = Modifier
                    .width(180.dp)
                    .height(45.dp),
                colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF4CAF50)),
                shape = RoundedCornerShape(8.dp),
            ) {
                Text(
                    text = "Registrar producto",
                    fontSize = 13.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color.White,
                    modifier = Modifier.padding(end = 5.dp)
                )
                Image(
                    painter = painterResource(id = R.drawable.add_icon),
                    contentDescription = "",
                    modifier = Modifier.size(50.dp),
                    colorFilter = ColorFilter.tint(Color.White)
                )
            }
        }

        // LazyColumn modificado
        LazyColumn(
            modifier = Modifier
                .fillMaxWidth()
        ) {
            items(products) { product ->
                createAddProduct(product)
            }
        }
    }
}

@Composable
fun showTimePicker(onTimeSelected: (String) -> Unit, onDismiss: () -> Unit) {
    val context = LocalContext.current
    val calendar = Calendar.getInstance()

    TimePickerDialog(
        context,
        { _, hour: Int, minute: Int ->
            val selectedTime = String.format("%02d:%02d", hour, minute)
            onTimeSelected(selectedTime)
        },
        calendar.get(Calendar.HOUR_OF_DAY),
        calendar.get(Calendar.MINUTE),
        true
    ).apply {
        setOnDismissListener {
            onDismiss() // Llama a onDismiss cuando se cierra el diálogo
        }
    }.show()
}