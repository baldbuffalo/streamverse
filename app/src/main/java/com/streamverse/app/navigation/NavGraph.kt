// app/src/main/java/com/streamverse/app/navigation/NavGraph.kt
// Add these imports:
import androidx.compose.ui.platform.LocalContext
import com.streamverse.app.auth.GoogleAuth

// Inside NavGraph(), right after `val navController = ...`:
val context = LocalContext.current

// And update the HOME composable's onLogout:
onLogout = {
    GoogleAuth.getClient(context).signOut()
    currentUser = null
    navController.navigate(Routes.LOGIN) {
        popUpTo(Routes.HOME) { inclusive = true }
    }
}
