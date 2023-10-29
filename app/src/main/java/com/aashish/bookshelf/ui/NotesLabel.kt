package com.aashish.bookshelf.ui

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import com.aashish.bookshelf.databinding.LayoutNotesLabelBinding
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.google.android.material.snackbar.Snackbar
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textfield.TextInputLayout

class NotesLabel(private val binding: LayoutNotesLabelBinding, onClick: (String, String) -> Unit) {

    init {
        with(binding) {
            /* Handle click on label to edit notes tag text */
            tvLabel.setOnClickListener {
                showEditDialog(tvLabel.text.toString(), onClick)
            }
        }
    }

    var text: String
        get() = binding.tvLabel.text.toString()
        set(value) {
            binding.tvLabel.text = value
        }

    fun attachTo(container: ViewGroup) {
        container.addView(binding.root)
    }

    private fun showEditDialog(currentText: String, onPositiveClick: (String, String) -> Unit) {
        val context = binding.root.context

        val editText = TextInputEditText(context).apply {
            setText(currentText)
        }
        val inputLayout = TextInputLayout(context).apply {
            addView(editText)
        }

        val dialog = MaterialAlertDialogBuilder(context)
            .setTitle("Edit Label")
            .setView(inputLayout)
            .setPositiveButton("Save") { _, _ ->
                val newText = editText.text.toString()
                if (newText.isNotBlank()) {
                    onPositiveClick(currentText, newText)
                } else {
                    Snackbar.make(binding.root, "Update Failed! New label cannot be empty", Snackbar.LENGTH_SHORT).show()
                }
            }
            .setNegativeButton("Cancel", null)
            .create()

        dialog.show()
    }

    companion object {
        fun inflate(context: Context, onClick: (String, String) -> Unit): NotesLabel {
            val binding = LayoutNotesLabelBinding.inflate(LayoutInflater.from(context))
            return NotesLabel(binding, onClick)
        }
    }
}