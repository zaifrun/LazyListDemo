package com.example.lazylistdemo

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import coil.compose.rememberAsyncImagePainter
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.ui.Alignment
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.foundation.lazy.items
import android.widget.Toast
import androidx.compose.ui.platform.LocalContext
import androidx.compose.foundation.clickable

import androidx.compose.ui.tooling.preview.Preview
import com.example.lazylistdemo.ui.theme.LazyListDemoTheme

class MainActivity : ComponentActivity() {

    private lateinit var carList: List<String>

    override fun onCreate(savedInstanceState: Bundle?) {

        carList = resources.getStringArray(R.array.car_array).toList()

        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            LazyListDemoTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    MainScreen(
                        carList = carList,
                        modifier = Modifier.padding(innerPadding)
                    )
                }
            }
        }
    }
}

@Composable
fun MainScreen(carList: List<String>, modifier: Modifier = Modifier) {
    ListPane(modifier, carList)
}

@Composable
fun ImageLoader(item: String, modifier: Modifier = Modifier) {

    val url = "https://www.payloadbooks.com/book_examples/car_logos/" + item.substringBefore(" ") + "_logo.png"

    Image(
        painter = rememberAsyncImagePainter(url),
        contentDescription = "car image",
        contentScale = ContentScale.Fit, //flere muligheder her man kan lege med.
        modifier = modifier
    )
}

@Composable
fun CarListItem(
    item: String,
    onItemClick: (String) -> Unit,
    modifier: Modifier = Modifier) {
    Card(
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.onPrimary
        ),
        modifier = modifier
            .padding(3.dp)
            .fillMaxWidth()
            .clickable { onItemClick(item) },
        elevation = CardDefaults.cardElevation(defaultElevation = 1.dp)
    ) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            ImageLoader(item, modifier = Modifier.size(75.dp))
            Spacer(modifier = Modifier.width(8.dp))
            Text(
                text = item,
                style = MaterialTheme.typography.headlineSmall,
                modifier = Modifier.padding(8.dp)
            )
        }
    }
}

@Composable
fun ListPane(
    modifier: Modifier = Modifier,
    carList: List<String>
) {

    val context = LocalContext.current

    val onListItemClick = { text : String ->

        Toast.makeText(
            context,
            text,
            Toast.LENGTH_SHORT
        ).show()
    }

    LazyColumn(modifier) {
        //itererer over hver item, i dette tilfælde en string, i listen
        //og laver en ny composable her.
        items(carList) { model ->
            CarListItem(model, onItemClick = onListItemClick)
        }
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun MainPreview() {

    val carList: List<String> = listOf(
        "Cadillac Eldorado",
        "Ford Fairlane",
        "Plymouth Fury"
    )

    LazyListDemoTheme {
        Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
            MainScreen(
                carList = carList,
                modifier = Modifier.padding(innerPadding)
            )
        }
    }
}