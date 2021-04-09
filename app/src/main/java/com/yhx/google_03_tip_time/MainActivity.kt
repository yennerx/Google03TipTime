package com.yhx.google_03_tip_time

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.yhx.google_03_tip_time.databinding.ActivityMainBinding
import java.text.NumberFormat

class MainActivity : AppCompatActivity() {
/**
 * View binding
 * Open the app's build.gradle file ( Gradle Scripts > build.gradle (Module: Tip_Time.app) )
 */
    private lateinit var binding: ActivityMainBinding /**Se agrega código para enlace de vista. (La clave lateinit Es una promesa de que su código inicializará la variable antes de usarla. Si no lo hace, su aplicación se bloqueará.)*/

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)/**Se agrega código para enlace de vista. (Esta línea inicializa el bjeto bindingo que utilizará para acceder a las Views en el activity_main.xmldiseño.)*/
        //setContentView(R.layout.activity_main) // Se reemplaza por el código para enlace de vista.
        setContentView(binding.root)/**Se agrega código para enlace de vista. (Configure la vista de contenido de la actividad. En lugar de pasar el ID de recurso del diseño, R.layout.activity_main, especifica la raíz de la jerarquía de puntos de vista en su aplicación, binding.root.)*/

        binding.calculateButton.setOnClickListener{calculateTip()}
    }

    private fun calculateTip(){
        val stringInTextField = binding.costOfServiceEditText.text.toString() //you can convert an Editable to a String by calling toString() on it.
        val cost = stringInTextField.toDoubleOrNull() //toDouble() needs to be called on a String --- Calling toDouble() on a string that is empty or a string that doesn't represent a valid decimal number doesn't work. Fortunately Kotlin also provides a method called toDoubleOrNull() which handles these problems. It returns a decimal number if it can, or it returns null if there's a problem.
        //The return instruction means exit the method without executing the rest of the instructions. If the method needed to return a value, you would specify it with a return instruction with an expression.
        if (cost == null || cost == 0.0){
            displayTip(0.0)
            return
        }

        val tipPerentage = when(binding.tipOptions.checkedRadioButtonId){
            R.id.option_twenty_percent -> 0.20
            R.id.option_eighteen_percent -> 0.18
            else -> 0.15
        }

        var tip = cost * tipPerentage

        //The term rounding means adjusting a decimal number up or down to the closest integer value, but in this case, you only want to round up, or find the ceiling. You can use the ceil() function to do that. There are several functions with that name, but the one you want is defined in kotlin.math. You could add an import statement, but in this case it's simpler to just tell Android Studio which you mean by using kotlin.math.ceil().
        if (binding.roundUpSwitch.isChecked){
            tip = kotlin.math.ceil(tip)
        }

        displayTip(tip)
    }

    //As you might expect, Kotlin provides methods for formatting different types of numbers. But the tip amount is a little different—it represents a currency value. Different countries use different currencies, and have different rules for formatting decimal numbers. For example, in U.S. dollars, 1234.56 would be formatted as $1,234.56, but in Euros, it would be formatted €1.234,56. Fortunately, the Android framework provides methods for formatting numbers as currency, so you don't need to know all the possibilities. The system automatically formats currency based on the language and other settings that the user has chosen on their phone. Read more about NumberFormat in the Android developer documentation.
    private fun displayTip(tip: Double){
        NumberFormat.getCurrencyInstance()
        val formatedTip = NumberFormat.getCurrencyInstance().format(tip)
        binding.tipResult.text = getString(R.string.tip_mount, formatedTip)
    }
}