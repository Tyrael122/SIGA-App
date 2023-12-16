package com.makesoftware.siga.ui.commons.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.ArrowCircleRight
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.makesoftware.siga.ui.theme.AlternativeColorScheme
import com.makesoftware.siga.ui.theme.alternativeTypography


@Composable
fun FormNumberTextField(
    modifier: Modifier = Modifier,
    value: Int,
    onValueChange: (Int?) -> Unit,
    fieldDescription: String,
    maxCharCount: Int = 2,
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
            .background(AlternativeColorScheme.secondary_color)
            .padding(horizontal = 15.dp)
    ) {
        Text(
            text = fieldDescription, style = MaterialTheme.typography.bodyLarge.copy(
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
        )

        DefaultNumberOutlinedTextField(
            value = value,
            onValueChange = onValueChange,
            maxCharCount = maxCharCount,
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

@Composable
fun FormSelectableDataGrid(
    modifier: Modifier = Modifier,
    numberOfSelectedItems: Int,
    infoText: String,
    onSelectRequest: () -> Unit,
    isFeminine: Boolean = true,
) {
    Row(
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically,
        modifier = modifier.fillMaxWidth(),
    ) {
        Text(
            text = infoText, style = alternativeTypography.bodyLarge.copy(
                fontWeight = FontWeight.Bold, color = MaterialTheme.colorScheme.primary
            ), modifier = Modifier.padding(start = 10.dp)
        )

        Row(verticalAlignment = Alignment.CenterVertically) {
            val pluralChar = if (numberOfSelectedItems > 1) "s" else ""
            val genderChar = if (isFeminine) "a" else "o"
            val selectedItemsText = "selecionad$genderChar$pluralChar"

            Text(
                text = "$numberOfSelectedItems $selectedItemsText",
                style = MaterialTheme.typography.bodyMedium.copy(
                    color = AlternativeColorScheme.green, fontWeight = FontWeight.Normal
                )
            )

            IconButton(
                onClick = onSelectRequest, modifier = Modifier.padding(start = 10.dp)
            ) {
                Icon(
                    Icons.Outlined.ArrowCircleRight,
                    contentDescription = null,
                    tint = MaterialTheme.colorScheme.primary,
                    modifier = Modifier.size(35.dp)
                )
            }
        }

    }
}
