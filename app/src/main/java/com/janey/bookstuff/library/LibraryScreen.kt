package com.janey.bookstuff.library

import androidx.annotation.DrawableRes
import androidx.compose.foundation.Image
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Badge
import androidx.compose.material3.Card
import androidx.compose.material3.DatePicker
import androidx.compose.material3.DatePickerDialog
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FilterChip
import androidx.compose.material3.FilterChipDefaults.filterChipColors
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme.colorScheme
import androidx.compose.material3.SwipeToDismissBox
import androidx.compose.material3.SwipeToDismissBoxValue
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.rememberDatePickerState
import androidx.compose.material3.rememberSwipeToDismissBoxState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.PreviewLightDark
import androidx.compose.ui.unit.dp
import com.janey.bookstuff.R
import com.janey.bookstuff.ui.components.BaseScreen
import com.janey.bookstuff.ui.theme.BookStuffTheme
import com.janey.bookstuff.ui.theme.Typography
import java.time.LocalDate

@Composable
fun LibraryScreen() {
    BaseScreen {
        CurrentCheckouts()
        LibraryHolds()
    }
}

@Composable
fun LibraryHolds() {
    Text("Physical Holds", style = Typography.headlineMedium)
    HoldBookCard(
        bookId = 1,
        title = "Gideon the Ninth",
        status = "Expected Apr 17th",
        cover = R.drawable.gideon,
        onCheckoutClicked = {},
        onDeleteHoldClicked = {},
        isReadyForPickup = false
    )
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

@Composable
fun CurrentCheckouts() {
    Text("Current Checkouts", style = Typography.headlineMedium)
    FilterRow()
    CheckedOutLibraryBookCard(
        bookId = 1,
        title = "Gideon the Ninth",
        dueDate = "Feb 13 - 3 days",
        cover = R.drawable.gideon,
        isPhysical = true,
    )
    CheckedOutLibraryBookCard(
        bookId = 1,
        title = "Gideon the Ninth with a long title",
        dueDate = "Feb 13",
        cover = R.drawable.gideon,
        isPhysical = false,
    )
    CheckedOutLibraryBookCard(
        bookId = 1,
        title = "The long way to a small angry planet",
        dueDate = "Feb 13",
        cover = R.drawable.gideon,
        isPhysical = true,
    )
}

@Composable
fun FilterRow() {
    var isPhysicalSelected by remember { mutableStateOf(true) }
    var isDigitalSelected by remember { mutableStateOf(true) }
    Row(horizontalArrangement = Arrangement.spacedBy(4.dp)) {
        val physicalColours = if (isSystemInDarkTheme()) {
            filterChipColors(
                containerColor = TypeColours.Physical().dark.copy(alpha = 0.05f),
                labelColor = colorScheme.onSurface,
                selectedContainerColor = TypeColours.Physical().dark,
                selectedLabelColor = colorScheme.onSurface,
            )
        } else {
            filterChipColors(
                containerColor = TypeColours.Physical().light.copy(alpha = 0.05f),
                labelColor = colorScheme.onSurface,
                selectedContainerColor = TypeColours.Physical().light,
                selectedLabelColor = colorScheme.onSurface,
            )
        }
        val digitalColours = if (isSystemInDarkTheme()) {
            filterChipColors(
                containerColor = TypeColours.Digital().dark.copy(alpha = 0.05f),
                labelColor = colorScheme.onSurface,
                selectedContainerColor = TypeColours.Digital().dark,
                selectedLabelColor = colorScheme.onSurface,
            )
        } else {
            filterChipColors(
                containerColor = TypeColours.Digital().light.copy(alpha = 0.05f),
                labelColor = colorScheme.onSurface,
                selectedContainerColor = TypeColours.Digital().light,
                selectedLabelColor = colorScheme.onSurface,
            )
        }
        FilterChip(
            selected = isPhysicalSelected,
            onClick = { isPhysicalSelected = !isPhysicalSelected },
            label = { Text("Physical") },
            colors = physicalColours,
        )
        FilterChip(
            selected = isDigitalSelected,
            onClick = { isDigitalSelected = !isDigitalSelected },
            label = { Text("Digital") },
            colors = digitalColours
        )
    }
}

sealed class TypeColours {
    data class Physical(
        val light: Color = Color(0xFF8893D7),
        val dark: Color = Color(0xFF3949AB),
    ) : TypeColours()

    data class Digital(
        val light: Color = Color(0xFFC580D1),
        val dark: Color = Color(0xFF7D328B),
    ) : TypeColours()
}

@Composable
fun CheckedOutLibraryBookCard(
    bookId: Int,
    title: String,
    dueDate: String,
    @DrawableRes cover: Int,
    isPhysical: Boolean,
    onReturnClicked: () -> Unit = {},
    onRenewClicked: () -> Unit = {},
) {
    DismissableRow(
        onStartToEnd = onRenewClicked,
        onEndToStart = onReturnClicked,
        startToEndIcon = {
            Icon(
                tint = colorScheme.primary,
                modifier = Modifier.size(50.dp),
                painter = painterResource(id = R.drawable.renew),
                contentDescription = "Renew $title"
            )
        },
        endToStartIcon = {
            Icon(
                tint = colorScheme.primary,
                modifier = Modifier.size(50.dp),
                painter = painterResource(id = R.drawable.done),
                contentDescription = "Mark $title as returned"
            )
        },
    ) {
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .height(IntrinsicSize.Min)
                .padding(vertical = 8.dp)
        ) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Image(
                    painterResource(id = R.drawable.gideon),
                    contentDescription = null,
                    contentScale = ContentScale.Crop,
                    modifier = Modifier
                        .fillMaxHeight()
                        .width(80.dp),
                )
                Column(
                    modifier = Modifier
                        .padding(vertical = 16.dp)
                        .padding(start = 8.dp)
                        .weight(1f),
                    verticalArrangement = Arrangement.spacedBy(2.dp)
                ) {
                    Text(title, style = Typography.headlineMedium)
                    Text(dueDate, style = Typography.bodyMedium)
                    LibraryBookTypePill(isPhysical)
                }
            }
        }
    }
}

@Composable
fun HoldBookCard(
    bookId: Int,
    title: String,
    status: String,
    @DrawableRes cover: Int,
    isReadyForPickup: Boolean,
    onCheckoutClicked: () -> Unit,
    onDeleteHoldClicked: () -> Unit,
) {
    DismissableRow(
        onStartToEnd = onDeleteHoldClicked,
        onEndToStart = onCheckoutClicked,
        startToEndIcon = {
            Icon(
                tint = colorScheme.error,
                modifier = Modifier.size(50.dp),
                painter = painterResource(id = R.drawable.delete),
                contentDescription = "Remove hold for $title"
            )
        },
        endToStartIcon = {
            Icon(
                tint = colorScheme.primary,
                modifier = Modifier.size(50.dp),
                painter = painterResource(id = R.drawable.done),
                contentDescription = "Mark $title as checked out"
            )
        },
        isEndToStartEnabled = isReadyForPickup,
    ) {
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .height(IntrinsicSize.Min)
                .padding(vertical = 8.dp)
        ) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Image(
                    painterResource(id = R.drawable.gideon),
                    contentDescription = null,
                    contentScale = ContentScale.Crop,
                    modifier = Modifier
                        .fillMaxHeight()
                        .width(80.dp),
                )
                Column(
                    modifier = Modifier
                        .padding(vertical = 16.dp)
                        .padding(start = 8.dp)
                        .weight(1f),
                    verticalArrangement = Arrangement.spacedBy(2.dp)
                ) {
                    Text(title, style = Typography.headlineMedium)
                    Text(status, style = Typography.bodyMedium)
                }
            }
        }
    }
}

@Composable
fun DismissableRow(
    onStartToEnd: () -> Unit = {},
    onEndToStart: () -> Unit = {},
    startToEndIcon: @Composable () -> Unit,
    endToStartIcon: @Composable () -> Unit,
    isStartToEndEnabled: Boolean = true,
    isEndToStartEnabled: Boolean = true,
    content: @Composable () -> Unit,
) {
    val swipeToDismissState = rememberSwipeToDismissBoxState(
        confirmValueChange = {
            when (it) {
                SwipeToDismissBoxValue.Settled -> false
                SwipeToDismissBoxValue.StartToEnd -> {
                    onStartToEnd()
                    true
                }

                SwipeToDismissBoxValue.EndToStart -> {
                    onEndToStart()
                    true
                }
            }
        }
    )
    SwipeToDismissBox(
        state = swipeToDismissState,
        enableDismissFromStartToEnd = isStartToEndEnabled,
        enableDismissFromEndToStart = isEndToStartEnabled,
        backgroundContent = {
            Row(
                Modifier
                    .fillMaxSize(),
                verticalAlignment = Alignment.CenterVertically,
            ) {
                when (swipeToDismissState.targetValue) {
                    SwipeToDismissBoxValue.Settled -> {}
                    SwipeToDismissBoxValue.StartToEnd -> startToEndIcon()

                    SwipeToDismissBoxValue.EndToStart -> {
                        Spacer(modifier = Modifier.weight(1f))
                        endToStartIcon()
                    }
                }
            }
        }
    ) {
        content()
    }
}

@Composable
fun LibraryBookTypePill(isPhysical: Boolean) {
    val pillBackgroundColor = if (isSystemInDarkTheme()) {
        if (isPhysical) {
            TypeColours.Physical().dark
        } else {
            TypeColours.Digital().dark
        }
    } else {
        if (isPhysical) {
            TypeColours.Physical().light
        } else {
            TypeColours.Digital().light
        }
    }
    Badge(
        containerColor = pillBackgroundColor,
        contentColor = colorScheme.onSurface
    ) {
        Text(
            text = if (isPhysical) "Physical" else "Digital",
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LibraryRenewalPopup() {
    val datePickerState =
        rememberDatePickerState(
            initialSelectedDateMillis = LocalDate.now().plusWeeks(3).toEpochDay()
        )
    DatePickerDialog(
        onDismissRequest = { /*TODO*/ },
        confirmButton = {
            TextButton(
                onClick = { /*TODO*/ }
            ) {
                Text("Confirm")
            }
        },
        dismissButton = {
            TextButton(
                onClick = { /* TODO */ }
            ) {
                Text("Cancel")
            }
        }
    ) {
        DatePicker(state = datePickerState)
    }
}

@PreviewLightDark
@Composable
fun LibraryScreenPreview() {
    BookStuffTheme {
        LibraryScreen()
    }
}

@PreviewLightDark
@Composable
private fun DatePickerPreview() {
    BookStuffTheme {
        LibraryRenewalPopup()
    }
}