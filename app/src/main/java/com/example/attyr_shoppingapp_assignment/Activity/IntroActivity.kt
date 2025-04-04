package com.example.attyr_shoppingapp_assignment.Activity

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.lifecycleScope
import com.example.attyr_shoppingapp_assignment.R
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class IntroActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent { IntroScreen() }

        // Launch coroutine in lifecycleScope
        lifecycleScope.launch {
            delay(3000) // Wait for 3 seconds
            startActivity(Intent(this@IntroActivity, MainActivity::class.java))
            finish() // Close IntroActivity so user can't navigate back to it
        }
    }
}

@Composable
@Preview
fun IntroScreen() {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White),
        contentAlignment = Alignment.Center
    ) {
        Image(
            painter = painterResource(id = R.drawable.background1),
            contentDescription = "Splash Screen",
            modifier = Modifier.fillMaxSize(),
            contentScale = ContentScale.Crop
        )

        Column(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Image(
                painter = painterResource(id = R.drawable.logo1),
                contentDescription = "Logo",
                modifier = Modifier.height(150.dp)
            )
            Spacer(modifier = Modifier.height(30.dp))
            Text(
                text = "Welcome to \nAttyr",
                textAlign = TextAlign.Center,
                color = Color.Black,
                fontFamily = FontFamily.Cursive,
                fontSize = 65.sp,
                fontWeight = FontWeight.Bold
            )
            Spacer(modifier = Modifier.height(30.dp))
            Text(
                text = "One Stop Destination for all your Fashion needs",
                textAlign = TextAlign.Center,
                color = Color.Black,
                fontFamily = FontFamily.Cursive,
                fontSize = 30.sp,
                fontWeight = FontWeight.Normal
            )
            Spacer(modifier = Modifier.height(65.dp))
            CircularProgressIndicator(
                modifier = Modifier.size(45.dp),
                color = colorResource(R.color.pink),
                strokeWidth = 2.5.dp
            )
        }
    }
}
