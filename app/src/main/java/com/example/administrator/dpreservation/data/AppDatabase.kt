package com.example.administrator.dpreservation.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import com.example.administrator.dpreservation.data.clinic.Clinic
import com.example.administrator.dpreservation.data.clinic.ClinicDao
import com.example.administrator.dpreservation.utilities.DATABASE_NAME

@Database(entities = [Clinic::class],version = 1,exportSchema = false)
abstract class AppDatabase:RoomDatabase(){

    abstract fun getClinicDao():ClinicDao

    companion object {

        private @Volatile var instance:AppDatabase? = null

        fun getInstance(context: Context):AppDatabase{
            return instance?: synchronized(this){
                instance?: buildInstance(context).also { instance = it }
            }
        }
        private fun buildInstance(context: Context):AppDatabase{
            return Room.databaseBuilder(context,AppDatabase::class.java, DATABASE_NAME)
                .addCallback(object :RoomDatabase.Callback(){
                    override fun onCreate(db: SupportSQLiteDatabase) {
                        super.onCreate(db)
                       // initData(context)
                    }
                }).build()
        }

        private fun initData(context: Context){
            val list = ArrayList<Clinic>()
            for (i in 0..30){
                val clinic = Clinic("$i 诊所")
                list.add(clinic)
            }
            getInstance(context).getClinicDao().addClinic(list)
        }
    }
}