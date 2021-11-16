package com.example.notesapp.fragments

import android.os.Bundle
import android.os.CountDownTimer
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.navigation.fragment.findNavController
import com.example.notesapp.R
import com.example.notesapp.adapters.NoteListItemAdapter
import com.example.notesapp.databinding.FragmentPINCodeBinding
import com.example.notesapp.models.PinCodeModel
import com.example.notesapp.room_database.database.NotesDatabase
import com.example.notesapp.room_database.database.PinCodeDatabase
import kotlinx.coroutines.launch

class PINCodeFragment : BaseFragment() {

    private var _binding: FragmentPINCodeBinding ?= null
    private val binding get() = _binding!!

    private var timer: CountDownTimer ?= null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentPINCodeBinding.inflate(inflater ,container, false)

        startTimer()

        binding.btnCheckPinCode.setOnClickListener {
            checkPinCode()
        }
        binding.btnClear.setOnClickListener {
            binding.etPinCode.text!!.clear()
        }

        return binding.root
    }

    private fun startTimer(){
         timer = object : CountDownTimer(30000L, 1000L){
            override fun onTick(millisUntilFinished: Long) {
                binding.tvSecondsLef.text = (millisUntilFinished/1000).toString()
                if (binding.tvSecondsLef.text.toString().toInt() < 10){
                    binding.tvSecondsLef.text = "0${millisUntilFinished / 1000}"
                }
            }
            override fun onFinish() {
                requireActivity().onBackPressed()
            }
        }.start()
    }

    private fun finishTimer(){
        if (timer != null){
            timer!!.cancel()
            binding.tvSecondsLef.text = 30.toString()
            timer = null

        }
    }

    private fun checkPinCode(){
        val writtenPin = binding.etPinCode.text

        launch {
            context?.let {
                val _realPinCode = PinCodeDatabase.getDatabase(it).pinCodeDao().getRealPinCode()
                val realPinCode = _realPinCode[0].toString().toInt()

                if (!writtenPin.isNullOrEmpty() && writtenPin.length == 4 && realPinCode == writtenPin.toString().toInt()){
                    finishTimer()
                    findNavController().navigate(R.id.action_PINCodeFragment_to_mainFragment)
                }else{
                    Toast.makeText(requireContext(), "Your pin code is wrong, please try again", Toast.LENGTH_SHORT).show()
                    binding.etPinCode.text = null
                }
            }
        }
    }

}