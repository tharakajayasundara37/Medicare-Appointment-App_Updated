package com.example.medicareappointment.database

import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import com.example.medicareappointment.model.Appointment
import com.example.medicareappointment.model.Patient

class DatabaseHelper(context: Context) :
    SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION) {

    companion object {
        private const val DATABASE_NAME = "medicare.db"
        private const val DATABASE_VERSION = 3

        const val TABLE_PATIENT = "patients"
        const val COL_PATIENT_ID = "id"
        const val COL_PATIENT_NAME = "name"
        const val COL_PATIENT_AGE = "age"
        const val COL_PATIENT_PHONE = "phone"
        const val COL_PATIENT_ADDRESS = "address"

        const val TABLE_APPOINTMENT = "appointments"
        const val COL_APPOINTMENT_ID = "id"
        const val COL_APPOINTMENT_PATIENT = "patient_name"
        const val COL_APPOINTMENT_PHONE = "patient_phone"
        const val COL_APPOINTMENT_DOCTOR = "doctor_name"
        const val COL_APPOINTMENT_DATE = "appointment_date"
        const val COL_APPOINTMENT_TIME = "appointment_time"
    }

    override fun onCreate(db: SQLiteDatabase) {
        db.execSQL("""
            CREATE TABLE $TABLE_PATIENT (
                $COL_PATIENT_ID INTEGER PRIMARY KEY AUTOINCREMENT,
                $COL_PATIENT_NAME TEXT NOT NULL,
                $COL_PATIENT_AGE INTEGER NOT NULL,
                $COL_PATIENT_PHONE TEXT NOT NULL,
                $COL_PATIENT_ADDRESS TEXT NOT NULL
            )
        """.trimIndent())

        db.execSQL("""
            CREATE TABLE $TABLE_APPOINTMENT (
                $COL_APPOINTMENT_ID INTEGER PRIMARY KEY AUTOINCREMENT,
                $COL_APPOINTMENT_PATIENT TEXT NOT NULL,
                $COL_APPOINTMENT_PHONE TEXT NOT NULL,
                $COL_APPOINTMENT_DOCTOR TEXT NOT NULL,
                $COL_APPOINTMENT_DATE TEXT NOT NULL,
                $COL_APPOINTMENT_TIME TEXT NOT NULL
            )
        """.trimIndent())
    }

    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        if (oldVersion < 2) {
            db.execSQL("""
                CREATE TABLE IF NOT EXISTS $TABLE_APPOINTMENT (
                    $COL_APPOINTMENT_ID INTEGER PRIMARY KEY AUTOINCREMENT,
                    $COL_APPOINTMENT_PATIENT TEXT NOT NULL,
                    $COL_APPOINTMENT_DOCTOR TEXT NOT NULL,
                    $COL_APPOINTMENT_DATE TEXT NOT NULL,
                    $COL_APPOINTMENT_TIME TEXT NOT NULL
                )
            """.trimIndent())
        }

        if (oldVersion < 3) {
            db.execSQL(
                "ALTER TABLE $TABLE_APPOINTMENT ADD COLUMN $COL_APPOINTMENT_PHONE TEXT NOT NULL DEFAULT ''"
            )
        }
    }

    fun insertPatient(name: String, age: Int, phone: String, address: String): Long {
        val db = writableDatabase
        val values = ContentValues().apply {
            put(COL_PATIENT_NAME, name)
            put(COL_PATIENT_AGE, age)
            put(COL_PATIENT_PHONE, phone)
            put(COL_PATIENT_ADDRESS, address)
        }
        return db.insert(TABLE_PATIENT, null, values)
    }

    fun getAllPatients(): ArrayList<Patient> {
        val patientList = ArrayList<Patient>()
        val db = readableDatabase
        val cursor: Cursor = db.rawQuery("SELECT * FROM $TABLE_PATIENT", null)

        if (cursor.moveToFirst()) {
            do {
                patientList.add(
                    Patient(
                        cursor.getInt(0),
                        cursor.getString(1),
                        cursor.getInt(2),
                        cursor.getString(3),
                        cursor.getString(4)
                    )
                )
            } while (cursor.moveToNext())
        }

        cursor.close()
        return patientList
    }

    fun updatePatient(id: Int, name: String, age: Int, phone: String, address: String): Int {
        val db = writableDatabase
        val values = ContentValues().apply {
            put(COL_PATIENT_NAME, name)
            put(COL_PATIENT_AGE, age)
            put(COL_PATIENT_PHONE, phone)
            put(COL_PATIENT_ADDRESS, address)
        }

        return db.update(
            TABLE_PATIENT,
            values,
            "$COL_PATIENT_ID = ?",
            arrayOf(id.toString())
        )
    }

    fun deletePatient(id: Int): Int {
        val db = writableDatabase
        return db.delete(TABLE_PATIENT, "$COL_PATIENT_ID = ?", arrayOf(id.toString()))
    }

    fun insertAppointment(
        patientName: String,
        patientPhone: String,
        doctorName: String,
        date: String,
        time: String
    ): Long {
        val db = writableDatabase
        val values = ContentValues().apply {
            put(COL_APPOINTMENT_PATIENT, patientName)
            put(COL_APPOINTMENT_PHONE, patientPhone)
            put(COL_APPOINTMENT_DOCTOR, doctorName)
            put(COL_APPOINTMENT_DATE, date)
            put(COL_APPOINTMENT_TIME, time)
        }
        return db.insert(TABLE_APPOINTMENT, null, values)
    }

    fun getAllAppointments(): ArrayList<Appointment> {
        val appointmentList = ArrayList<Appointment>()
        val db = readableDatabase
        val cursor: Cursor = db.rawQuery("SELECT * FROM $TABLE_APPOINTMENT", null)

        if (cursor.moveToFirst()) {
            do {
                appointmentList.add(
                    Appointment(
                        cursor.getInt(0),
                        cursor.getString(1),
                        cursor.getString(2),
                        cursor.getString(3),
                        cursor.getString(4),
                        cursor.getString(5)
                    )
                )
            } while (cursor.moveToNext())
        }

        cursor.close()
        return appointmentList
    }

    fun updateAppointment(
        id: Int,
        patientName: String,
        patientPhone: String,
        doctorName: String,
        date: String,
        time: String
    ): Int {
        val db = writableDatabase
        val values = ContentValues().apply {
            put(COL_APPOINTMENT_PATIENT, patientName)
            put(COL_APPOINTMENT_PHONE, patientPhone)
            put(COL_APPOINTMENT_DOCTOR, doctorName)
            put(COL_APPOINTMENT_DATE, date)
            put(COL_APPOINTMENT_TIME, time)
        }

        return db.update(
            TABLE_APPOINTMENT,
            values,
            "$COL_APPOINTMENT_ID = ?",
            arrayOf(id.toString())
        )
    }

    fun deleteAppointment(id: Int): Int {
        val db = writableDatabase
        return db.delete(TABLE_APPOINTMENT, "$COL_APPOINTMENT_ID = ?", arrayOf(id.toString()))
    }

    fun getPatientCount(): Int {
        val db = readableDatabase
        val cursor = db.rawQuery("SELECT COUNT(*) FROM $TABLE_PATIENT", null)

        var count = 0
        if (cursor.moveToFirst()) {
            count = cursor.getInt(0)
        }

        cursor.close()
        return count
    }

    fun getAppointmentCount(): Int {
        val db = readableDatabase
        val cursor = db.rawQuery("SELECT COUNT(*) FROM $TABLE_APPOINTMENT", null)

        var count = 0
        if (cursor.moveToFirst()) {
            count = cursor.getInt(0)
        }

        cursor.close()
        return count
    }

    fun getRecentPatients(): ArrayList<Patient> {
        val patientList = ArrayList<Patient>()
        val db = readableDatabase

        val cursor = db.rawQuery(
            "SELECT * FROM $TABLE_PATIENT ORDER BY $COL_PATIENT_ID DESC LIMIT 2",
            null
        )

        if (cursor.moveToFirst()) {
            do {
                patientList.add(
                    Patient(
                        cursor.getInt(0),
                        cursor.getString(1),
                        cursor.getInt(2),
                        cursor.getString(3),
                        cursor.getString(4)
                    )
                )
            } while (cursor.moveToNext())
        }

        cursor.close()
        return patientList
    }

    fun getRecentAppointments(): ArrayList<Appointment> {
        val appointmentList = ArrayList<Appointment>()
        val db = readableDatabase

        val cursor = db.rawQuery(
            "SELECT * FROM $TABLE_APPOINTMENT ORDER BY $COL_APPOINTMENT_ID DESC LIMIT 2",
            null
        )

        if (cursor.moveToFirst()) {
            do {
                appointmentList.add(
                    Appointment(
                        cursor.getInt(0),
                        cursor.getString(1),
                        cursor.getString(2),
                        cursor.getString(3),
                        cursor.getString(4),
                        cursor.getString(5)
                    )
                )
            } while (cursor.moveToNext())
        }

        cursor.close()
        return appointmentList
    }
}
