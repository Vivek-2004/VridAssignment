package com.vrid.assignment.viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.vrid.assignment.data_models.BlogPost
import com.vrid.assignment.network.blogService
import kotlinx.coroutines.launch

class BlogViewModel : ViewModel() {
    private val _repository = blogService
    var blogPosts by mutableStateOf<List<BlogPost>>(emptyList())
    var isLoading by mutableStateOf(true)
    var errorMessage by mutableStateOf<String?>(null)

    init {
        loadBlogPosts()
    }

    fun loadBlogPosts() {
        viewModelScope.launch {
            try {
                blogPosts = _repository.getBlogPosts()
                isLoading = false
            } catch (e: Exception) {
                errorMessage = "Error loading blogs: ${e.message}"
                isLoading = false
            }
        }
    }
}