package com.g2.santurtziapp

import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import com.g2.santurtziapp.Constantes.Partida
import com.google.firebase.firestore.FirebaseFirestore


class DB(context: Context, name: String, factory: SQLiteDatabase.CursorFactory?, version:Int): SQLiteOpenHelper(context,name,factory, version){

    private val fb = FirebaseFirestore.getInstance()

    override fun onCreate(db: SQLiteDatabase?) {

        db!!.execSQL("create table Partida(apodo text primary key, punto text)")
        db!!.execSQL("create table Ranking(apodo text primary key, punto text)")

    }//onCreate(db: SQLiteDatabase?)

    override fun onUpgrade(db: SQLiteDatabase?, old: Int, new: Int) {

        db!!.execSQL("drop table if exists Partida")
        db!!.execSQL("drop table if exists Ranking")

        onCreate(db)

    }//onUpgrade(db: SQLiteDatabase?, old: Int, new: Int)

    fun insertarPartida(apodo: String) {

        val db: SQLiteDatabase = this.writableDatabase
        val insertar: ContentValues = ContentValues()

        insertar.put("apodo", apodo)
        insertar.put("punto", 0)
        db?.insert("Partida", null, insertar)

        fb.collection("Partida").document(apodo).set(hashMapOf("Punto" to 0))

    }//insertarPartida(apodo: String)

    fun cargarPartida(apodo: String): Partida? {

        val db: SQLiteDatabase = this.readableDatabase
        val cursor: Cursor = db.rawQuery("select * from partida where apodo = ?", arrayOf(apodo))

        return if (cursor.moveToFirst()) {

            Partida(cursor.getString(0), cursor.getString(1))

        } else {

            null

        }

    }//cargarPartida(apodo: String): Partida

    fun actualizarPartida(apodo: String, punto: String) {

        val db: SQLiteDatabase = this.writableDatabase
        val actualizar: ContentValues = ContentValues()

        actualizar.put("apodo", apodo)
        actualizar.put("punto", punto)

        db?.update("Partida", actualizar, "apodo = ?", arrayOf(apodo))

        fb.collection("Partida").document(apodo).set(hashMapOf("Punto" to punto))

    }//actualizarPartida(apodo: String, punto: String)

    fun cargarPartidas(): ArrayList<Partida> {

        val lista: ArrayList<Partida> = ArrayList()
        val db: SQLiteDatabase = this.readableDatabase
        val cursor: Cursor = db.rawQuery("select * from Partida order by apodo", null)

        while (cursor.moveToNext()) {

            lista.add(Partida(cursor.getString(0), cursor.getString(1)))

        }//has next

        return lista

    }//cargarPartida(): ArrayList<Partida>

    fun limpiarRanking() {

        val db: SQLiteDatabase = this.writableDatabase

        db.delete("Ranking", "", null)

    }//limpiarRanking()

    fun actualizarRanking(lista: ArrayList<Partida>) {

        val db: SQLiteDatabase = this.writableDatabase
        val actualizar: ContentValues = ContentValues()

        lista.forEach() {

            actualizar.put("apodo", it.apodo)
            actualizar.put("punto", it.punto)
            db?.insert("Ranking", null, actualizar)

        }//lista.forEach()

    }//actualizarRanking(lista: ArrayList<Partida>)

    fun cargarRanking(): ArrayList<Partida> {

        val lista: ArrayList<Partida> = ArrayList()
        val db: SQLiteDatabase = this.readableDatabase
        val cursor: Cursor = db.rawQuery("select * from Ranking order by punto desc", null)

        while (cursor.moveToNext()) {

            lista.add(Partida(cursor.getString(0), cursor.getString(1)))

        }//has next

        return lista

    }//cargarRanking(): ArrayList<Partida>

}//DB

