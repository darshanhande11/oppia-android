package org.oppia.app.customview.interaction

import android.content.Context
import android.util.AttributeSet
import android.widget.EditText
import org.oppia.app.model.InteractionObject
import org.oppia.app.model.NumberUnit
import org.oppia.app.model.NumberWithUnits
import org.oppia.app.parser.StringToFractionParser
import java.util.regex.Pattern

/** The custom EditText class for number input with units interaction view. */
class NumberWithUnitsInputInteractionView @JvmOverloads constructor(
  context: Context,
  attrs: AttributeSet? = null,
  defStyle: Int = android.R.attr.editTextStyle
) : EditText(context, attrs, defStyle), InteractionAnswerRetriever {

  override fun getPendingAnswer(): InteractionObject {
    var units = ""
    lateinit var value: String
    val rawInput = text.toString()
    val interactionObjectBuilder =
      InteractionObject.newBuilder().setNumberWithUnits(NumberWithUnits.getDefaultInstance())
    if (text.isNullOrEmpty()) {
      return interactionObjectBuilder.build()
    }
    if (Pattern.compile("[^A-Za-z(₹$€£¥]").matcher(rawInput).find()) {
      val m = Pattern.compile("[a-zA-Z(₹\$€£¥/^*)-?\\d]+|\\d+").matcher(rawInput)
      while (m.find()) {
        if (Pattern.compile("[a-zA-Z(₹\$€£¥]").matcher(m.group()).find()) {
          units = m.group()
        }
      }
      value = rawInput.replace(units, "")
    } else {
      value = rawInput
      units = ""
    }
    if (!value.contains(".")) {
      interactionObjectBuilder.setNumberWithUnits(
        NumberWithUnits.newBuilder().setFraction(StringToFractionParser().getFractionFromString(value)).addUnit(
          NumberUnit.newBuilder().setUnit(units)
        )
      )
    } else interactionObjectBuilder.setNumberWithUnits(
      NumberWithUnits.newBuilder().setReal(value.toFloat()).addUnit(
        NumberUnit.newBuilder().setUnit(units)
      )
    )
    return interactionObjectBuilder.build()
  }
}

