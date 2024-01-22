package com.example.a001_task_app_version_final.ui

import android.annotation.SuppressLint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.Window
import android.widget.Button
import android.widget.EditText
import android.widget.Spinner
import android.widget.Switch
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.a001_task_app_version_final.R
import com.example.a001_task_app_version_final.data.AdminSQL
import com.example.a001_task_app_version_final.data.Tarea
import com.example.a001_task_app_version_final.data.Usuario
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.snackbar.Snackbar

class Lista_tareas : AppCompatActivity(), OnCheckBoxClickListener {
    private lateinit var taskAdapter: MostrarTareas
    private lateinit var listaTareas: MutableList<Tarea>
    private lateinit var usuario: Usuario
    private lateinit var adminSQL: AdminSQL
    private lateinit var recyclerView: RecyclerView
    private lateinit var listaTareasFiltrada: MutableList<Tarea>
    private lateinit var switchSelector: Switch

    private var REALIZADA = 1
    private var NO_REALIZADA = 0

    private var estado = NO_REALIZADA
    private var filtroPrioridad: String? = null;

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_lista_tareas)

        adminSQL = AdminSQL(this, "appTask", null, 1)
        listaTareasFiltrada = mutableListOf()

        val extras: Bundle? = intent.extras
        usuario = (extras?.getSerializable("usuario") as? Usuario)!!

        if (usuario != null) {
            switchSelector = findViewById(R.id.switchSelector)
            recyclerView = findViewById(R.id.recyclerViewTasks)

            InicializarAdapterMostrarTareas()
            estado = NO_REALIZADA
            mostrarTareas(estado, switchSelector.isChecked)

            switchSelector.setOnCheckedChangeListener { _, isChecked ->
                estado = if (isChecked) REALIZADA else NO_REALIZADA
                mostrarTareas(estado, isChecked)
            }


            val swipeHandler = object : SwipeToDeleteCallback(this, taskAdapter) {
                override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                    val position = viewHolder.adapterPosition
                    val tareaABorrar = (recyclerView.adapter as MostrarTareas).getTaskAtPosition(position)

                    (recyclerView.adapter as MostrarTareas).removeTask(tareaABorrar)
                    adminSQL.borrarTarea(tareaABorrar.id)
                    mostrarTareas(estado, switchSelector.isChecked)
                }
            }

            val itemTouchHelper = ItemTouchHelper(swipeHandler)
            itemTouchHelper.attachToRecyclerView(recyclerView)

            val btnNuevaTarea = findViewById<Button>(R.id.botonCrear)
            val btnFiltrar = findViewById<Button>(R.id.btnFilter)

            btnNuevaTarea.setOnClickListener {
                mostrarDialogoNuevaTarea(usuario!!) // Llama al metodo que muestra un alert view con la ventana para registrar la tarea
            }

            btnFiltrar.setOnClickListener {
                mostrarDialogoFiltrar(usuario!!)
            }

        } else {
            Toast.makeText(this, "No se encontró información del usuario", Toast.LENGTH_SHORT).show()
        }

    }

    override fun onCheckBoxClicked(Tarea: Tarea) {
        val nuevoEstado = if (Tarea.estado == REALIZADA) NO_REALIZADA else REALIZADA
        adminSQL.cambiarEstado(Tarea.id, nuevoEstado)
        adminSQL.buscarTareaPorId(Tarea.id)

        /*
        val estadoAntes = if (Tarea.estado == REALIZADA) "Realizada" else "No realizada"
        val estadoDespues = if (nuevoEstado == REALIZADA) "Realizada" else "No realizada"
        val mensaje = "Estado antes: $estadoAntes\nEstado Actual: $estadoDespues"
        val snackbar = Snackbar.make(recyclerView, mensaje, Snackbar.LENGTH_LONG)
        snackbar.show()
        */

        mostrarTareas(estado, switchSelector.isChecked)
    }

    @SuppressLint("CutPasteId", "MissingInflatedId")
    private fun mostrarDialogoNuevaTarea(usuario: Usuario) {
        val bottomSheetDialog = BottomSheetDialog(this, R.style.FullScreenDialog)
        val view = layoutInflater.inflate(R.layout.crear_nota_dialog, null)
        bottomSheetDialog.setContentView(view)
        bottomSheetDialog.show()

        val btnCrear = view.findViewById<Button>(R.id.crearNota)
        val etTitulo = view.findViewById<EditText>(R.id.editTextTitulo)
        val etDescripcion = view.findViewById<EditText>(R.id.editTextDescripcion)
        val spPrioridad = view.findViewById<Spinner>(R.id.tipoPrioridad)

        btnCrear.setOnClickListener {
            val titulo = etTitulo.text.toString()
            val descripcion = etDescripcion.text.toString()
            val indice: Int = spPrioridad.selectedItemPosition
            val prioridadesArray: Array<String> = resources.getStringArray(R.array.prioridades)

            if (indice > -1 && indice < prioridadesArray.size) {
                val prioridad: String = prioridadesArray[indice]

                if(adminSQL.registrarTarea(usuario.id, titulo, descripcion, prioridad)){
                    mostrarTareas(estado,switchSelector.isChecked)
                    bottomSheetDialog.dismiss()
                } else{
                    Toast.makeText(this, "ERROR AL CREAR LA TAREA", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

    private fun mostrarDialogoFiltrar(usuario: Usuario) {
        val viewFiltrar = layoutInflater.inflate(R.layout.filtrar_dialogo, null)
        val builder = AlertDialog.Builder(this)
        builder.setView(viewFiltrar)

        val alertDialog = builder.create()
        alertDialog.show()

        val btnFiltrar = viewFiltrar.findViewById<Button>(R.id.btnFiltrar)
        val btnBorrar = viewFiltrar.findViewById<Button>(R.id.btnBorrar)
        val spPrioridad = viewFiltrar.findViewById<Spinner>(R.id.tipoPrioridad)
        val prioridadesArray: Array<String> = resources.getStringArray(R.array.prioridades)

        btnFiltrar.setOnClickListener {
            val indice: Int = spPrioridad.selectedItemPosition

            if (indice > -1 && indice < prioridadesArray.size) {
                filtroPrioridad = prioridadesArray[indice]
                mostrarTareas(estado, switchSelector.isChecked)
                alertDialog.dismiss()
            }
        }

        btnBorrar.setOnClickListener {
            filtroPrioridad = null
            mostrarTareas(estado, switchSelector.isChecked)
            alertDialog.dismiss()
        }

        val window: Window? = alertDialog.window
        window?.setBackgroundDrawableResource(R.drawable.dialogo_registro_background)
    }

    private fun InicializarAdapterMostrarTareas() {
        if (!::taskAdapter.isInitialized) {
            taskAdapter = MostrarTareas(this, mutableListOf(), this)
            recyclerView.adapter = taskAdapter
            recyclerView.layoutManager = LinearLayoutManager(this)
        }
    }

    private fun mostrarTareas(estado: Int, switchChecked: Boolean) {
        listaTareas = adminSQL.obtenerListaTareasIdUsuario(usuario!!.id) as MutableList<Tarea>
        filtrarTareas(estado)
        taskAdapter.actualizarLista(listaTareasFiltrada)
    }

    @SuppressLint("NotifyDataSetChanged")
    fun filtrarTareas(estado: Int) {
        listaTareasFiltrada.clear()

        when (estado) {
            0 -> { listaTareasFiltrada.addAll(listaTareas.filter { it.estado == NO_REALIZADA }) }
            1 -> { listaTareasFiltrada.addAll(listaTareas.filter { it.estado == REALIZADA }) }
        }

        filtroPrioridad?.let { prioridad ->
            listaTareasFiltrada = listaTareasFiltrada.filter { it.prioridad == prioridad } as MutableList<Tarea>
        }

        taskAdapter.notifyDataSetChanged()
    }

    fun filtrarPorPrioridad(prioridad: String){
        listaTareasFiltrada.addAll(listaTareasFiltrada.filter { it.prioridad==prioridad })
        taskAdapter.actualizarLista(listaTareasFiltrada)
    }
}