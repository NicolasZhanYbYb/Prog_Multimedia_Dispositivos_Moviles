package com.nzv.mynarrativecontent

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.tabs.TabLayoutMediator
import com.nzv.mynarrativecontent.databinding.ActivityMainBinding

@Suppress("UNREACHABLE_CODE")
class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val viewPager2 = binding.viewPager2
        val adapter = ViewPager2Adapter(supportFragmentManager, lifecycle)

        adapter.addFragment(PrincipalFragment(), "Tu Contenido")
        adapter.addFragment(FavFragment(), "Favoritos")
        adapter.addFragment(TopFragment(), "Top")

        viewPager2.adapter = adapter

        TabLayoutMediator(binding.tabLayout, viewPager2){tab, position ->
            tab.text = adapter.getPageTitle(position)
        }.attach()
    }
}