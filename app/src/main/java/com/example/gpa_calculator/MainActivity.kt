package com.example.gpa_calculator

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import com.example.gpa_calculator.ui.theme.GPA_CALCULATORTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            GPA_CALCULATORTheme {
                GPAContent()
            }
        }
    }
}

@Composable
fun GPAContent() {
    // Variables for storing course grades
    var course1Grade by remember { mutableStateOf("") }
    var course2Grade by remember { mutableStateOf("") }
    var course3Grade by remember { mutableStateOf("") }
    var course4Grade by remember { mutableStateOf("") }
    var course5Grade by remember { mutableStateOf("") }

    // State for GPA and calculation flag
    var gpa by remember { mutableStateOf(0.0) }
    var isCalculated by remember { mutableStateOf(false) }

    // Input validation states
    var error1 by remember { mutableStateOf(false) }
    var error2 by remember { mutableStateOf(false) }
    var error3 by remember { mutableStateOf(false) }
    var error4 by remember { mutableStateOf(false) }
    var error5 by remember { mutableStateOf(false) }


    val focusManager = LocalFocusManager.current

    // Background color logic based on GPA
    val backgroundColor = when {
        !isCalculated -> Color.White
        gpa < 60 -> Color.Red
        gpa in 60.0..79.0 -> Color.Yellow
        gpa >= 80 -> Color.Green
        else -> Color.White
    }

    // Content layout
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(backgroundColor) // Apply background color based on GPA
            .padding(16.dp),
        horizontalAlignment = Alignment.Start,
        verticalArrangement = Arrangement.Top
    ) {
        Text(
            text = "GPA Calculator",
            style = MaterialTheme.typography.headlineMedium.copy(fontWeight = FontWeight.Bold),
            modifier = Modifier.padding(bottom = 24.dp)
        )

        // Grade input fields for each course
        OutlinedTextField(
            value = course1Grade,
            onValueChange = { course1Grade = it },
            label = { Text("Course 1 Grade") },
            isError = error1,
            keyboardOptions = KeyboardOptions.Default.copy(keyboardType = KeyboardType.Number),
            modifier = Modifier.fillMaxWidth().padding(vertical = 4.dp)
        )

        OutlinedTextField(
            value = course2Grade,
            onValueChange = { course2Grade = it },
            label = { Text("Course 2 Grade") },
            isError = error2,
            keyboardOptions = KeyboardOptions.Default.copy(keyboardType = KeyboardType.Number),
            modifier = Modifier.fillMaxWidth().padding(vertical = 4.dp)
        )

        OutlinedTextField(
            value = course3Grade,
            onValueChange = { course3Grade = it },
            label = { Text("Course 3 Grade") },
            isError = error3,
            keyboardOptions = KeyboardOptions.Default.copy(keyboardType = KeyboardType.Number),
            modifier = Modifier.fillMaxWidth().padding(vertical = 4.dp)
        )

        OutlinedTextField(
            value = course4Grade,
            onValueChange = { course4Grade = it },
            label = { Text("Course 4 Grade") },
            isError = error4,
            keyboardOptions = KeyboardOptions.Default.copy(keyboardType = KeyboardType.Number),
            modifier = Modifier.fillMaxWidth().padding(vertical = 4.dp)
        )

        OutlinedTextField(
            value = course5Grade,
            onValueChange = { course5Grade = it },
            label = { Text("Course 5 Grade") },
            isError = error5,
            keyboardOptions = KeyboardOptions.Default.copy(keyboardType = KeyboardType.Number),
            modifier = Modifier.fillMaxWidth().padding(vertical = 4.dp)
        )

        Spacer(modifier = Modifier.height(16.dp))

        // Button to compute or reset the GPA
        Button(
            onClick = {
                if (!isCalculated) {
                    // Clear error states
                    error1 = course1Grade.isBlank() || course1Grade.toDoubleOrNull() == null
                    error2 = course2Grade.isBlank() || course2Grade.toDoubleOrNull() == null
                    error3 = course3Grade.isBlank() || course3Grade.toDoubleOrNull() == null
                    error4 = course4Grade.isBlank() || course4Grade.toDoubleOrNull() == null
                    error5 = course5Grade.isBlank() || course5Grade.toDoubleOrNull() == null

                    // If no errors, calculate GPA
                    if (!error1 && !error2 && !error3 && !error4 && !error5) {
                        val g1 = course1Grade.toDouble()
                        val g2 = course2Grade.toDouble()
                        val g3 = course3Grade.toDouble()
                        val g4 = course4Grade.toDouble()
                        val g5 = course5Grade.toDouble()

                        gpa = (g1 + g2 + g3 + g4 + g5) / 5.0
                        isCalculated = true
                        focusManager.clearFocus() // Hide keyboard
                    }
                } else {
                    // Reset form
                    course1Grade = ""
                    course2Grade = ""
                    course3Grade = ""
                    course4Grade = ""
                    course5Grade = ""
                    gpa = 0.0
                    isCalculated = false
                }
            },
            modifier = Modifier
                .fillMaxWidth()
                .height(50.dp)
        ) {
            Text(text = if (isCalculated) "Clear Form" else "Compute GPA")
        }

        // Display GPA result after calculation
        Spacer(modifier = Modifier.height(16.dp))
        if (isCalculated) {
            Text(
                text = "Your GPA is: ${"%.2f".format(gpa)}",
                style = MaterialTheme.typography.headlineSmall.copy(fontWeight = FontWeight.Bold)
            )
        }
    }
}
