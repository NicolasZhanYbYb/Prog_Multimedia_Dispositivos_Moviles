package com.nzv.mynarrativecontent

import android.content.Intent
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.firebase.firestore.FirebaseFirestore

class FavFragment : Fragment() {
    //private lateinit var binding: FragmentFavBinding
    //private lateinit var studentDBHelper: MyStudentsDBOpenHelper
    private lateinit var db: FirebaseFirestore

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        /*binding = FragmentHistoryBinding.inflate(inflater)
        studentDBHelper = MyStudentsDBOpenHelper(requireContext())
        db = studentDBHelper.readableDatabase
        cursor = db.rawQuery(selectStudent, null)
        setUpRecyclerView(cursor)
        return binding.root*/
    }

   /* override fun onResume() {
        super.onResume()
        cursor.close()
        cursor = db.rawQuery(selectStudent, null)
        setUpRecyclerView(cursor)
    }

    override fun onDestroy() {
        super.onDestroy()
        Log.d("onDestroy", "Cerramos la conexión")
        cursor.close()
        db.close()
    }

    private fun setUpRecyclerView(cursor: Cursor) {
        val studentAdapterCursor = StudentAdapterCursor(requireContext(), cursor)
        binding.myStudentsList.setHasFixedSize(true)
        binding.myStudentsList.layoutManager = LinearLayoutManager(requireContext())
        binding.myStudentsList.adapter = studentAdapterCursor
    }*/
}