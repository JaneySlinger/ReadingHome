package com.janey.bookstuff.home

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.PreviewLightDark
import androidx.compose.ui.unit.dp
import com.janey.bookstuff.R
import com.janey.bookstuff.library.CheckedOutLibraryBookCard
import com.janey.bookstuff.library.HoldBookCard
import com.janey.bookstuff.tbr.RecentlyReleased
import com.janey.bookstuff.ui.components.BaseScreen
import com.janey.bookstuff.ui.components.HorizontalPagerIndicator
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

@Composable
fun ExpiringSoonRow() {
    val pagerState = rememberPagerState(pageCount = {
        4
    })
    Text(text = "Expiring Soon...", style = Typography.headlineMedium)

    HorizontalPager(
        state = pagerState,
        contentPadding = PaddingValues(end = 16.dp)
    ) {
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
    HorizontalPagerIndicator(pagerState)
}

@Composable
fun ReadyForPickupRow() {
    val pagerState = rememberPagerState(pageCount = {
        3
    })
    Text(text = "Ready for Pickup", style = Typography.headlineMedium)
    HorizontalPager(
        state = pagerState,
        contentPadding = PaddingValues(end = 16.dp)
    ) {
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
    HorizontalPagerIndicator(pagerState)
}

@PreviewLightDark
@Composable
private fun HomeScreenPreview() {
    BookStuffTheme {
        HomeScreen()
    }
}