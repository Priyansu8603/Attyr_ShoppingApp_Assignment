package com.example.attyr_shoppingapp_assignment.Activity

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.attyr_shoppingapp_assignment.R
import com.example.attyr_shoppingapp_assignment.ui.theme.Attyr_ShoppingApp_AssignmentTheme

class ExploreActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            Attyr_ShoppingApp_AssignmentTheme {
                ExploreScreen()
            }
        }
    }
}
@Composable
@Preview
fun ExploreScreen() {
    Box(
        modifier = Modifier
            .fillMaxSize()
    ) {
        // Background Image
        Image(
            painter = painterResource(id = R.drawable.background1), // Replace with your image resource
            contentDescription = "Background Image",
            modifier = Modifier.fillMaxSize(),
            contentScale = ContentScale.FillBounds // Adjusts the image to fill the screen
        )

        // Overlay Text
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp), // Optional padding for better spacing
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = "\uD83D\uDECD \"Explore.\n Discover. \nEnjoy!\"",
                color = colorResource(R.color.heavy_pink),
                fontSize = 50.sp,
                fontFamily = FontFamily.Cursive,
                textAlign = TextAlign.Center,
                fontWeight = FontWeight.SemiBold,
            )
        }
    }
}
