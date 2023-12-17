package com.makesoftware.siga.ui.commons.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.CloudOff
import androidx.compose.material.icons.outlined.FolderOff
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun NoContentIndicator(
    modifier: Modifier = Modifier,
    text: String = "Sem registros.",
) {
    IconIndicator(
        modifier = modifier,
        text = text,
        icon = Icons.Outlined.FolderOff,
    )
}

@Composable
fun NoInternetIndicator(
    modifier: Modifier = Modifier,
    text: String = "Sem conexão com a internet.",
) {
    IconIndicator(
        modifier = modifier,
        text = text,
        icon = Icons.Outlined.CloudOff,
    )
}


@Composable
fun IconIndicator(
    modifier: Modifier = Modifier,
    text: String,
    icon: ImageVector,
    textStyle: TextStyle = MaterialTheme.typography.bodyLarge.copy(
        textAlign = TextAlign.Center, color = MaterialTheme.colorScheme.onSurface, fontSize = 20.sp
    ),
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
        modifier = modifier,
    ) {
        Icon(
            icon,
            contentDescription = null,
            tint = MaterialTheme.colorScheme.onSurface,
            modifier = Modifier
                .fillMaxWidth(.25F)
                .aspectRatio(1F)
        )

        Spacer(Modifier.height(10.dp))

        Text(
            text = text,
            style = textStyle,
        )
    }
}