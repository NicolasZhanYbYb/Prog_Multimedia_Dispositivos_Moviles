package com.nizhvi.practica3

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.google.android.material.tabs.TabLayoutMediator
import com.nizhvi.practica3.databinding.ActivityMainBinding

//Nicolas Zhan
@Suppress("UNREACHABLE_CODE")
class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private lateinit var viewPager2Adapter: ViewPager2Adapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val viewPager2 = binding.viewPager2
        val adapter = ViewPager2Adapter(supportFragmentManager, lifecycle)

        adapter.addFragment(PrincipalFragment(), "Principal")
        adapter.addFragment(HistoryFragment(), "History")

        viewPager2.adapter = adapter

        TabLayoutMediator(binding.tabLayout, viewPager2){tab, position ->
            tab.text = adapter.getPageTitle(position)
        }.attach()
    }
}