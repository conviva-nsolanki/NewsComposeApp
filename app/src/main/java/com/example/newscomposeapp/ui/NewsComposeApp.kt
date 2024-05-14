package com.example.newscomposeapp.ui

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.platform.SoftwareKeyboardController
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.example.newscomposeapp.MainViewModel
import com.example.newscomposeapp.data.Article
import com.example.newscomposeapp.ui.theme.NewsComposeAppTheme

@Composable
fun NewsComposeApp(viewModel: MainViewModel) {

    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    val keyboardController = LocalSoftwareKeyboardController.current

    NewsComposeAppTheme {
        Scaffold { paddingValues ->
            Column(
                modifier = Modifier.padding(paddingValues)
            ) {
                ComposeSearchBar(
                    modifier = Modifier,
                    onSearchTextChange = viewModel::onSearchTextChange,
                    keyboardController = keyboardController
                )
                ArticlesContent(
                    modifier = Modifier,
                    contentPadding = paddingValues,
                    articles = uiState.articleList,
                    uiState.isLoading,
                    keyboardController = keyboardController
                )
            }
        }
    }
}

@Composable
fun ComposeSearchBar(
    modifier: Modifier = Modifier,
    onSearchTextChange: (String, Boolean) -> Unit,
    keyboardController: SoftwareKeyboardController?
) {

    var searchText by remember {
        mutableStateOf("")
    }

    OutlinedTextField(
        value = searchText,
        onValueChange = {
            searchText = it
            onSearchTextChange(searchText, false)
        },
        label = { Text(text = "Search Article") },
        modifier = modifier
            .fillMaxWidth()
            .padding(8.dp),
        singleLine = true,
        keyboardActions = KeyboardActions(
            onDone = {
                onSearchTextChange(searchText, true)
                keyboardController?.hide()
            },
            onGo = {
                onSearchTextChange(searchText, true)
                keyboardController?.hide()
            }
        ),
    )

}

@Composable
fun ArticlesContent(
    modifier: Modifier = Modifier,
    contentPadding: PaddingValues = PaddingValues(0.dp),
    articles: List<Article>,
    isLoading: Boolean,
    keyboardController: SoftwareKeyboardController?
) {
    if (isLoading) {
        Box(
            modifier = Modifier.fillMaxWidth(),
            contentAlignment = Alignment.Center
        ) {
            CircularProgressIndicator(
                modifier = modifier
                    .width(48.dp),
                color = MaterialTheme.colorScheme.secondary,
                trackColor = MaterialTheme.colorScheme.surfaceVariant,
            )
        }
    } else {
        keyboardController?.hide()

        LazyColumn(
            contentPadding = contentPadding,
            modifier = modifier.padding(16.dp)
        ) {
            items(articles) { article ->
                ArticleContent(
                    modifier = modifier,
                    article = article
                )
            }
        }
    }
}

@Composable
fun ArticleContent(
    modifier: Modifier = Modifier,
    article: Article
) {
    Column(
        modifier = modifier
    ) {
        ArticleImageContent(
            modifier = modifier,
            article.imageUrl
        )
        TitleContent(
            modifier = modifier,
            title = article.title
        )
        DescriptionContent(
            modifier = modifier,
            description = article.abstract
        )
    }
}

@Composable
fun ArticleImageContent(
    modifier: Modifier = Modifier,
    imageUrl: String) {

    AsyncImage(
        modifier = modifier
            .fillMaxWidth()
            .height(200.dp),
        model = ImageRequest.Builder(LocalContext.current)
        .data(imageUrl)
        .crossfade(true)
        .build(),
        contentScale = ContentScale.Crop,
        contentDescription = "news article")
    
}

@Composable
fun TitleContent(modifier: Modifier = Modifier, title: String) {
    Text(
        modifier = modifier,
        fontSize = 18.sp,
        fontWeight = FontWeight.Bold,
        text = title
    )
}

@Composable
fun DescriptionContent(modifier: Modifier = Modifier, description: String) {
    Text(
        modifier = modifier.padding(bottom = 8.dp),
        text = description
    )
}

@Preview
@Composable
fun PrevArticleContent(modifier: Modifier = Modifier) {

    ArticleContent(article = Article(
        url = "url",
        publishDate = "test publish date",
        title = "Today's top news",
        abstract = "So many things going on",
        imageUrl = ""
    ))

}