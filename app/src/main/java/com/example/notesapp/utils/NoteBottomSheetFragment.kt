package com.example.notesapp.utils

import android.annotation.SuppressLint
import android.app.Dialog
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import android.widget.ImageView
import android.widget.LinearLayout
import androidx.coordinatorlayout.widget.CoordinatorLayout
import androidx.localbroadcastmanager.content.LocalBroadcastManager
import com.example.notesapp.R
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetDialogFragment

class NoteBottomSheetFragment : BottomSheetDialogFragment() {
   private var selectedColor = "#ffffff"

    companion object {
        var noteId = -1
        fun newInstance(id: Int): NoteBottomSheetFragment {
            val args = Bundle()
            val fragment = NoteBottomSheetFragment()
            noteId = id
            fragment.arguments = args
            return fragment

        }
    }

    @SuppressLint("RestrictedApi")
    override fun setupDialog(dialog: Dialog, style: Int) {
        super.setupDialog(dialog, style)


        val view = LayoutInflater.from(context).inflate(R.layout.fragment_notes_bottom_sheet, null)
        dialog.setContentView(view)

        val param = (view.parent as View).layoutParams as CoordinatorLayout.LayoutParams

        val behavior = param.behavior

        if (behavior is BottomSheetBehavior<*>) {
            behavior.setBottomSheetCallback(object : BottomSheetBehavior.BottomSheetCallback() {
                override fun onStateChanged(bottomSheet: View, newState: Int) {
                    var state = ""
                    when (newState) {
                        BottomSheetBehavior.STATE_DRAGGING -> {
                            state = "DRAGGING"
                        }
                        BottomSheetBehavior.STATE_SETTLING -> {
                            state = "SETTLING"
                        }
                        BottomSheetBehavior.STATE_EXPANDED -> {
                            state = "EXPANDED"
                        }
                        BottomSheetBehavior.STATE_COLLAPSED -> {
                            state = "COLLAPSED"
                        }
                        BottomSheetBehavior.STATE_HIDDEN -> {
                            state = "HIDDEN"
                            dismiss()
                            behavior.state = BottomSheetBehavior.STATE_COLLAPSED
                        }
                    }
                }

                override fun onSlide(bottomSheet: View, slideOffset: Float) {}

            })
        }
    }


    private fun setListener() {
        val flNote1: FrameLayout = requireView().findViewById(R.id.fl_note1)
        val flNote2: FrameLayout = requireView().findViewById(R.id.fl_note2)
        //val flNote3: FrameLayout = requireView().findViewById(R.id.fl_note3)
        val flNote4: FrameLayout = requireView().findViewById(R.id.fl_note4)
        val flNote5: FrameLayout = requireView().findViewById(R.id.fl_note5)
        val flNote6: FrameLayout = requireView().findViewById(R.id.fl_note6)
        val flNote7: FrameLayout = requireView().findViewById(R.id.fl_note7)

        val imgNote1: ImageView = requireView().findViewById(R.id.img_note1)
        val imgNote2: ImageView = requireView().findViewById(R.id.img_note2)
        //val imgNote3: ImageView = requireView().findViewById(R.id.img_note3)
        val imgNote4: ImageView = requireView().findViewById(R.id.img_note4)
        val imgNote5: ImageView = requireView().findViewById(R.id.img_note5)
        val imgNote6: ImageView = requireView().findViewById(R.id.img_note6)
        val imgNote7: ImageView = requireView().findViewById(R.id.img_note7)

        val layoutAddImage: LinearLayout = requireView().findViewById(R.id.ll_add_image)
        val layoutDeleteNote: LinearLayout = requireView().findViewById(R.id.ll_delete_note)

        flNote1.setOnClickListener {
            imgNote1.visibility = View.VISIBLE
            imgNote2.visibility = View.GONE
            //imgNote3.visibility = View.GONE
            imgNote4.visibility = View.GONE
            imgNote5.visibility = View.GONE
            imgNote6.visibility = View.GONE
            imgNote7.visibility = View.GONE
            selectedColor = "#add6ff"

            val intent = Intent("bottom_sheet_action")
            intent.putExtra("action", "Blue")
            intent.putExtra("selected_color", selectedColor)
            LocalBroadcastManager.getInstance(requireContext()).sendBroadcast(intent)
            dismiss()

        }
        flNote2.setOnClickListener {
            imgNote1.setImageResource(0)
            imgNote2.setImageResource(R.drawable.ic_tick)
            //imgNote3.setImageResource(0)
            imgNote4.setImageResource(0)
            imgNote5.setImageResource(0)
            imgNote6.setImageResource(0)
            imgNote7.setImageResource(0)
            selectedColor = "#f1c27d"

            val intent = Intent("bottom_sheet_action")
            intent.putExtra("action", "Yellow")
            intent.putExtra("selected_color", selectedColor)
            LocalBroadcastManager.getInstance(requireContext()).sendBroadcast(intent)
            dismiss()

        }
//        flNote3.setOnClickListener {
//
//            imgNote1.setImageResource(0)
//            imgNote2.setImageResource(0)
//            imgNote3.setImageResource(R.drawable.ic_tick)
//            imgNote4.setImageResource(0)
//            imgNote5.setImageResource(0)
//            imgNote6.setImageResource(0)
//            imgNote7.setImageResource(0)
//            selectedColor = "#ffffff"
//
//            val intent = Intent("bottom_sheet_action")
//            intent.putExtra("action", "White")
//            intent.putExtra("selected_color", selectedColor)
//            LocalBroadcastManager.getInstance(requireContext()).sendBroadcast(intent)
//            dismiss()
//
//        }
        flNote4.setOnClickListener {

            imgNote1.setImageResource(0)
            imgNote2.setImageResource(0)
            //imgNote3.setImageResource(0)
            imgNote4.setImageResource(R.drawable.ic_tick)
            imgNote5.setImageResource(0)
            imgNote6.setImageResource(0)
            imgNote7.setImageResource(0)
            selectedColor = "#dec3c3"

            val intent = Intent("bottom_sheet_action")
            intent.putExtra("action", "Purple")
            intent.putExtra("selected_color", selectedColor)
            LocalBroadcastManager.getInstance(requireContext()).sendBroadcast(intent)
            dismiss()

        }
        flNote5.setOnClickListener {

            imgNote1.setImageResource(0)
            imgNote2.setImageResource(0)
            //imgNote3.setImageResource(0)
            imgNote4.setImageResource(0)
            imgNote5.setImageResource(R.drawable.ic_tick)
            imgNote6.setImageResource(0)
            imgNote7.setImageResource(0)
            selectedColor = "#0aebaf"

            val intent = Intent("bottom_sheet_action")
            intent.putExtra("action", "Green")
            intent.putExtra("selected_color", selectedColor)
            LocalBroadcastManager.getInstance(requireContext()).sendBroadcast(intent)
            dismiss()

        }
        flNote6.setOnClickListener {

            imgNote1.setImageResource(0)
            imgNote2.setImageResource(0)
            //imgNote3.setImageResource(0)
            imgNote4.setImageResource(0)
            imgNote5.setImageResource(0)
            imgNote6.setImageResource(R.drawable.ic_tick)
            imgNote7.setImageResource(0)
            selectedColor = "#ffbbee"

            val intent = Intent("bottom_sheet_action")
            intent.putExtra("action", "Orange")
            intent.putExtra("selected_color", selectedColor)
            LocalBroadcastManager.getInstance(requireContext()).sendBroadcast(intent)
            dismiss()

        }
        flNote7.setOnClickListener {

            imgNote1.setImageResource(0)
            imgNote2.setImageResource(0)
            //imgNote3.setImageResource(0)
            imgNote4.setImageResource(0)
            imgNote5.setImageResource(0)
            imgNote6.setImageResource(0)
            imgNote7.setImageResource(R.drawable.ic_tick)
            selectedColor = "#c0c5ce"

            val intent = Intent("bottom_sheet_action")
            intent.putExtra("action", "Black")
            intent.putExtra("selected_color", selectedColor)
            LocalBroadcastManager.getInstance(requireContext()).sendBroadcast(intent)
            dismiss()
        }
        layoutAddImage.setOnClickListener {
            val intent = Intent("bottom_sheet_action")
            intent.putExtra("action", "Image")
            LocalBroadcastManager.getInstance(requireContext()).sendBroadcast(intent)
        }
        layoutDeleteNote.setOnClickListener {
            val intent = Intent("bottom_sheet_action")
            intent.putExtra("action", "DeleteNote")
            LocalBroadcastManager.getInstance(requireContext()).sendBroadcast(intent)
            dismiss()
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_notes_bottom_sheet, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val layoutDeleteNote: LinearLayout = requireView().findViewById(R.id.ll_delete_note)

        if (noteId != -1){
            layoutDeleteNote.visibility = View.VISIBLE
        }else{
            layoutDeleteNote.visibility = View.GONE
        }


        setListener()
    }


}