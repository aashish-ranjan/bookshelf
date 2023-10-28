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
import com.aashish.bookshelf.model.Book
import com.bumptech.glide.Glide

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

}