package com.example.notesapp.fragments

import android.Manifest
import android.app.Activity.RESULT_OK
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.graphics.BitmapFactory
import android.graphics.Color
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.content.ContentProviderCompat.requireContext
import androidx.localbroadcastmanager.content.LocalBroadcastManager
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.example.notesapp.R
import com.example.notesapp.databinding.FragmentAddNoteBinding
import com.example.notesapp.models.NotesModel
import com.example.notesapp.room_database.database.NotesDatabase
import com.example.notesapp.utils.NoteBottomSheetFragment
import kotlinx.coroutines.launch
import pub.devrel.easypermissions.AppSettingsDialog
import pub.devrel.easypermissions.EasyPermissions
import java.text.SimpleDateFormat
import java.util.*

class AddNoteFragment : BaseFragment(), EasyPermissions.PermissionCallbacks,
    EasyPermissions.RationaleCallbacks {

    private var _binding: FragmentAddNoteBinding? = null
    private val binding get() = _binding!!
    private val currentDate = SimpleDateFormat("dd/MM/yyyy").format(Date())
    private val currentTime = SimpleDateFormat("hh:mm").format(Date())
    private val READ_STORAGE_PERMISSION_CODE = 1
    private val READ_STORAGE_REQUEST_CODE = 2
    private var selectedImagePath = ""

    private var selectedColor = "#ffffff"

    //  private val args: AddNoteFragmentArgs by navArgs()
//
    //  private val noteId = args.noteId

    private var noteId = -1

    companion object {
        @JvmStatic
        fun newInstance() =
            AddNoteFragment().apply {
                arguments = Bundle().apply {
                }
            }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentAddNoteBinding.inflate(inflater, container, false)
        binding.colorView.setBackgroundColor(Color.parseColor(selectedColor))

        noteId = requireArguments().getInt("noteId", -1)
        //  val noteId = args.noteId
        if (noteId != -1) {
            launch {
                context?.let {
                    val notes = NotesDatabase.getDatabase(it).noteDao().getSpecificNote(noteId)

                    binding.etNoteTitle.setText(notes.title)
                    binding.etNoteDescription.setText(notes.note_text)

                    selectedColor = notes.color!!

                    binding.colorView.setBackgroundColor(Color.parseColor(selectedColor))



                    if (notes.imagePath != "") {
                        selectedImagePath = notes.imagePath!!
                        binding.imgNote.setImageBitmap(BitmapFactory.decodeFile(notes.imagePath))
                        binding.rlImageLayout.visibility = View.VISIBLE
                    } else {
                        binding.rlImageLayout.visibility = View.GONE
                    }
                }
            }
        }

        LocalBroadcastManager.getInstance(requireContext())
            .registerReceiver(broadcastReceiver, IntentFilter("bottom_sheet_action"))


        binding.tvCreatedDate.text = ("$currentDate  $currentTime")

        binding.btnDone.setOnClickListener {

            if (noteId != -1) {
                updateNote()
            } else {
                saveNote()
            }

        }
        binding.btnBack.setOnClickListener {
            requireActivity().supportFragmentManager.popBackStack()
        }
        binding.btnMore.setOnClickListener {
            val noteBottomSheetFragment = NoteBottomSheetFragment.newInstance(noteId)
            noteBottomSheetFragment.show(
                requireActivity().supportFragmentManager,
                "Note Bottom Sheet Fragment"
            )
        }
        binding.btnDeleteImage.setOnClickListener {
            selectedImagePath = ""
            binding.rlImageLayout.visibility = View.GONE
        }

        return binding.root
    }

    private fun saveNote() {
        if (binding.etNoteTitle.text.isNullOrEmpty()) {
            Toast.makeText(requireContext(), "Title Required", Toast.LENGTH_SHORT).show()
        } else {
            launch {
                val note = NotesModel()
                note.title = binding.etNoteTitle.text.toString()
                note.date_created = currentDate.toString()
                note.time_created = currentTime.toString()
                note.note_text = binding.etNoteDescription.text.toString()
                note.color = selectedColor
                note.imagePath = selectedImagePath

                context.let {
                    NotesDatabase.getDatabase(requireContext()).noteDao().insertNotes(note)
                    binding.etNoteTitle.setText("")
                    binding.etNoteDescription.setText("")
                    binding.rlImageLayout.visibility = View.GONE

                    requireActivity().supportFragmentManager.popBackStack()

                }
            }
        }
    }

    private fun updateNote() {
        launch {
            context.let {
                val note =
                    NotesDatabase.getDatabase(requireContext()).noteDao().getSpecificNote(noteId)
                note.title = binding.etNoteTitle.text.toString()
                note.date_created = currentDate.toString()
                note.time_created = currentTime.toString()
                note.note_text = binding.etNoteDescription.text.toString()
                note.color = selectedColor
                note.imagePath = selectedImagePath


                NotesDatabase.getDatabase(requireContext()).noteDao().updateNote(note)
                binding.etNoteTitle.setText("")
                binding.etNoteDescription.setText("")
                binding.rlImageLayout.visibility = View.GONE

                requireActivity().supportFragmentManager.popBackStack()
            }
        }
    }

    private fun deleteNote() {
        launch {
            context?.let {
                NotesDatabase.getDatabase(requireContext()).noteDao().deleteSpecificNote(noteId)
                requireActivity().supportFragmentManager.popBackStack()
            }
        }
    }

    private val broadcastReceiver: BroadcastReceiver = object : BroadcastReceiver() {
        override fun onReceive(context: Context?, intent: Intent?) {
            val action = intent!!.getStringExtra("action")

            when (action!!) {
                "Blue" -> {
                    selectedColor = intent.getStringExtra("selected_color")!!
                    binding.colorView.setBackgroundColor(Color.parseColor(selectedColor))
                }
                "Yellow" -> {
                    selectedColor = intent.getStringExtra("selected_color")!!
                    binding.colorView.setBackgroundColor(Color.parseColor(selectedColor))
                }
//                "White" -> {
//                    selectedColor = intent.getStringExtra("selected_color")!!
//                    binding.colorView.setBackgroundColor(Color.parseColor(selectedColor))
//                }
                "Purple" -> {
                    selectedColor = intent.getStringExtra("selected_color")!!
                    binding.colorView.setBackgroundColor(Color.parseColor(selectedColor))
                }
                "Green" -> {
                    selectedColor = intent.getStringExtra("selected_color")!!
                    binding.colorView.setBackgroundColor(Color.parseColor(selectedColor))
                }
                "Orange" -> {
                    selectedColor = intent.getStringExtra("selected_color")!!
                    binding.colorView.setBackgroundColor(Color.parseColor(selectedColor))
                }
                "Black" -> {
                    selectedColor = intent.getStringExtra("selected_color")!!
                    binding.colorView.setBackgroundColor(Color.parseColor(selectedColor))
                }
                "Image" -> {
                    readExternalStorageTask()
                }
                "DeleteNote" -> {
                    deleteNote()
                }
                else -> {
                    selectedColor = intent.getStringExtra("selected_color")!!
                    binding.colorView.setBackgroundColor(Color.parseColor(selectedColor))
                    binding.rlImageLayout.visibility = View.GONE
                }
            }
        }
    }

    override fun onDestroy() {
        LocalBroadcastManager.getInstance(requireContext()).unregisterReceiver(broadcastReceiver)
        super.onDestroy()
    }

    private fun readExternalStoragePermission(): Boolean {
        return EasyPermissions.hasPermissions(
            requireContext(),
            Manifest.permission.READ_EXTERNAL_STORAGE
        )
    }


    private fun readExternalStorageTask() {
        if (readExternalStoragePermission()) {
            pickImageFromGallery()
        } else {
            EasyPermissions.requestPermissions(
                this,
                "This app needs access to your storage.",
                READ_STORAGE_PERMISSION_CODE,
                Manifest.permission.READ_EXTERNAL_STORAGE
            )
        }
    }

    private fun pickImageFromGallery() {
        val intent = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
        startActivityForResult(intent, READ_STORAGE_REQUEST_CODE)
    }

    private fun getPathFromUri(contentUri: Uri): String? {
        var filePath: String?
        var cursor = requireActivity().contentResolver.query(contentUri, null, null, null, null)
        if (cursor == null) {
            filePath = contentUri.path
        } else {
            cursor.moveToFirst()
            val index = cursor.getColumnIndex("_data")
            filePath = cursor.getString(index)
            cursor.close()
        }
        return filePath
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == READ_STORAGE_REQUEST_CODE && resultCode == RESULT_OK) {
            val selectedImageUri = data!!.data
            if (selectedImageUri != null) {
                try {
                    val inputStream =
                        requireActivity().contentResolver.openInputStream(selectedImageUri)
                    val bitmap = BitmapFactory.decodeStream(inputStream)
                    binding.imgNote.setImageBitmap(bitmap)
                    binding.rlImageLayout.visibility = View.VISIBLE
                    selectedImagePath = getPathFromUri(selectedImageUri)!!

                } catch (e: Exception) {
                    Toast.makeText(requireContext(), e.message.toString(), Toast.LENGTH_SHORT)
                        .show()
                }
            }
        }
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        EasyPermissions.onRequestPermissionsResult(
            requestCode,
            permissions,
            grantResults,
            requireActivity()
        )
    }

    override fun onPermissionsDenied(requestCode: Int, perms: MutableList<String>) {
        if (EasyPermissions.somePermissionPermanentlyDenied(this, perms)) {
            AppSettingsDialog.Builder(requireActivity()).build()
        }
    }

    override fun onPermissionsGranted(requestCode: Int, perms: MutableList<String>) {}

    override fun onRationaleAccepted(requestCode: Int) {}

    override fun onRationaleDenied(requestCode: Int) {}
}