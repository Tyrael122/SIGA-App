package com.makesoftware.siga.ui.login

import androidx.compose.foundation.Image
import androidx.compose.foundation.interaction.InteractionSource
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ElevatedButton
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.makesoftware.siga.R
import com.makesoftware.siga.ui.theme.alternativeTypography

@Composable
fun WelcomeScreen(onLogin: () -> Unit, modifier: Modifier = Modifier) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.SpaceBetween,
        modifier = modifier
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(26.dp),
        ) {
            Image(
                painter = painterResource(id = R.drawable.siga_logo),
                contentDescription = null,
                modifier = Modifier
                    .size(300.dp)
                    .padding(top = 32.dp)
            )

            Text(
                text = stringResource(id = R.string.app_name),
                style = MaterialTheme.typography.displayMedium,
                color = MaterialTheme.colorScheme.primary
            )

            Text(
                text = stringResource(id = R.string.default_presentation_message),
                style = alternativeTypography.bodyLarge,
                fontWeight = FontWeight.Normal,
                textAlign = TextAlign.Center,
                modifier = Modifier.padding(horizontal = 18.dp),
            )
        }

        DefaultElevatedButton(onClick = onLogin, text = "Entrar")
    }
}


@Composable
fun LoginFormScreen(
    onPasswordReset: () -> Unit, onLogin: () -> Unit, modifier: Modifier = Modifier
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.SpaceBetween,
        modifier = modifier
    ) {
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            LoginHeaderText(modifier = Modifier.padding(top = 32.dp))

            Spacer(Modifier.height(63.dp))

            LoginForm(
                onPasswordReset = onPasswordReset,
                modifier = modifier
                    .fillMaxWidth()
                    .padding(horizontal = 30.dp)
            )
        }

        DefaultElevatedButton(onClick = onLogin, text = "Entrar")
    }
}

@Composable
fun LoginHeaderText(modifier: Modifier = Modifier) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally, modifier = modifier
    ) {
        Text(
            text = stringResource(id = R.string.app_name),
            style = MaterialTheme.typography.headlineLarge,
            color = MaterialTheme.colorScheme.primary
        )

        Text(
            text = stringResource(id = R.string.default_welcome_message),
            style = alternativeTypography.bodyLarge,
            textAlign = TextAlign.Center,
        )
    }
}

@Composable
fun LoginForm(modifier: Modifier = Modifier, onPasswordReset: () -> Unit) {
    Column(horizontalAlignment = Alignment.CenterHorizontally, modifier = modifier) {
        var login by rememberSaveable { mutableStateOf("") }
        DefaultTextField(value = login, placeholderText = "Login", onValueChange = { login = it })

        Spacer(Modifier.height(30.dp))

        var password by rememberSaveable { mutableStateOf("") }
        DefaultTextField(
            value = password,
            placeholderText = "Senha",
            onValueChange = { password = it })

        TextButton(onClick = onPasswordReset, Modifier.align(Alignment.End)) {
            Text(text = "Esqueceu sua senha?", style = MaterialTheme.typography.bodyMedium)
        }
    }
}

@Composable
fun DefaultElevatedButton(onClick: () -> Unit, text: String, modifier: Modifier = Modifier) {
    ElevatedButton(
        onClick = onClick,
        colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.primary),
        shape = RoundedCornerShape(10.dp),
        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = 76.dp)
            .padding(bottom = 76.dp)
            .height(60.dp)
            .widthIn(max = 245.dp)
    ) {
        Text(
            text = text,
            style = alternativeTypography.bodyLarge,
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DefaultTextField(
    value: String,
    placeholderText: String,
    onValueChange: (String) -> Unit,
    modifier: Modifier = Modifier,
    singleLine: Boolean = true,
    visualTransformation: VisualTransformation = VisualTransformation.None,
    interactionSource: InteractionSource = remember { MutableInteractionSource() },
    enabled: Boolean = true,
    isError: Boolean = false,
) {

    val textStyle = MaterialTheme.typography.bodyLarge
    BasicTextField(
        value = value,
        onValueChange = onValueChange,
        textStyle = textStyle,
        modifier = modifier.fillMaxWidth()
    ) {
        TextFieldDefaults.OutlinedTextFieldDecorationBox(
            value = value,
            innerTextField = it,
            enabled = true,
            singleLine = singleLine,
            visualTransformation = visualTransformation,
            interactionSource = interactionSource,
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
                colors = TextFieldDefaults.outlinedTextFieldColors(
                    containerColor = Color(0xFFF1F4FF),
                    unfocusedBorderColor = MaterialTheme.colorScheme.primary,
                ),
                focusedBorderThickness = 4.dp,
                unfocusedBorderThickness = 2.dp
            )
        }
    }
}