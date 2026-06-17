package com.streamverse.app.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.focusable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.tv.material3.*
import coil3.compose.AsyncImage
import com.streamverse.app.data.Episode
import com.streamverse.app.data.Show
import com.streamverse.app.data.User
import com.streamverse.app.ui.theme.*

@OptIn(ExperimentalTvMaterial3Api::class)
@Composable
 ShowDetailScreen(
    show: Show,
    user: User?,
    onBack: () -> Unit
) {
    var selectedSeason by remember { mutableStateOf(1) }
    val episodes = show.getEpisodes(selectedSeason)

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(BackgroundDark)
    ) {
        LazyColumn(modifier = Modifier.fillMaxSize()) {

            // ── BACK NAV BAR ────────────────────────────────────
            item {
                DetailNavbar(user = user, onBack = onBack)
            }

            // ── SHOW BANNER ─────────────────────────────────────
            item {
                ShowBanner(show = show)
            }

            // ── SEASONS + EPISODES ──────────────────────────────
            item {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 60.dp, vertical = 40.dp),
                    horizontalArrangement = Arrangement.spacedBy(40.dp)
                ) {
                    // Left: season sidebar
                    SeasonSidebar(
                        seasonCount     = show.seasonCount,
                        selectedSeason  = selectedSeason,
                        onSeasonSelect  = { selectedSeason = it }
                    )

                    // Right: episodes
                    EpisodeList(
                        show     = show,
                        season   = selectedSeason,
                        episodes = episodes,
                        modifier = Modifier.weight(1f)
                    )
                }
            }

            item { Spacer(Modifier.height(60.dp)) }
        }
    }
}

// ── DETAIL NAVBAR ────────────────────────────────────────────────────
@OptIn(ExperimentalTvMaterial3Api::class)
@Composable
private fun DetailNavbar(user: User?, onBack: () -> Unit) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .height(68.dp)
            .background(Color(0xF705050E))
            .border(width = 1.dp, color = BorderSubtle,
                shape = RoundedCornerShape(0.dp))
            .padding(horizontal = 60.dp),
        verticalAlignment     = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Row(
            verticalAlignment     = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(18.dp)
        ) {
            // Back button
            Surface(
                onClick  = onBack,
                shape    = ClickableSurfaceDefaults.shape(RoundedCornerShape(8.dp)),
                colors   = ClickableSurfaceDefaults.colors(
                    containerColor        = Color(0x0DFFFFFF),
                    focusedContainerColor = Color(0x1AFFFFFF),
                )
            ) {
                Row(
                    verticalAlignment      = Alignment.CenterVertically,
                    horizontalArrangement  = Arrangement.spacedBy(6.dp),
                    modifier               = Modifier.padding(horizontal = 14.dp, vertical = 8.dp)
                ) {
                    Text("←", fontSize = 16.sp, color = TextPrimary)
                    Text("Back", fontSize = 14.sp, fontWeight = FontWeight.SemiBold, color = TextPrimary)
                }
            }
            Text(
                text          = "STREAMVERSE",
                fontSize      = 26.sp,
                fontWeight    = FontWeight.Black,
                letterSpacing = 2.sp,
                color         = AccentRed
            )
        }

        // User avatar
        Box(
            modifier         = Modifier
                .size(36.dp)
                .clip(androidx.compose.foundation.shape.CircleShape)
                .background(Brush.linearGradient(listOf(AccentRed, Color(0xFF8A0008)))),
            contentAlignment = Alignment.Center
        ) {
            Text(
                text       = user?.name?.firstOrNull()?.uppercase() ?: "G",
                fontSize   = 14.sp,
                fontWeight = FontWeight.Bold,
                color      = TextPrimary
            )
        }
    }
}

// ── SHOW BANNER ──────────────────────────────────────────────────────
@Composable
private fun ShowBanner(show: Show) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(380.dp)
    ) {
        AsyncImage(
            model              = show.bannerUrl,
            contentDescription = show.title,
            contentScale       = ContentScale.Crop,
            modifier           = Modifier.fillMaxSize()
        )

        // Gradients
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(
                    Brush.horizontalGradient(
                        listOf(Color(0xF205050E), Color.Transparent),
                        startX = 0f, endX = 0.75f
                    )
                )
        )
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(
                    Brush.verticalGradient(
                        listOf(Color.Transparent, Color(0xFF05050E)),
                        startY = 0.6f
                    )
                )
        )

        // Show info
        Column(
            modifier = Modifier
                .align(Alignment.BottomStart)
                .padding(start = 60.dp, bottom = 32.dp)
        ) {
            // Emoji badge
            Box(
                modifier = Modifier
                    .size(52.dp)
                    .clip(androidx.compose.foundation.shape.CircleShape)
                    .background(show.color.copy(alpha = 0.8f))
                    .border(2.dp, Color(0x2EFFFFFF),
                        androidx.compose.foundation.shape.CircleShape),
                contentAlignment = Alignment.Center
            ) {
                Text(show.badge, fontSize = 22.sp)
            }

            Spacer(Modifier.height(10.dp))
            Text(
                text          = show.title.uppercase(),
                fontSize      = 54.sp,
                fontWeight    = FontWeight.Black,
                letterSpacing = 1.5.sp,
                lineHeight    = 52.sp,
                color         = TextPrimary
            )
            Spacer(Modifier.height(10.dp))
            Row(
                verticalAlignment     = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(14.dp)
            ) {
                Text("${show.matchPercent}% Match", fontSize = 14.sp,
                    fontWeight = FontWeight.Bold, color = MatchGreen)
                Text(show.year.toString(), fontSize = 14.sp, color = TextSecondary)
                Box(
                    modifier = Modifier
                        .border(1.dp, TextMuted, RoundedCornerShape(4.dp))
                        .padding(horizontal = 6.dp, vertical = 2.dp)
                ) { Text(show.rating, fontSize = 11.sp, color = TextMuted) }
                Text("${show.seasonCount} Seasons", fontSize = 14.sp, color = TextSecondary)
            }
            Spacer(Modifier.height(10.dp))
            Text(
                text      = show.description,
                fontSize  = 14.sp,
                color     = Color(0xFFB8B8CC),
                lineHeight = 22.sp,
                maxLines  = 2,
                overflow  = TextOverflow.Ellipsis,
                modifier  = Modifier.widthIn(max = 520.dp)
            )
            Spacer(Modifier.height(14.dp))
            Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                show.tags.forEach { tag ->
                    Box(
                        modifier = Modifier
                            .clip(RoundedCornerShape(20.dp))
                            .background(Color(0x0FFFFFFF))
                            .border(1.dp, BorderSubtle, RoundedCornerShape(20.dp))
                            .padding(horizontal = 12.dp, vertical = 4.dp)
                    ) { Text(tag, fontSize = 12.sp, color = TextSecondary) }
                }
            }
        }
    }
}

// ── SEASON SIDEBAR ───────────────────────────────────────────────────
@OptIn(ExperimentalTvMaterial3Api::class)
@Composable
private fun SeasonSidebar(
    seasonCount: Int,
    selectedSeason: Int,
    onSeasonSelect: (Int) -> Unit
) {
    val listState = rememberLazyListState()

    Column(modifier = Modifier.width(200.dp)) {
        Text(
            text          = "SEASONS",
            fontSize      = 11.sp,
            fontWeight    = FontWeight.Bold,
            letterSpacing = 2.sp,
            color         = TextMuted,
            modifier      = Modifier.padding(bottom = 16.dp)
        )
        LazyColumn(
            state   = listState,
            modifier = Modifier.heightIn(max = 500.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            items(seasonCount) { index ->
                val sn     = index + 1
                val active = selectedSeason == sn
                var focused by remember { mutableStateOf(false) }

                Surface(
                    onClick  = { onSeasonSelect(sn) },
                    modifier = Modifier
                        .fillMaxWidth()
                        .onFocusChanged { focused = it.isFocused },
                    shape    = ClickableSurfaceDefaults.shape(RoundedCornerShape(10.dp)),
                    colors   = ClickableSurfaceDefaults.colors(
                        containerColor        = if (active) AccentRed else Color(0x00FFFFFF),
                        focusedContainerColor = if (active) Color(0xFFFF1A24) else Color(0x0DFFFFFF),
                    ),
                    border   = ClickableSurfaceDefaults.border(
                        border = Border(
                            border = androidx.compose.foundation.BorderStroke(
                                1.dp,
                                if (active) AccentRed
                                else if (focused) BorderBright
                                else BorderSubtle
                            )
                        )
                    )
                ) {
                    Row(
                        modifier              = Modifier.padding(horizontal = 16.dp, vertical = 12.dp),
                        verticalAlignment     = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(10.dp)
                    ) {
                        Text(
                            text       = if (active) "✓" else " ",
                            fontSize   = 13.sp,
                            color      = if (active) TextPrimary else Color.Transparent
                        )
                        Text(
                            text       = "Season $sn",
                            fontSize   = 14.sp,
                            fontWeight = if (active) FontWeight.Bold else FontWeight.Normal,
                            color      = if (active) TextPrimary else TextSecondary
                        )
                    }
                }
            }
        }
    }
}

// ── EPISODE LIST ─────────────────────────────────────────────────────
@OptIn(ExperimentalTvMaterial3Api::class)
@Composable
private fun EpisodeList(show: Show, season: Int, episodes: List<Episode>, modifier: Modifier = Modifier) {
    Column(modifier = modifier) {
        Row(
            modifier              = Modifier.fillMaxWidth().padding(bottom = 22.dp),
            verticalAlignment     = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Column {
                Text("Season $season", fontSize = 22.sp,
                    fontWeight = FontWeight.Bold, color = TextPrimary)
                Text("${episodes.size} Episodes", fontSize = 13.sp,
                    color = TextMuted, modifier = Modifier.padding(top = 4.dp))
            }
            Box(
                modifier = Modifier
                    .clip(RoundedCornerShape(20.dp))
                    .background(AccentRed.copy(alpha = 0.12f))
                    .border(1.dp, AccentRed.copy(alpha = 0.27f), RoundedCornerShape(20.dp))
                    .padding(horizontal = 14.dp, vertical = 6.dp)
            ) {
                Text("Season 1 by default", fontSize = 12.sp,
                    color = AccentRed, fontWeight = FontWeight.SemiBold)
            }
        }

        // Episode cards
        episodes.forEach { ep ->
            EpisodeCard(episode = ep, showId = show.id, season = season)
            Spacer(Modifier.height(14.dp))
        }
    }
}

// ── EPISODE CARD ─────────────────────────────────────────────────────
@OptIn(ExperimentalTvMaterial3Api::class)
@Composable
private fun EpisodeCard(episode: Episode, showId: String, season: Int) {
    var focused by remember { mutableStateOf(false) }

    Surface(
        onClick  = { /* play episode */ },
        modifier = Modifier
            .fillMaxWidth()
            .onFocusChanged { focused = it.isFocused },
        shape    = ClickableSurfaceDefaults.shape(RoundedCornerShape(14.dp)),
        colors   = ClickableSurfaceDefaults.colors(
            containerColor        = CardDark,
            focusedContainerColor = CardHovered,
        ),
        border   = ClickableSurfaceDefaults.border(
            border = Border(
                border = androidx.compose.foundation.BorderStroke(
                    1.dp,
                    if (focused) BorderBright else BorderSubtle
                )
            )
        )
    ) {
        Row(
            modifier              = Modifier.padding(18.dp),
            horizontalArrangement = Arrangement.spacedBy(18.dp),
            verticalAlignment     = Alignment.CenterVertically
        ) {
            // Square episode thumbnail
            Box(
                modifier = Modifier
                    .size(162.dp)
                    .clip(RoundedCornerShape(10.dp))
            ) {
                AsyncImage(
                    model              = episode.thumbnailUrl(showId, season),
                    contentDescription = episode.title,
                    contentScale       = ContentScale.Crop,
                    modifier           = Modifier.fillMaxSize()
                )

                // Play overlay on focus
                if (focused) {
                    Box(
                        modifier         = Modifier
                            .fillMaxSize()
                            .background(Color(0x72000000)),
                        contentAlignment = Alignment.Center
                    ) {
                        Box(
                            modifier         = Modifier
                                .size(48.dp)
                                .clip(androidx.compose.foundation.shape.CircleShape)
                                .background(Color(0xE5FFFFFF)),
                            contentAlignment = Alignment.Center
                        ) {
                            Text("▶", fontSize = 18.sp, color = Color(0xFF111111))
                        }
                    }
                }

                // EP badge
                Box(
                    modifier = Modifier
                        .align(Alignment.BottomStart)
                        .padding(8.dp)
                        .clip(RoundedCornerShape(6.dp))
                        .background(Color(0xB8000000))
                        .padding(horizontal = 8.dp, vertical = 3.dp)
                ) {
                    Text(
                        text       = "EP ${episode.number}",
                        fontSize   = 11.sp,
                        fontWeight = FontWeight.Bold,
                        color      = Color(0xD9FFFFFF)
                    )
                }
            }

            // Text block
            Column(
                modifier            = Modifier.weight(1f),
                verticalArrangement = Arrangement.Center
            ) {
                Text(
                    text          = "EPISODE ${episode.number}",
                    fontSize      = 11.sp,
                    fontWeight    = FontWeight.Bold,
                    letterSpacing = 1.5.sp,
                    color         = TextMuted
                )
                Spacer(Modifier.height(7.dp))
                Text(
                    text       = episode.title,
                    fontSize   = 16.sp,
                    fontWeight = FontWeight.Bold,
                    color      = TextPrimary,
                    lineHeight = 22.sp
                )
                Spacer(Modifier.height(9.dp))
                Text(
                    text      = episode.description,
                    fontSize  = 13.sp,
                    color     = TextSecondary,
                    lineHeight = 20.sp,
                    maxLines  = 3,
                    overflow  = TextOverflow.Ellipsis
                )
                Spacer(Modifier.height(12.dp))
                Row(
                    horizontalArrangement = Arrangement.spacedBy(12.dp),
                    verticalAlignment     = Alignment.CenterVertically
                ) {
                    Text("⏱ ${episode.duration}", fontSize = 12.sp, color = TextMuted)
                    Box(
                        modifier = Modifier
                            .clip(RoundedCornerShape(20.dp))
                            .background(MatchGreen.copy(alpha = 0.10f))
                            .border(1.dp, MatchGreen.copy(alpha = 0.20f), RoundedCornerShape(20.dp))
                            .padding(horizontal = 10.dp, vertical = 3.dp)
                    ) {
                        Text("HD", fontSize = 11.sp, fontWeight = FontWeight.SemiBold, color = MatchGreen)
                    }
                }
            }
        }
    }
}
