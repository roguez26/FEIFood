package com.feifood.feifood.presentation

import android.support.v4.os.IResultReceiver._Parcel
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.requiredHeight
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.internal.isLiveLiteralsEnabled
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.feifood.feifood.R
import com.feifood.feifood.model.Product

@Composable
fun createLabeledTextField(
    label: String,
    value: String,
    onValueChange: (String) -> Unit,
) {
    Text(
        text = label,
        color = Color.Black,
        fontWeight = FontWeight.Bold,
        fontSize = 14.sp,
        modifier = Modifier.padding(start = 3.dp).padding(top = 10.dp)
    )
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .border(1.dp, Color.Gray, RoundedCornerShape(4.dp))
            .background(Color.Transparent)
            .padding(horizontal = 4.dp) // Ajuste para el padding externo
    ) {
        TextField(
            value = value,
            onValueChange = onValueChange,
            colors = TextFieldDefaults.colors(
                unfocusedContainerColor = Color.Transparent,
                focusedContainerColor = Color.Transparent,
                focusedTextColor = Color.Black
            ),
            textStyle = androidx.compose.ui.text.TextStyle(
                color = Color.Black,
                fontSize = 12.sp,
                fontWeight = FontWeight.Normal
            ),
        )
    }
}

@Composable
fun createLabeledTextFieldDisabled(
    label: String,
    value: String,
    onValueChange: (String) -> Unit,
) {
    Text(
        text = label,
        color = Color.Black,
        fontWeight = FontWeight.Bold,
        fontSize = 14.sp,
        modifier = Modifier.padding(start = 3.dp).padding(top = 10.dp),

    )
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .border(1.dp, Color.Gray, RoundedCornerShape(4.dp))
            .background(Color.Transparent)
            .padding(horizontal = 4.dp) // Ajuste para el padding externo
    ) {
        TextField(
            value = value,
            onValueChange = onValueChange,
            colors = TextFieldDefaults.colors(
                unfocusedContainerColor = Color.Transparent,
                focusedContainerColor = Color.Transparent,
                focusedTextColor = Color.Black,
                disabledContainerColor = Color.White
            ),
            enabled = false,
            textStyle = androidx.compose.ui.text.TextStyle(
                color = Color.Black,
                fontSize = 12.sp,
                fontWeight = FontWeight.Normal
            ),
        )
    }
}


@Composable
fun createTitle(title:String, color:Color) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 10.dp).padding(bottom = 20.dp)
    ) {
        Text(
            text = title,
            fontSize = 26.sp,
            fontWeight = FontWeight.Bold,
            color = color,
            modifier = Modifier.align(Alignment.Center)
        )
    }
}

@Composable
fun createSubtitle(subtitle:String, color:Color) {
    Text(
        text = subtitle,
        fontSize = 18.sp,
        fontWeight = FontWeight.Bold,
        color = Color.Black,
        modifier = Modifier.padding(start = 3.dp).padding(top = 15.dp)
    )
}

@Composable
fun createBackButton(onBackClick: () -> Unit) {
    IconButton (
        onClick = { onBackClick() },
        modifier = Modifier
            .padding(top = 10.dp)
            .width(32.dp)
            .height(32.dp)
    ) {
        Image(
            painter = painterResource(id = R.drawable.back_icon),
            contentDescription = "",
            modifier = Modifier.fillMaxSize(),
            colorFilter = ColorFilter.tint(Color.Black)
        )
    }
}

@Composable
fun createTimeButton(onTimeClick: () -> Unit) {
    Box(
        modifier = Modifier
            .padding(top = 15.dp)
            .size(32.dp) // Ajusta el tamaño total del fondo del botón
            .background(Color(0xFF8BC34A), shape = CircleShape) // Fondo circular
            .clickable { onTimeClick() }, // Hace que sea clickeable
        contentAlignment = Alignment.Center // Centra el icono dentro del fondo
    ) {
        Image(
            painter = painterResource(id = R.drawable.time_icon),
            contentDescription = null,
            modifier = Modifier.size(20.dp), // Ajusta el tamaño del icono directamente
            colorFilter = ColorFilter.tint(Color.White)
        )
    }
}

@Composable
fun createAddProductCrossButton(isExpanded: Boolean, onToggle: (Boolean) -> Unit) {
    val icon: Painter = if (isExpanded) {
        painterResource(id = R.drawable.collapse_icon) // Ícono cuando está expandido
    } else {
        painterResource(id = R.drawable.add_cross_icon) // Ícono cuando está contraído
    }

    IconButton(
        onClick = { onToggle(!isExpanded) }, // Invoca `onToggle` con el valor contrario
        modifier = Modifier
            .padding(top = 10.dp)
            .size(30.dp) // Ajusta el tamaño según tus necesidades
            .background(Color(0xFFF1F1F1), shape = RoundedCornerShape(8.dp))
    ) {
        Image(
            painter = icon,
            contentDescription = null,
            modifier = Modifier.fillMaxSize(),
            colorFilter = ColorFilter.tint(Color.Black)
        )
    }
}



@Composable
fun createMainButton(text: String, onMainButtonClick: () -> Unit) {
    Button(
        onClick = { onMainButtonClick() },
        shape = RoundedCornerShape(8.dp),
        modifier = Modifier
            .fillMaxWidth()
            .height(45.dp) // Mantén solo este
            .padding(start = 3.dp),
        colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFD32F2F))
    ) {
        Text(text = text, color = Color.White)
    }
}

@Composable
fun createSecondaryButton(text: String, onMainButtonClick: () -> Unit) {
    Button(
        onClick = { onMainButtonClick() },
        shape = RoundedCornerShape(8.dp),
        modifier = Modifier
            .fillMaxWidth()
            .height(45.dp) // Mantén solo este
            .padding(start = 3.dp),
        colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFDCDCDC))
    ) {
        Text(text = text, color = Color.White)
    }
}


@Composable
fun createPostButton(product:Product) {
    Button(
        onClick = {},  // Define la acción que quieres ejecutar en el click
        modifier = Modifier.padding(2.dp).fillMaxWidth().height(250.dp),
        shape = RoundedCornerShape(8.dp),
        colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFF6F6F6)
        )
    ) {
        Text(
            text = product.name,
            color = Color.Black,
            modifier = Modifier.align(Alignment.Top).padding(bottom = 20.dp)
        )
        Text (
            text = product.description,
            color = Color.Black
        )
    }
}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun <T> createExposedDropdownMenuBox(
    expanded: Boolean,
    onExpandedChange: () -> Unit,
    selectedItem: T,
    onItemSelected: (T) -> Unit,
    options: List<T>,
    itemLabel: (T) -> String // Función que especifica cómo obtener el texto de cada objeto
) {
    ExposedDropdownMenuBox(
        expanded = expanded,
        onExpandedChange = { onExpandedChange() },
        modifier = Modifier.fillMaxWidth()
    ) {
        TextField(
            value = itemLabel(selectedItem),
            onValueChange = {},
            readOnly = true,
            trailingIcon = {
                ExposedDropdownMenuDefaults.TrailingIcon(expanded = expanded)
            },
            modifier = Modifier
                .menuAnchor()
                .padding(3.dp)
                .border(1.dp, Color.Gray, shape = RoundedCornerShape(8.dp)),
            colors = TextFieldDefaults.colors(
                unfocusedContainerColor = Color.White,
                focusedContainerColor = Color(0xFFEAEAEA),
                focusedTextColor = Color.Black
            ),
            textStyle = androidx.compose.ui.text.TextStyle(
                color = Color.Black,
                fontSize = 16.sp,
                fontWeight = FontWeight.Normal
            )
        )

        ExposedDropdownMenu(
            expanded = expanded,
            onDismissRequest = { onExpandedChange() }
        ) {
            options.forEach { option ->
                DropdownMenuItem(
                    text = {
                        Text(
                            text = itemLabel(option),
                            color = Color(0xFF333333),
                            fontSize = 14.sp,
                            fontWeight = FontWeight.Medium
                        )
                    },
                    onClick = {
                        onItemSelected(option)
                        onExpandedChange()
                    },
                    modifier = Modifier
                        .background(Color(0xFFF1F1F1))
                        .padding(4.dp)
                )
            }
        }
    }
}

@Composable
fun createAddProduct(producto:Product) {
    var isExpanded by remember { mutableStateOf(false) }
    var example:String by remember { mutableStateOf("") }
    Card (modifier = Modifier
        .fillMaxWidth()
        .padding(10.dp),
        shape = RoundedCornerShape(8.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
        ) {
        Row {
            Column (modifier = Modifier.width(300.dp)){
                Text(text = producto.name,
                    modifier = Modifier.padding(10.dp),
                    fontWeight = FontWeight.Bold)
                Text(text = producto.description,
                    modifier = Modifier.padding(10.dp))
            }
            createAddProductCrossButton(
                isExpanded = isExpanded,
                onToggle = { isExpanded = it } // Actualiza el estado con el valor retornado
            )
        }
        if (isExpanded) {
            Column(modifier = Modifier.padding(12.dp)) {
                createLabeledTextField(
                    label = "Cantidad a agregar",
                    value = example,
                    onValueChange = { example = it }
                )

                Row (modifier = Modifier.align(Alignment.CenterHorizontally).padding(top = 20.dp)){
                    Column (modifier = Modifier.width(130.dp)) {
                        createMainButton("Agregar") { }
                    }
                    Column (modifier = Modifier.width(130.dp)) {
                        createSecondaryButton("Cancelar") { isExpanded = false
                            example = ""}
                    }

                }
            }
        }

    }

}