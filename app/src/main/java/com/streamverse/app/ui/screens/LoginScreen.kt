package com.streamverse.app.ui.screens

import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.animation.core.*
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.focusable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.tv.material3.*
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.common.api.ApiException
import com.streamverse.app.auth.GoogleAuth
import com.streamverse.app.data.User
import com.streamverse.app.ui.theme.*

@OptIn(ExperimentalTvMaterial3Api::class)
@Composable
fun LoginScreen(onLogin: (User) -> Unit) {
    var email    by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var loading  by remember { mutableStateOf(false) }
    var errorMessage by remember { mutableStateOf<String?>(null) }

    val context = LocalContext.current
    val googleSignInClient = remember { GoogleAuth.getClient(context) }

    val googleSignInLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.StartActivityForResult()
    ) { result ->
        val task = GoogleSignIn.getSignedInAccountFromIntent(result.data)
        try {
            val account = task.getResult(ApiException::class.java)
            errorMessage = null
            onLogin(
                User(
                    name  = account.displayName ?: account.email?.substringBefore("@") ?: "Viewer",
                    email = account.email ?: "viewer@streamverse.com"
                )
            )
        } catch (e: ApiException) {
            // Status code 12501 = user cancelled the picker; don't show an error for that.
            if (e.statusCode != 12501) {
                errorMessage = "Google sign-in failed. Please try again."
            }
        }
    }

    // Animated glow pulse
    val glowAlpha by rememberInfiniteTransition(label = "glow").animateFloat(
        initialValue = 0.04f, targetValue = 0.10f,
        animationSpec = infiniteRepeatable(
            animation = tween(2000, easing = EaseInOutSine),
            repeatMode = RepeatMode.Reverse
        ), label = "glowAlpha"
    )

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(BackgroundDark),
        contentAlignment = Alignment.Center
    ) {
        // Background radial glow
        Box(
            modifier = Modifier
                .size(600.dp)
                .background(
                    Brush.radialGradient(
                        colors = listOf(
                            AccentRed.copy(alpha = glowAlpha),
                            Color.Transparent
                        )
                    )
                )
        )

        Row(
            modifier           = Modifier.fillMaxSize(),
            verticalAlignment  = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center
        ) {
            // ── LEFT: Branding ────────────────────────────────────
            Column(
                modifier            = Modifier.width(480.dp).padding(end = 60.dp),
                horizontalAlignment = Alignment.Start
            ) {
                Text(
                    text       = "STREAM\nVERSE",
                    fontSize   = 80.sp,
                    fontWeight = FontWeight.Black,
                    lineHeight = 76.sp,
                    color      = Color.Transparent,
                    modifier   = Modifier.then(
                        Modifier.background(
                            Brush.linearGradient(listOf(AccentRed, Color(0xFFFF6B6B))),
                            shape = RoundedCornerShape(4.dp)
                        )
                    )
                )
                Text(
                    text       = "STREAMVERSE",
                    fontSize   = 72.sp,
                    fontWeight = FontWeight.Black,
                    lineHeight = 68.sp,
                    style      = LocalTextStyle.current.copy(
                        brush = Brush.linearGradient(listOf(AccentRed, Color(0xFFFF6B6B)))
                    )
                )
                Spacer(Modifier.height(16.dp))
                Text(
                    text       = "EVERY SHOW · ENDLESS WORLDS",
                    fontSize   = 13.sp,
                    fontWeight = FontWeight.Medium,
                    letterSpacing = 5.sp,
                    color      = TextMuted
                )
                Spacer(Modifier.height(32.dp))
                Text(
                    text      = "25,000+ shows and movies\nfrom every corner of the world,\nall in one place.",
                    fontSize  = 18.sp,
                    color     = TextSecondary,
                    lineHeight = 28.sp
                )
            }

            // ── RIGHT: Login card ─────────────────────────────────
            Column(
                modifier = Modifier
                    .width(420.dp)
                    .clip(RoundedCornerShape(20.dp))
                    .background(Color(0xF50C0C1E))
                    .border(1.dp, BorderSubtle, RoundedCornerShape(20.dp))
                    .padding(44.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text       = "Welcome back",
                    fontSize   = 26.sp,
                    fontWeight = FontWeight.Bold,
                    color      = TextPrimary
                )
                Spacer(Modifier.height(6.dp))
                Text(
                    text     = "Sign in to your StreamVerse account",
                    fontSize = 14.sp,
                    color    = TextSecondary,
                    textAlign = TextAlign.Center
                )
                Spacer(Modifier.height(32.dp))

                // Email field
                TvTextField(
                    value    = email,
                    onChange = { email = it },
                    label    = "Email address",
                    keyboard = KeyboardType.Email
                )
                Spacer(Modifier.height(14.dp))

                // Password field
                TvTextField(
                    value    = password,
                    onChange = { password = it },
                    label    = "Password",
                    keyboard = KeyboardType.Password,
                    isPassword = true
                )
                Spacer(Modifier.height(28.dp))

                // Sign in button
                TvSignInButton(
                    loading = loading,
                    onClick = {
                        loading = true
                        onLogin(User(
                            name  = email.substringBefore("@").replaceFirstChar { it.uppercase() },
                            email = email.ifBlank { "viewer@streamverse.com" }
                        ))
                    }
                )

                Spacer(Modifier.height(20.dp))
                DividerWithLabel(label = "OR")
                Spacer(Modifier.height(20.dp))

                // Google Sign-In button
                TvGoogleSignInButton(
                    onClick = { googleSignInLauncher.launch(googleSignInClient.signInIntent) }
                )

                errorMessage?.let { msg ->
                    Spacer(Modifier.height(14.dp))
                    Text(msg, fontSize = 12.sp, color = AccentRed, textAlign = TextAlign.Center)
                }

                Spacer(Modifier.height(20.dp))
                Text(
                    text     = "No account? Sign up free",
                    fontSize = 13.sp,
                    color    = TextMuted,
                    textAlign = TextAlign.Center
                )
            }
        }
    }
}

@Composable
private fun DividerWithLabel(label: String) {
    Row(verticalAlignment = Alignment.CenterVertically, modifier = Modifier.fillMaxWidth()) {
        Box(Modifier.weight(1f).height(1.dp).background(BorderSubtle))
        Text(label, fontSize = 12.sp, color = TextMuted, modifier = Modifier.padding(horizontal = 12.dp))
        Box(Modifier.weight(1f).height(1.dp).background(BorderSubtle))
    }
}

@OptIn(ExperimentalTvMaterial3Api::class)
@Composable
private fun TvTextField(
    value: String,
    onChange: (String) -> Unit,
    label: String,
    keyboard: KeyboardType = KeyboardType.Text,
    isPassword: Boolean = false
) {
    var focused by remember { mutableStateOf(false) }
    OutlinedTextField(
        value         = value,
        onValueChange = onChange,
        label         = { androidx.compose.material3.Text(label, color = TextMuted, fontSize = 13.sp) },
        singleLine    = true,
        keyboardOptions = KeyboardOptions(keyboardType = keyboard),
        visualTransformation = if (isPassword) PasswordVisualTransformation() else
            androidx.compose.ui.text.input.VisualTransformation.None,
        colors = OutlinedTextFieldDefaults.colors(
            focusedTextColor     = TextPrimary,
            unfocusedTextColor   = TextSecondary,
            focusedBorderColor   = AccentRed,
            unfocusedBorderColor = BorderBright,
            cursorColor          = AccentRed,
            focusedContainerColor   = Color(0x0AFFFFFF),
            unfocusedContainerColor = Color(0x06FFFFFF),
        ),
        modifier = Modifier
            .fillMaxWidth()
            .onFocusChanged { focused = it.isFocused }
            .focusable()
    )
}

@OptIn(ExperimentalTvMaterial3Api::class)
@Composable
private fun TvSignInButton(loading: Boolean, onClick: () -> Unit) {
    var focused by remember { mutableStateOf(false) }
    Button(
        onClick   = onClick,
        modifier  = Modifier
            .fillMaxWidth()
            .height(52.dp)
            .onFocusChanged { focused = it.isFocused },
        colors    = ButtonDefaults.colors(
            containerColor         = AccentRed,
            focusedContainerColor  = Color(0xFFFF1A24),
            pressedContainerColor  = Color(0xFFC4080E),
            contentColor           = TextPrimary,
            focusedContentColor    = TextPrimary,
        ),
        shape = ButtonDefaults.shape(shape = RoundedCornerShape(10.dp))
    ) {
        Text(
            text       = if (loading) "Signing in…" else "Sign In",
            fontSize   = 16.sp,
            fontWeight = FontWeight.Bold
        )
    }
}

@OptIn(ExperimentalTvMaterial3Api::class)
@Composable
private fun TvGoogleSignInButton(onClick: () -> Unit) {
    var focused by remember { mutableStateOf(false) }
    Button(
        onClick  = onClick,
        modifier = Modifier
            .fillMaxWidth()
            .height(52.dp)
            .onFocusChanged { focused = it.isFocused },
        colors   = ButtonDefaults.colors(
            containerColor        = Color(0xFFF1F1F1),
            focusedContainerColor = Color.White,
            contentColor          = Color(0xFF1F1F1F),
            focusedContentColor   = Color(0xFF1F1F1F),
        ),
        shape = ButtonDefaults.shape(shape = RoundedCornerShape(10.dp))
    ) {
        Row(verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.spacedBy(10.dp)) {
            Text("G", fontSize = 18.sp, fontWeight = FontWeight.Black, color = Color(0xFF4285F4))
            Text("Sign in with Google", fontSize = 15.sp, fontWeight = FontWeight.SemiBold)
        }
    }
}
