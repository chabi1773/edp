package com.example.app1.components

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.unit.dp
import com.example.app1.TemperatureRecord
import java.text.SimpleDateFormat
import java.util.Locale

@Composable
fun LineChartComponent(records: List<TemperatureRecord>){
    if (records.isEmpty()) return

    val maxTemp = records.maxOf { it.temperature }
    val minTemp = records.minOf { it.temperature }

    val tempRange = maxTemp - minTemp
    val dateFormat = SimpleDateFormat("HH:mm", Locale.getDefault())

    Canvas(modifier = Modifier
        .fillMaxWidth()
        .height(200.dp)) {
        val widthStep = size.width / (records.size - 1).coerceAtLeast(1)
        val height = size.height

        val path = Path()
        records.forEachIndexed { index, record ->
            val x = index * widthStep
            val y = height - ((record.temperature - minTemp) / tempRange) * height
            if (index == 0) path.moveTo(x, y) else path.lineTo(x, y)
        }
        drawPath(
            path = path,
            color = Color.Blue,
            style = androidx.compose.ui.graphics.drawscope.Stroke(width = 4f)
        )
    }
}