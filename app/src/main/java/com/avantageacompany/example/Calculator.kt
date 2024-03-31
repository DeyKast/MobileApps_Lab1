import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.unit.times
import com.avantageacompany.example.CalculatorActions
import com.avantageacompany.example.CalculatorOperation
import com.avantageacompany.example.CalculatorState
import com.avantageacompany.example.ui.theme.dark_shade_of_blue
import com.avantageacompany.example.ui.theme.shade_of_orange


@Composable
fun Calculator(
    state: CalculatorState,
    modifier: Modifier = Modifier,
    buttonSpacing: Dp = 8.dp,
    onAction: (CalculatorActions) -> Unit
) {
    val screenWidth = with(LocalDensity.current) { LocalConfiguration.current.screenWidthDp.dp }
    val buttonWidth = (screenWidth - 5 * buttonSpacing) / 4

    Column(
        modifier = modifier.fillMaxSize()
    ) {
        Text(
            text = "${state.number1}${state.operation?.symbol ?: ""}${state.number2}",
            textAlign = TextAlign.End,
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 16.dp),
            fontWeight = FontWeight.Light,
            fontSize = 80.sp,
            color = Color.White,
            maxLines = 2
        )

        Spacer(modifier = Modifier.weight(1f))

        Column(
            modifier = Modifier.fillMaxWidth()
        ) {
            ButtonRow(
                buttons = listOf("AC", "Del", "/"),
                backgroundColors = listOf(shade_of_orange, shade_of_orange, dark_shade_of_blue),
                onAction = onAction,
                buttonWidth = buttonWidth

            )
            ButtonRow(
                buttons = listOf( "7", "8", "9", "x"),
                backgroundColors = listOf(Color.DarkGray, Color.DarkGray, Color.DarkGray, dark_shade_of_blue),
                onAction = onAction,
                buttonWidth = buttonWidth
            )
            ButtonRow(
                buttons = listOf( "4", "5", "6", "-"),
                backgroundColors = listOf(Color.DarkGray, Color.DarkGray, Color.DarkGray, dark_shade_of_blue),
                onAction = onAction,
                buttonWidth = buttonWidth
            )
            ButtonRow(
                buttons = listOf("1" ,"2", "3", "+" ),
                backgroundColors = listOf(Color.DarkGray, Color.DarkGray, Color.DarkGray, dark_shade_of_blue),
                onAction = onAction,
                buttonWidth = buttonWidth
            )
            ButtonRow(
                buttons = listOf("0", ".", "="),
                backgroundColors = listOf(Color.DarkGray, Color.DarkGray, dark_shade_of_blue),
                onAction = onAction,
                buttonWidth = buttonWidth.times(2f)
            )
        }
    }
}

@Composable
fun ButtonRow(
    buttons: List<String>,
    backgroundColors: List<Color>,
    onAction: (CalculatorActions) -> Unit,
    buttonWidth: Dp
) {
    val buttonSpacing = 8.dp;
    Row(
        modifier = Modifier.fillMaxWidth().padding(vertical = 4.dp),
        horizontalArrangement = Arrangement.spacedBy(buttonSpacing)
    ) {
        buttons.forEachIndexed { index, symbol ->
            CalculatorButton(
                symbol = symbol,
                backgroundColor = backgroundColors[index],
                onClick = {
                    when (symbol) {
                        "AC" -> onAction(CalculatorActions.Clear)
                        "Del" -> onAction(CalculatorActions.Delete)
                        "/" -> onAction(CalculatorActions.Operation(CalculatorOperation.Divide))
                        "x" -> onAction(CalculatorActions.Operation(CalculatorOperation.Multiply))
                        "-" -> onAction(CalculatorActions.Operation(CalculatorOperation.Subtract))
                        "+" -> onAction(CalculatorActions.Operation(CalculatorOperation.Add))
                        "=" -> onAction(CalculatorActions.Calculate)
                        else -> {
                            if (symbol.matches("[0-9]".toRegex())) {
                                onAction(CalculatorActions.Number(symbol.toInt()))
                            } else if (symbol == ".") {
                                onAction(CalculatorActions.Decimal)
                            }
                        }
                    }
                },
                modifier = Modifier
                    .width(if (symbol == "0") buttonWidth.times(2f) else buttonWidth)
                    .weight(if (symbol == "AC" || symbol == "0") 2f else 1f)
            )
        }
    }
}

@Composable
fun CalculatorButton(
    symbol: String,
    backgroundColor: Color,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier
            .background(color = backgroundColor, shape = CircleShape)
            .padding(8.dp)
            .clickable(onClick = onClick),
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = symbol,
            fontSize = 24.sp,
            color = Color.White,
            textAlign = TextAlign.Center
        )
    }
}



