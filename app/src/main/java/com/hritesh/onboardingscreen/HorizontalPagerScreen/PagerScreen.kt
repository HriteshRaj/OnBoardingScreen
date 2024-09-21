package com.hritesh.onboardingscreen.HorizontalPagerScreen

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row

import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier

import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.google.accompanist.pager.ExperimentalPagerApi
import com.google.accompanist.pager.HorizontalPager
import com.google.accompanist.pager.HorizontalPagerIndicator
import com.google.accompanist.pager.rememberPagerState
import com.hritesh.onboardingscreen.R
import com.hritesh.onboardingscreen.model.HorizontalPagerContent
import kotlinx.coroutines.launch

@OptIn(ExperimentalPagerApi::class)
@Composable
fun PagerScreen() {

    val pagerState = rememberPagerState(initialPage = 0)
    val list = getListOfPager()
    val isNextVisible = remember {
        derivedStateOf {
            pagerState.currentPage != list.size - 1
        }
    }
    val isPrevVisible = remember {
        derivedStateOf {
            pagerState.currentPage != 0
        }
    }

    val scope = rememberCoroutineScope()
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
            ,modifier=Modifier.padding(8.dp)
    ) {
        Box(
            modifier = Modifier
                .fillMaxHeight(0.75f)
                .fillMaxWidth()
        ) {

            HorizontalPager(count = list.size, state = pagerState) { page ->
                ContentDetails(list = list, currentPage = page)


            }


        }
        HorizontalPagerIndicator(pagerState = pagerState, modifier = Modifier.padding(26.dp))

        PagerWithButton(
            isNextVisible.value,
            isPrevVisible.value,
            onNextClick = {
                scope.launch {
                    pagerState.animateScrollToPage(pagerState.currentPage + 1)
                }
            },
            onPrevClick = { scope.launch { pagerState.animateScrollToPage(pagerState.currentPage - 1) } })

    }

}

@Composable
fun ContentDetails(list: List<HorizontalPagerContent>, currentPage: Int) {
    Column(
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        Text(text = list[currentPage].title, style = MaterialTheme.typography.headlineMedium)
        Spacer(modifier = Modifier.height(8.dp))
        AsyncImage(
            model = list[currentPage].res, contentDescription = null,
            modifier = Modifier
                .width(300.dp)
                .height(380.dp)
        )
        Spacer(modifier = Modifier.height(8.dp))
        Text(
            text = list[currentPage].description,
            style = MaterialTheme.typography.bodySmall,



        )

    }
}

@Composable
fun PagerWithButton(
    isNextVisible: Boolean,
    isPrevVisible: Boolean,
    onNextClick: () -> Unit,
    onPrevClick: () -> Unit
) {

    Row(modifier=Modifier.fillMaxWidth(),verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.SpaceEvenly) {
        if (isPrevVisible) {
            Button(onClick = onPrevClick) {
                Text(text = "Prev")
            }
        }

        if (isNextVisible) {
            Button(onClick = onNextClick) {
                Text(text = "Next")

            }
        }


    }
}


fun getListOfPager(): List<HorizontalPagerContent> {
    return listOf(


        HorizontalPagerContent(
            title = "Welcome TO RENT EV",
            R.drawable.illustration1, "Electric vehicle is best"
        ),
        HorizontalPagerContent(
            title = "Save Fuel",
            R.drawable.illustration2, "Save Money as well as Fuel "
        ),
        HorizontalPagerContent(
            title = "Green ENERGY",
            R.drawable.illustration3, "Make use of GREEN ENERGY and SAVE ENVIRONMENT"
        )


    )
}