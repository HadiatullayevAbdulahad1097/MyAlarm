package com.abdulahad.myalarm

import android.annotation.SuppressLint
import android.app.AlarmManager
import android.app.PendingIntent
import android.content.Intent
import android.graphics.Color
import android.os.Build
import android.os.Bundle
import android.view.WindowManager
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.getSystemService
import com.abdulahad.myalarm.databinding.ActivityMain2Binding
import com.abdulahad.myalarm.receiver.OnTimeReceiver
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import java.lang.reflect.Field
import java.util.*
import kotlin.time.Duration.Companion.hours
import kotlin.time.Duration.Companion.minutes

class MainActivity2 : AppCompatActivity() {
    lateinit var onTimeReceiver: OnTimeReceiver
    lateinit var alarmManager: AlarmManager
    lateinit var binding: ActivityMain2Binding
    lateinit var calendar: Calendar
    lateinit var pendingIntent: PendingIntent
    lateinit var firebaseDatabase: FirebaseDatabase
    lateinit var reference: DatabaseReference
    @SuppressLint("UnspecifiedImmutableFlag", "InlinedApi", "ObsoleteSdkInt")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMain2Binding.inflate(layoutInflater)
        setContentView(binding.root)

        val window = window
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS)
        window.statusBarColor = Color.parseColor("#DCD8D8")

        firebaseDatabase = FirebaseDatabase.getInstance()

        reference = firebaseDatabase.getReference("Alarm")

        onTimeReceiver = OnTimeReceiver()

        calendar = Calendar.getInstance()

        alarmManager = getSystemService(ALARM_SERVICE) as AlarmManager

        binding.timePicker.setOnTimeChangedListener { view, hourOfDay, minute ->
            if (Build.VERSION.SDK_INT >=21) {
                calendar.set(Calendar.HOUR_OF_DAY, binding.timePicker.hour)
                calendar.set(Calendar.MINUTE, binding.timePicker.minute)
            } else{
                calendar.set(Calendar.HOUR_OF_DAY, binding.timePicker.currentHour)
                calendar.set(Calendar.MINUTE, binding.timePicker.currentMinute)
            }
            }
        binding.save.setOnClickListener {
            pendingIntent = PendingIntent.getBroadcast(
                this,
                0,
                Intent(this, OnTimeReceiver::class.java),
                PendingIntent.FLAG_UPDATE_CURRENT

            )
            alarmManager.set(AlarmManager.RTC_WAKEUP, calendar.timeInMillis, pendingIntent)
            binding.root.isEnabled = false
        }
    }
}