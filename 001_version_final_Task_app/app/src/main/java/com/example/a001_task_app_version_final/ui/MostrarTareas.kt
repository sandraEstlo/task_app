package com.example.a001_task_app_version_final.ui

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.example.a001_task_app_version_final.R
import com.example.a001_task_app_version_final.data.AdminSQL
import com.example.a001_task_app_version_final.data.Tarea

class MostrarTareas (
    private val context: Context,
    private var listaTareas:  MutableList<Tarea>,
    private val checkBoxClickListener: OnCheckBoxClickListener
) : RecyclerView.Adapter<MostrarTareas.ViewHolder>() {
    private val REALIZADA = 0
    private val NO_REALIZADA = 1
    private lateinit var adminSQL: AdminSQL

    init {
        setHasStableIds(true)
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val taskNameTextView: TextView = itemView.findViewById(R.id.textViewTaskName)
        val taskDescriptionTextView: TextView = itemView.findViewById(R.id.textViewTaskDescription)
        val checkBoxTask: CheckBox = itemView.findViewById(R.id.checkBoxTask)
        val priorityTextView: TextView = itemView.findViewById(R.id.textViewPriority)
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.tarea_item, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val task = listaTareas[position]

        holder.taskNameTextView.text = task.titulo
        holder.taskDescriptionTextView.text = task.descripcion
        holder.priorityTextView.text = task.prioridad
        holder.checkBoxTask.isChecked = task.estado != REALIZADA

        val prioridadesArray = context.resources.getStringArray(R.array.prioridades)
        when (task.prioridad) {
            prioridadesArray[0] -> holder.priorityTextView.setBackgroundResource(R.drawable.prioridad_elemento_crucial)
            prioridadesArray[1] -> holder.priorityTextView.setBackgroundResource(R.drawable.prioridad_elemento_estrategico)
            prioridadesArray[2] -> holder.priorityTextView.setBackgroundResource(R.drawable.prioridad_elemento_inmediato)
            prioridadesArray[3] -> holder.priorityTextView.setBackgroundResource(R.drawable.prioridad_elemento_ocasional)

            else -> holder.itemView.setBackgroundColor(
                ContextCompat.getColor(
                    context,
                    android.R.color.transparent
                )
            )
        }

        holder.checkBoxTask.setOnClickListener {
            //adminSQL = AdminSQL(context, "appTask", null, 1)
            checkBoxClickListener.onCheckBoxClicked(task)
        }
    }

    override fun getItemCount(): Int {
        return listaTareas.size
    }

    override fun getItemId(position: Int): Long {
        return listaTareas[position].id.toLong()
    }

    fun actualizarLista(nuevaLista: List<Tarea>) {
        listaTareas.clear()
        listaTareas.addAll(nuevaLista)
        notifyDataSetChanged()
    }

    fun getTaskAtPosition(position: Int): Tarea {
        return listaTareas[position]
    }

    fun removeItem(position: Int) {
        listaTareas.removeAt(position)
        notifyItemRemoved(position)
    }

    fun removeTask(tarea: Tarea) {
        val position = listaTareas.indexOf(tarea)
        if (position != -1) {
            listaTareas.removeAt(position)
            notifyItemRangeRemoved(position, 1)
        }
    }
}