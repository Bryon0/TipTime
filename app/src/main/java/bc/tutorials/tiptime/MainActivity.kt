package bc.tutorials.tiptime

import android.os.Bundle
import android.widget.ToggleButton
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeDrawingPadding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.TextUnitType
import androidx.compose.ui.unit.dp

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.material3.Icon
import androidx.compose.material3.Switch
import androidx.compose.material3.SwitchDefaults
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import bc.tutorials.tiptime.ui.theme.TipTimeTheme
import java.text.NumberFormat
import kotlin.math.ceil
import kotlin.math.round
import kotlin.math.roundToInt

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            TipTimeTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                ) {
                    TipTimeLayout()
                }
            }
        }
    }
}

private fun calculateTip(amount: Double, tipPercent: Double = 15.0): String {
    val tip = tipPercent / 100 * amount
    return NumberFormat.getCurrencyInstance().format(tip)
}

private fun calculateTipValue(amount: Double, tipPercent: Double = 15.0): String {
    val tip = tipPercent / 100 * amount

    return NumberFormat.getCurrencyInstance().format(ceil(tip))
}

@Composable
fun TipTimeLayout() {
    var percentInput by remember { mutableStateOf("") }
    var amountInput by remember { mutableStateOf("") }
    var roundUp by remember { mutableStateOf(false) }
    val amount = amountInput.toDoubleOrNull() ?: 0.0
    var tipPercent = percentInput.toDoubleOrNull() ?: 0.0
    var tip = calculateTip(amount, tipPercent)

    if(roundUp) {
       tip = calculateTipValue(amount, tipPercent).toString()
    }

    Column(
        modifier = Modifier
            .statusBarsPadding()
            .padding(horizontal = 40.dp)
            .verticalScroll(rememberScrollState())
            .safeDrawingPadding(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(
            text = stringResource(R.string.calculate_tip),
            modifier = Modifier
                .padding(bottom = 16.dp, top = 40.dp)
                .align(alignment = Alignment.Start)
        )
        EditBillAmountNumberField(value = amountInput,
            onValueChange = { amountInput = it },
            modifier = Modifier
                .padding(bottom = 32.dp)
                .fillMaxWidth())
        EditCustomTipAmountNumberField(value = percentInput,
            onValueChange = { percentInput = it },
            modifier = Modifier
                .padding(bottom = 32.dp)
                .fillMaxWidth())
        Row(modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically,
            ) {
            Text(stringResource(R.string.round_up_tip))
            Switch( modifier = Modifier
                .fillMaxWidth()
                .wrapContentWidth(Alignment.End),
                checked = roundUp,
                colors = SwitchDefaults.colors(
                    checkedThumbColor = Color.Green,
                    uncheckedThumbColor = Color.DarkGray,
                ),
                onCheckedChange = {
                    roundUp = it
                }

            )
        }

        Text(
            text = stringResource(R.string.tip_amount, tip),
            style = MaterialTheme.typography.displaySmall,
            fontSize = TextUnit(type = TextUnitType.Sp, value = 16F)
        )
        Spacer(modifier = Modifier.height(150.dp))
    }
}

@Composable
fun EditBillAmountNumberField(
    value: String,
    onValueChange: (String) -> Unit,
    modifier: Modifier = Modifier) {
    
    TextField(
        value = value,
        leadingIcon = { Icon(painter = painterResource(id = R.drawable.money), null) },
        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
        singleLine = true,
        label = { Text(stringResource(R.string.bill_amount))},
        onValueChange = onValueChange,
        modifier = modifier
    )
}

@Composable
fun EditCustomTipAmountNumberField(
    value: String,
    onValueChange: (String) -> Unit,
    modifier: Modifier = Modifier) {

    TextField(
        value = value,
        leadingIcon = { Icon(painter = painterResource(id = R.drawable.percent), null) },
        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
        singleLine = true,
        label = { Text(stringResource(R.string.tip_percentage))},

        onValueChange = onValueChange,
        modifier = modifier
    )
}


@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    TipTimeTheme {
        TipTimeLayout()
    }
}