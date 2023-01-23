package com.example.rickandmortydb.presentation

import android.app.ActivityOptions
import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.material.Button
import androidx.compose.material.Divider
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Text
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.rickandmortydb.R
import com.example.rickandmortydb.presentation.results.ResultsActivity
import com.example.rickandmortydb.ui.theme.RickAndMortyDBTheme

class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            var searchBox by remember { mutableStateOf("") }

            RickAndMortyDBTheme {
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Image(
                        painter = painterResource(id = R.drawable.logo),
                        contentDescription = stringResource(id = R.string.logo),
                        modifier = Modifier
                            .padding(26.dp)
                            .fillMaxWidth()
                    )
                    Text(
                        text = stringResource(id = R.string.character_database),
                        style = TextStyle(fontSize = 18.sp, fontWeight = FontWeight.Bold)
                    )

                    Divider(
                        color = Color.LightGray,
                        thickness = 1.dp,
                        startIndent = 40.dp,
                        modifier = Modifier.padding(top = 40.dp, end = 40.dp)
                    )

                    OutlinedTextField(
                        value = searchBox,
                        onValueChange = { searchBox = it },
                        label = { Text("Enter Character Name") },
                        placeholder = { Text("ex: Rick Sanchez") },
                        modifier = Modifier.padding(top = 30.dp),
                        singleLine = true,
                        keyboardActions = KeyboardActions(onDone = {showSearchDetails(searchBox)}),
                    )

                    Button(onClick = {
                        showSearchDetails(searchBox)
                    }, content = {
                        Text(text = "Search")
                    })

                    Divider(
                        color = Color.LightGray,
                        thickness = 1.dp,
                        startIndent = 40.dp,
                        modifier = Modifier.padding(top = 40.dp, end = 40.dp)
                    )



                    Button(modifier = Modifier.padding(40.dp), onClick = {
                        randomSearch()
                    }, content = {
                        Text(text = "Show Random Characters")
                    })

                    Divider(
                        color = Color.LightGray,
                        thickness = 1.dp,
                        startIndent = 40.dp,
                        modifier = Modifier.padding(end = 40.dp)
                    )

                    Spacer(modifier = Modifier.weight(1f))
                    Text(
                        text = "Made using Rick and Morty API",
                        modifier = Modifier.align(Alignment.Start)
                    )
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Text(text = "Author: CJGSaunders")
                        Text(text = "Version 1.0")
                    }
                }
            }
        }
    }


    private fun showSearchDetails(character: String) {
        val resultsActivityIntent = Intent(
            this,
            ResultsActivity::class.java
        )
        resultsActivityIntent.putExtra("EXTRA_NAME", character)
        val options = ActivityOptions.makeSceneTransitionAnimation(this)
        startActivity(resultsActivityIntent, options.toBundle())
    }

    private fun randomSearch() {
        val resultsActivityIntent = Intent(
            this,
            ResultsActivity::class.java
        )
        resultsActivityIntent.putExtra("EXTRA_RANDOM", "random")
        val options = ActivityOptions.makeSceneTransitionAnimation(this)
        startActivity(resultsActivityIntent, options.toBundle())
    }
}