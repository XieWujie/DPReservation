package com.example.administrator.dpreservation.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import com.example.administrator.dpreservation.data.doctor.Doctor
import com.example.administrator.dpreservation.data.doctor.DoctorDao
import com.example.administrator.dpreservation.data.evaluation.Evaluation
import com.example.administrator.dpreservation.data.evaluation.EvaluationDao
import com.example.administrator.dpreservation.data.message.Message
import com.example.administrator.dpreservation.data.message.MessageDao
import com.example.administrator.dpreservation.data.order.Order
import com.example.administrator.dpreservation.data.order.OrderDao
import com.example.administrator.dpreservation.data.user.User
import com.example.administrator.dpreservation.data.user.UserDao
import com.example.administrator.dpreservation.utilities.DATABASE_NAME
import com.google.protobuf.EnumValue


@Database(entities = [Doctor::class,User::class,Message::class,Order::class,Evaluation::class],version = 3,exportSchema = false)
abstract class AppDatabase:RoomDatabase(){

    abstract fun getClinicDao():DoctorDao

    abstract fun getUserDao():UserDao

    abstract fun getMessageDao():MessageDao

    abstract fun getOrder():OrderDao

    abstract fun getEvaluationDao():EvaluationDao

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
                    }
                }).build()
        }
    }
}