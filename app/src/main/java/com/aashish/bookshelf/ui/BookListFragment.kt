package com.aashish.bookshelf.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.aashish.bookshelf.databinding.FragmentBookListBinding
import com.aashish.bookshelf.model.Book
import com.aashish.bookshelf.utils.Resource

class BookListFragment: Fragment() {
    private var _binding: FragmentBookListBinding? = null
    private val binding
        get() = checkNotNull(_binding) {
            "Binding is null. Is the view visible?"
        }
    private lateinit var viewModel: BookListFragmentViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentBookListBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val bookRepository = (requireActivity() as BooksActivity).bookRepository
        val bookListFragmentViewModelFactory = BookListFragmentViewModelFactory(bookRepository)
        viewModel = ViewModelProvider(this, bookListFragmentViewModelFactory)[BookListFragmentViewModel::class.java]


        binding.rvBookList.layoutManager = LinearLayoutManager(requireActivity())
        val adapter = BookRecyclerViewAdapter()
        binding.rvBookList.adapter = adapter
        viewModel.bookListLiveData.observe(viewLifecycleOwner) { bookListResource ->
            when(bookListResource) {
                is Resource.Success -> {
                    showList(adapter, bookListResource.data ?: emptyList())
                }
                is Resource.Error -> {
                    showError(bookListResource.message ?: "Error occurred")
                }
                is Resource.Loading -> {
                    showProgressBar()
                }
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun showList(adapter: BookRecyclerViewAdapter, bookList: List<Book>) {
        adapter.submitList(bookList)
        binding.rvBookList.isVisible = true
        hideError()
        hideProgressBar()
    }

    private fun hideList() {
        binding.rvBookList.isVisible = false
    }

    private fun showProgressBar() {
        binding.progressBar.isVisible = true
        hideList()
        hideError()
    }

    private fun hideProgressBar() {
        binding.progressBar.isVisible = false
    }

    private fun showError(errorMessage: String) {
        binding.tvError.text = errorMessage
        binding.tvError.isVisible = true
        hideList()
        hideProgressBar()
    }

    private fun hideError() {
        binding.tvError.isVisible = false
    }

}