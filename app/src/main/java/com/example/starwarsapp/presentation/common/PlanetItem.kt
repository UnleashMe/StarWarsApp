package com.example.starwarsapp.presentation.common

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.starwarsapp.domain.model.Planet

@Composable
fun PlanetItem(
    planet: Planet,
    isFaved: Boolean,
    onFaveClick: (Planet) -> Unit,
    onUnfaveClick: (Planet) -> Unit,
    modifier: Modifier = Modifier
) {
    Card(
        border = BorderStroke(3.dp, Color.Magenta),
        shape = RoundedCornerShape(16.dp),
        modifier = Modifier.padding(bottom = 8.dp).then(modifier)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp),
            verticalAlignment = Alignment.Top,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Column(
                verticalArrangement = Arrangement.SpaceAround,
                horizontalAlignment = Alignment.Start,
                modifier = Modifier.weight(9f)
            ) {
                Text(text = planet.name, fontSize = 24.sp, fontWeight = FontWeight.Bold)
                Text(text = planet.diameter)
                Text(text = planet.population)
            }
            FaveIcon(
                subject = planet,
                isFaved = isFaved,
                faveColor = Color.Magenta,
                onFaveClick = { onFaveClick(planet) },
                onUnfaveClick = { onUnfaveClick(planet) })
        }
    }
}