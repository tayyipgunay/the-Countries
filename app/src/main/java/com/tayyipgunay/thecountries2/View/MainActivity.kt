package com.tayyipgunay.thecountries2.View

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.tayyipgunay.thecountries2.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private lateinit var binding:ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding=ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

    }
}