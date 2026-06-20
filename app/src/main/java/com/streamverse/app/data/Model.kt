package com.streamverse.app.data

import androidx.compose.ui.graphics.Color

data class Episode(
    val number: Int,
    val title: String,
    val description: String,
    val duration: String,
) {
    fun thumbnailUrl(showId: String, season: Int) =
        "https://picsum.photos/seed/$showId-$season-$number/400/400"
}

data class Show(
    val id: String,
    val title: String,
    val category: String,
    val badge: String,
    val color: Color,
    val matchPercent: Int,
    val rating: String,
    val year: Int,
    val seasonCount: Int,
    val description: String,
    val tags: List<String>,
    val seasons: Map<Int, List<Episode>>,
) {
    val posterUrl   get() = "https://picsum.photos/seed/$id/310/464"
    val bannerUrl   get() = "https://picsum.photos/seed/$id-banner/1400/400"
    val heroUrl     get() = "https://picsum.photos/seed/$id-hero/1400/550"

    fun getEpisodes(season: Int): List<Episode> =
        seasons[season] ?: (1..8).map { i ->
            Episode(i, "Episode $i",
                "Season $season, Episode $i — the story continues with new twists.",
                "${20 + (id[0].code + i * 3) % 18} min")
        }
}

data class User(val name: String, val email: String, val uid: String = "")
