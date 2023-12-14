package com.makesoftware.siga.ui.users.admin.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.interaction.InteractionSource
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.FilterAlt
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ElevatedButton
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.IconButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldColors
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import com.makesoftware.siga.ui.theme.secondary_color

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AdminCourseScreen(modifier: Modifier = Modifier) {
    Scaffold(
        floatingActionButton = {
            FloatingActionButton(
                onClick = { /* TODO */ }, modifier = Modifier.padding(end = 16.dp, bottom = 16.dp)
            ) {
                Icon(Icons.Filled.Add, contentDescription = null)
            }
        }, modifier = modifier
    ) {
        DataGrid(
            modifier = Modifier
                .padding(it)
                .padding(horizontal = 15.dp)
                .padding(top = 30.dp, bottom = 15.dp)
        )
    }
}

@Composable
fun DataGrid(modifier: Modifier = Modifier, backgroundColor: Color = secondary_color) {
    Column(
        verticalArrangement = Arrangement.SpaceBetween,
        modifier = modifier
            .shadow(1.dp, RoundedCornerShape(10.dp))
            .fillMaxSize()
            .clip(RoundedCornerShape(10.dp))
            .background(backgroundColor)
    ) {
        SearchBar(
            Modifier
                .padding(horizontal = 12.dp)
                .padding(top = 15.dp)
        )
    }
}

@Composable
fun SearchBar(modifier: Modifier = Modifier) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = modifier
            .height(40.dp)
            .fillMaxWidth()
    ) {
        FilterButton()

        Spacer(Modifier.width(16.dp))

        SearchTextField(modifier = Modifier.weight(1f))
    }
}

@Composable
fun FilterButton(modifier: Modifier = Modifier) {
    ElevatedButton(
        onClick = { /*TODO*/ },
        contentPadding = PaddingValues(0.dp),
        shape = RoundedCornerShape(10.dp),
        colors = ButtonDefaults.buttonColors(
            containerColor = Color.White, contentColor = MaterialTheme.colorScheme.onSurface
        ),
        modifier = modifier
            .width(50.dp)
            .fillMaxHeight()
    ) {
        Icon(Icons.Filled.FilterAlt, contentDescription = null)
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SearchTextField(modifier: Modifier = Modifier) {
    Row(
        verticalAlignment = Alignment.CenterVertically, modifier = modifier.fillMaxHeight()
    ) {

        var text by remember { mutableStateOf("") }

        BasicFilledTextField(
            value = text,
            onValueChange = { text = it },
            textStyle = MaterialTheme.typography.bodyLarge.copy(fontWeight = FontWeight.Normal),
            placeholderText = "Search...",
            leadingIcon = {
                Icon(
                    Icons.Filled.Search,
                    contentDescription = null,
                )
            },
            colors = TextFieldDefaults.textFieldColors(
                containerColor = MaterialTheme.colorScheme.surface,
                textColor = MaterialTheme.colorScheme.onSurface,
                focusedIndicatorColor = Color.Transparent,
                unfocusedIndicatorColor = Color.Transparent,
                disabledIndicatorColor = Color.Transparent
            ),
            modifier = Modifier
                .weight(1f)
                .shadow(1.dp, RoundedCornerShape(10.dp))
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BasicFilledTextField(
    value: String,
    modifier: Modifier = Modifier,
    placeholderText: String = "",
    onValueChange: (String) -> Unit,
    textStyle: TextStyle,
    shape: RoundedCornerShape = RoundedCornerShape(10.dp),
    leadingIcon: @Composable (() -> Unit)? = null,
    colors: TextFieldColors = TextFieldDefaults.textFieldColors(),
    contentPadding: PaddingValues = PaddingValues(0.dp),
    singleLine: Boolean = true,
    visualTransformation: VisualTransformation = VisualTransformation.None,
    interactionSource: InteractionSource = remember { MutableInteractionSource() },
    enabled: Boolean = true,
    isError: Boolean = false,
) {
    BasicTextField(
        value = value,
        onValueChange = onValueChange,
        textStyle = textStyle,
        modifier = modifier.fillMaxWidth()
    ) {
        TextFieldDefaults.OutlinedTextFieldDecorationBox(
            value = value,
            innerTextField = it,
            contentPadding = contentPadding,
            enabled = true,
            singleLine = singleLine,
            leadingIcon = leadingIcon,
            visualTransformation = visualTransformation,
            interactionSource = interactionSource,
            colors = colors,
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
                colors = colors,
                shape = shape,
                focusedBorderThickness = 0.dp,
                unfocusedBorderThickness = 0.dp
            )
        }
    }
}