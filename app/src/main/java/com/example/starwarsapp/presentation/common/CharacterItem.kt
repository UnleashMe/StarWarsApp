package com.example.starwarsapp.presentation.common

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Icon
import androidx.compose.material3.Card
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.starwarsapp.R
import com.example.starwarsapp.domain.model.Character
import com.example.starwarsapp.domain.model.Gender

@Composable
fun CharacterItem(
    character: Character,
    isFaved: Boolean,
    onFaveClick: (Character) -> Unit,
    onUnfaveClick: (Character) -> Unit,
    modifier: Modifier = Modifier
) {
    Card(
        border = BorderStroke(3.dp, Color.Blue),
        shape = RoundedCornerShape(16.dp),
        modifier = Modifier.padding(bottom = 8.dp).then(modifier)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Column(
                verticalArrangement = Arrangement.SpaceAround,
                horizontalAlignment = Alignment.Start
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceAround
                ) {
                    Text(text = character.name, fontSize = 24.sp, fontWeight = FontWeight.Bold)
                    Icon(
                        painter = painterResource(id = if (character.gender is Gender.Male) R.drawable.baseline_male_24 else R.drawable.baseline_female_24),
                        contentDescription = "gender_icon"
                    )
                }
                Text(text = character.starships)
            }
            FaveIcon(
                subject = character,
                isFaved = isFaved,
                faveColor = Color.Blue,
                onFaveClick = { onFaveClick(character) },
                onUnfaveClick = { onUnfaveClick(character) })
        }
    }
}