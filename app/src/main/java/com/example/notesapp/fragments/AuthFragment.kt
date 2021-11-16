package com.example.notesapp.fragments

import android.content.Context
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.navigation.fragment.findNavController
import com.example.notesapp.R
import com.example.notesapp.databinding.FragmentAuthBinding
import com.example.notesapp.models.PinCodeModel
import com.example.notesapp.room_database.database.PinCodeDatabase
import kotlinx.coroutines.launch

class AuthFragment : BaseFragment() {

    private var _binding: FragmentAuthBinding? = null
    private val binding get() = _binding!!
    private val TAG = "MAIN_TAG"


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentAuthBinding.inflate(inflater, container, false)



        binding.btnSave.setOnClickListener {
            savePinCode()
        }
        binding.btnClear.setOnClickListener {
            binding.etPinCode.text!!.clear()
            binding.etPinCodeRepeat.text!!.clear()
        }

        return binding.root
    }

    private fun savePinCode() {
        val pin1 = binding.etPinCode.text.toString()
        val pin2 = binding.etPinCodeRepeat.text.toString()

        val pin1L = binding.etPinCode.text!!.length
        val pin2L = binding.etPinCodeRepeat.text!!.length



        if (pin1L == 4){
            if (pin1 == pin2) {
                launch {
                    val pinCode = PinCodeModel()
                    pinCode.pinCode = pin1.toString().toInt()

                    PinCodeDatabase.getDatabase(requireContext()).pinCodeDao().insertPinCode(pinCode)

                    Toast.makeText(requireContext(), "Pin code inserted successfully", Toast.LENGTH_SHORT).show()
                    findNavController().navigate(R.id.action_authFragment_to_mainFragment )
                    onPinCodeCreated()
                }

            } else {
                Toast.makeText(requireContext(), "PIN codes are not same", Toast.LENGTH_SHORT).show()
            }
        }else{
            Toast.makeText(requireContext(), "Please make sure that you filled blanks correctly", Toast.LENGTH_SHORT).show()
        }


    }
    private fun onPinCodeCreated(){
        val sharedPref = requireActivity().getSharedPreferences("onPinCodeCreated", Context.MODE_PRIVATE)
        val editor = sharedPref.edit()
        editor.putBoolean("Created", true)
        editor.apply()
    }

}