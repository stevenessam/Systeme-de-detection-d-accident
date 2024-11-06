package com.example.smart.presentation

import android.content.Context
import android.content.pm.PackageManager
import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.hardware.SensorManager
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.sp
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import fi.iki.elonen.NanoHTTPD

class MainActivity : ComponentActivity() {

    private lateinit var sensorManager: SensorManager
    private var heartRateSensor: Sensor? = null
    private lateinit var server: HTTPServer
    private var heartRateValue by mutableStateOf("--")

    companion object {
        private const val REQUEST_BODY_SENSORS_PERMISSION = 1001
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Vérifier les permissions pour le capteur de fréquence cardiaque
        checkPermissions()

        // Initialiser le SensorManager et le capteur de fréquence cardiaque
        sensorManager = getSystemService(Context.SENSOR_SERVICE) as SensorManager
        heartRateSensor = sensorManager.getDefaultSensor(Sensor.TYPE_HEART_RATE)

        // Démarrer le serveur HTTP pour exposer la valeur de fréquence cardiaque
        try {
            server = HTTPServer { heartRateValue }
            server.start()
        } catch (e: Exception) {
            e.printStackTrace()
        }

        setContent {
            HeartRateApp()
        }
    }

    private fun checkPermissions() {
        if (ContextCompat.checkSelfPermission(this, android.Manifest.permission.BODY_SENSORS)
            != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this,
                arrayOf(android.Manifest.permission.BODY_SENSORS),
                REQUEST_BODY_SENSORS_PERMISSION)
        }
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == REQUEST_BODY_SENSORS_PERMISSION) {
            if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                // Permission accordée, accès au capteur autorisé
            } else {
                // Permission refusée, gérer l'erreur ici
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        server.stop() // Arrêter le serveur HTTP lorsque l'activité est détruite
    }

    @Composable
    fun HeartRateApp() {
        // État mutable pour stocker la fréquence cardiaque
        var heartRate by remember { mutableStateOf("--") }

        if (heartRateSensor == null) {
            heartRate = "Capteur de fréquence cardiaque non disponible"
        } else {
            // Définir un Listener pour les mises à jour de fréquence cardiaque
            DisposableEffect(Unit) {
                val listener = object : SensorEventListener {
                    override fun onSensorChanged(event: SensorEvent?) {
                        if (event?.sensor?.type == Sensor.TYPE_HEART_RATE) {
                            heartRate = event.values[0].toInt().toString()
                            heartRateValue = heartRate // Mettre à jour heartRateValue pour le serveur
                        }
                    }

                    override fun onAccuracyChanged(sensor: Sensor?, accuracy: Int) {}
                }

                try {
                    sensorManager.registerListener(listener, heartRateSensor, SensorManager.SENSOR_DELAY_NORMAL)
                } catch (e: Exception) {
                    e.printStackTrace()
                }

                onDispose {
                    sensorManager.unregisterListener(listener)
                }
            }
        }

        Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = "Fréquence cardiaque : $heartRate BPM",
                fontSize = 24.sp,
                textAlign = TextAlign.Center
            )
        }
    }

    // Classe interne pour le serveur HTTP
    inner class HTTPServer(private val getHeartRate: () -> String) : NanoHTTPD(1500) {
        override fun serve(session: IHTTPSession): Response {
            return when (session.uri) {
                "/heartrate" -> {
                    val heartRate = getHeartRate()
                    newFixedLengthResponse(Response.Status.OK, "text/plain", heartRate)
                }
                else -> {
                    newFixedLengthResponse(Response.Status.NOT_FOUND, "text/plain", "Resource not found")
                }
            }
        }
    }
}
