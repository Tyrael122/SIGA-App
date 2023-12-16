package com.makesoftware.siga.ui.commons.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.makesoftware.siga.ui.theme.secondary_color


@Composable
fun FormNumberTextField(
    value: Int,
    onValueChange: (Int?) -> Unit,
    placeholderText: String,
    modifier: Modifier = Modifier
) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween,
        modifier = modifier
            .fillMaxWidth()
            .heightIn(min = 60.dp)
            .border(
                width = 1.dp,
                color = MaterialTheme.colorScheme.primary,
                shape = RoundedCornerShape(10.dp)
            )
            .background(secondary_color)
            .padding(horizontal = 15.dp)
    ) {
        Text(
            text = placeholderText, style = MaterialTheme.typography.bodyLarge.copy(
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
        )

        DefaultNumberTextField(
            value = value,
            onValueChange = onValueChange,
            placeholderText = "0",
            modifier = Modifier.width(40.dp),
        )
    }
}

@Composable
fun FormTextField(
    value: String, onValueChange: (String) -> Unit, minLines: Int = 1, placeholderText: String
) {
    DefaultOutlinedTextField(
        modifier = Modifier.fillMaxWidth(),
        value = value,
        placeholderText = placeholderText,
        onValueChange = onValueChange,
        singleLine = false,
        minLines = minLines,
    )
}