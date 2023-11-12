package com.nizhvi.ejemplo42

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.nizhvi.ejemplo42.databinding.FragmentButtonsBinding

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [ButtonsFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class ButtonsFragment : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null
    private lateinit var binding: FragmentButtonsBinding


    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentButtonsBinding.inflate(inflater)
        binding.button.setOnClickListener { actionsButtons("button") }
        binding.imageButton.setOnClickListener { actionsButtons("imageButton") }
        binding.fabButton.setOnClickListener { fabActionButton() }
        return binding.root
    }

    private fun actionsButtons(name: String) {
        when (name) {
            "button" -> {
                binding.checkBox.isChecked = true
                binding.radioButton1.isChecked = true
                binding.radioButton2.isChecked = false
                binding.toggleButton.isChecked = true
                binding.switch1.isChecked = true
            }
            else -> {
                binding.checkBox.isChecked = false
                binding.radioButton1.isChecked = false
                binding.radioButton2.isChecked = true
                binding.toggleButton.isChecked = false
                binding.switch1.isChecked = false
            }
        }
    }

    private fun fabActionButton() {
        var texto: String
        texto = "Pulsados: \n";
        if(binding.checkBox.isChecked)
            texto += "CheckBox "

        if(binding.radioButton1.isChecked)
            texto += "Radio 1 "
        else
            texto += "Radio 2 "

        if(binding.toggleButton.isChecked)
            texto += "Toggle Button "
        if(binding.switch1.isChecked)
            texto += "Switch"
        Toast.makeText(context, texto, Toast.LENGTH_LONG).show()
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment ButtonsFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            ButtonsFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}