// app/src/main/java/com/streamverse/app/auth/GoogleAuth.kt
package com.streamverse.app.auth

import android.content.Context
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.streamverse.app.R

/**
 * Single source of truth for the GoogleSignInClient, used both to launch
 * the system account picker on login and to fully sign out on logout.
 *
 * Requests an ID token so the result can be exchanged for a Firebase Auth
 * credential -- that's what gives each signed-in user a stable UID that
 * "My List" and watch history are stored under, so the data follows the
 * Google account across devices instead of staying local to one install.
 */
object GoogleAuth {
    fun getClient(context: Context): GoogleSignInClient {
        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestEmail()
            .requestProfile()
            .requestIdToken(context.getString(R.string.default_web_client_id))
            .build()
        return GoogleSignIn.getClient(context, gso)
    }
}
