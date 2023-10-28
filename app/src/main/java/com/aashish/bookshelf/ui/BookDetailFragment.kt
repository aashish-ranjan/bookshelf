package com.aashish.bookshelf.ui

import android.app.AlertDialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.navArgs
import com.aashish.bookshelf.R
import com.aashish.bookshelf.databinding.FragmentBookDetailBinding
import com.aashish.bookshelf.model.Book
import com.bumptech.glide.Glide
import com.google.android.material.snackbar.Snackbar

class BookDetailFragment : Fragment() {
    private var _binding: FragmentBookDetailBinding? = null
    private val binding
        get() = checkNotNull(_binding) {
            "Binding is null. Is the view visible?"
        }
    private lateinit var viewModel: BookDetailFragmentViewModel
    private val args: BookDetailFragmentArgs by navArgs()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentBookDetailBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val bookRepository = (requireActivity() as BooksActivity).bookRepository
        val bookDetailFragmentViewModelFactory = BookDetailFragmentViewModelFactory(bookRepository)
        viewModel = ViewModelProvider(
            this,
            bookDetailFragmentViewModelFactory
        )[BookDetailFragmentViewModel::class.java]

        initUi(args.book)
        setupClickListeners()
        viewModel.markedFavouriteLiveData.observe(viewLifecycleOwner) { markedFavourite ->
            binding.ivFavourite.setImageResource(
                if (markedFavourite == true) {
                    R.drawable.ic_favourite_selected
                } else {
                    R.drawable.ic_favourite_unselected
                }
            )
        }
        viewModel.noteLabelListLiveData.observe(viewLifecycleOwner) { noteTagList ->
            binding.labelContainer.removeAllViews()
            noteTagList?.forEach { noteTag ->
                addNewNoteTag(noteTag)
            }
        }
    }

    private fun addNewNoteTag(text: String) {
        val notesLabel = NotesLabel.inflate(requireContext()) { old: String, new: String->
            viewModel.updateLabel(old, new)
        }
        notesLabel.text = text
        notesLabel.attachTo(binding.labelContainer)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun initUi(book: Book) {
        with(binding) {
            Glide.with(requireContext()).load(book.imageUrl)
                .placeholder(R.drawable.img_placeholder).into(binding.ivCoverPicture)
            tvTitle.text = book.title
            tvRating.text = book.score.toString()
            tvPublicationYear.text = book.publicationYear.toString()
        }
    }

    private fun setupClickListeners() {
        with(binding) {
            ivFavourite.setOnClickListener {
                viewModel.toggleFavourite()
            }
            ivAddLabel.setOnClickListener {
                showInputDialog()
            }
        }
    }

    private fun showInputDialog() {
        val editText = EditText(requireContext())
        editText.hint = "Enter label text"

        val dialog = AlertDialog.Builder(requireContext())
            .setTitle("Add New Label")
            .setView(editText)
            .setPositiveButton("Add") { _, _ ->
                val labelText = editText.text.toString()
                if (labelText.isNotEmpty()) {
                    viewModel.addNewLabel(labelText)
                } else {
                    Snackbar.make(binding.root, "Label cannot be empty", Snackbar.LENGTH_SHORT).show()
                }
            }
            .setNegativeButton("Cancel", null)
            .create()

        dialog.show()
    }

}