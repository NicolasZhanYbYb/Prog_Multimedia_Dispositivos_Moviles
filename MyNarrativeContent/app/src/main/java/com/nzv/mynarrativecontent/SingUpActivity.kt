
package com.nzv.mynarrativecontent

import android.annotation.SuppressLint
import android.app.DatePickerDialog
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import com.google.firebase.firestore.CollectionReference
import com.google.firebase.firestore.FirebaseFirestore
import com.nzv.mynarrativecontent.databinding.ActivitySingUpBinding
import java.util.Calendar

class SingUpActivity : AppCompatActivity() {
    private lateinit var binding: ActivitySingUpBinding
    private lateinit var db: FirebaseFirestore
    private var selectDate: Calendar? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivitySingUpBinding.inflate(layoutInflater)
        setContentView(binding.root)
        startElements()
    }

    @SuppressLint("SetTextI18n")
    private fun startElements() {
        with (binding){
            btSingUpSU.setOnClickListener { actionsBtSingUp(it) }
            tvDatePicker.setOnClickListener { myDatePicker(it) }
        }
    }

    private fun actionsBtSingUp(it: View) {
        Utils.closeKeyboard(it, this)
        checkCanAccept(it)
    }

    //Comprueba que todos los campos estén informados para poder aceptar
    private fun checkCanAccept(view: View) {
        if (binding.etPassSU.text.isEmpty() || binding.etPassSU.length() < 8) {
            Utils.showSnackBar("La contraseña es muy corta", view)
            return
        }

        if (binding.etFullName.text.isEmpty()) {
            Utils.showSnackBar("Debes indicarnos tu nombre", view)
            return
        }

        if (binding.etFullName.text.isEmpty()) {
            Utils.showSnackBar("Debes indicarnos tu nombre", view)
            return
        }
        
        val user = binding.etUserSu.text.toString()
        val userCollection: CollectionReference = db.collection("Usuarios")

        if (user.isEmpty() || user.isBlank()) {
            Utils.showSnackBar("El nombre de usuario no puede estar vacio", view)
            return
        }

        getNameUser(userCollection, user, view)
    }

    private fun myDatePicker(view: View) {
        val cal = selectDate ?: Calendar.getInstance()
        val dateSetListener = DatePickerDialog.OnDateSetListener { _, year, month, day ->

            cal.set(Calendar.YEAR, year)
            cal.set(Calendar.MONTH, month)
            cal.set(Calendar.DAY_OF_MONTH, day)

            binding.tvDatePicker.text = "${cal.get(Calendar.DAY_OF_MONTH)} " +
                    "/ ${cal.get(Calendar.MONTH) + 1} / ${cal.get(Calendar.YEAR)}"
            selectDate = cal
        }

        val datePickerDialog = DatePickerDialog(
            this,
            dateSetListener,
            cal.get(Calendar.YEAR),
            cal.get(Calendar.MONTH),
            cal.get(Calendar.DAY_OF_MONTH)
        )

        datePickerDialog.datePicker.maxDate = System.currentTimeMillis()
        datePickerDialog.show()
    }

    private fun getNameUser(usersCollection: CollectionReference, nameUser: String, view: View) {
        val user = hashMapOf(
            "user" to binding.etUserSu.text.toString().trim(),
            "name" to binding.etFullName.text.toString(),
            "hash" to binding.etPassSU.text.toString(),
            "avatar" to binding.etAvatarSU.text.toString(),
            "birthday" to selectDate?.get(Calendar.DAY_OF_MONTH),
            "birthmonth" to selectDate?.get(Calendar.MONTH),
            "birthyear" to selectDate?.get(Calendar.YEAR)
        )

        usersCollection.whereEqualTo("user", nameUser.lowercase()).get().apply {
            addOnSuccessListener {
                for (user in it) {
                    Utils.showSnackBar("El nombre de usuario ya existe", view)
                    return@addOnSuccessListener
                }

                usersCollection.document().set(user).addOnSuccessListener {
                    Log.d("DOC_SET", "Documento añadido!")
                }.addOnFailureListener { e ->
                    Log.w("DOC_SET", "Error en la escritura", e)
                }
                Utils.showSnackBar("Te has registrado correctamente", view)
                finish()

            }
            addOnFailureListener { exception ->
                Log.d("DOC", "Error durante la recogida de documentos: ", exception)
            }
        }
    }
}