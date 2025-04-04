package com.example.attyr_shoppingapp_assignment.Activity

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.collectIsDraggedAsState
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Arrangement.Absolute.SpaceBetween
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.core.content.ContextCompat.startActivity
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.example.attyr_shoppingapp_assignment.Model.CategoryModel
import com.example.attyr_shoppingapp_assignment.Model.ItemsModel
import com.example.attyr_shoppingapp_assignment.Model.ListItems
import com.example.attyr_shoppingapp_assignment.Model.SliderModel
import com.example.attyr_shoppingapp_assignment.R
import com.example.attyr_shoppingapp_assignment.ViewModel.MainViewModel
import com.example.attyr_shoppingapp_assignment.ui.theme.Attyr_ShoppingApp_AssignmentTheme
import com.example.attyr_shoppingapp_assignment.ui.theme.poppinsFontFamily
import com.google.accompanist.pager.ExperimentalPagerApi
import com.google.accompanist.pager.HorizontalPager
import com.google.accompanist.pager.PagerState

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            Attyr_ShoppingApp_AssignmentTheme {
                MainActivityScreen{

                }
            }
        }
    }
}

@Composable

fun MainActivityScreen(onCartClick: () -> Unit) {

    val viewModel = MainViewModel()

    val banners = remember { mutableStateListOf<SliderModel>() }
    val categories = remember{ mutableStateListOf<CategoryModel>()}
    val Popular = remember{ mutableStateListOf< ItemsModel>()}


    var showBannerLoading by remember { mutableStateOf(true) }
    var showCategoryLoading by remember { mutableStateOf(true) }
    var showPopularLoading by remember { mutableStateOf(true) }

    LaunchedEffect(Unit) {
        viewModel.loadBanner().observeForever {
            banners.clear()
            banners.addAll(it)
            showBannerLoading = false
        }
    }

    LaunchedEffect(Unit) {
        viewModel.loadCategory().observeForever {
            categories.clear()
            categories.addAll(it)
            showCategoryLoading = false
        }
    }



    LaunchedEffect(Unit) {
        viewModel.loadPopular().observeForever {
            Popular.clear()
            Popular.addAll(it)
            showPopularLoading = false
        }
    }


    ConstraintLayout(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
    ) {
        val (scrollList, bottomMenu) = createRefs()
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .constrainAs(scrollList) {
                    top.linkTo(parent.top)
                    bottom.linkTo(parent.bottom)
                    start.linkTo(parent.start)
                    end.linkTo(parent.end)
                }) {

            item {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 70.dp)
                        .padding(horizontal = 16.dp),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Column() {
                        Text(
                            "Welcome Back!!",
                            color = Color.Black,
                            fontSize = 18.sp,
                            fontFamily = poppinsFontFamily
                        )
                        Text(
                            "Priyansu 😊",
                            color = Color.Black,
                            fontSize = 18.sp,
                            fontFamily = poppinsFontFamily
                        )
                    }
                    Row() {
                        Image(
                            painter = painterResource(id = R.drawable.search_icon),
                            contentDescription = "Notification",

                        )
                        Spacer(modifier = Modifier.width(16.dp))
                        Image(
                            painter = painterResource(id = R.drawable.bell_icon),
                            contentDescription = "Cart"
                        )

                    }
                }
            }
            item {
                if (showBannerLoading) {
                    Box(
                        modifier = Modifier
                            .fillMaxSize()
                            .height(200.dp),
                        contentAlignment = Alignment.Center
                    ) {
                        CircularProgressIndicator(modifier = Modifier.size(40.dp),
                            color = colorResource(R.color.pink),
                            strokeWidth = 2.5.dp)
                    }

                } else {
                    Banners(banners)
                }
            }

            item{
                Text(
                    text = "Categories",
                    color = Color.Black,
                    fontSize = 18.sp,
                    fontFamily = poppinsFontFamily,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.padding(horizontal = 16.dp).padding(top = 16.dp)
                )
            }

            item {
                if (showCategoryLoading) {
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(50.dp),
                        contentAlignment = Alignment.Center
                    ){
                        CircularProgressIndicator(modifier = Modifier.size(40.dp),
                            color = colorResource(R.color.pink),
                            strokeWidth = 2.5.dp)
                    }
                } else {
                    CategoryList(categories)
                }
            }


            item{
                SectionTitle(title = "Most Popular", actionText = "View All")
            }
            item{
                if (showPopularLoading) {
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(50.dp),
                        contentAlignment = Alignment.Center
                        ) {
                        CircularProgressIndicator(modifier = Modifier.size(40.dp),
                            color = colorResource(R.color.pink),
                            strokeWidth = 2.5.dp)
                    }
                }else{
                    ListItems(Popular)
                }
            }
        }
        BottomMenu(modifier = Modifier.fillMaxWidth()
            .constrainAs(bottomMenu){
                bottom.linkTo(parent.bottom)
            },
            onItemClick = onCartClick
        )
    }
}

@Composable
fun CategoryList(categories: SnapshotStateList<CategoryModel>) {
    var selectedIndex by remember { mutableStateOf(-1) }
    val context = LocalContext.current
    LazyRow(
        modifier = Modifier
            .fillMaxWidth(),
        horizontalArrangement = Arrangement.spacedBy(24.dp),
        contentPadding = PaddingValues(start = 16.dp, end = 16.dp,top = 8.dp)
    ) {
        items(categories.size) { index ->
        CategoryItem(
            item = categories[index],
            isSelected = index == selectedIndex,
            onItemClick = {
                selectedIndex = index
                Handler(Looper.getMainLooper()).postDelayed({
                val intent = Intent(context, ListItemsActivity::class.java).apply{
                    putExtra("id",categories[index].id.toString())
                    putExtra("title",categories[index].title)
                }
                 startActivity(context,intent,null)
                },500)
            }
        )
    }
    }

}

@Composable
fun CategoryItem(item: CategoryModel,isSelected:Boolean,onItemClick:()->Unit){
    Column(
        modifier = Modifier
            .clickable(onClick = onItemClick),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        AsyncImage(
            model = item.picUrl,
            contentDescription = "Category",
            contentScale = ContentScale.Inside,
            modifier = Modifier
                .size(if (isSelected) 70.dp else 60.dp)
                .background(
                    color = if (isSelected) colorResource(R.color.pink) else Color.Gray,
                    shape = RoundedCornerShape(100.dp)
                ),
            colorFilter = if(isSelected) {
                ColorFilter.tint(Color.White)
            }
            else{
                ColorFilter.tint(Color.Black)
            }

        )
        Spacer(modifier = Modifier.padding(top = 8.dp))
        Text(
            text = item.title,
            color = Color.Black,
            fontSize = 12.sp,
            fontFamily = poppinsFontFamily,
            fontWeight = FontWeight.Bold
        )

    }
}

@Composable
fun SectionTitle(title: String, actionText: String) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 24.dp)
            .padding(horizontal=16.dp),
        horizontalArrangement = SpaceBetween,
    ){
        Text(
            text = title,
            color = Color.Black,
            fontSize = 18.sp,
            fontFamily = poppinsFontFamily,
            fontWeight = FontWeight.Bold
        )
        Text(
            text = actionText,
            color = Color.Black,
            fontSize = 18.sp,
            fontWeight = FontWeight.Bold,
            fontFamily = poppinsFontFamily,
            )
    }
}

@OptIn(ExperimentalPagerApi::class)
@Composable
fun Banners(banners: SnapshotStateList<SliderModel>) {
    AutoSlidingCarousel(banners = banners)
}

@OptIn(ExperimentalPagerApi::class)
@Composable
fun AutoSlidingCarousel(
    modifier: Modifier = Modifier.padding(top = 16.dp),
    pagerState: PagerState = remember { PagerState() },
    banners: List<SliderModel>
) {
    val isdragged by pagerState.interactionSource.collectIsDraggedAsState()

    Column(
        modifier = Modifier.fillMaxSize()
    ) {
        HorizontalPager(count = banners.size, state = pagerState) { page ->
            AsyncImage(
                model = ImageRequest.Builder(LocalContext.current)
                    .data(banners[page].url)
                    .crossfade(true)
                    .build(),
                contentDescription = "Banner",
                contentScale = ContentScale.FillBounds,
                modifier = Modifier
                    .padding(horizontal = 16.dp)
                    .padding(top = 16.dp, bottom = 8.dp)
                    .height(150.dp)
            )
        }
        DotIndicator(
            modifier = Modifier
                .padding(horizontal = 8.dp)
                .align(Alignment.CenterHorizontally),
            totalDots = banners.size,
            selectedIndex = if (isdragged) pagerState.currentPage else pagerState.currentPage,
            dotSize = 8.dp
        )
    }

}

@Composable
fun DotIndicator(
    modifier: Modifier = Modifier,
    totalDots: Int,
    selectedIndex: Int,
    selectedColor: Color = colorResource(R.color.pink),
    unSelectedColor: Color = colorResource(R.color.grey),
    dotSize: Dp
) {
    LazyRow(
        modifier = modifier.wrapContentSize()
    ) {
        items(totalDots) { index ->
            IndicatorDot(
                color = if (index == selectedIndex) selectedColor else unSelectedColor,
                size = dotSize
            )
            if (index != totalDots - 1) {
                Spacer(modifier = Modifier.padding(horizontal = 2.dp))
            }
        }
    }
}

@Composable
fun IndicatorDot(modifier: Modifier = Modifier, size: Dp, color: Color) {
    Box(
        modifier = modifier.size(size)
            .background(color,shape = RoundedCornerShape(50)),
        ){

    }
}

@Composable
fun BottomMenu(modifier: Modifier,onItemClick: () -> Unit){
    val context = LocalContext.current
    Row(
        modifier = modifier.padding(start = 16.dp, end = 16.dp,bottom=32.dp)
            .background(
                colorResource(R.color.pink),
                shape = RoundedCornerShape(15.dp)
            ),
        horizontalArrangement = Arrangement.SpaceAround,
    ){
        BottomMenuItem(icon = painterResource(id = R.drawable.btn_1), text = "Explorer"){
            val intent = Intent(context, ExploreActivity::class.java)
            context.startActivity(intent)
        }
        BottomMenuItem(icon = painterResource(id = R.drawable.btn_2), text = "Cart")

        BottomMenuItem(icon = painterResource(id = R.drawable.btn_3), text = "Favourites"){
            val intent = Intent(context, FavouriteActivity::class.java)
            context.startActivity(intent)
        }
        BottomMenuItem(icon = painterResource(id = R.drawable.btn_4), text = "Orders"){
            val intent = Intent(context, OrdersActivity::class.java)
            context.startActivity(intent)
        }
        BottomMenuItem(icon = painterResource(id = R.drawable.btn_5), text = "Profile"){
            val intent = Intent(context, ProfileActivity::class.java)
            context.startActivity(intent)
        }
    }
}

@Composable
fun BottomMenuItem(icon:Painter,text:String,onItemClick: (() -> Unit)?=null){
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
        modifier = Modifier.padding(8.dp)
            .clickable{onItemClick?.invoke()}
            .height(70.dp)

    ){
        Icon(icon,contentDescription = text, tint = Color.White)
        Spacer(modifier = Modifier.padding(vertical=4.dp))
        Text(text = text,color = Color.White, fontSize = 12.sp, fontFamily = poppinsFontFamily)
    }
}