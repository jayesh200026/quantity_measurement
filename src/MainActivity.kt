package com.example.quantitymeasurement

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val fragment=ConversionFragment()

        supportFragmentManager.beginTransaction().apply {
            replace(R.id.flFragment,fragment)
            commit()
        }

    }

    fun loadAddFragment(view: View) {
        val addFragment=AdditionFragment()
        supportFragmentManager.beginTransaction().apply {
            replace(R.id.flFragment,addFragment)
            addToBackStack(null)
            commit()
        }
    }

    fun loadConvertFragment(view: View) {
        val convertFragment = ConversionFragment()
        supportFragmentManager.beginTransaction().apply {
            replace(R.id.flFragment,convertFragment)
            addToBackStack(null)
            commit()
        }
    }
}
