package com.isroot.echocaulator

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.viewModels
import com.isroot.echocaulator.databinding.ActivityMainBinding

class MainActivity : BaseActivity() {
    lateinit var binding : ActivityMainBinding
    val viewModel : MainViewModel  by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setUpBinding()
    }

    override fun getLayoutResource(): Int {
        return R.layout.activity_main
    }

    private fun setUpBinding() {
        binding = getBinding() as ActivityMainBinding
        (binding as ActivityMainBinding).viewModel = viewModel
    }
}