package com.example.trade_game.presenter

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.trade_game.common.formatDateTime
import com.example.trade_game.domain.models.Event
import com.example.trade_game.domain.view.EventsViewModel
import kotlinx.coroutines.launch

@Composable
fun EventsScreen(
    isGestureNavigation: Boolean,
    viewModel: EventsViewModel = viewModel()
) {
    val scope = rememberCoroutineScope()
    val events by viewModel.events.collectAsState()

    val ratingPadding = if (isGestureNavigation) 0.dp else 30.dp

    LaunchedEffect(Unit) {
        scope.launch {
            viewModel.getEvents(50, 0)
        }
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(vertical = ratingPadding)
            .background(MaterialTheme.colorScheme.background),
        contentAlignment = Alignment.TopCenter
    ) {
        when {
            events != null && events!!.data.isNotEmpty() -> {
                LazyColumn {
                    items(events!!.data) { event ->
                        EventCard(event)
                    }
                }
            }

            events != null -> {
                Box(
                    modifier = Modifier.fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) {
                    Card(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(200.dp)
                            .padding(horizontal = 20.dp, vertical = 20.dp),
                        shape = RoundedCornerShape(30.dp),
                        elevation = CardDefaults.cardElevation(defaultElevation = 8.dp)
                    ) {
                        Box(
                            modifier = Modifier
                                .fillMaxSize()
                                .background(MaterialTheme.colorScheme.background),
                            contentAlignment = Alignment.Center
                        ) {
                            Text(
                                text = "Тут пока что ничего нет",
                                fontSize = 40.sp,
                                fontWeight = FontWeight.Bold,
                                textAlign = TextAlign.Center,
                                lineHeight = 35.sp,
                                color = MaterialTheme.colorScheme.primary
                            )
                        }
                    }
                }
            }
        }

    }
}

@Composable
fun EventCard(event: Event) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 20.dp, vertical = 20.dp),
        shape = RoundedCornerShape(30.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 8.dp)
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .background(MaterialTheme.colorScheme.secondary),
            contentAlignment = Alignment.TopCenter
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth(0.9f)
                    .padding(12.dp)
            ) {
                Text(
                    text = event.title,
                    color = MaterialTheme.colorScheme.primary,
                    fontSize = 24.sp,
                    fontWeight = FontWeight.Bold
                )
                Spacer(modifier = Modifier.height(10.dp))
                Text(text = event.description, color = MaterialTheme.colorScheme.onSecondary)
                Spacer(modifier = Modifier.height(10.dp))
                Text(
                    modifier = Modifier.fillMaxWidth(),
                    textAlign = TextAlign.End,
                    color = Color.Gray,
                    text = formatDateTime(event.created_at)
                )
            }
        }
    }


}

