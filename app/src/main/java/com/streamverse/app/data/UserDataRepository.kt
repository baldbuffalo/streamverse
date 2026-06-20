package com.streamverse.app.data

import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ListenerRegistration
import com.google.firebase.firestore.Query

/**
 * Firestore-backed per-user data: "My List" and watch history. Everything
 * is keyed by the Firebase Auth UID, which comes from exchanging the
 * Google Sign-In ID token for a Firebase credential (see GoogleAuth.kt and
 * LoginScreen.kt). That UID is stable across devices, so the same data
 * follows a Google account wherever it signs in.
 */
object UserDataRepository {
    private val db get() = FirebaseFirestore.getInstance()

    private fun myListCollection(uid: String) =
        db.collection("users").document(uid).collection("myList")

    private fun historyCollection(uid: String) =
        db.collection("users").document(uid).collection("watchHistory")

    fun addToMyList(uid: String, showId: String) {
        myListCollection(uid).document(showId)
            .set(mapOf("addedAt" to System.currentTimeMillis()))
    }

    fun removeFromMyList(uid: String, showId: String) {
        myListCollection(uid).document(showId).delete()
    }

    /** Live updates of show IDs currently in the user's list. */
    fun observeMyList(uid: String, onChange: (List<String>) -> Unit): ListenerRegistration =
        myListCollection(uid).addSnapshotListener { snapshot, _ ->
            onChange(snapshot?.documents?.map { it.id } ?: emptyList())
        }

    fun recordWatched(uid: String, showId: String, season: Int, episodeNumber: Int) {
        val docId = "${showId}_s${season}_e${episodeNumber}"
        historyCollection(uid).document(docId).set(
            mapOf(
                "showId"        to showId,
                "season"        to season,
                "episodeNumber" to episodeNumber,
                "watchedAt"     to System.currentTimeMillis()
            )
        )
    }

    /** Live updates of the most recently watched shows, most recent first, deduped. */
    fun observeRecentlyWatchedShowIds(
        uid: String,
        limit: Long = 12,
        onChange: (List<String>) -> Unit
    ): ListenerRegistration =
        historyCollection(uid)
            .orderBy("watchedAt", Query.Direction.DESCENDING)
            .limit(limit)
            .addSnapshotListener { snapshot, _ ->
                val ids = snapshot?.documents
                    ?.mapNotNull { it.getString("showId") }
                    ?.distinct()
                    ?: emptyList()
                onChange(ids)
            }
}
