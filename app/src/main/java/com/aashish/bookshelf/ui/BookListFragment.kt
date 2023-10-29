package com.aashish.bookshelf.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.aashish.bookshelf.databinding.FragmentBookListBinding
import com.aashish.bookshelf.model.Book
import com.aashish.bookshelf.utils.Resource

class BookListFragment : Fragment() {
    private var _binding: FragmentBookListBinding? = null
    private val binding
        get() = checkNotNull(_binding) {
            "Binding is null. Is the view visible?"
        }
    private lateinit var viewModel: BookListFragmentViewModel
    private lateinit var yearAdapter: YearAdapter
    private var yearList = emptyList<Int>()

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
        viewModel = ViewModelProvider(
            this,
            bookListFragmentViewModelFactory
        )[BookListFragmentViewModel::class.java]


        val adapter = BookRecyclerViewAdapter { bookId: String ->
            val action = BookListFragmentDirections.actionBookListFragmentToBookDetailFragment(bookId)
            findNavController().navigate(action)
        }

        binding.rvBookList.apply {
            layoutManager = LinearLayoutManager(requireActivity())
            addItemDecoration(
                DividerItemDecoration(this.context, DividerItemDecoration.VERTICAL)
            )
            this.adapter = adapter
        }
        binding.rvYearList.layoutManager =
            LinearLayoutManager(requireActivity(), LinearLayoutManager.HORIZONTAL, false)

        viewModel.bookListResourceLiveData.observe(viewLifecycleOwner) { bookListResource ->
            when (bookListResource) {
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
        highlightAndScrollToMatchingYear(adapter)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun showList(adapter: BookRecyclerViewAdapter, bookList: List<Book>) {
        adapter.submitList(bookList)
        setupYearList(bookList)
        binding.rvBookList.isVisible = true
        hideError()
        hideProgressBar()
    }

    private fun setupYearList(bookList: List<Book>) {
        yearList = bookList.map { it.publicationYear }
            .distinct()
            .sortedDescending()

        binding.rvYearList.adapter = YearAdapter(yearList) { clickedYear ->
            val position = bookList.indexOfFirst {
                it.publicationYear == clickedYear
            }
            if (position != -1) {
                binding.rvBookList.scrollToPosition(position)
            }
        }
    }

    private fun highlightAndScrollToMatchingYear(adapter: BookRecyclerViewAdapter) {
        binding.rvBookList.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)
                val firstVisiblePosition =
                    (binding.rvBookList.layoutManager as LinearLayoutManager).findFirstVisibleItemPosition()
                val book = adapter.getBookAtPosition(firstVisiblePosition)
                viewModel.setHighlightedYear(book.publicationYear)
            }
        })

        viewModel.highlightedYear.observe(viewLifecycleOwner) { year ->
            if (::yearAdapter.isInitialized) {
                yearAdapter.updateHighlightedYear(year)
                val yearPosition = yearList.indexOf(year)
                if (yearPosition != -1) {
                    (binding.rvYearList.layoutManager as LinearLayoutManager).scrollToPositionWithOffset(
                        yearPosition,
                        0
                    )
                }
            }
        }
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