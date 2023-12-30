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
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldColors
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.PopupProperties
import com.makesoftware.siga.ui.theme.AlternativeColorScheme
import com.makesoftware.siga.ui.theme.alternativeTypography
import com.makesoftware.siga.ui.users.admin.screens.LocalIsReadOnly


@Composable
fun FormTextField(
    value: String,
    onValueChange: (String) -> Unit,
    isReadOnly: Boolean? = null,
    minLines: Int = 1,
    placeholderText: String
) {
    DefaultOutlinedTextField(
        modifier = Modifier.fillMaxWidth(),
        value = value,
        placeholderText = placeholderText,
        onValueChange = onValueChange,
        singleLine = false,
        isReadOnly = isReadOnly ?: LocalIsReadOnly.current,
        minLines = minLines,
    )
}

@Composable
fun FormNumberTextField(
    modifier: Modifier = Modifier,
    value: Int,
    onValueChange: (Int) -> Unit,
    fieldDescription: String,
    maxCharCount: Int = 2,
    isReadOnly: Boolean? = null,
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
            isReadOnly = isReadOnly ?: LocalIsReadOnly.current,
            modifier = Modifier.width(40.dp),
        )
    }
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

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FormDropdownMenu(
    modifier: Modifier = Modifier,

    // Can change later to a List<Pair<Long, String>>.
    // You could provide another function, onValueChanged, that would return the id of the selected item, when there was a match in the list.
    // Provide a parameter called selectedValue too.
    // Then, you wouldn't need to override the selectedOptionText. If you still did, you would need to override onSelectionChanged too.

    options: List<String>,
    selectedOptionText: String,
    onSelectionChanged: (String) -> Unit,
    isSearchable: Boolean = true,
    textFieldColors: TextFieldColors = ExposedDropdownMenuDefaults.textFieldColors(
        containerColor = AlternativeColorScheme.secondary_color,
        unfocusedIndicatorColor = MaterialTheme.colorScheme.primary,
    ),
    placeholder: @Composable (() -> Unit)? = null,
    textFieldShape: Shape = RoundedCornerShape(10.dp),
) {
    var expanded by remember { mutableStateOf(false) }

    ExposedDropdownMenuBox(
        expanded = expanded,
        onExpandedChange = { expanded = it },
        modifier = modifier,
    ) {

        val focusRequester = remember { FocusRequester() }
        val focusManager = LocalFocusManager.current

        val filteredOptions = if (isSearchable) {
            options.filter { it.contains(selectedOptionText, ignoreCase = true) }
        } else {
            options
        }

        OutlinedTextField(
            modifier = Modifier
                .menuAnchor()
                .fillMaxWidth()
                .clip(RoundedCornerShape(10.dp))
                .focusRequester(focusRequester)
                .onFocusChanged {
                    expanded = it.isFocused

                    if (!filteredOptions.contains(selectedOptionText)) {
                        onSelectionChanged("")
                    }
                },
            value = selectedOptionText,
            readOnly = !isSearchable,
            onValueChange = onSelectionChanged,
            placeholder = placeholder,
            shape = textFieldShape,
            trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = expanded) },
            colors = textFieldColors,
        )

        if (filteredOptions.isNotEmpty()) {
            DropdownMenu(
                modifier = Modifier.exposedDropdownSize(true),
                properties = createPopUpProperties(isSearchable),
                expanded = expanded,
                onDismissRequest = {
                    expanded = false
                },
            ) {
                filteredOptions.forEach { selectionOption ->
                    DropdownMenuItem(
                        text = { Text(selectionOption) },
                        onClick = {
                            expanded = false
                            focusManager.clearFocus()
                            onSelectionChanged(selectionOption)
                        },
                        contentPadding = ExposedDropdownMenuDefaults.ItemContentPadding,
                    )
                }
            }
        }
    }
}

fun createPopUpProperties(isSearchable: Boolean): PopupProperties {
    return if (isSearchable) {
        PopupProperties(
            focusable = false,
            dismissOnClickOutside = false // This is false because the loss of focus is handled by the TextField. (See onFocusChanged)
        )
    } else {
        PopupProperties(
            focusable = true, dismissOnClickOutside = true, dismissOnBackPress = true
        )
    }
}
