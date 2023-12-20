package com.nzv.mynarrativecontent

import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.firebase.firestore.CollectionReference
import com.google.firebase.firestore.FirebaseFirestore
import com.nzv.mynarrativecontent.databinding.FragmentFavBinding
import com.nzv.mynarrativecontent.databinding.FragmentPrincipalBinding

class PrincipalFragment : Fragment() {
    private lateinit var binding: FragmentPrincipalBinding
    private lateinit var studentDBHelper: MyContentDBOpenHelper
    private lateinit var db: FirebaseFirestore
    private lateinit var myAdapter: ContentAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentPrincipalBinding.inflate(inflater)
        studentDBHelper = MyContentDBOpenHelper(requireContext())
        db = FirebaseFirestore.getInstance()
        setUpRecyclerView()
        return binding.root
    }

    override fun onResume() {
        super.onResume()
        setUpRecyclerView()
    }

    private fun fillList(contentCollection: CollectionReference) {
        contentCollection.addSnapshotListener { querySnapshot, firestoreException ->
            if (firestoreException != null) {
                return@addSnapshotListener
            }

            for (document in querySnapshot!!) {
                document!!["modulo"].toString()
            }
        }

        myAdapter = ContentAdapter(getStudents(), requireContext())
        binding.myContentList.adapter = myAdapter
    }

    private fun setUpRecyclerView() {
       // val studentAdapterCursor = ContentAdapter(,requireContext())
        //binding.myContentList.layoutManager = LinearLayoutManager(requireContext())

        binding.myContentList.setHasFixedSize(true)
        binding.myContentList.layoutManager = LinearLayoutManager(requireContext())
    }
}