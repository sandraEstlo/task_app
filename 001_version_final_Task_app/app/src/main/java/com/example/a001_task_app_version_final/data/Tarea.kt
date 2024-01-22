package com.example.a001_task_app_version_final.data

import java.io.Serializable

data class Tarea(
                 val id: Int,
                 val titulo: String,
                 val descripcion: String,
                 val prioridad: String,
                 val idUsuario: Int,
                 var estado: Int
): Serializable