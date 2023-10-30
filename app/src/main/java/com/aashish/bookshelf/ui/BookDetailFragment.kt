package com.aashish.bookshelf.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.navArgs
import com.aashish.bookshelf.R
import com.aashish.bookshelf.databinding.FragmentBookDetailBinding
import com.bumptech.glide.Glide
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.google.android.material.snackbar.Snackbar
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textfield.TextInputLayout

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
        val userBookInfoRepository = (requireActivity() as BooksActivity).userBookInfoRepository
        val bookRepository = (requireActivity() as BooksActivity).bookRepository
        val authManager = (requireActivity() as BooksActivity).authManager
        val bookDetailFragmentViewModelFactory = BookDetailFragmentViewModelFactory(
            args.bookId,
            authManager.getLastLoginUserId(),
            userBookInfoRepository,
            bookRepository
        )
        viewModel = ViewModelProvider(
            this,
            bookDetailFragmentViewModelFactory
        )[BookDetailFragmentViewModel::class.java]

        setupClickListeners()
        setUpObservers()
    }

    private fun addNewNoteTag(text: String) {
        val notesLabel = NotesLabel.inflate(requireContext()) { old: String, new: String ->
            viewModel.updateLabel(old, new)
        }
        notesLabel.text = text
        notesLabel.attachTo(binding.labelContainer)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
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

    private fun setUpObservers() {
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
        viewModel.bookLiveData.observe(viewLifecycleOwner) { book ->
            book?.let {
                with(binding) {
                    Glide.with(requireContext()).load(it.imageUrl)
                        .placeholder(R.drawable.img_placeholder).into(binding.ivCoverPicture)
                    tvTitle.text = it.title
                    tvRating.text = it.score.toString()
                    tvPublicationYear.text = it.publicationYear.toString()
                }
            }
        }
    }

    private fun showInputDialog() {
        val context = requireContext()
        val textInputLayout = TextInputLayout(context).apply {
            addView(TextInputEditText(context))
        }

        val dialog = MaterialAlertDialogBuilder(context)
            .setTitle(getString(R.string.add_label))
            .setView(textInputLayout)
            .setPositiveButton(getString(R.string.add)) { _, _ ->
                val labelText = textInputLayout.editText?.text.toString()
                if (labelText.isNotEmpty()) {
                    viewModel.addNewLabel(labelText)
                } else {
                    Snackbar.make(binding.root, "", Snackbar.LENGTH_SHORT)
                        .show()
                }
            }
            .setNegativeButton(getString(R.string.cancel), null)
            .create()

        dialog.show()
    }

}