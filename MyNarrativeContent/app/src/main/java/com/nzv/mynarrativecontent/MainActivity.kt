package com.nzv.mynarrativecontent

import android.app.AlertDialog
import android.content.DialogInterface
import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.tabs.TabLayoutMediator
import com.nzv.mynarrativecontent.databinding.ActivityMainBinding

@Suppress("UNREACHABLE_CODE")
class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private var userName = ""
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val name = intent.getStringExtra(Utils.EXTRA_NAME)
        userName = name ?: ""
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

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        val inflate = menuInflater
        inflate.inflate(R.menu.second_menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.addItem -> {
                val myIntent = Intent(this, AddContentActivity::class.java).apply {
                    putExtra(Utils.EXTRA_USER, userName)
                }
                startActivity(myIntent)

                true
            }
            R.id.logOutItem -> {
                myAlertDialog("¿Seguro que deseas cerrar la sesión?")
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    private fun myAlertDialog(message: String) {
        val builder = AlertDialog.Builder(this)

        builder.apply {
            setTitle("Cerrar Sesion")
            setMessage(message)
            setPositiveButton(android.R.string.ok)  { _, _ ->
                finish()
            }
            setNegativeButton(android.R.string.cancel) { _, _ ->
            }
        }

        builder.show()
    }
}