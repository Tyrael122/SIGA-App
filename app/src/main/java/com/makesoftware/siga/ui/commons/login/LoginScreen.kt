package com.makesoftware.siga.ui.commons.login

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.makesoftware.siga.R
import com.makesoftware.siga.ui.commons.components.DefaultElevatedButton
import com.makesoftware.siga.ui.commons.components.DefaultOutlinedTextField
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

        DefaultElevatedButton(
            onClick = onLogin, text = "Entrar", modifier = Modifier.padding(bottom = 76.dp)
        )
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

        DefaultElevatedButton(
            onClick = onLogin, text = "Entrar", modifier = Modifier.padding(bottom = 76.dp)
        )
    }
}

@Composable
private fun LoginHeaderText(modifier: Modifier = Modifier) {
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
private fun LoginForm(modifier: Modifier = Modifier, onPasswordReset: () -> Unit) {
    Column(horizontalAlignment = Alignment.CenterHorizontally, modifier = modifier) {
        var login by rememberSaveable { mutableStateOf("") }
        LoginTextField(
            value = login,
            placeholderText = "Login",
            onValueChange = { login = it },
        )

        Spacer(Modifier.height(30.dp))

        var password by rememberSaveable { mutableStateOf("") }
        LoginTextField(
            value = password,
            placeholderText = "Senha",
            onValueChange = { password = it },
        )

        TextButton(onClick = onPasswordReset, Modifier.align(Alignment.End)) {
            Text(text = "Esqueceu sua senha?", style = MaterialTheme.typography.bodyMedium)
        }
    }
}

@Composable
fun LoginTextField(value: String, onValueChange: (String) -> Unit, placeholderText: String) {
    DefaultOutlinedTextField(
        modifier = Modifier.fillMaxWidth(),
        value = value,
        placeholderText = placeholderText,
        onValueChange = onValueChange,
        focusedBorderThickness = 4.dp,
        unfocusedBorderThickness = 2.dp,
    )
}