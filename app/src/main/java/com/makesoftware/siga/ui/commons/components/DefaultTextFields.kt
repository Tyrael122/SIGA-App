package com.makesoftware.siga.ui.commons.components

import androidx.compose.foundation.interaction.InteractionSource
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldColors
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.makesoftware.siga.ui.theme.AlternativeColorScheme


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DefaultOutlinedTextField(
    modifier: Modifier = Modifier,
    value: String,
    placeholderText: String = "",
    onValueChange: (String) -> Unit,
    textStyle: TextStyle = MaterialTheme.typography.bodyLarge,
    containerColor: Color = AlternativeColorScheme.secondary_color,
    focusedBorderThickness: Dp = 2.dp,
    unfocusedBorderThickness: Dp = 1.dp,
    singleLine: Boolean = true,
    leadingIcon: @Composable (() -> Unit)? = null,
    colors: TextFieldColors = TextFieldDefaults.outlinedTextFieldColors(
        containerColor = containerColor,
        unfocusedBorderColor = MaterialTheme.colorScheme.primary,
    ),
    visualTransformation: VisualTransformation = VisualTransformation.None,
    interactionSource: InteractionSource = remember { MutableInteractionSource() },
    enabled: Boolean = true,
    isReadOnly: Boolean = false,
    isError: Boolean = false,
    keyboardActions: KeyboardActions = KeyboardActions(),
    keyboardOptions: KeyboardOptions = KeyboardOptions(),
    contentPadding: PaddingValues = TextFieldDefaults.outlinedTextFieldPadding(),
    minLines: Int = 1,
) {
    BasicTextField(
        value = value,
        onValueChange = onValueChange,
        textStyle = textStyle,
        singleLine = singleLine,
        minLines = minLines,
        readOnly = isReadOnly,
        keyboardActions = keyboardActions,
        keyboardOptions = keyboardOptions,
        modifier = modifier
    ) {
        TextFieldDefaults.OutlinedTextFieldDecorationBox(
            value = value,
            innerTextField = it,
            enabled = enabled,
            singleLine = singleLine,
            visualTransformation = visualTransformation,
            interactionSource = interactionSource,
            leadingIcon = leadingIcon,
            contentPadding = contentPadding,
            placeholder = {
                Text(
                    text = placeholderText, style = textStyle
                )
            },
        ) {
            TextFieldDefaults.OutlinedBorderContainerBox(
                enabled = enabled,
                isError = isError,
                interactionSource = interactionSource,
                shape = RoundedCornerShape(10.dp),
                colors = colors,
                focusedBorderThickness = focusedBorderThickness,
                unfocusedBorderThickness = unfocusedBorderThickness
            )
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DefaultNumberOutlinedTextField(
    modifier: Modifier = Modifier,
    value: Int,
    onValueChange: (Int) -> Unit,
    isReadOnly: Boolean = false,
    maxCharCount: Int = Int.MAX_VALUE
) {
    DefaultOutlinedTextField(
        modifier = modifier.heightIn(min = 40.dp),
        value = value.toString(),
        onValueChange = {
            if (it.length > maxCharCount) {
                return@DefaultOutlinedTextField
            }

            val number = if (value == 0) {
                it.replace("0", "").toIntOrNull()
            } else {
                it.toIntOrNull()
            }

            onValueChange(number ?: 0)
        },
        textStyle = MaterialTheme.typography.bodyLarge.copy(textAlign = TextAlign.Center),
        colors = TextFieldDefaults.outlinedTextFieldColors(
            containerColor = MaterialTheme.colorScheme.surface,
            textColor = MaterialTheme.colorScheme.onSurface,
            focusedBorderColor = MaterialTheme.colorScheme.onSurface,
            unfocusedBorderColor = MaterialTheme.colorScheme.onSurface,
        ),
        isReadOnly = isReadOnly,
        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
        contentPadding = PaddingValues(horizontal = 5.dp),
    )
}
