package com.streamverse.app.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.focusable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.scale
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.tv.material3.*
import coil.compose.AsyncImage
import com.streamverse.app.data.*
import com.streamverse.app.ui.theme.*

@OptIn(ExperimentalTvMaterial3Api::class)
@Composable
fun HomeScreen(
    user: com.streamverse.app.data.User?,
    onShowClick: (Show) -> Unit,
    onLogout: () -> Unit
) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(BackgroundDark)
    ) {
        LazyColumn(modifier = Modifier.fillMaxSize()) {

            // ── HERO BANNER ──────────────────────────────────────
            item {
                HeroBanner(show = HERO_SHOW, onShowClick = onShowClick)
            }

            // ── CATEGORY ROWS ────────────────────────────────────
            items(CATEGORIES) { (catId, catLabel) ->
                val shows = getShowsForCategory(catId)
                if (shows.isNotEmpty()) {
                    CategoryRow(
                        label     = catLabel,
                        shows     = shows,
                        onShowClick = onShowClick
                    )
                }
            }

            item { Spacer(Modifier.height(60.dp)) }
        }

        // ── NAVBAR OVERLAY ──────────────────────────────────────
        Navbar(user = user, onLogout = onLogout)
    }
}

// ── NAVBAR ───────────────────────────────────────────────────────────
@OptIn(ExperimentalTvMaterial3Api::class)
@Composable
private fun Navbar(
    user: com.streamverse.app.data.User?,
    onLogout: () -> Unit
) {
    val navLinks = listOf("Home", "TV Shows", "Movies", "New & Popular", "My List")
    var logoutFocused by remember { mutableStateOf(false) }

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .height(68.dp)
            .background(
                Brush.verticalGradient(
                    listOf(Color(0xF705050E), Color.Transparent)
                )
            )
            .padding(horizontal = 60.dp),
        verticalAlignment      = Alignment.CenterVertically,
        horizontalArrangement  = Arrangement.SpaceBetween
    ) {
        // Logo
        Text(
            text       = "STREAMVERSE",
            fontSize   = 28.sp,
            fontWeight = FontWeight.Black,
            letterSpacing = 2.sp,
            color      = AccentRed
        )

        // Nav links
        Row(horizontalArrangement = Arrangement.spacedBy(28.dp)) {
            navLinks.forEach { label ->
                var focused by remember { mutableStateOf(false) }
                Text(
                    text      = label,
                    fontSize  = 14.sp,
                    color     = if (focused) TextPrimary else TextSecondary,
                    modifier  = Modifier
                        .onFocusChanged { focused = it.isFocused }
                        .focusable()
                )
            }
        }

        // User avatar + logout
        Row(
            verticalAlignment     = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            Text(
                text      = user?.name ?: "Guest",
                fontSize  = 14.sp,
                color     = TextSecondary
            )
            // Avatar circle
            Box(
                modifier            = Modifier
                    .size(38.dp)
                    .clip(CircleShape)
                    .background(
                        Brush.linearGradient(listOf(AccentRed, Color(0xFF8A0008)))
                    ),
                contentAlignment    = Alignment.Center
            ) {
                Text(
                    text       = user?.name?.firstOrNull()?.uppercase() ?: "G",
                    fontSize   = 16.sp,
                    fontWeight = FontWeight.Bold,
                    color      = TextPrimary
                )
            }
            // Logout button
            Surface(
                onClick  = onLogout,
                modifier = Modifier
                    .onFocusChanged { logoutFocused = it.isFocused },
                shape    = ClickableSurfaceDefaults.shape(RoundedCornerShape(8.dp)),
                colors   = ClickableSurfaceDefaults.colors(
                    containerColor        = Color(0x0AFFFFFF),
                    focusedContainerColor = Color(0x22FFFFFF),
                )
            ) {
                Text(
                    text     = "Sign Out",
                    fontSize = 13.sp,
                    color    = TextSecondary,
                    modifier = Modifier.padding(horizontal = 14.dp, vertical = 8.dp)
                )
            }
        }
    }
}

// ── HERO BANNER ──────────────────────────────────────────────────────
@OptIn(ExperimentalTvMaterial3Api::class)
@Composable
private fun HeroBanner(show: Show, onShowClick: (Show) -> Unit) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(520.dp)
    ) {
        // Backdrop image
        AsyncImage(
            model             = show.heroUrl,
            contentDescription = show.title,
            contentScale      = ContentScale.Crop,
            modifier          = Modifier.fillMaxSize()
        )

        // Gradient overlays
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(
                    Brush.horizontalGradient(
                        listOf(Color(0xF705050E), Color(0x8C05050E), Color.Transparent)
                    )
                )
        )
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(
                    Brush.verticalGradient(
                        listOf(Color.Transparent, Color(0xFF05050E)),
                        startY = 0.45f
                    )
                )
        )

        // Content
        Column(
            modifier = Modifier
                .align(Alignment.BottomStart)
                .padding(start = 60.dp, bottom = 52.dp)
        ) {
            // Tags
            Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                show.tags.forEach { tag ->
                    Box(
                        modifier = Modifier
                            .clip(RoundedCornerShape(20.dp))
                            .background(Color(0x12FFFFFF))
                            .border(1.dp, BorderSubtle, RoundedCornerShape(20.dp))
                            .padding(horizontal = 12.dp, vertical = 4.dp)
                    ) {
                        Text(tag, fontSize = 12.sp, color = TextSecondary)
                    }
                }
            }
            Spacer(Modifier.height(14.dp))

            // Title
            Text(
                text       = show.title.uppercase(),
                fontSize   = 68.sp,
                fontWeight = FontWeight.Black,
                lineHeight = 64.sp,
                color      = TextPrimary,
                letterSpacing = 2.sp
            )
            Spacer(Modifier.height(12.dp))

            // Meta row
            Row(
                verticalAlignment     = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                Text("${show.matchPercent}% Match", fontSize = 14.sp,
                    fontWeight = FontWeight.Bold, color = MatchGreen)
                Text(show.year.toString(), fontSize = 14.sp, color = TextSecondary)
                Box(
                    modifier = Modifier
                        .border(1.dp, TextMuted, RoundedCornerShape(4.dp))
                        .padding(horizontal = 6.dp, vertical = 2.dp)
                ) { Text(show.rating, fontSize = 12.sp, color = TextMuted) }
                Text("${show.seasonCount} Seasons", fontSize = 14.sp, color = TextSecondary)
            }
            Spacer(Modifier.height(14.dp))

            // Description
            Text(
                text      = show.description,
                fontSize  = 15.sp,
                color     = Color(0xFFC0C0D8),
                lineHeight = 24.sp,
                maxLines  = 2,
                overflow  = TextOverflow.Ellipsis,
                modifier  = Modifier.widthIn(max = 500.dp)
            )
            Spacer(Modifier.height(28.dp))

            // Buttons
            Row(horizontalArrangement = Arrangement.spacedBy(12.dp)) {
                HeroButton(label = "▶  Play", primary = true, onClick = { onShowClick(show) })
                HeroButton(label = "ℹ  More Info", primary = false, onClick = { onShowClick(show) })
            }
        }
    }
}

@OptIn(ExperimentalTvMaterial3Api::class)
@Composable
private fun HeroButton(label: String, primary: Boolean, onClick: () -> Unit) {
    Button(
        onClick  = onClick,
        colors   = ButtonDefaults.colors(
            containerColor        = if (primary) TextPrimary else Color(0x20FFFFFF),
            focusedContainerColor = if (primary) Color(0xFFE8E8E8) else Color(0x38FFFFFF),
            contentColor          = if (primary) Color(0xFF111111) else TextPrimary,
            focusedContentColor   = if (primary) Color(0xFF111111) else TextPrimary,
        ),
        shape    = ButtonDefaults.shape(RoundedCornerShape(8.dp)),
        modifier = Modifier.height(48.dp)
    ) {
        Text(text = label, fontSize = 15.sp, fontWeight = FontWeight.Bold)
    }
}

// ── CATEGORY ROW ─────────────────────────────────────────────────────
@OptIn(ExperimentalTvMaterial3Api::class)
@Composable
private fun CategoryRow(
    label: String,
    shows: List<Show>,
    onShowClick: (Show) -> Unit
) {
    Column(modifier = Modifier.padding(bottom = 40.dp)) {
        Text(
            text       = label,
            fontSize   = 20.sp,
            fontWeight = FontWeight.Bold,
            color      = TextPrimary,
            modifier   = Modifier.padding(start = 60.dp, bottom = 16.dp)
        )
        LazyRow(
            contentPadding        = PaddingValues(horizontal = 60.dp),
            horizontalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            items(shows) { show ->
                ShowCard(show = show, onClick = { onShowClick(show) })
            }
        }
    }
}

// ── SHOW CARD ─────────────────────────────────────────────────────────
@OptIn(ExperimentalTvMaterial3Api::class)
@Composable
private fun ShowCard(show: Show, onClick: () -> Unit) {
    var focused by remember { mutableStateOf(false) }

    Surface(
        onClick   = onClick,
        modifier  = Modifier
            .width(155.dp)
            .height(250.dp)
            .scale(if (focused) 1.08f else 1f)
            .onFocusChanged { focused = it.isFocused },
        shape     = ClickableSurfaceDefaults.shape(RoundedCornerShape(10.dp)),
        colors    = ClickableSurfaceDefaults.colors(
            containerColor        = CardDark,
            focusedContainerColor = CardHovered,
        )
    ) {
        Box(modifier = Modifier.fillMaxSize()) {
            // Poster image
            AsyncImage(
                model             = show.posterUrl,
                contentDescription = show.title,
                contentScale      = ContentScale.Crop,
                modifier          = Modifier.fillMaxSize()
            )

            // Gradient overlay
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(
                        Brush.verticalGradient(
                            listOf(Color.Transparent, show.color.copy(alpha = 0.47f)),
                            startY = 0.45f
                        )
                    )
            )

            // Badge top-left
            Box(
                modifier = Modifier
                    .padding(8.dp)
                    .size(30.dp)
                    .clip(CircleShape)
                    .background(Color(0x99000000))
                    .align(Alignment.TopStart),
                contentAlignment = Alignment.Center
            ) {
                Text(show.badge, fontSize = 14.sp)
            }

            // Rating top-right
            Box(
                modifier = Modifier
                    .padding(8.dp)
                    .clip(RoundedCornerShape(4.dp))
                    .background(Color(0xA6000000))
                    .padding(horizontal = 6.dp, vertical = 3.dp)
                    .align(Alignment.TopEnd)
            ) {
                Text(show.rating, fontSize = 10.sp, color = TextSecondary, fontWeight = FontWeight.SemiBold)
            }

            // Match % bottom-left
            Text(
                text     = "${show.matchPercent}% Match",
                fontSize = 11.sp,
                color    = MatchGreen,
                fontWeight = FontWeight.Bold,
                modifier = Modifier
                    .align(Alignment.BottomStart)
                    .padding(8.dp)
            )

            // Play overlay on focus
            if (focused) {
                Box(
                    modifier         = Modifier
                        .fillMaxSize()
                        .background(Color(0x61000000)),
                    contentAlignment = Alignment.Center
                ) {
                    Box(
                        modifier         = Modifier
                            .size(48.dp)
                            .clip(CircleShape)
                            .background(Color(0xEDFFFFFF)),
                        contentAlignment = Alignment.Center
                    ) {
                        Text("▶", fontSize = 18.sp, color = Color(0xFF111111))
                    }
                }
            }
        }
    }

    // Title below card
    Spacer(Modifier.height(8.dp))
    Text(
        text     = show.title,
        fontSize = 13.sp,
        fontWeight = FontWeight.SemiBold,
        color    = if (focused) TextPrimary else TextSecondary,
        maxLines = 1,
        overflow = TextOverflow.Ellipsis,
        modifier = Modifier.widthIn(max = 155.dp).padding(horizontal = 2.dp)
    )
    Text(
        text     = "${show.year} · ${show.tags.first()}",
        fontSize = 11.sp,
        color    = TextMuted,
        modifier = Modifier.padding(horizontal = 2.dp)
    )
}
