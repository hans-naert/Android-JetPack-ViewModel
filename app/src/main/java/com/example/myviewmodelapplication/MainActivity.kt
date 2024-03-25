package com.example.myviewmodelapplication

import android.content.SharedPreferences
import android.os.Bundle
import android.provider.Settings.Global
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.ViewModelProvider
import com.example.myviewmodelapplication.databinding.ActivityMainBinding
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {

    lateinit var binding : ActivityMainBinding
    lateinit var viewmodel: MainViewmodel
    lateinit var sp: SharedPreferences

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        sp = getPreferences(MODE_PRIVATE)

        binding= ActivityMainBinding.inflate(layoutInflater)

        enableEdgeToEdge()
        setContentView(binding.root)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        val countReserved=sp.getInt("count_reserved", 0)

        viewmodel = ViewModelProvider(this, MainViewmodelFactory(countReserved)).get(MainViewmodel::class.java)

        viewmodel.counter.observe(this) {
            binding.counterTextView.text=it.toString()
        }

        binding.addButton.setOnClickListener {
            viewmodel.plusOne()
        }

        viewmodel.userName.observe(this) {
            binding.usernameTextView.text=it
        }

        viewmodel.user.observe(this) {
            binding.userIdTextView.text=it.firstname
        }

        GlobalScope.launch(Dispatchers.Main)  {
            viewmodel.userIdLiveData.value="Piet"
            delay(1000)
            viewmodel.userIdLiveData.value="Jan"
            delay(1000)
            viewmodel.userIdLiveData.value="Klaas"
            }
    }


    override fun onPause() {
        super.onPause()
        sp.edit().putInt("count_reserved", viewmodel.counter.value?:0).apply()
    }
}