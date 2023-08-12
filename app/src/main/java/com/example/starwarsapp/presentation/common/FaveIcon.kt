package com.example.starwarsapp.presentation.common

import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.spring
import androidx.compose.animation.core.tween
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.material.Icon
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import com.example.starwarsapp.domain.model.SearchSubject
import kotlinx.coroutines.launch

@Composable
fun FaveIcon(
    subject: SearchSubject,
    isFaved: Boolean,
    faveColor: Color,
    onFaveClick: (SearchSubject) -> Unit,
    onUnfaveClick: (SearchSubject) -> Unit
) {

    val scale = remember {
        Animatable(1f)
    }

    val interactionSource = remember {
        MutableInteractionSource()
    }

    var selected by remember {
        mutableStateOf(false)
    }

    LaunchedEffect(key1 = selected) {
        if (selected) {
            launch {
                scale.animateTo(
                    targetValue = 0.3f,
                    animationSpec = tween(
                        durationMillis = 500
                    )
                )
                scale.animateTo(
                    targetValue = 1f,
                    animationSpec = spring(
                        dampingRatio = Spring.DampingRatioHighBouncy,
                        stiffness = Spring.StiffnessVeryLow
                    )
                )
            }
        } else {
            launch {
                scale.animateTo(
                    targetValue = 1f,
                    animationSpec = tween(
                        durationMillis = 300
                    )
                )
            }
        }
    }

    Icon(
        imageVector = if (isFaved) Icons.Default.Favorite else Icons.Default.FavoriteBorder,
        contentDescription = "fave_icon",
        modifier = Modifier
            .clickable(indication = null, interactionSource = interactionSource) {
                selected = if (isFaved) {
                    onUnfaveClick(subject)
                    false
                } else {
                    onFaveClick(subject)
                    true
                }

            }
            .scale(scale.value),
        tint = if (isFaved) faveColor else Color.Black
    )
}