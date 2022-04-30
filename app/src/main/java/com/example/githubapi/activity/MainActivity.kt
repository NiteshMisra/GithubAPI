package com.example.githubapi.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.navigation.fragment.NavHostFragment
import com.example.githubapi.R
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private val navHost by lazy {
        supportFragmentManager
            .findFragmentById(R.id.doubt_base_container) as NavHostFragment?
    }

    private val navController by lazy {
        navHost?.navController
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        navController?.setGraph(R.navigation.repo_nav_graph)
    }
}