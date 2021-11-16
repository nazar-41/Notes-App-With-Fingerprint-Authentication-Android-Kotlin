package com.example.notesapp.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.SearchView
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.example.notesapp.R
import com.example.notesapp.adapters.NoteListItemAdapter
import com.example.notesapp.databinding.FragmentMainBinding
import com.example.notesapp.models.NotesModel
import com.example.notesapp.room_database.database.NotesDatabase
import kotlinx.coroutines.launch
import java.util.*
import kotlin.collections.ArrayList

class MainFragment : BaseFragment() {

    private var _binding: FragmentMainBinding? = null
    private val binding get() = _binding!!

    private var notesAdapter: NoteListItemAdapter = NoteListItemAdapter()

    private var noteArray = ArrayList<NotesModel>()


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentMainBinding.inflate(inflater, container, false)

        binding.rvMain.setHasFixedSize(true)
        binding.rvMain.layoutManager =
            StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL)

        launch {
            context?.let {
                val notes = NotesDatabase.getDatabase(it).noteDao().getAllNotes()
                notesAdapter.setData(notes)
                noteArray = notes as ArrayList<NotesModel>
                binding.rvMain.adapter = notesAdapter
            }
        }
        notesAdapter.setOnClickListener(onClicked)

        binding.fabBtnCreateNote.setOnClickListener {
            replaceFragment(AddNoteFragment.newInstance(), false)
        }


        binding.searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener{
            override fun onQueryTextSubmit(query: String?): Boolean {
                return true
            }

            override fun onQueryTextChange(newText: String?): Boolean {

                var tempArr = ArrayList<NotesModel>()

                for (arr in noteArray){
                    if (arr.title!!.toLowerCase(Locale.getDefault()).contains(newText.toString())){
                        tempArr.add(arr)
                    }
                }
                notesAdapter.setData(tempArr)
                notesAdapter.notifyDataSetChanged()

                return true
            }

        })

        return binding.root
    }

    private val onClicked = object : NoteListItemAdapter.OnItemClickListener {
        override fun onClicked(noteId: Int) {
//            val action = MainFragmentDirections.actionMainFragmentToAddNoteFragment(noteId)
//            findNavController().navigate(action)
//            // findNavController().navigate(R.id.action_mainFragment_to_addNoteFragment)

            var fragment :Fragment
            var bundle = Bundle()
            bundle.putInt("noteId",noteId)
            fragment = AddNoteFragment.newInstance()
            fragment.arguments = bundle

            replaceFragment(fragment,false)

        }

    }

    fun replaceFragment(fragment: Fragment, transition:Boolean){
        val fragmentTransition = requireActivity().supportFragmentManager.beginTransaction()

        if (transition){
            fragmentTransition.setCustomAnimations(android.R.anim.slide_out_right,android.R.anim.slide_in_left)
        }
        fragmentTransition.replace(R.id.fragmentContainerView,fragment).addToBackStack(fragment.javaClass.simpleName).commit()
    }

}