package ru.spiritblog.petsave.common

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import ru.spiritblog.petsave.R

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }
}