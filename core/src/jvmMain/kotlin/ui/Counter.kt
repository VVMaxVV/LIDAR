package ui

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.LocalTextStyle
import androidx.compose.material.TextField
import androidx.compose.material.TextFieldDefaults
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Remove
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import util.compareTo
import util.minus
import util.plus
import java.util.Locale

private const val BORDER_WIDTH = 1
private const val ROUNDED_CORNER = 16

@Composable
fun Counter(
    startValue: Number = 0,
    modifier: Modifier = Modifier,
    minValue: Number = 0,
    maxValue: Number = Int.MAX_VALUE,
    step: Number = 1,
    textStyle: TextStyle = LocalTextStyle.current,
    numberFormat: NumberFormat = NumberFormat.IntegerFormat,
    onValueChanged: (Number) -> Unit
) {
    var count by rememberSaveable { mutableStateOf(numberFormat.format(startValue.toDouble())) }
    var isError by rememberSaveable { mutableStateOf(false) }

    Row(
        modifier = modifier.border(BorderStroke(BORDER_WIDTH.dp, Color.Black), RoundedCornerShape(ROUNDED_CORNER.dp)).fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically
    ) {
        IconButton(
            onClick = {
                count = numberFormat.format(
                    (count.toDouble() - step).coerceAtLeast(minValue.toFloat()).also(onValueChanged)
                )
            },
            modifier = Modifier.weight(0.25f)
        ) {
            Icon(Icons.Filled.Remove, contentDescription = "Decrement")
        }

        TextField(
            value = count,
            modifier = Modifier
                .widthIn(min = 32.dp).border(1.dp, Color.Black).padding(vertical = 4.dp).weight(0.25f).wrapContentHeight(),
            keyboardOptions = KeyboardOptions.Default.copy(keyboardType = KeyboardType.Number),
            textStyle = textStyle,
            singleLine = true,
            colors = TextFieldDefaults.textFieldColors(
                backgroundColor = Color.Transparent,
                cursorColor = Color.Black,
                focusedIndicatorColor = Color.Transparent,
                unfocusedIndicatorColor = Color.Transparent,
                disabledIndicatorColor = Color.Transparent,
                errorIndicatorColor = Color.Red
            ),
            isError = isError,
            onValueChange = { newValue ->
                try {
                    count = newValue
                    isError = false
                    when {
                        newValue.toDouble() < minValue -> count = numberFormat.format(minValue.toDouble())
                        newValue.toDouble() > maxValue -> count = numberFormat.format(maxValue.toDouble())
                    }
                    onValueChanged(numberFormat.toNumber(count))
                } catch (e: NumberFormatException) {
                    isError = true
                }
            }
        )

        IconButton(
            onClick = {
                count = numberFormat.format(
                    (count.toDouble() + step).coerceAtMost(maxValue.toFloat()).also(onValueChanged)
                )
            },
            modifier = Modifier.weight(0.25f)
        ) {
            Icon(Icons.Filled.Add, contentDescription = "Increment")
        }
    }
}

sealed class NumberFormat {
    abstract fun format(value: Number): String
    abstract fun toNumber(value: String): Number

    object IntegerFormat : NumberFormat() {
        override fun format(value: Number): String = value.toInt().toString()
        override fun toNumber(value: String): Number = value.toInt()
    }

    data class Decimal(val fractionDigits: Int) : NumberFormat() {
        override fun format(value: Number): String = "%.${fractionDigits}f".format(Locale.US, value)
        override fun toNumber(value: String): Number = value.toDouble()
    }
}
