package com.example.attyr_shoppingapp_assignment.Activity

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout
import com.example.attyr_shoppingapp_assignment.Model.ListItemsFullSize
import com.example.attyr_shoppingapp_assignment.R
import com.example.attyr_shoppingapp_assignment.ViewModel.MainViewModel
import com.example.attyr_shoppingapp_assignment.ui.theme.Attyr_ShoppingApp_AssignmentTheme
import com.example.attyr_shoppingapp_assignment.ui.theme.poppinsFontFamily

class ListItemsActivity : ComponentActivity() {

    private val viewModel = MainViewModel()
    private var id: String = ""
    private var title: String = ""


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        id = intent.getStringExtra("id") ?: ""
        title = intent.getStringExtra("title") ?: ""

        setContent {
            Attyr_ShoppingApp_AssignmentTheme {
                ListItemScreen(
                    title = title,
                    id = id,
                    onBackClick = { finish() },
                    viewModel = viewModel
                )
            }
        }
    }


    @Composable
    fun ListItemScreen(
        title: String,
        id: String,
        onBackClick: () -> Unit,
        viewModel: MainViewModel
    ) {
        val items by viewModel.loadFiltered(id).observeAsState(emptyList())
        var isLoading by remember { mutableStateOf(true) }

        LaunchedEffect(id) {
            viewModel.loadFiltered(id)
        }
        Column(
            modifier = Modifier.Companion
                .fillMaxSize()
        ) {
            ConstraintLayout(
                modifier = Modifier.Companion.padding(top = 50.dp, start = 16.dp, end = 30.dp)
            ) {
                val (backBtn, cartTxt) = createRefs()

                Text(
                    text = title,
                    modifier = Modifier.Companion
                        .fillMaxWidth()
                        .constrainAs(cartTxt) { centerTo(parent) },
                    textAlign = TextAlign.Companion.Center,
                    fontFamily = poppinsFontFamily,
                    fontSize = 25.sp,
                    fontWeight = FontWeight.Companion.Bold
                )

                Image(
                    painter = painterResource(id = R.drawable.back),
                    contentDescription = "Back",
                    modifier = Modifier.Companion
                        .clickable {
                            onBackClick()
                        }
                        .constrainAs(backBtn) {
                            start.linkTo(parent.start)
                            top.linkTo(parent.top)
                            bottom.linkTo(parent.bottom)
                        }

                )

            }
            if (isLoading) {
                Box(
                    modifier = Modifier.Companion.fillMaxSize(),
                    contentAlignment = Alignment.Companion.Center
                ) {
                    CircularProgressIndicator(modifier = Modifier.size(40.dp),
                        color = colorResource(R.color.pink),
                        strokeWidth = 2.5.dp)
                }
            } else {
                ListItemsFullSize(items)
            }

        }
        LaunchedEffect(items) {
            isLoading = items.isEmpty()
        }

    }
}