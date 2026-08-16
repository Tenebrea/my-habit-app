package com.example.myhabitapp.presentation.mainScreen.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.capitalize
import androidx.compose.ui.tooling.preview.PreviewLightDark
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.example.myhabitapp.ui.theme.HabitAppTheme
import kotlinx.datetime.LocalDate
import kotlinx.datetime.Month
import myhabitapp.shared.generated.resources.Res
import myhabitapp.shared.generated.resources.placeholder_image
import org.jetbrains.compose.resources.painterResource
import java.time.DayOfWeek
import java.util.Date


@Composable
fun UserDetails(
    modifier: Modifier = Modifier,
    userName: String,
    image: Painter,
    date: LocalDate,
    imageSize: Dp
) {
    Row(
        modifier = modifier,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Column {
            Text(
                text = "Hello, $userName",
                style = MaterialTheme.typography.displayLarge,
                color = MaterialTheme.colorScheme.onBackground
            )
            Text(
                text = "${ date.dayOfWeek.name.lowercase().replaceFirstChar{ it.uppercase() } }, " +
                        date.day + " " +
                        date.month.name.lowercase().replaceFirstChar{ it.uppercase() } + " " +
                        date.year,
                style = MaterialTheme.typography.bodySmall,
                color = MaterialTheme.colorScheme.onBackground
            )
        }
        Spacer(modifier = Modifier.weight(1f))
        Image(
            painter = image,
            contentDescription = "Profile image",
            contentScale = ContentScale.Crop,
            modifier = Modifier.clip(CircleShape).size(imageSize)
        )
    }
}

@PreviewLightDark
@Composable
fun UserDetailsPreview() {
    HabitAppTheme {
        UserDetails(
            userName = "My name",
            image = painterResource(Res.drawable.placeholder_image),
            date = LocalDate(2000, 11, 30),
            imageSize = 64.dp
        )
    }
}
