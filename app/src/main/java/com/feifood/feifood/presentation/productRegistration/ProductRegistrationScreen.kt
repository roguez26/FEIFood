package com.feifood.feifood.presentation.productRegistration

import com.feifood.feifood.model.dataccess.ProductManager
import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.feifood.feifood.Constants
import com.feifood.feifood.CurrentSession
import com.feifood.feifood.model.Product
import com.feifood.feifood.presentation.createBackButton
import com.feifood.feifood.presentation.createExposedDropdownMenuBox
import com.feifood.feifood.presentation.createLabeledTextField
import com.feifood.feifood.presentation.createMainButton
import com.feifood.feifood.presentation.createMessageErrorDialog
import com.feifood.feifood.presentation.createMessageInformativeDialog
import com.feifood.feifood.presentation.createTitle
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.tasks.await


@Composable
fun ProductRegistrationScreen( navigateToPostCreation: () -> Unit) {
    var name:String by remember { mutableStateOf("") }
    var price:String by remember { mutableStateOf("")}
    var description:String by remember { mutableStateOf("")}
    val showInformativeDialog = remember { mutableStateOf(false) }
    val showErrorDialog = remember { mutableStateOf(false) }
    var product: Product? by remember { mutableStateOf(null) }
    var errorMessage:String by remember { mutableStateOf("")}
    var expanded by remember { mutableStateOf(false) }
    val categories = listOf("Comida", "Postre", "Bebida")
    var selectedCategory by remember { mutableStateOf(categories[0]) }


    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
            .padding(16.dp),
        verticalArrangement = Arrangement.Top
    ) {
        Row {

           createBackButton { navigateToPostCreation() }
            createTitle("Registrar producto", Color.Black)
        }

        createExposedDropdownMenuBox(
            expanded = expanded,
            onExpandedChange = { expanded = !expanded },
            selectedItem = selectedCategory,
            onItemSelected = { selectedCategory = it },
            options = categories,
            itemLabel = {it.toString()}
        )

        createLabeledTextField(
            label = "Nombre del producto",
            value = name,
            onValueChange = {name = it}
        )
        createLabeledTextField(
            label = "Precio",
            value = price,
            onValueChange = {price = it}
        )

        createLabeledTextField(
            label = "Descripcion",
            value = description,
            onValueChange = {description = it}
        )



        Spacer(modifier = Modifier.weight(0.5f))

        createMainButton("Guardar") {
            try {
                val productManager = ProductManager()
                product = Product.create(name, description, price, "", selectedCategory.toString(), CurrentSession.currentUser?.userId.toString())
                product?.let { productManager.createProduct(it, showInformativeDialog, showErrorDialog)}
            } catch (e: IllegalArgumentException) {
                showErrorDialog.value = true
                errorMessage = e.message.toString()
            }
        }

        if (showInformativeDialog.value) {
            createMessageInformativeDialog(
                title = "Éxito",
                message = "Producto registrado con éxito.",
                onDismiss = { showInformativeDialog.value = false }
            )
            product = null
            name = ""
            description = ""
            price = ""
        }
        if (showErrorDialog.value){
            showErrorMessage(showErrorDialog, errorMessage)
        }

    }
}

@Composable
fun showErrorMessage(showErrorDialog: MutableState<Boolean>, errorMessage:String) {
        createMessageErrorDialog(
            title = "Error de registro",
            message = errorMessage,
            onDismiss = { showErrorDialog.value = false }
        )
}





