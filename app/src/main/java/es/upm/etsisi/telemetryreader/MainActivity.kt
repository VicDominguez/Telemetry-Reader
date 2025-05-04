package es.upm.etsisi.telemetryreader

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import es.upm.etsisi.telemetryreader.ui.theme.TelemetryReaderTheme
import es.upm.etsisi.telemetryreader.ui.navigation.AppNavigation

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            TelemetryReaderTheme {
                AppNavigation()
            }
        }
    }
}
