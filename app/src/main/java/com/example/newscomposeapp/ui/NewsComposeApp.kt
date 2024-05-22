package com.example.newscomposeapp.ui

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.platform.SoftwareKeyboardController
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.conviva.apptracker.controller.TrackerController
import com.example.newscomposeapp.MainViewModel
import com.example.newscomposeapp.R
import com.example.newscomposeapp.data.Article
import com.example.newscomposeapp.ui.theme.NewsComposeAppTheme
import org.json.JSONObject

@Composable
fun NewsComposeApp(viewModel: MainViewModel, trackerController: TrackerController?) {
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
                    onSettingsSelected = viewModel::onSettingsClicked,
                    keyboardController = keyboardController
                )
                ArticlesContent(
                    modifier = Modifier,
                    contentPadding = paddingValues,
                    articles = uiState.articleList,
                    uiState.isLoading,
                    keyboardController = keyboardController,
                    trackerController
                )
            }
        }
    }
}

@Composable
fun ComposeSearchBar(
    modifier: Modifier = Modifier,
    onSearchTextChange: (String, Boolean) -> Unit,
    onSettingsSelected: (Boolean) -> Unit,
    keyboardController: SoftwareKeyboardController?,
) {

    var searchText by remember {
        mutableStateOf("")
    }

    Row(
        modifier = Modifier.height(IntrinsicSize.Min)
    ) {
        OutlinedTextField(
            value = searchText,
            onValueChange = {
                searchText = it
                onSearchTextChange(searchText, false)
            },
            label = { Text(text = "Search Article") },
            modifier = modifier
                .fillMaxWidth(0.9f)
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
        IconButton(
            modifier = modifier
                .fillMaxWidth()
                .fillMaxHeight(),
            onClick = { onSettingsSelected(true) }) {
            Image(
                painter = painterResource(id = R.drawable.ic_settings_24),
                contentDescription = "settings",
                modifier = modifier.fillMaxWidth().fillMaxHeight().padding(end = 8.dp),
                colorFilter = ColorFilter.tint(MaterialTheme.colorScheme.primary)
            )

        }
    }

}

@Composable
fun ArticlesContent(
    modifier: Modifier = Modifier,
    contentPadding: PaddingValues = PaddingValues(0.dp),
    articles: List<Article>,
    isLoading: Boolean,
    keyboardController: SoftwareKeyboardController?,
    trackerController: TrackerController?
) {
    TrackScreenView(trackerController, "Search Screen Viewed")
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
                    article = article,
                    trackerController
                )
            }
        }
    }
}

@Composable
fun ArticleContent(
    modifier: Modifier = Modifier,
    article: Article,
    trackerController: TrackerController? = null
) {
    Column(
        modifier = modifier.clickable {
            val json = JSONObject()
            json.put("title", article.title)
            trackerController?.trackCustomEvent(
                "Article Clicked",
                json
            )
        }
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

    ArticleContent(
        article = Article(
            url = "url",
            publishDate = "test publish date",
            title = "Today's top news",
            abstract = "So many things going on",
            imageUrl = ""
        )
    )

}

@Composable
fun TrackScreenView(
    trackerController: TrackerController?,
    screenName: String,
) {
    DisposableEffect(Unit) {
        val json = JSONObject().also {
            it.put("screen name", screenName)
        }
        trackerController?.trackCustomEvent("Screen View", json)
        onDispose {
            // screen exit
        }
    }
}