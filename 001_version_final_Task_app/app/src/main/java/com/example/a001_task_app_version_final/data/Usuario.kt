package com.example.a001_task_app_version_final.data

import java.io.Serializable

data class Usuario(
                   val id: Int,
                   val nombreUsuario: String,
                   val clave: String,
                   var tareas: MutableList<Tarea> = mutableListOf()
): Serializable