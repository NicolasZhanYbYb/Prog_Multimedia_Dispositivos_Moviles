package com.nzv.exam

import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.os.Bundle
import android.util.Log
import android.view.Menu
import androidx.recyclerview.widget.LinearLayoutManager
import com.nzv.exam.databinding.ActivityThirdBinding
import androidx.appcompat.app.AppCompatActivity

class ThirdActivity : AppCompatActivity() {
    private lateinit var binding: ActivityThirdBinding
    private lateinit var appDBHelper: MyAppDBOpenHelper
    private lateinit var db: SQLiteDatabase
    private lateinit var cursor: Cursor
    private lateinit var modalidad: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityThirdBinding.inflate(layoutInflater)
        appDBHelper = MyAppDBOpenHelper(this)
        setContentView(binding.root)
        modalidad = intent.getStringExtra(Utils.EXTRA_MODALITY).toString()

        appDBHelper = MyAppDBOpenHelper(this)
        db = appDBHelper.readableDatabase
        val selectStudent = "SELECT * FROM ${MyAppDBOpenHelper.APP_TABLE} WHERE ${MyAppDBOpenHelper.MODALITY_COLUMN} = '${modalidad}';"
        cursor = db.rawQuery(selectStudent, null)
        setUpRecyclerView()
    }

    override fun onDestroy() {
        super.onDestroy()
        Log.d("onDestroy", "Cerramos la conexión")
        cursor.close()
        db.close()
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        val inflate = menuInflater
        inflate.inflate(R.menu.main_menu, menu)
        this.title = "Aplicaciones de $modalidad";
        return true
    }

    private fun setUpRecyclerView() {
        val appAdapterCursor = AppAdapterCursor(this, cursor)
        binding.myRVApp.setHasFixedSize(true)
        binding.myRVApp.layoutManager = LinearLayoutManager(this)
        binding.myRVApp.adapter = appAdapterCursor
    }
}
