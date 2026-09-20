package com.example.medicareappointment.database


import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import com.example.medicareappointment.model.Appointment
import com.example.medicareappointment.model.Patient
import com.example.medicareappointment.model.Doctor
import com.example.medicareappointment.model.Admin


class DatabaseHelper(context: Context) :
    SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION) {


    companion object {


        private const val DATABASE_NAME = "medicare.db"

        private const val DATABASE_VERSION = 12
        const val TABLE_PATIENT = "patients"
        const val COL_PATIENT_ID = "id"

        const val COL_PATIENT_CODE = "patient_code"

        const val COL_PATIENT_NAME = "name"

        const val COL_PATIENT_AGE = "age"

        const val COL_PATIENT_PHONE = "phone"

        const val COL_PATIENT_ADDRESS = "address"

        const val COL_PATIENT_EMAIL = "email"

        const val COL_PATIENT_PASSWORD = "password"

        const val COL_PATIENT_PROFILE_IMAGE = "profile_image"

        const val COL_PATIENT_CREATED_DATE = "created_date"



        const val TABLE_APPOINTMENT = "appointments"

        const val COL_APPOINTMENT_ID = "id"

        const val COL_APPOINTMENT_PATIENT_ID = "patient_id"

        const val COL_APPOINTMENT_DOCTOR_ID = "doctor_id"

        const val COL_APPOINTMENT_DOCTOR = "doctor_name"

        const val COL_APPOINTMENT_DATE = "appointment_date"

        const val COL_APPOINTMENT_TIME = "appointment_time"

        const val COL_APPOINTMENT_STATUS = "appointment_status"

        const val COL_APPOINTMENT_CREATED_DATE = "created_date"


        const val TABLE_DOCTOR = "doctors"

        const val COL_DOCTOR_ID = "id"

        const val COL_DOCTOR_NAME = "name"

        const val COL_DOCTOR_SPECIALIZATION = "specialization"

        const val COL_DOCTOR_PHONE = "phone"

        const val COL_DOCTOR_EMAIL = "email"

        const val COL_DOCTOR_PASSWORD = "password"

        const val COL_DOCTOR_QUALIFICATION = "qualification"

        const val COL_DOCTOR_REG_NO = "registration_number"

        const val COL_DOCTOR_EXPERIENCE = "experience"

        const val COL_DOCTOR_DOCUMENT = "document"

        const val COL_DOCTOR_PROFILE_IMAGE = "profile_image"
        const val COL_DOCTOR_WORKING_DAYS = "working_days"

        const val COL_DOCTOR_START_TIME = "start_time"

        const val COL_DOCTOR_END_TIME = "end_time"

        const val COL_DOCTOR_STATUS = "status"

        const val COL_DOCTOR_APPOINTMENT_DURATION = "appointment_duration"

    }


    override fun onCreate(db: SQLiteDatabase) {


        db.execSQL(
            """
        CREATE TABLE $TABLE_PATIENT(
    
            $COL_PATIENT_ID INTEGER PRIMARY KEY AUTOINCREMENT,
    
            $COL_PATIENT_CODE TEXT UNIQUE,
    
            $COL_PATIENT_NAME TEXT NOT NULL,
    
            $COL_PATIENT_AGE INTEGER NOT NULL,
    
            $COL_PATIENT_PHONE TEXT NOT NULL,
    
            $COL_PATIENT_ADDRESS TEXT NOT NULL,
    
            $COL_PATIENT_EMAIL TEXT UNIQUE,
    
            $COL_PATIENT_PASSWORD TEXT,
    
            $COL_PATIENT_PROFILE_IMAGE TEXT,
    
            $COL_PATIENT_CREATED_DATE TEXT
    
            )
            """.trimIndent()
        )
        db.execSQL(
            """
        CREATE TABLE IF NOT EXISTS admins(
    
            id INTEGER PRIMARY KEY AUTOINCREMENT,
    
            name TEXT,
    
            email TEXT UNIQUE,
    
            phone TEXT,
    
            password TEXT,
    
            profile_image TEXT,
    
            role TEXT,
    
            created_date TEXT
    
        )
    """.trimIndent()
        )
        db.execSQL(
            """
    CREATE TABLE $TABLE_APPOINTMENT(

        $COL_APPOINTMENT_ID INTEGER PRIMARY KEY AUTOINCREMENT,

        $COL_APPOINTMENT_PATIENT_ID INTEGER NOT NULL,

        $COL_APPOINTMENT_DOCTOR_ID INTEGER,

        $COL_APPOINTMENT_DOCTOR TEXT NOT NULL,

        $COL_APPOINTMENT_DATE TEXT NOT NULL,

        $COL_APPOINTMENT_TIME TEXT NOT NULL,

        $COL_APPOINTMENT_STATUS TEXT NOT NULL DEFAULT 'Pending',

        $COL_APPOINTMENT_CREATED_DATE TEXT

    )
    """.trimIndent()
        )

        db.execSQL(
            """
            CREATE TABLE $TABLE_DOCTOR(

                $COL_DOCTOR_ID INTEGER PRIMARY KEY AUTOINCREMENT,

                $COL_DOCTOR_NAME TEXT NOT NULL,

                $COL_DOCTOR_SPECIALIZATION TEXT NOT NULL,

                $COL_DOCTOR_PHONE TEXT NOT NULL,

                $COL_DOCTOR_EMAIL TEXT NOT NULL,

                $COL_DOCTOR_PASSWORD TEXT NOT NULL,

                $COL_DOCTOR_QUALIFICATION TEXT,

                $COL_DOCTOR_REG_NO TEXT,

                $COL_DOCTOR_EXPERIENCE TEXT,

                $COL_DOCTOR_DOCUMENT TEXT,
                
                $COL_DOCTOR_PROFILE_IMAGE TEXT,


                $COL_DOCTOR_WORKING_DAYS TEXT,

                $COL_DOCTOR_START_TIME TEXT,

                $COL_DOCTOR_END_TIME TEXT,
                
                $COL_DOCTOR_APPOINTMENT_DURATION INTEGER DEFAULT 30,


                $COL_DOCTOR_STATUS TEXT DEFAULT 'Pending'

            )
            """.trimIndent()
        )
        insertDefaultAdmin(db)

    }

    override fun onUpgrade(
        db: SQLiteDatabase,
        oldVersion: Int,
        newVersion: Int
    ) {


        if (oldVersion < 4) {

            db.execSQL(
                """
                CREATE TABLE IF NOT EXISTS $TABLE_DOCTOR(

                    $COL_DOCTOR_ID INTEGER PRIMARY KEY AUTOINCREMENT,

                    $COL_DOCTOR_NAME TEXT NOT NULL,

                    $COL_DOCTOR_SPECIALIZATION TEXT NOT NULL,

                    $COL_DOCTOR_PHONE TEXT NOT NULL,

                    $COL_DOCTOR_EMAIL TEXT NOT NULL

                )
                """.trimIndent()
            )

        }
        if (oldVersion < 5) {

            db.execSQL(
                """
                ALTER TABLE $TABLE_APPOINTMENT
                ADD COLUMN $COL_APPOINTMENT_STATUS TEXT NOT NULL DEFAULT 'Pending'
                """.trimIndent()
            )
        }

        if (oldVersion < 6) {

            db.execSQL(
                "ALTER TABLE $TABLE_DOCTOR ADD COLUMN password TEXT"
            )

            db.execSQL(
                "ALTER TABLE $TABLE_DOCTOR ADD COLUMN qualification TEXT"
            )

            db.execSQL(
                "ALTER TABLE $TABLE_DOCTOR ADD COLUMN registration_number TEXT"
            )

            db.execSQL(
                "ALTER TABLE $TABLE_DOCTOR ADD COLUMN experience TEXT"
            )

            db.execSQL(
                "ALTER TABLE $TABLE_DOCTOR ADD COLUMN document TEXT"
            )

            db.execSQL(
                "ALTER TABLE $TABLE_DOCTOR ADD COLUMN working_days TEXT"
            )

            db.execSQL(
                "ALTER TABLE $TABLE_DOCTOR ADD COLUMN start_time TEXT"
            )

            db.execSQL(
                "ALTER TABLE $TABLE_DOCTOR ADD COLUMN end_time TEXT"
            )

            db.execSQL(
                "ALTER TABLE $TABLE_DOCTOR ADD COLUMN status TEXT DEFAULT 'Pending'"
            )

        }
        if (oldVersion < 9) {


            db.execSQL(

                """
        ALTER TABLE $TABLE_PATIENT
        ADD COLUMN $COL_PATIENT_CODE TEXT
        """.trimIndent()

            )


            db.execSQL(

                """
        ALTER TABLE $TABLE_PATIENT
        ADD COLUMN $COL_PATIENT_EMAIL TEXT
        """.trimIndent()

            )


            db.execSQL(

                """
        ALTER TABLE $TABLE_PATIENT
        ADD COLUMN $COL_PATIENT_PASSWORD TEXT
        """.trimIndent()

            )


            db.execSQL(

                """
        ALTER TABLE $TABLE_PATIENT
        ADD COLUMN $COL_PATIENT_PROFILE_IMAGE TEXT
        """.trimIndent()

            )


            db.execSQL(

                """
        ALTER TABLE $TABLE_PATIENT
        ADD COLUMN $COL_PATIENT_CREATED_DATE TEXT
        """.trimIndent()

            )

        }
        if (oldVersion < 7) {

            db.execSQL(
                """
        CREATE TABLE IF NOT EXISTS admins(

            id INTEGER PRIMARY KEY AUTOINCREMENT,

            name TEXT,

            email TEXT UNIQUE,

            phone TEXT,

            password TEXT,

            profile_image TEXT,

            role TEXT,

            created_date TEXT

        )
        """.trimIndent()
            )
            insertDefaultAdmin(db)
        }
        if (oldVersion < 10) {

            db.execSQL(
                "DROP TABLE IF EXISTS $TABLE_APPOINTMENT"
            )


            db.execSQL(
                """
        CREATE TABLE $TABLE_APPOINTMENT(

            $COL_APPOINTMENT_ID INTEGER PRIMARY KEY AUTOINCREMENT,

            $COL_APPOINTMENT_PATIENT_ID INTEGER NOT NULL,

            $COL_APPOINTMENT_DOCTOR_ID INTEGER,

            $COL_APPOINTMENT_DOCTOR TEXT NOT NULL,

            $COL_APPOINTMENT_DATE TEXT NOT NULL,

            $COL_APPOINTMENT_TIME TEXT NOT NULL,

            $COL_APPOINTMENT_STATUS TEXT NOT NULL DEFAULT 'Pending',

            $COL_APPOINTMENT_CREATED_DATE TEXT

        )
        """.trimIndent()
            )
        }

        if (oldVersion < 11) {

            db.execSQL(

                """
        ALTER TABLE $TABLE_DOCTOR
        ADD COLUMN $COL_DOCTOR_APPOINTMENT_DURATION INTEGER DEFAULT 30
        """.trimIndent()

            )

        }
        if (oldVersion < 12) {


            db.execSQL(

                """
        ALTER TABLE $TABLE_DOCTOR
        ADD COLUMN $COL_DOCTOR_PROFILE_IMAGE TEXT DEFAULT ''
        """.trimIndent()

            )


        }

        if (oldVersion < 8) {

            db.execSQL(
                """
        CREATE TABLE IF NOT EXISTS admins(

            id INTEGER PRIMARY KEY AUTOINCREMENT,
            name TEXT,
            email TEXT UNIQUE,
            phone TEXT,
            password TEXT,
            profile_image TEXT,
            role TEXT,
            created_date TEXT

        )
        """.trimIndent()
            )

            insertDefaultAdmin(db)

        }

    }

    fun insertDoctor(

        name: String,

        specialization: String,

        phone: String,

        email: String

    ): Long {


        val db = writableDatabase


        val values = ContentValues().apply {

            put(COL_DOCTOR_NAME, name)

            put(COL_DOCTOR_SPECIALIZATION, specialization)

            put(COL_DOCTOR_PHONE, phone)

            put(COL_DOCTOR_EMAIL, email)

        }


        return db.insert(

            TABLE_DOCTOR,

            null,

            values

        )

    }
// ============================================================
// ADMIN - ADD PATIENT
// Email / Password නැතිව patient add කරන්න
// ============================================================

    fun insertPatient(
        name: String,
        age: Int,
        phone: String,
        address: String
    ): Long {

        val db = writableDatabase

        val createdDate =
            java.text.SimpleDateFormat(
                "yyyy-MM-dd",
                java.util.Locale.getDefault()
            ).format(java.util.Date())

        val values = ContentValues().apply {

            put(COL_PATIENT_NAME, name)
            put(COL_PATIENT_AGE, age)
            put(COL_PATIENT_PHONE, phone)
            put(COL_PATIENT_ADDRESS, address)

            putNull(COL_PATIENT_CODE)
            putNull(COL_PATIENT_EMAIL)
            putNull(COL_PATIENT_PASSWORD)

            put(COL_PATIENT_PROFILE_IMAGE, "")
            put(COL_PATIENT_CREATED_DATE, createdDate)
        }

        val patientId = db.insert(
            TABLE_PATIENT,
            null,
            values
        )

        if (patientId > 0) {

            val patientCode =
                String.format(
                    "PAT-%06d",
                    patientId
                )

            val codeValues = ContentValues().apply {
                put(COL_PATIENT_CODE, patientCode)
            }

            db.update(
                TABLE_PATIENT,
                codeValues,
                "$COL_PATIENT_ID = ?",
                arrayOf(patientId.toString())
            )
        }

        return patientId
    }


// ============================================================
// PATIENT SELF REGISTRATION
// Email + Password එක්ක account create කරන්න
// ============================================================

    fun insertPatient(
        name: String,
        age: Int,
        phone: String,
        address: String,
        email: String,
        password: String
    ): Long {

        val db = writableDatabase

        val createdDate =
            java.text.SimpleDateFormat(
                "yyyy-MM-dd",
                java.util.Locale.getDefault()
            ).format(java.util.Date())

        val values = ContentValues().apply {

            put(COL_PATIENT_NAME, name)
            put(COL_PATIENT_AGE, age)
            put(COL_PATIENT_PHONE, phone)
            put(COL_PATIENT_ADDRESS, address)

            put(COL_PATIENT_EMAIL, email)
            put(COL_PATIENT_PASSWORD, password)

            putNull(COL_PATIENT_CODE)

            put(COL_PATIENT_PROFILE_IMAGE, "")
            put(COL_PATIENT_CREATED_DATE, createdDate)
        }

        val patientId = db.insert(
            TABLE_PATIENT,
            null,
            values
        )

        if (patientId > 0) {

            val patientCode =
                String.format(
                    "PAT-%06d",
                    patientId
                )

            val codeValues = ContentValues().apply {
                put(COL_PATIENT_CODE, patientCode)
            }

            db.update(
                TABLE_PATIENT,
                codeValues,
                "$COL_PATIENT_ID = ?",
                arrayOf(patientId.toString())
            )
        }

        return patientId
    }
    fun insertDoctorApplication(

        name: String,

        specialization: String,

        phone: String,

        email: String,

        password: String,

        qualification: String,

        registrationNumber: String,

        experience: String,

        document: String,

        profileImage: String,

        workingDays: String,

        startTime: String,

        endTime: String,

        appointmentDuration: Int

    ): Long {


        val db = writableDatabase


        val values = ContentValues().apply {


            put(
                COL_DOCTOR_NAME,
                name
            )


            put(
                COL_DOCTOR_SPECIALIZATION,
                specialization
            )


            put(
                COL_DOCTOR_PHONE,
                phone
            )


            put(
                COL_DOCTOR_EMAIL,
                email
            )


            put(
                COL_DOCTOR_PASSWORD,
                password
            )


            put(
                COL_DOCTOR_QUALIFICATION,
                qualification
            )


            put(
                COL_DOCTOR_REG_NO,
                registrationNumber
            )


            put(
                COL_DOCTOR_EXPERIENCE,
                experience
            )


            put(
                COL_DOCTOR_DOCUMENT,
                document
            )

            put(
                COL_DOCTOR_PROFILE_IMAGE,
                profileImage
            )


            put(
                COL_DOCTOR_WORKING_DAYS,
                workingDays
            )


            put(
                COL_DOCTOR_START_TIME,
                startTime
            )


            put(
                COL_DOCTOR_END_TIME,
                endTime
            )


            // NEW
            put(
                COL_DOCTOR_APPOINTMENT_DURATION,
                appointmentDuration
            )


            put(
                COL_DOCTOR_STATUS,
                "Pending"
            )

        }


        return db.insert(

            TABLE_DOCTOR,

            null,

            values

        )

    }


    fun insertAppointment(

        patientId: Int,

        doctorId: Int,

        doctorName: String,

        date: String,

        time: String

    ): Long {


        val db = writableDatabase


        val values = ContentValues().apply {


            put(
                COL_APPOINTMENT_PATIENT_ID,
                patientId
            )


            put(
                COL_APPOINTMENT_DOCTOR_ID,
                doctorId
            )


            put(
                COL_APPOINTMENT_DOCTOR,
                doctorName
            )


            put(
                COL_APPOINTMENT_DATE,
                date
            )


            put(
                COL_APPOINTMENT_TIME,
                time
            )


            put(
                COL_APPOINTMENT_STATUS,
                "Pending"
            )


            put(
                COL_APPOINTMENT_CREATED_DATE,
                System.currentTimeMillis().toString()
            )

        }


        return db.insert(
            TABLE_APPOINTMENT,
            null,
            values
        )
    }

    fun updatePatient(

        id: Int,

        name: String,

        age: Int,

        phone: String,

        address: String

    ): Int {


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

            "$COL_PATIENT_ID=?",

            arrayOf(id.toString())

        )

    }

    fun updateAppointmentStatus(

        appointmentId: Int,

        status: String

    ): Boolean {


        val db = writableDatabase



        val values = ContentValues()



        values.put(
            COL_APPOINTMENT_STATUS,
            status
        )



        val result = db.update(

            TABLE_APPOINTMENT,

            values,

            "id=?",

            arrayOf(

                appointmentId.toString()

            )

        )



        return result > 0

    }


    fun deleteAppointment(id: Int): Int {


        val db = writableDatabase


        return db.delete(

            TABLE_APPOINTMENT,

            "$COL_APPOINTMENT_ID=?",

            arrayOf(id.toString())

        )

    }


    fun deletePatient(id: Int): Int {


        val db = writableDatabase


        return db.delete(

            TABLE_PATIENT,

            "$COL_PATIENT_ID=?",

            arrayOf(id.toString())
        )
    }

    fun getAllDoctors(): ArrayList<Doctor> {


        val doctorList = ArrayList<Doctor>()


        val db = readableDatabase


        val cursor = db.rawQuery(

            "SELECT * FROM $TABLE_DOCTOR",

            null

        )


        if (cursor.moveToFirst()) {
            do {
                doctorList.add(
                    Doctor(
                        id = cursor.getInt(0),

                        name = cursor.getString(1) ?: "",

                        specialization = cursor.getString(2) ?: "",

                        phone = cursor.getString(3) ?: "",

                        email = cursor.getString(4) ?: "",

                        password = cursor.getString(5) ?: "",

                        qualification = cursor.getString(6) ?: "",

                        registrationNumber = cursor.getString(7) ?: "",

                        experience = cursor.getString(8) ?: "",

                        document = cursor.getString(9) ?: "",

                        profileImage = cursor.getString(10) ?: "",

                        workingDays = cursor.getString(11) ?: "",

                        startTime = cursor.getString(12) ?: "",

                        endTime = cursor.getString(13) ?: "",

                        appointmentDuration = cursor.getInt(14),

                        status = cursor.getString(15) ?: "Pending"
                    )
                )
            } while (cursor.moveToNext())
        }
        cursor.close()
        return doctorList

    }

    fun getAllPatients(): ArrayList<Patient> {


        val patientList = ArrayList<Patient>()


        val db = readableDatabase


        val cursor = db.rawQuery(

            "SELECT * FROM $TABLE_PATIENT",

            null

        )


        if (cursor.moveToFirst()) {


            do {


                patientList.add(

                    Patient(

                        id = cursor.getInt(
                            cursor.getColumnIndexOrThrow(
                                COL_PATIENT_ID
                            )
                        ),

                        patientCode = cursor.getString(
                            cursor.getColumnIndexOrThrow(
                                COL_PATIENT_CODE
                            )
                        ) ?: "",

                        name = cursor.getString(
                            cursor.getColumnIndexOrThrow(
                                COL_PATIENT_NAME
                            )
                        ) ?: "",

                        age = cursor.getInt(
                            cursor.getColumnIndexOrThrow(
                                COL_PATIENT_AGE
                            )
                        ),

                        phone = cursor.getString(
                            cursor.getColumnIndexOrThrow(
                                COL_PATIENT_PHONE
                            )
                        ) ?: "",

                        address = cursor.getString(
                            cursor.getColumnIndexOrThrow(
                                COL_PATIENT_ADDRESS
                            )
                        ) ?: "",

                        email = cursor.getString(
                            cursor.getColumnIndexOrThrow(
                                COL_PATIENT_EMAIL
                            )
                        ) ?: "",

                        password = cursor.getString(
                            cursor.getColumnIndexOrThrow(
                                COL_PATIENT_PASSWORD
                            )
                        ) ?: "",

                        profileImage = cursor.getString(
                            cursor.getColumnIndexOrThrow(
                                COL_PATIENT_PROFILE_IMAGE
                            )
                        ) ?: "",

                        createdDate = cursor.getString(
                            cursor.getColumnIndexOrThrow(
                                COL_PATIENT_CREATED_DATE
                            )
                        ) ?: ""

                    )

                )


            } while (cursor.moveToNext())


        }


        cursor.close()


        return patientList

    }
    fun getAllAppointments(): ArrayList<Appointment> {


        val appointmentList = ArrayList<Appointment>()


        val db = readableDatabase


        val cursor = db.rawQuery(

            "SELECT * FROM $TABLE_APPOINTMENT",

            null

        )


        if (cursor.moveToFirst()) {


            do {


                appointmentList.add(

                    Appointment(

                        id = cursor.getInt(0),

                        patientName = cursor.getString(1)
                            ?: "",

                        patientPhone = "",

                        doctorName = cursor.getString(3)
                            ?: "",

                        appointmentDate = cursor.getString(4)
                            ?: "",

                        appointmentTime = cursor.getString(5)
                            ?: "",

                        appointmentStatus = cursor.getString(6) ?: "Pending"

                    )

                )


            } while (cursor.moveToNext())


        }


        cursor.close()


        return appointmentList

    }


    fun getAppointmentCount(): Int {


        val db = readableDatabase


        val cursor = db.rawQuery(

            "SELECT COUNT(*) FROM $TABLE_APPOINTMENT",

            null

        )


        var count = 0


        if (cursor.moveToFirst()) {

            count = cursor.getInt(0)

        }


        cursor.close()


        return count

    }


    fun getDoctorCount(): Int {


        val db = readableDatabase


        val cursor = db.rawQuery(

            "SELECT COUNT(*) FROM $TABLE_DOCTOR",

            null

        )


        var count = 0


        if (cursor.moveToFirst()) {

            count = cursor.getInt(0)

        }


        cursor.close()


        return count

    }


    fun getPatientCount(): Int {


        val db = readableDatabase


        val cursor = db.rawQuery(

            "SELECT COUNT(*) FROM $TABLE_PATIENT",

            null

        )


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

            """
            SELECT * FROM $TABLE_PATIENT
            ORDER BY $COL_PATIENT_ID DESC
            LIMIT 5
            """.trimIndent(),

            null

        )


        if (cursor.moveToFirst()) {


            do {


                patientList.add(

                    Patient(

                        id = cursor.getInt(0),

                        patientCode = cursor.getString(1) ?: "",

                        name = cursor.getString(2) ?: "",

                        age = cursor.getInt(3),

                        phone = cursor.getString(4) ?: "",

                        address = cursor.getString(5) ?: "",

                        email = cursor.getString(6) ?: "",

                        password = cursor.getString(7) ?: "",

                        profileImage = cursor.getString(8) ?: "",

                        createdDate = cursor.getString(9) ?: ""

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

            """
            SELECT * FROM $TABLE_APPOINTMENT
            ORDER BY $COL_APPOINTMENT_ID DESC
            LIMIT 5
            """.trimIndent(),

            null

        )


        if (cursor.moveToFirst()) {


            do {


                appointmentList.add(

                    Appointment(

                        id = cursor.getInt(0),

                        patientName = cursor.getString(1),

                        patientPhone = cursor.getString(2),

                        doctorName = cursor.getString(3),

                        appointmentDate = cursor.getString(4),

                        appointmentTime = cursor.getString(5),

                        appointmentStatus = cursor.getString(6) ?: "Pending"
                    )

                )


            } while (cursor.moveToNext())

        }


        cursor.close()


        return appointmentList

    }


    fun getAppointmentStatusCount(status: String): Int {


        val db = readableDatabase


        val cursor = db.rawQuery(

            """
            SELECT COUNT(*)
            FROM $TABLE_APPOINTMENT
            WHERE $COL_APPOINTMENT_STATUS=?
            """.trimIndent(),

            arrayOf(status)

        )


        var count = 0


        if (cursor.moveToFirst()) {

            count = cursor.getInt(0)

        }


        cursor.close()


        return count

    }

    fun updateAppointment(

        id: Int,

        patientId: Int,

        doctorId: Int,

        doctorName: String,

        date: String,

        time: String,

        status: String = "Pending"

    ): Int {


        val db = writableDatabase


        val values = ContentValues().apply {


            put(
                COL_APPOINTMENT_PATIENT_ID,
                patientId
            )


            put(
                COL_APPOINTMENT_DOCTOR_ID,
                doctorId
            )


            put(
                COL_APPOINTMENT_DOCTOR,
                doctorName
            )


            put(
                COL_APPOINTMENT_DATE,
                date
            )


            put(
                COL_APPOINTMENT_TIME,
                time
            )


            put(
                COL_APPOINTMENT_STATUS,
                status
            )

        }


        return db.update(

            TABLE_APPOINTMENT,

            values,

            "$COL_APPOINTMENT_ID=?",

            arrayOf(id.toString())

        )

    }

    fun updateDoctorStatus(

        id: Int,

        status: String

    ): Int {


        val db = writableDatabase


        val values = ContentValues().apply {

            put(
                COL_DOCTOR_STATUS,
                status
            )

        }


        return db.update(

            TABLE_DOCTOR,

            values,

            "$COL_DOCTOR_ID=?",

            arrayOf(id.toString())

        )

    }
    fun getPatientAppointmentCount(
        patientId: Int
    ): Int {


        val db = readableDatabase


        val cursor = db.rawQuery(

            """
        SELECT COUNT(*)
        FROM $TABLE_APPOINTMENT
        WHERE $COL_APPOINTMENT_PATIENT_ID = ?
        """.trimIndent(),

            arrayOf(
                patientId.toString()
            )

        )


        var count = 0


        if(cursor.moveToFirst()){

            count = cursor.getInt(0)

        }


        cursor.close()


        return count

    }
    fun getUpcomingAppointmentCount(
        patientId: Int
    ): Int {


        val db = readableDatabase


        val cursor = db.rawQuery(

            """
        SELECT COUNT(*)
        FROM $TABLE_APPOINTMENT
        WHERE $COL_APPOINTMENT_PATIENT_ID = ?
        AND $COL_APPOINTMENT_STATUS != 'Completed'
        AND $COL_APPOINTMENT_STATUS != 'Cancelled'
        """.trimIndent(),

            arrayOf(
                patientId.toString()
            )

        )


        var count = 0


        if(cursor.moveToFirst()){

            count = cursor.getInt(0)

        }


        cursor.close()


        return count

    }
    fun isAppointmentAvailable(
        doctorId: Int,
        date: String,
        time: String
    ): Boolean {


        val db = readableDatabase


        val cursor = db.rawQuery(

            """
        SELECT COUNT(*)
        FROM $TABLE_APPOINTMENT
        WHERE $COL_APPOINTMENT_DOCTOR_ID = ?
        AND $COL_APPOINTMENT_DATE = ?
        AND $COL_APPOINTMENT_TIME = ?
        AND $COL_APPOINTMENT_STATUS != 'Cancelled'
        """.trimIndent(),

            arrayOf(

                doctorId.toString(),

                date,

                time

            )

        )



        var count = 0



        if(cursor.moveToFirst()){

            count = cursor.getInt(0)

        }



        cursor.close()



        return count == 0

    }
    fun getPatientAppointments(
        patientId: Int
    ): ArrayList<Appointment> {


        val appointmentList =
            ArrayList<Appointment>()


        val db =
            readableDatabase



        val cursor = db.rawQuery(

            """
        SELECT *
        FROM $TABLE_APPOINTMENT
        WHERE $COL_APPOINTMENT_PATIENT_ID = ?
        ORDER BY $COL_APPOINTMENT_ID DESC
        """.trimIndent(),

            arrayOf(
                patientId.toString()
            )

        )



        if(cursor.moveToFirst()){


            do{


                appointmentList.add(

                    Appointment(

                        id = cursor.getInt(0),

                        patientName = "",

                        patientPhone = "",

                        doctorName = cursor.getString(3) ?: "",

                        appointmentDate = cursor.getString(4) ?: "",

                        appointmentTime = cursor.getString(5) ?: "",

                        appointmentStatus = cursor.getString(6) ?: "Pending"

                    )

                )


            }while(cursor.moveToNext())


        }


        cursor.close()


        return appointmentList

    }

    fun getDoctorStatusCount(status: String): Int {

        val db = readableDatabase
        val cursor = db.rawQuery(

            """
        SELECT COUNT(*)
        FROM $TABLE_DOCTOR
       WHERE LOWER($COL_DOCTOR_STATUS)=LOWER(?)
        """.trimIndent(),

            arrayOf(status)

        )


        var count = 0


        if (cursor.moveToFirst()) {

            count = cursor.getInt(0)

        }

        cursor.close()

        return count
    }

    fun getDoctorsByStatus(status: String): ArrayList<Doctor> {

        val doctorList = ArrayList<Doctor>()
        val db = readableDatabase
        val cursor = db.rawQuery(

            """
        SELECT *
        FROM $TABLE_DOCTOR
        WHERE LOWER($COL_DOCTOR_STATUS)=LOWER(?)
        ORDER BY $COL_DOCTOR_ID DESC
        """.trimIndent(),

            arrayOf(status)

        )


        if (cursor.moveToFirst()) {


            do {


                doctorList.add(

                    Doctor(

                        id = cursor.getInt(0),

                        name = cursor.getString(1) ?: "",

                        specialization = cursor.getString(2) ?: "",

                        phone = cursor.getString(3) ?: "",

                        email = cursor.getString(4) ?: "",

                        password = cursor.getString(5) ?: "",

                        qualification = cursor.getString(6) ?: "",

                        registrationNumber = cursor.getString(7) ?: "",

                        experience = cursor.getString(8) ?: "",

                        document = cursor.getString(9) ?: "",

                        profileImage = cursor.getString(10) ?: "",


                        workingDays = cursor.getString(11) ?: "",

                        startTime = cursor.getString(12) ?: "",

                        endTime = cursor.getString(13) ?: "",

                        appointmentDuration = cursor.getInt(14),

                        status = cursor.getString(15) ?: "Pending"
                    )
                )
            } while (cursor.moveToNext())
        }
        cursor.close()


        return doctorList

    }

    fun doctorLogin(

        email: String,

        password: String

    ): Doctor? {


        val db = readableDatabase


        val cursor = db.rawQuery(

            """
        SELECT *
        FROM $TABLE_DOCTOR
        WHERE $COL_DOCTOR_EMAIL = ?
        AND $COL_DOCTOR_PASSWORD = ?
        """.trimIndent(),

            arrayOf(
                email,
                password
            )

        )


        var doctor: Doctor? = null



        if (cursor.moveToFirst()) {


            doctor = Doctor(

                id = cursor.getInt(0),

                name = cursor.getString(1) ?: "",

                specialization = cursor.getString(2) ?: "",

                phone = cursor.getString(3) ?: "",

                email = cursor.getString(4) ?: "",

                password = cursor.getString(5) ?: "",

                qualification = cursor.getString(6) ?: "",

                registrationNumber = cursor.getString(7) ?: "",

                experience = cursor.getString(8) ?: "",

                document = cursor.getString(9) ?: "",

                profileImage = cursor.getString(10) ?: "",

                workingDays = cursor.getString(11) ?: "",

                startTime = cursor.getString(12) ?: "",

                endTime = cursor.getString(13) ?: "",

                appointmentDuration = cursor.getInt(14),

                status = cursor.getString(15) ?: "Pending"
            )
        }
        cursor.close()
        return doctor

    }
    fun patientLogin(
        email: String,
        password: String
    ): Patient? {

        val db = readableDatabase

        val cursor = db.rawQuery(
            """
        SELECT *
        FROM $TABLE_PATIENT
        WHERE $COL_PATIENT_EMAIL = ?
        AND $COL_PATIENT_PASSWORD = ?
        """.trimIndent(),
            arrayOf(email, password)
        )

        var patient: Patient? = null

        if (cursor.moveToFirst()) {

            patient = Patient(
                id = cursor.getInt(
                    cursor.getColumnIndexOrThrow(COL_PATIENT_ID)
                ),

                patientCode = cursor.getString(
                    cursor.getColumnIndexOrThrow(COL_PATIENT_CODE)
                ) ?: "",

                name = cursor.getString(
                    cursor.getColumnIndexOrThrow(COL_PATIENT_NAME)
                ) ?: "",

                age = cursor.getInt(
                    cursor.getColumnIndexOrThrow(COL_PATIENT_AGE)
                ),

                phone = cursor.getString(
                    cursor.getColumnIndexOrThrow(COL_PATIENT_PHONE)
                ) ?: "",

                address = cursor.getString(
                    cursor.getColumnIndexOrThrow(COL_PATIENT_ADDRESS)
                ) ?: "",

                email = cursor.getString(
                    cursor.getColumnIndexOrThrow(COL_PATIENT_EMAIL)
                ) ?: "",

                password = cursor.getString(
                    cursor.getColumnIndexOrThrow(COL_PATIENT_PASSWORD)
                ) ?: "",

                profileImage = cursor.getString(
                    cursor.getColumnIndexOrThrow(COL_PATIENT_PROFILE_IMAGE)
                ) ?: "",

                createdDate = cursor.getString(
                    cursor.getColumnIndexOrThrow(COL_PATIENT_CREATED_DATE)
                ) ?: ""
            )
        }

        cursor.close()

        return patient
    }
    fun getPatientById(patientId: Int): Patient? {

        val db = readableDatabase

        val cursor = db.rawQuery(
            """
        SELECT *
        FROM $TABLE_PATIENT
        WHERE $COL_PATIENT_ID = ?
        LIMIT 1
        """.trimIndent(),
            arrayOf(patientId.toString())
        )

        var patient: Patient? = null

        if (cursor.moveToFirst()) {

            patient = Patient(

                id = cursor.getInt(
                    cursor.getColumnIndexOrThrow(COL_PATIENT_ID)
                ),

                patientCode = cursor.getString(
                    cursor.getColumnIndexOrThrow(COL_PATIENT_CODE)
                ) ?: "",

                name = cursor.getString(
                    cursor.getColumnIndexOrThrow(COL_PATIENT_NAME)
                ) ?: "",

                age = cursor.getInt(
                    cursor.getColumnIndexOrThrow(COL_PATIENT_AGE)
                ),

                phone = cursor.getString(
                    cursor.getColumnIndexOrThrow(COL_PATIENT_PHONE)
                ) ?: "",

                address = cursor.getString(
                    cursor.getColumnIndexOrThrow(COL_PATIENT_ADDRESS)
                ) ?: "",

                email = cursor.getString(
                    cursor.getColumnIndexOrThrow(COL_PATIENT_EMAIL)
                ) ?: "",

                password = cursor.getString(
                    cursor.getColumnIndexOrThrow(COL_PATIENT_PASSWORD)
                ) ?: "",

                profileImage = cursor.getString(
                    cursor.getColumnIndexOrThrow(
                        COL_PATIENT_PROFILE_IMAGE
                    )
                ) ?: "",

                createdDate = cursor.getString(
                    cursor.getColumnIndexOrThrow(
                        COL_PATIENT_CREATED_DATE
                    )
                ) ?: ""
            )
        }

        cursor.close()

        return patient
    }
    fun getAdminProfile(): Admin? {


        val db = readableDatabase


        val cursor = db.rawQuery(

            """
        SELECT *
        FROM admins
        LIMIT 1
        """,

            null

        )


        var admin: Admin? = null


        if (cursor.moveToFirst()) {


            admin = Admin(

                id = cursor.getInt(
                    cursor.getColumnIndexOrThrow("id")
                ),

                name = cursor.getString(
                    cursor.getColumnIndexOrThrow("name")
                ),

                email = cursor.getString(
                    cursor.getColumnIndexOrThrow("email")
                ),

                phone = cursor.getString(
                    cursor.getColumnIndexOrThrow("phone")
                ),

                password = cursor.getString(
                    cursor.getColumnIndexOrThrow("password")
                ),

                profileImage = cursor.getString(
                    cursor.getColumnIndexOrThrow("profile_image")
                ),

                role = cursor.getString(
                    cursor.getColumnIndexOrThrow("role")
                ),

                createdDate = cursor.getString(
                    cursor.getColumnIndexOrThrow("created_date")
                )

            )

        }


        cursor.close()


        return admin

    }

    fun updateAdminProfileImage(
        adminId: Int,
        imagePath: String
    ): Int {

        val db = writableDatabase

        val values = ContentValues().apply {
            put("profile_image", imagePath)
        }

        return db.update(
            "admins",
            values,
            "id=?",
            arrayOf(adminId.toString())
        )
    }
    private fun insertDefaultAdmin(db: SQLiteDatabase) {

        val cursor = db.rawQuery(
            "SELECT * FROM admins LIMIT 1",
            null
        )

        if (!cursor.moveToFirst()) {

            val values = ContentValues().apply {

                put("name", "System Administrator")
                put("email", "admin@medicare.com")
                put("phone", "0770000000")
                put("password", "123456")
                put("profile_image", "")
                put("role", "Super Admin")
                put("created_date", "2026-09-15")

            }


            db.insert(
                "admins",
                null,
                values
            )
        }
        cursor.close()
    }
    fun getDoctorById(
        doctorId: Int
    ): Doctor? {


        val db = readableDatabase


        val cursor = db.rawQuery(

            """
        SELECT *
        FROM $TABLE_DOCTOR
        WHERE $COL_DOCTOR_ID = ?
        LIMIT 1
        """.trimIndent(),

            arrayOf(
                doctorId.toString()
            )

        )


        var doctor: Doctor? = null


        if(cursor.moveToFirst()){


            doctor = Doctor(

                id = cursor.getInt(0),

                name = cursor.getString(1) ?: "",

                specialization = cursor.getString(2) ?: "",

                phone = cursor.getString(3) ?: "",

                email = cursor.getString(4) ?: "",

                password = cursor.getString(5) ?: "",

                qualification = cursor.getString(6) ?: "",

                registrationNumber = cursor.getString(7) ?: "",

                experience = cursor.getString(8) ?: "",

                document = cursor.getString(9) ?: "",

                profileImage = cursor.getString(10) ?: "",

                workingDays = cursor.getString(11) ?: "",

                startTime = cursor.getString(12) ?: "",

                endTime = cursor.getString(13) ?: "",

                appointmentDuration = cursor.getInt(14),

                status = cursor.getString(15) ?: "Pending"

            )

        }


        cursor.close()


        return doctor

    }

    fun getDoctorAppointmentCount(
        doctorId: Int
    ): Int {


        val db = readableDatabase


        val cursor = db.rawQuery(

            """
        SELECT COUNT(*)
        FROM $TABLE_APPOINTMENT
        WHERE doctor_id = ?
        """.trimIndent(),

            arrayOf(
                doctorId.toString()
            )

        )


        var count = 0


        if(cursor.moveToFirst()){

            count = cursor.getInt(0)

        }


        cursor.close()


        return count

    }
    fun getDoctorUpcomingAppointmentCount(
        doctorId: Int
    ): Int {


        val db = readableDatabase


        val cursor = db.rawQuery(

            """
        SELECT COUNT(*)

        FROM $TABLE_APPOINTMENT

        WHERE $COL_APPOINTMENT_DOCTOR_ID = ?

        AND $COL_APPOINTMENT_STATUS = 'Pending'

        """.trimIndent(),

            arrayOf(
                doctorId.toString()
            )

        )


        var count = 0


        if(cursor.moveToFirst()){

            count = cursor.getInt(0)

        }


        cursor.close()


        return count

    }
    fun getDoctorAppointments(
        doctorId: Int
    ): ArrayList<Appointment> {


        val appointments = ArrayList<Appointment>()


        val db = readableDatabase



        val cursor = db.rawQuery(

            """
        SELECT 
            a.*,
            p.name,
            p.phone

        FROM $TABLE_APPOINTMENT a

        LEFT JOIN $TABLE_PATIENT p

        ON a.$COL_APPOINTMENT_PATIENT_ID = p.$COL_PATIENT_ID


        WHERE a.$COL_APPOINTMENT_DOCTOR_ID = ?

        ORDER BY a.$COL_APPOINTMENT_ID DESC

        """.trimIndent(),

            arrayOf(
                doctorId.toString()
            )

        )



        if(cursor.moveToFirst()){


            do{


                appointments.add(

                    Appointment(

                        id = cursor.getInt(0),


                        patientId = cursor.getInt(1),


                        doctorId = cursor.getInt(2),


                        patientName = cursor.getString(8) ?: "",


                        patientPhone = cursor.getString(9) ?: "",


                        doctorName = cursor.getString(3) ?: "",


                        appointmentDate = cursor.getString(4) ?: "",


                        appointmentTime = cursor.getString(5) ?: "",


                        appointmentStatus = cursor.getString(6) ?: "Pending"
                    )

                )


            }while(cursor.moveToNext())


        }


        cursor.close()


        return appointments

    }
}