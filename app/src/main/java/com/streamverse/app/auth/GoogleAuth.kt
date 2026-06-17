// app/src/main/java/com/streamverse/app/auth/GoogleAuth.kt
package com.streamverse.app.auth

import android.content.Context
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions

/**
 * Single source of truth for the GoogleSignInClient, used both to launch
 * the system account picker on login and to fully sign out on logout.
 */
object GoogleAuth {
    fun getClient(context: Context): GoogleSignInClient {
        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestEmail()
            .requestProfile()
            // If you later need a verifiable token for a backend, register a
            // "Web application" OAuth client too and uncomment this with that
            // client's ID:
            // .requestIdToken(context.getString(R.string.default_web_client_id))
            .build()
        return GoogleSignIn.getClient(context, gso)
    }
}
