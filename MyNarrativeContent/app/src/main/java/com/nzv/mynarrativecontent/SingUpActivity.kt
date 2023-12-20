
package com.nzv.mynarrativecontent

import android.annotation.SuppressLint
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import com.google.firebase.FirebaseApp
import com.google.firebase.firestore.CollectionReference
import com.google.firebase.firestore.DocumentReference
import com.google.firebase.firestore.FirebaseFirestore
import com.nzv.mynarrativecontent.databinding.ActivitySingUpBinding
import com.nzv.mynarrativecontent.databinding.ActivityStartBinding

class SingUpActivity : AppCompatActivity() {
    private lateinit var binding: ActivitySingUpBinding
    private lateinit var db: FirebaseFirestore

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivitySingUpBinding.inflate(layoutInflater)
        setContentView(binding.root)

        FirebaseApp.initializeApp(applicationContext)
        db = FirebaseFirestore.getInstance()
        startElements()
    }

    @SuppressLint("SetTextI18n")
    private fun startElements() {
        with (binding){
            btSingUpSU.setOnClickListener { actionsBtSingUp(it) }
        }
    }

    private fun actionsBtSingUp(it: View) {
        Utils.closeKeyboard(it, this)
        if (!checkCanAccept(it))
            return
        
    }

    //Comprueba que todos los campos estén informados para poder aceptar
    private fun checkCanAccept(view: View): Boolean {
        if (binding.etPassSU.text.isEmpty() || binding.etPassSU.length() < 8) {
            Utils.showSnackBar("La contraseña es muy corta", view)
            return false
        }
        
        val user = binding.etUserSu.text.toString()
        val userCollection: CollectionReference = db.collection("Usuarios")

        if (user.isEmpty() || user.isBlank()) {
            Utils.showSnackBar("El nombre de usuario no puede estar vacio", view)
            return false
        }

        if (getNameUser(userCollection, user)) {
            Utils.showSnackBar("El nombre de usuario ya existe", view)
            return false
        }
        return true
    }
    /*fun obtenerElementoConEscuchaActiva(docRef: DocumentReference) {
        docRef.addSnapshotListener { document, e ->
            if (e != null) {
                Log.w("addSnapshotListener", "Escucha fallida!", e)
                return@addSnapshotListener
            }

            if (document != null && document.exists()) {
                Log.d("addSnapshotListener", "Información actual: ${document.data}")
                val texto = document["modulo"].toString() + " - " +
                        document["nombre"].toString() + " " +
                        document["apellido"].toString()
                binding.tvAvatarSU.text = texto
            } else {
                Log.d("addSnapshotListener", "Información actual: null")
            }
        }
    }*/

    private fun getNameUser(usersCollection: CollectionReference, nameUser: String): Boolean {
        usersCollection.whereEqualTo("user", nameUser.lowercase()).get().apply {
            addOnSuccessListener {
                for (document in it) {
                    return@addOnSuccessListener
                }
            }
            addOnFailureListener { exception ->
                Log.d("DOC", "Error durante la recogida de documentos: ", exception)
            }
        }
        return false
    }
}