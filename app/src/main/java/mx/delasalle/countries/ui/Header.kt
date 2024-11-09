package mx.delasalle.countries.ui

import androidx.compose.foundation.layout.*
import androidx.compose.material3.Text
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import androidx.compose.ui.graphics.Color
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.unit.dp

@Composable
fun Header(title: String) {
    Column(modifier = Modifier.fillMaxWidth()) {
        // Espaciado antes del encabezado
        Spacer(modifier = Modifier.height(40.dp)) // Ajusta este valor para el espacio necesario

        Text(
            text = title,
            fontSize = 24.sp,
            fontWeight = FontWeight.Bold,
            color = Color(0xFF063971),
            modifier = Modifier.padding(start = 30.dp) // Padding al lado izquierdo
        )
    }
}
