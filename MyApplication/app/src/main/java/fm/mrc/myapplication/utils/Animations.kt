package fm.mrc.myapplication.utils

import androidx.compose.animation.core.*
import androidx.compose.foundation.layout.offset
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.composed
import androidx.compose.ui.unit.dp

fun Modifier.pulseAnimation(isAvailable: Boolean): Modifier = composed {
    if (!isAvailable) return@composed this

    val infiniteTransition = rememberInfiniteTransition(label = "pulse")
    val offset by infiniteTransition.animateFloat(
        initialValue = 0f,
        targetValue = 1f,
        animationSpec = infiniteRepeatable(
            animation = tween(1000),
            repeatMode = RepeatMode.Reverse
        ),
        label = "pulse"
    )

    this.offset(y = (offset * 4).dp)
}

fun Modifier.shakeAnimation(isAvailable: Boolean): Modifier = composed {
    if (isAvailable) return@composed this

    val infiniteTransition = rememberInfiniteTransition(label = "shake")
    val offset by infiniteTransition.animateFloat(
        initialValue = 0f,
        targetValue = 1f,
        animationSpec = infiniteRepeatable(
            animation = tween(100),
            repeatMode = RepeatMode.Reverse
        ),
        label = "shake"
    )

    this.offset(x = (offset * 4).dp)
} 