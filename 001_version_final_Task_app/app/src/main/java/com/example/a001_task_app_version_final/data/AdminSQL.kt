package com.example.a001_task_app_version_final.data

import android.annotation.SuppressLint
import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.util.Log

class AdminSQL (context: Context, name: String, factory: SQLiteDatabase.CursorFactory?, version: Int) :
    SQLiteOpenHelper(context, name, factory, version){

    val TABLE_USUARIO="usuarios"
    val TABLE_TAREA="tareas"

    override fun onCreate(db: SQLiteDatabase?) {
        val CREATE_DATABASE_USER = (
                "CREATE TABLE $TABLE_USUARIO("
                        + "id INTEGER PRIMARY KEY AUTOINCREMENT,"
                        + "nombre TEXT,"
                        + "clave TEXT"
                        + ")"
                )

        val CREATE_DATABASE_TASK = (
                "CREATE TABLE $TABLE_TAREA("
                        + "id_tarea INTEGER PRIMARY KEY AUTOINCREMENT,"
                        + "titulo TEXT,"
                        + "descripcion TEXT,"
                        + "prioridad TEXT,"
                        + "id_usuario INTEGER,"
                        + "estado INTEGER DEFAULT 0 CHECK (estado IN (0, 1)) NOT NULL,"
                        + "FOREIGN KEY (id_usuario) REFERENCES $TABLE_USUARIO(id)"
                        + ")"
                )

        db?.execSQL(CREATE_DATABASE_USER)
        db?.execSQL(CREATE_DATABASE_TASK)
    }

    override fun onUpgrade(p0: SQLiteDatabase?, p1: Int, p2: Int) {
        TODO("Not yet implemented")
    }

    @SuppressLint("Recycle", "Range")
    fun isUsuarioRegistrado(nombreUsuario: String, clave: String): Int {
        val db = readableDatabase
        val query = "SELECT id FROM $TABLE_USUARIO WHERE nombre=? AND clave=?"
        val selectionArgs = arrayOf(nombreUsuario, clave)

        return db.rawQuery(query, selectionArgs)?.use { cursor ->
            if (cursor.moveToFirst()) {
                cursor.getInt(cursor.getColumnIndex("id"))
            } else {
                -1
            }
        } ?: -1
    }

    fun registrarUsuario(nombreUsuario: String, clave: String): Boolean {
        val db = writableDatabase
        val contenido = ContentValues().apply {
            put("nombre", nombreUsuario)
            put("clave", clave)
        }

        val resultado = db.insert(TABLE_USUARIO, null, contenido)
        db.close()

        return resultado != -1L
    }

    @SuppressLint("Range")
    fun obtenerUsuarioDesdeDB(idUsuario: Int):Usuario?{
        val db = readableDatabase
        val query = "SELECT * FROM $TABLE_USUARIO WHERE id=?"
        val cursor = db.rawQuery(query, arrayOf(idUsuario.toString()))

        var usuario:Usuario?=null
        if(cursor.moveToFirst()){
            val nombre = cursor.getString(cursor.getColumnIndex("nombre"))
            val clave = cursor.getString(cursor.getColumnIndex("clave"))
            usuario = Usuario(idUsuario,nombre,clave)
        }

        cursor.close()
        db.close()

        return usuario
    }

    @SuppressLint("Range")
    fun obtenerListaTareasIdUsuario(idUsuario:Int):List<Tarea>?{
        val db = readableDatabase
        val query = "SELECT * FROM $TABLE_TAREA WHERE id_usuario=?"
        val selectionArgs = arrayOf(idUsuario.toString())

        val listaTareasUsuario= mutableListOf<Tarea>()

        db.rawQuery(query,selectionArgs)?.use { cursor ->
            while(cursor.moveToNext()){
                val idTarea = cursor.getInt(cursor.getColumnIndex("id_tarea"))
                val titulo = cursor.getString(cursor.getColumnIndex("titulo"))
                val descripcion = cursor.getString(cursor.getColumnIndex("descripcion"))
                val prioridad = cursor.getString(cursor.getColumnIndex("prioridad"))
                val idUsuario = cursor.getInt(cursor.getColumnIndex("id_usuario"))
                val estado = cursor.getInt(cursor.getColumnIndex("estado"))

                listaTareasUsuario.add(Tarea(idTarea,titulo,descripcion,prioridad,idUsuario,estado))
            }
        }

        db.close()
        return listaTareasUsuario
    }

    fun registrarTarea(idUsuario: Int, titulo: String, descripcion: String, prioridad: String): Boolean {
        val db = writableDatabase

        try {
            val contenido = ContentValues().apply {
                put("titulo", titulo)
                put("descripcion", descripcion)
                put("prioridad", prioridad)
                put("id_usuario", idUsuario)
            }

            val result = db.insert(TABLE_TAREA, null, contenido)

            if (result == -1L) {
                Log.e("RegistrarTarea", "Error al registrar la tarea.")
                return false
            }
            Log.e("RegistrarTarea", "TAREA REGISTRADA")
            return true
        } catch (e: Exception) {
            Log.e("RegistrarTarea", "Error al registrar la tarea: ${e.message}")
            return false
        } finally {
            db.close()
        }
    }

    fun cambiarEstado(idTarea: Int, nuevoEstado: Int): Boolean {
        val db = this.writableDatabase

        return try {
            val contentValues = ContentValues().apply {
                put("estado", nuevoEstado)
            }

            val whereClause = "id_tarea = ?"
            val whereArgs = arrayOf(idTarea.toString())

            val rowsAffected = db.update(TABLE_TAREA, contentValues, whereClause, whereArgs)
            rowsAffected > 0
        } catch (e: Exception) {
            Log.e("Cambiar estado","Error al modificar el estado")
            false
        } finally {
            db.close()
        }
    }

    fun borrarTarea(idTarea:Int):Boolean {
        val db = writableDatabase
        val whereClause = "id_Tarea = ?"
        val whereArgs = arrayOf(idTarea.toString())
        val rowsAffected = db.delete(TABLE_TAREA, whereClause, whereArgs)

        return rowsAffected > 0
    }

    @SuppressLint("Range")
    fun buscarTareaPorId(idTarea:Int):Tarea?{
        val db = readableDatabase
        val query = "SELECT * FROM $TABLE_TAREA WHERE id_tarea=?"
        val cursor = db.rawQuery(query, arrayOf(idTarea.toString()))

        var tarea: Tarea? = null
        if (cursor.moveToFirst()) {
            val idTarea = cursor.getInt(cursor.getColumnIndex("id_tarea"))
            val titulo = cursor.getString(cursor.getColumnIndex("titulo"))
            val descripcion = cursor.getString(cursor.getColumnIndex("descripcion"))
            val prioridad = cursor.getString(cursor.getColumnIndex("prioridad"))
            val idUsuario = cursor.getInt(cursor.getColumnIndex("id_usuario"))
            val estado = cursor.getInt(cursor.getColumnIndex("estado"))

            tarea = Tarea(idTarea, titulo, descripcion, prioridad, idUsuario, estado)
        }

        cursor.close()
        return tarea
    }
}