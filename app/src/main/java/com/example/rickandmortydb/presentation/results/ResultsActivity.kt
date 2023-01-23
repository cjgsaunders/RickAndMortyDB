package com.example.rickandmortydb.presentation.results

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.Card
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.example.rickandmortydb.R
import com.example.rickandmortydb.domain.objects.CharacterInfo
import com.example.rickandmortydb.ui.theme.RickAndMortyDBTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ResultsActivity : ComponentActivity() {

    private val viewModel: ResultsActivityViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        var searchCharacter = intent.getStringExtra("EXTRA_NAME")
        searchCharacter?.let {
            viewModel.loadSearchInfo(searchCharacter!!)
        }

        val randomCharacters = intent.getStringExtra("EXTRA_RANDOM")
        randomCharacters?.let {
            viewModel.getRandomCharacters()
            searchCharacter = "Random"
        }

        setContent {
            RickAndMortyDBTheme {
                Column(modifier = Modifier.fillMaxSize()) {
                    Column(
                        modifier = Modifier
                            .background(Color.LightGray)
                            .fillMaxWidth(),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Text(
                            text = "Search for $searchCharacter",
                            style = TextStyle(fontSize = 18.sp, fontWeight = FontWeight.Bold),
                            modifier = Modifier
                                .align(Alignment.CenterHorizontally)
                                .padding(20.dp)
                        )
                    }

                    viewModel.state.value.errorMessage?.let {
                        Column(
                            verticalArrangement = Arrangement.Center,
                            modifier = Modifier.fillMaxSize()
                        ) {

                            Text(
                                text = "Sorry! Character not found",
                                style = TextStyle(fontSize = 18.sp, fontWeight = FontWeight.Bold),
                                modifier = Modifier.align(Alignment.CenterHorizontally)
                            )
                        }
                    }

                    viewModel.state.value.searchData?.let {
                        CharactersList(viewModel.state.value.searchData!!.result)
                    }
                }
            }
        }
    }

    @Composable
    fun CharactersList(characters: List<CharacterInfo>) {
        LazyColumn(state = rememberLazyListState()) {
            items(characters.size) { i ->
                val currentCharacter = characters[i]
                Card(
                    shape = MaterialTheme.shapes.small,
                    elevation = 8.dp,
                    modifier = Modifier
                        .padding(
                            bottom = 6.dp,
                            top = 6.dp
                        )
                        .fillMaxWidth()
                ) {
                    Row() {
                        AsyncImage(
                            model = characters[i].characterImage,
                            contentDescription = null,
                            placeholder = painterResource(R.drawable.logo),
                            contentScale = ContentScale.Crop,
                            modifier = Modifier.size(140.dp)

                        )
                        Column(
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            Text(
                                characters[i].characterName,
                                style = TextStyle(fontSize = 18.sp, fontWeight = FontWeight.Bold),
                                modifier = Modifier.align(Alignment.CenterHorizontally)
                            )
                            Text(
                                "${currentCharacter.status} - ${currentCharacter.species}",
                                style = TextStyle(fontSize = 12.sp),
                                modifier = Modifier.padding(start = 10.dp, bottom = 10.dp)
                            )
                            Text(
                                "Last known location:",
                                style = TextStyle(
                                    fontWeight = FontWeight.Bold,
                                    color = Color.DarkGray,
                                    fontSize = 10.sp
                                ), modifier = Modifier.padding(start = 10.dp)
                            )
                            Text(
                                currentCharacter.lastLocation,
                                style = TextStyle(fontSize = 14.sp),
                                modifier = Modifier.padding(start = 10.dp, bottom = 10.dp)
                            )
                            Text(
                                "Place of origin: ",
                                style = TextStyle(
                                    fontWeight = FontWeight.Bold,
                                    color = Color.DarkGray,
                                    fontSize = 10.sp
                                ), modifier = Modifier.padding(start = 10.dp)
                            )
                            Text(
                                currentCharacter.firstLocation,
                                style = TextStyle(fontSize = 14.sp),
                                modifier = Modifier.padding(start = 10.dp)
                            )
                        }
                    }
                }
            }
        }
    }
}