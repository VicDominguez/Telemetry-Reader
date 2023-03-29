package es.upm.etsisi.lectortelemetrias.v2

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import es.upm.etsisi.lectortelemetrias.v2.ui.theme.LectorTelemetriasTheme
import es.upm.etsisi.lectortelemetrias.v2.ui.navigation.AppNavigation

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            LectorTelemetriasTheme {
                AppNavigation()
            }
        }
    }
}
