package com.feifood.feifood.model.dataccess

import com.google.firebase.firestore.FirebaseFirestore
import com.feifood.feifood.model.Product
import androidx.compose.runtime.MutableState
import com.feifood.feifood.Constants
import com.google.firebase.firestore.QuerySnapshot

class ProductManager {

    fun createProduct(
        product: Product,
        showInformativeDialog: MutableState<Boolean>,
        showErrorDialog: MutableState<Boolean>
    ) {
        val db: FirebaseFirestore = FirebaseFirestore.getInstance()
        db.collection(Constants.COLLECTION_PRODUCT).add(product)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    showInformativeDialog.value = true
                } else {
                    showErrorDialog.value = true
                }
            }
    }

    fun getProductsByUserId(
        userId: String,
        onResult: (List<Product>) -> Unit
    ) {
        val db: FirebaseFirestore = FirebaseFirestore.getInstance()
        db.collection(Constants.COLLECTION_PRODUCT)
            .whereEqualTo("userId", userId)
            .get()
            .addOnSuccessListener { querySnapshot ->
                val products = querySnapshot.documents.mapNotNull { document ->
                    document.toObject(Product::class.java)
                }
                onResult(products) // Llama al callback con los productos
            }
            .addOnFailureListener {
                onResult(emptyList()) // Si hay error, retorna lista vacía
            }
    }
}
