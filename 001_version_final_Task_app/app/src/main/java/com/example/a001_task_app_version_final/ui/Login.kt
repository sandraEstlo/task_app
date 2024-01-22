package com.example.a001_task_app_version_final.ui

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Window
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import com.example.a001_task_app_version_final.R
import com.example.a001_task_app_version_final.data.AdminSQL
import com.example.a001_task_app_version_final.data.Tarea

class Login : AppCompatActivity() {

    private val listaTareas: MutableList<Tarea> = mutableListOf()
    private lateinit var adminSQL: AdminSQL

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        adminSQL = AdminSQL(this,"appTask",null,1)

        val btnLogin: Button = findViewById(R.id.btnLogin)
        val etnombre = findViewById<EditText>(R.id.ediTextName)
        val etpsw = findViewById<EditText>(R.id.ediTextPassword)


        btnLogin.setOnClickListener {
            val nombre = etnombre.text.toString()
            val psw = etpsw.text.toString()
            val idUsuario = adminSQL.isUsuarioRegistrado(nombre, psw)

            if (idUsuario != -1) {
                val usuarioAutentificado = adminSQL.obtenerUsuarioDesdeDB(idUsuario)

                if (usuarioAutentificado != null) {
                    usuarioAutentificado.tareas = (adminSQL.obtenerListaTareasIdUsuario(usuarioAutentificado.id)
                        ?: emptyList()).toMutableList()
                    val intent = Intent(this, Lista_tareas::class.java)
                    intent.putExtra("usuario", usuarioAutentificado)
                    startActivity(intent)
                }
            } else {
                etnombre.text.clear()
                etpsw.text.clear()
                mostrarDialogoPersonalizadoRegistro()
            }
        }
    }

    private fun mostrarDialogoPersonalizadoRegistro() {
        val viewPersonalizado = layoutInflater.inflate(R.layout.dialogo_registro_personalizado, null)
        val builder = AlertDialog.Builder(this)
        builder.setView(viewPersonalizado)

        val alertDialog = builder.create()
        alertDialog.show()

        val botonCancelar = viewPersonalizado.findViewById<ImageView>(R.id.cerrar)
        val botonRegistrar = viewPersonalizado.findViewById<Button>(R.id.registrar)
        val etNombre = viewPersonalizado.findViewById<EditText>(R.id.editTextUsuario)
        val etpsw = viewPersonalizado.findViewById<EditText>(R.id.editTextClave)

        botonCancelar.setOnClickListener {
            alertDialog.dismiss()
        }

        botonRegistrar.setOnClickListener {
            val nom = etNombre.text.toString()
            val psw = etpsw.text.toString()

            if (nom.isNotEmpty() && psw.isNotEmpty()) {
                if(adminSQL.registrarUsuario(nom, psw)){
                    Toast.makeText(this,"USUARIO REGISTRADO", Toast.LENGTH_SHORT).show()
                    alertDialog.dismiss()
                }
                else{
                    Toast.makeText(this,"NO SE HA PODIDO REGISTRAR AL USUARIO", Toast.LENGTH_SHORT).show()
                }
            } else {
                Toast.makeText(this,"DEBES INTRODUCIR TODOS LOS DATOS", Toast.LENGTH_SHORT).show()
            }
        }

        val window: Window? = alertDialog.window
        window?.setBackgroundDrawableResource(R.drawable.dialogo_registro_background)
    }
}