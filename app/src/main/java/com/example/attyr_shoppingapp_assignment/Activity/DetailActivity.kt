package com.example.attyr_shoppingapp_assignment.Activity

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout
import coil.compose.rememberAsyncImagePainter
import com.example.attyr_shoppingapp_assignment.Model.ItemsModel
import com.example.attyr_shoppingapp_assignment.R
import com.example.attyr_shoppingapp_assignment.ui.theme.Attyr_ShoppingApp_AssignmentTheme
import com.example.attyr_shoppingapp_assignment.ui.theme.poppinsFontFamily
import com.example.project1762.Helper.ManagmentCart

class DetailActivity : ComponentActivity() {
    private lateinit var item: ItemsModel
    private lateinit var managementCart: ManagmentCart

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        item = intent.getSerializableExtra("object") as ItemsModel
        managementCart = ManagmentCart(this)

        setContent {
            Attyr_ShoppingApp_AssignmentTheme {
                DetailScreen(
                    item = item,
                    onBackClick = { finish() },
                    onAddToCartClick = {
                        item.numberInCart = 1
                        managementCart.insertItem(item)
                    },
                    onCartClick = {

                    }
                )
            }
        }
    }
}

@Composable
private fun DetailActivity.DetailScreen(
    item: ItemsModel,
    onBackClick: () -> Unit,
    onAddToCartClick: () -> Unit,
    onCartClick: () -> Unit
) {
    var selectedImageUrl by remember { mutableStateOf(item.picUrl.first()) }
    var selectedModelIndex by remember { mutableStateOf(-1) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
            .verticalScroll(rememberScrollState())

    ) {

        ConstraintLayout(
            modifier = Modifier
                .fillMaxWidth()
                .height(430.dp)
                .padding(bottom = 30.dp)
        ) {

            val (back, fav, mainImage, thumbnail) = createRefs()
            Image(
                painter = rememberAsyncImagePainter(model = selectedImageUrl),
                contentDescription = "Main Image",
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .fillMaxSize()
                    .background(
                        colorResource(R.color.pink),
                        shape = RoundedCornerShape(10.dp)
                    )
                    .constrainAs(mainImage) {
                        top.linkTo(parent.top)
                        bottom.linkTo(parent.bottom)
                        start.linkTo(parent.start)
                        end.linkTo(parent.end)
                    }

            )

            Image(
                painter = painterResource(id = R.drawable.back),
                contentDescription = "Back",
                modifier = Modifier
                    .padding(top = 48.dp,start = 16.dp)
                    .constrainAs(back) {
                        top.linkTo(parent.top)
                        start.linkTo(parent.start)
                    }
                    .clickable { onBackClick() }
            )

            LazyRow(
                modifier = Modifier
                    .padding(vertical = 16.dp)
                    .background(
                        color = colorResource(R.color.white),
                        shape = RoundedCornerShape(10.dp)
                    )
                    .constrainAs(thumbnail) {
                        start.linkTo(parent.start)
                        bottom.linkTo(parent.bottom)
                        end.linkTo(parent.end)
                    }
            ) {
                items(item.picUrl) { imageUrl ->
                    ImageThumbnail(
                        imageUrl = imageUrl,
                        isSelected = selectedImageUrl == imageUrl,
                        onClick = { selectedImageUrl = imageUrl }
                    )
                }

            }
        }
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .padding(top= 16.dp)
                .padding(horizontal = 16.dp)
        ) {
            Text(
                text = item.title,
                fontSize = 20.sp,
                fontFamily = poppinsFontFamily,
                fontWeight = FontWeight.Bold,
                color = Color.DarkGray,
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f)
                    .padding(end = 16.dp)
            )

            Text(
                text = "$${item.price}",
                fontSize = 20.sp,
                fontFamily = poppinsFontFamily,
                fontWeight = FontWeight.Bold,
                color = Color.DarkGray,
            )

        }
        RatingBar(rating = item.rating)

        ModelSelector(
            models = item.model,
            selectedModelIndex = selectedModelIndex,
            onModelSelected = { selectedModelIndex = it }
        )

        Text(
            text =  item.description,
            fontSize = 20.sp,
            fontFamily = poppinsFontFamily,
            fontWeight = FontWeight.SemiBold,
            color = Color.DarkGray,
            modifier = Modifier
                .padding(16.dp)

        )

        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .fillMaxWidth()
                .padding(start=10.dp, end = 10.dp, bottom = 25.dp)
        ){
            IconButton(
                onClick = onCartClick,
                modifier = Modifier
                    .background(colorResource(R.color.pink),shape = RoundedCornerShape(10.dp))

            ) {
                Icon(
                    painter = painterResource(id = R.drawable.btn_2),
                    contentDescription = "Cart",
                    tint = Color.White,
                    modifier = Modifier.size(24.dp)
                )
            }

            Button(
                onClick = onAddToCartClick,
                shape = RoundedCornerShape(10.dp),
                colors = ButtonDefaults.buttonColors(
                    backgroundColor = colorResource(R.color.pink),
                    contentColor = Color.White
                ),
                modifier = Modifier
                    .padding(start = 10.dp)
                    .weight(1f)
                    .height(50.dp)
            ){
                Text(
                    text="Add to Cart",
                    fontSize = 20.sp,
                    fontFamily = poppinsFontFamily,
                    fontWeight = FontWeight.SemiBold,

                )
            }

        }


    }

}

@Composable
fun ModelSelector(models: ArrayList<String>, selectedModelIndex: Int, onModelSelected: (Int) -> Unit) {
    LazyRow(
        modifier = Modifier.padding(vertical = 8.dp).padding(horizontal = 16.dp)
    ) {
        itemsIndexed(models) {
            index, model ->
            Box(
                modifier = Modifier
                    .padding(end= 16.dp)
                    .height(40.dp)
                    .then(
                        if(selectedModelIndex == index) {
                            Modifier.border(1.dp, colorResource(R.color.pink), shape = RoundedCornerShape(10.dp))
                        } else {
                            Modifier.border(1.dp, Color.LightGray, shape = RoundedCornerShape(10.dp))
                        }
                    )
                    .background(
                        if(selectedModelIndex == index) {
                            colorResource(R.color.pink)
                        } else {
                            Color.White
                        }
                    )
                    .clickable{onModelSelected(index)}
                    .padding(horizontal = 16.dp),
            ){
                Text(
                    text = model,
                    textAlign = TextAlign.Center,
                    fontWeight = FontWeight.Bold,
                    fontSize = 18.sp,
                    fontFamily = poppinsFontFamily,
                    color = if(selectedModelIndex == index) {
                        Color.White
                    } else {
                        Color.DarkGray
                    },
                    modifier = Modifier.align(Alignment.Center)
                )
            }
        }

    }
}

@Composable
fun RatingBar(rating: Double) {
   Row(
       verticalAlignment = Alignment.CenterVertically,
       modifier = Modifier.padding(top = 16.dp)
           .padding(horizontal = 16.dp)

   ){

       Text(
           text = "Select Model",
           fontSize = 20.sp,
           fontWeight = FontWeight.Bold,
           modifier = Modifier.weight(1f)
       )

       Image(
           painter = painterResource(id = R.drawable.star),
           contentDescription = "Star",
           modifier = Modifier.padding(end= 8.dp)

       )
       Text(
           text = "$rating Rating",
           style = MaterialTheme.typography.body2
       )

   }
}

@Composable
fun ImageThumbnail(imageUrl: String, isSelected: Boolean, onClick: () -> Unit) {
    val backColor = if (isSelected) colorResource(R.color.pink) else Color.LightGray

    Box(
        modifier = Modifier
            .padding(4.dp)
            .size(55.dp)
            .then(
                if(isSelected) {
                    Modifier.border(1.dp, Color.White, shape = RoundedCornerShape(10.dp))
                } else {
                    Modifier
                }
            )
            .background(backColor,shape = RoundedCornerShape(10.dp))
            .clickable(onClick = onClick)
    ) {

        Image(
            painter = rememberAsyncImagePainter(model=imageUrl),
            contentDescription = "Thumbnail Image",
            contentScale = ContentScale.Crop,
            modifier = Modifier.fillMaxSize()
        )
    }
}