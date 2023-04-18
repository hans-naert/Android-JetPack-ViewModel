package com.example.jetpackapplication

import android.content.SharedPreferences
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.edit
import androidx.lifecycle.ViewModelProvider
import com.example.jetpackapplication.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    lateinit var viewModel: MainViewModel
    lateinit var binding: ActivityMainBinding
    lateinit var sp: SharedPreferences
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        lifecycle.addObserver(MyObserver())

        sp = getPreferences(MODE_PRIVATE)
        val countReserved = sp.getInt("COUNTER", 0)

        viewModel = ViewModelProvider(this, MainViewModelFactory(countReserved)).get(MainViewModel::class.java)

        viewModel.counter.observe(this) { count ->
            binding.infoTextView.text=count.toString()
        }
        binding.plusOneButton.setOnClickListener {
            viewModel.plusOne()
        }
    }

    override fun onPause() {
        super.onPause()
        sp.edit {
            putInt("COUNTER", viewModel.counter.value ?: 0)
        }
    }

}