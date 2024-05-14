package com.example.newscomposeapp

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.newscomposeapp.data.Article
import com.example.newscomposeapp.data.repository.NewYorkTimesRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.filter
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

data class NewsArticleUiState(
    var articleList: List<Article> = listOf(),
    var query: String = "",
    var isLoading: Boolean = false
)

@OptIn(FlowPreview::class)
@HiltViewModel
class MainViewModel @Inject constructor(repository: NewYorkTimesRepository): ViewModel() {

    companion object {
        const val TAG = "MainViewModel"
    }

    private val _uiState = MutableStateFlow(NewsArticleUiState())
    val uiState: StateFlow<NewsArticleUiState> = _uiState

    private val _searchText = MutableStateFlow("")
    private val searchText: StateFlow<String> = _searchText

    init {
        viewModelScope.launch {
            searchText.filter { newText ->
                newText.isNotBlank() && newText.length >= 3
            }.debounce(3000L).collect { query ->
                Log.d(TAG, "got the result for: $query")
                try {
                    val searchResult = repository.getSearchResult(query)
                    _uiState.update {
                        it.copy(
                            articleList = searchResult,
                            isLoading = false
                        )
                    }
                } catch (e: Exception) {
                    Log.d(TAG, "e: $e")
                }
            }
        }

    }

    fun onSearchTextChange(newText: String, fromKeyboardAction: Boolean = false) {
        if (newText != _searchText.value) {
            _searchText.value = newText
            _uiState.update {
                it.copy(
                    isLoading = true
                )
            }
        }
    }


}