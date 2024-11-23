package com.janey.bookstuff.home

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.PreviewLightDark
import androidx.compose.ui.unit.dp
import com.janey.bookstuff.R
import com.janey.bookstuff.library.HoldBookCard
import com.janey.bookstuff.library.CheckedOutLibraryBookCard
import com.janey.bookstuff.tbr.RecentlyReleased
import com.janey.bookstuff.ui.components.BaseScreen
import com.janey.bookstuff.ui.theme.BookStuffTheme
import com.janey.bookstuff.ui.theme.Typography

@Composable
fun HomeScreen() {

    BaseScreen {
        ExpiringSoonRow()
        ReadyForPickupRow()
        RecentlyReleased()
    }
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun ExpiringSoonRow() {
    val pagerState = rememberPagerState(pageCount = {
        4
    })
    Text(text = "Expiring Soon...", style = Typography.headlineMedium)

    HorizontalPager(
        state = pagerState,
        contentPadding = PaddingValues(end = 16.dp)
    ) { page ->
        Row(Modifier.padding(end = 8.dp)) {
            CheckedOutLibraryBookCard(
                bookId = 1,
                title = "Gideon the Ninth",
                dueDate = "Feb 13 - 3 days",
                cover = R.drawable.gideon,
                isPhysical = true,
            )
        }
    }
    Row(
        Modifier
            .wrapContentHeight()
            .fillMaxWidth()
            .padding(bottom = 8.dp),
        horizontalArrangement = Arrangement.Center
    ) {
        repeat(pagerState.pageCount) { iteration ->
            val color =
                if (pagerState.currentPage == iteration) Color.DarkGray else Color.LightGray
            Box(
                modifier = Modifier
                    .padding(2.dp)
                    .clip(CircleShape)
                    .background(color)
                    .size(8.dp)
            )
        }
    }
}

@Composable
fun ReadyForPickupRow() {
    val pagerState = rememberPagerState(pageCount = {
        1
    })
    Text(text = "Ready for Pickup", style = Typography.headlineMedium)

    HorizontalPager(
        state = pagerState,
        contentPadding = PaddingValues(end = 16.dp)
    ) { page ->
        Row(Modifier.padding(end = 8.dp)) {
            HoldBookCard(
                bookId = 1,
                title = "Gideon the Ninth",
                status = "Ready for Pickup",
                cover = R.drawable.gideon,
                onCheckoutClicked = {},
                onDeleteHoldClicked = {},
                isReadyForPickup = true
            )
        }
    }
    Row(
        Modifier
            .wrapContentHeight()
            .fillMaxWidth()
            .padding(bottom = 8.dp),
        horizontalArrangement = Arrangement.Center
    ) {
        if(pagerState.pageCount > 1) {
            repeat(pagerState.pageCount) { iteration ->
                val color =
                    if (pagerState.currentPage == iteration) Color.DarkGray else Color.LightGray
                Box(
                    modifier = Modifier
                        .padding(2.dp)
                        .clip(CircleShape)
                        .background(color)
                        .size(8.dp)
                )
            }
        }
    }
}

@PreviewLightDark
@Composable
private fun HomeScreenPreview() {
    BookStuffTheme {
        HomeScreen()
    }
}