package com.example.trade_game.presenter.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.trade_game.common.Montserrat
import com.example.trade_game.domain.models.TopUser
import com.example.trade_game.ui.theme.Primary

@Composable
fun RatingCard(user: TopUser, index: Int, navController: NavController){
    val interactionSource = remember { MutableInteractionSource() }
    val ratingCardColor = when (index){
        1 -> Primary
        2 -> Color(0xFF4C68B4)
        3 -> Color(0xFF60709C)
        else -> Color(0xFF7F7F7F)
    }

    Card (
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 10.dp)
            .clickable (
                interactionSource = interactionSource,
                indication = null
            ){
                navController.navigate("Profile/${user.id}")
            },
        colors = CardDefaults.cardColors(
            containerColor = ratingCardColor
        )
    ) {
        Row (
            modifier = Modifier
                .fillMaxWidth()
                .padding(12.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column {
                Text(
                    text = user.username,
                    fontFamily = Montserrat,
                    fontWeight = FontWeight.Bold,
                    color = Color.White,
                    fontSize = 20.sp
                )
                Text(
                    text = "$${user.total_balance}",
                    fontFamily = Montserrat,
                    color = Color.White,
                    fontSize = 16.sp,
                )
            }
            Text(
                text = "#$index",
                fontFamily = Montserrat,
                color = Color.White,
                fontWeight = FontWeight.Bold,
                fontSize = 20.sp
            )
        }
    }
    Spacer(Modifier.height(10.dp))
}

@Composable
fun RatingUserCard(name: String, balance: String, place: Int){
    Card (
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 10.dp),
        colors = CardDefaults.cardColors(
            containerColor = Primary
        )
    ) {
        Row (
            modifier = Modifier
                .fillMaxWidth()
                .padding(12.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column {
                Text(
                    text = name,
                    fontFamily = Montserrat,
                    fontWeight = FontWeight.Bold,
                    color = Color.White,
                    fontSize = 20.sp
                )
                Text(
                    text = "$$balance",
                    fontFamily = Montserrat,
                    color = Color.White,
                    fontSize = 16.sp,
                )
            }
            Text(
                text = "#$place",
                fontFamily = Montserrat,
                color = Color.White,
                fontWeight = FontWeight.Bold,
                fontSize = 20.sp
            )
        }
    }
}