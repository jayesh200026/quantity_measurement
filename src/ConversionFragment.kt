package com.example.quantitymeasurement

import android.os.Bundle
import android.text.Editable
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import android.text.TextWatcher
import java.text.DecimalFormat
import kotlin.math.exp


class ConversionFragment : Fragment(),AdapterView.OnItemSelectedListener {

    val units= arrayListOf<String?>("Temperature","Distance","Mass","Volume")
    val temperatureValues= arrayListOf<String?>("Celsius","Fahrenheit","Kelvin")
    val volumeValues= arrayListOf<String?>("Liter","Gallon","Milli liter")
    val massValues= arrayListOf<String?>("KG","Grams","Pound")
    val distanceValues= arrayListOf<String?>("Meter","killo meter","centi meter")

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment

        val view=inflater.inflate(R.layout.fragment_conversion, container, false)
        val spin=view.findViewById<Spinner>(R.id.quantities)
        val secondSpin=view.findViewById<Spinner>(R.id.inputType)
        val thirdSpin=view.findViewById<Spinner>(R.id.outputType)
        spin.onItemSelectedListener = this
        secondSpin.onItemSelectedListener=this
        thirdSpin.onItemSelectedListener=this

        val array_adapter = ArrayAdapter<String>(view.context, android.R.layout.simple_spinner_item, units)

        array_adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        spin.adapter=array_adapter
        val input=view.findViewById<EditText>(R.id.inputValue)
        input.afterTextChanged {
            if(input.text!=null)
                convertQt(view, input.text, spin)
        }
        return view
    }

    private fun convertQt(view: View?, text: Editable, spin: Spinner?) {
        if(text.isEmpty()){
            noNumberToPrint()
            return
        }
        if(text.toString()!="") {
            when (spin?.selectedItemPosition) {
                0 -> convertTemp(view, text.toString())
                1 -> convertDistance(view, text.toString())
                2 -> convertMass(view,text.toString())
                3 -> convertVolume(view, text.toString())
            }
        }


    }

    private fun convertMass(view: View?, weight: String) {
        var res=0.0
        if(weight!="") {
            var tempspin: Int =
                view?.findViewById<Spinner>(R.id.inputType)?.selectedItemPosition as Int
            val output = view?.findViewById<TextView>(R.id.textView)
            when (tempspin) {
                0 -> {
                    res=convertFromKG(view,weight)
                }
                1 -> {
                    res=convertFromGrams(view,weight)
                }
                2 -> {
                    res=convertFromPound(view,weight)
                }
            }
            printConvertedResult(res, output)
        }

    }

    private fun convertFromPound(view: View, weight: String): Double {
        var res=0.0
        when (view.findViewById<Spinner>(R.id.outputType).selectedItemPosition) {
            2 -> {
                res = weight.toDouble()
            }
            0 -> {
                res = weight.toDouble() / 2.205
            }
            1 -> {
                res = weight.toDouble() * 454
            }

        }
        return res
    }

    private fun convertFromGrams(view: View, weight: String): Double {
        var res=0.0
        when (view.findViewById<Spinner>(R.id.outputType).selectedItemPosition) {
            1 -> {
                res = weight.toDouble()
            }
            0 -> {
                res = weight.toDouble() / 1000
            }
            2 -> {
                res = weight.toDouble() / 454
            }

        }
        return res
    }

    private fun convertFromKG(view: View, weight: String): Double {
        var res=0.0
        when (view.findViewById<Spinner>(R.id.outputType).selectedItemPosition) {
            0 -> {
                res = weight.toDouble()
            }
            1 -> {
                res = weight.toDouble() * 1000
            }
            2 -> {
                res = weight.toDouble() * 2.205
            }
        }
    return res
    }

    private fun convertVolume(view: View?, volume: String) {
        var res=0.0
        if(volume!="") {
            var tempspin: Int =
                view?.findViewById<Spinner>(R.id.inputType)?.selectedItemPosition as Int
            val output = view?.findViewById<TextView>(R.id.textView)
            when (tempspin) {
                0 -> {
                    res=convertFromLiter(view,volume)
                }
                1 -> {
                    res=convertFromGallon(view,volume)
                }
                2 -> {
                    res=convertFromML(view,volume)
                }
            }
            printConvertedResult(res,output)
        }
    }

    private fun convertFromML(view: View, volume: String): Double {
        var res=0.0
        when (view.findViewById<Spinner>(R.id.outputType).selectedItemPosition) {
            2 -> {
                res=volume.toDouble()
            }
            0 -> {
                res = volume.toDouble() / 1000
            }
            1 -> {
                res = volume.toDouble() / 3785
            }

        }
        return res
    }

    private fun convertFromGallon(view: View, volume: String): Double {
        var res=0.0
        when (view.findViewById<Spinner>(R.id.outputType).selectedItemPosition) {
            1 -> {
                res=volume.toDouble()
            }
            0 -> {
                res = volume.toDouble() * 3.785
            }
            2 -> {
                res = volume.toDouble() * 3785
            }

        }
        return res
    }

    private fun convertFromLiter(view: View, volume: String): Double {
        var res=0.0
        when (view.findViewById<Spinner>(R.id.outputType).selectedItemPosition) {
            0 -> {
                res=volume.toDouble()
            }
            1 -> {
                res = volume.toDouble() / 3.785
            }
            2 -> {
                res = volume.toDouble() * 1000
            }
        }
        return res
    }

    private fun noNumberToPrint() {
        val output= view?.findViewById<TextView>(R.id.textView)
        output?.text=""
    }

    private fun convertDistance(view: View?, lenght: String) {
        var res=0.0
        if(lenght!="") {
            var tempspin: Int =
                view?.findViewById<Spinner>(R.id.inputType)?.selectedItemPosition as Int
            val output = view?.findViewById<TextView>(R.id.textView)
            when (tempspin) {
                0 -> {
                    res=convertFromMeter(view,lenght)
                }
                1 -> {
                    res=convertFromKM(view,lenght)
                }
                2 -> {
                    res=convertFromCM(view,lenght)
                }
            }
            printConvertedResult(res,output)
        }
    }

    private fun convertFromCM(view: View, lenght: String): Double {
        var res=0.0
        when (view.findViewById<Spinner>(R.id.outputType).selectedItemPosition) {
            2 -> {
                res=lenght.toDouble()
            }
            0 -> {
                res = lenght.toDouble() / 100
            }
            1 -> {
                res = lenght.toDouble() / 100000
            }
        }
        return res

    }

    private fun convertFromKM(view: View, lenght: String): Double {
        var res=0.0
        when (view.findViewById<Spinner>(R.id.outputType).selectedItemPosition) {
            1 -> {
                res=lenght.toDouble()
            }
            0 -> {
                res = lenght.toDouble() * 1000
            }
            2 -> {
                res = lenght.toDouble() * 100000
            }
        }
        return res
    }

    private fun convertFromMeter(view: View, lenght: String): Double {
        var res=0.0
        when (view?.findViewById<Spinner>(R.id.outputType).selectedItemPosition) {
            0 -> {
                res=lenght.toDouble()
            }
            1 -> {
                res = lenght.toDouble() / 1000
            }
            2 -> {
                res = lenght.toDouble() * 100
            }
        }
return res
    }

    private fun convertTemp(view: View?, temp: String) {
        var res=0.0
        if(temp!="") {
            var tempspin: String =
                view?.findViewById<Spinner>(R.id.inputType)?.selectedItem as String
            val output = view?.findViewById<TextView>(R.id.textView)
            when (tempspin) {
                "Celsius" -> {
                    res=convertFromCelsius(view,temp)
                }
                "Fahrenheit" -> {
                    res=convertFromFahrenheit(view,temp)
                }
                "Kelvin" -> {
                    res=convertFromKelvin(view,temp)
                }
            }
            printConvertedResult(res,output)
        }

    }

    private fun convertFromKelvin(view: View, temp: String): Double {
        var res=0.0
        when (view.findViewById<Spinner>(R.id.outputType).selectedItem as String) {
            "Kelvin" -> {
                    res=temp.toDouble()
            }
            "Celsius" -> {
                res = temp.toDouble() - 273.15
            }
            "Fahrenheit" -> {
                res = (temp.toDouble() - 273.15) * 9 / 5 + 32
            }
        }
        return res
    }

    private fun convertFromFahrenheit(view: View, temp: String): Double {
        var res=0.0
        when (view.findViewById<Spinner>(R.id.outputType).selectedItem as String) {
            "Fahrenheit" -> {
                    res=temp.toDouble()
            }
            "Celsius" -> {
                res = (temp.toDouble() - 32) * 5 / 9
            }
            "Kelvin" -> {
                res = (temp.toDouble() - 32) * 5 / 9 + 273.15
            }
        }
    return res
    }

    private fun convertFromCelsius(view: View, temp: String): Double {
        var res=0.0
        when (view.findViewById<Spinner>(R.id.outputType).selectedItem as String) {
            "Celsius" -> {
                    res=temp.toDouble()
            }
            "Fahrenheit" -> {
                res = temp.toDouble() * 1.8 + 32
            }
            "Kelvin" -> {
                res = temp.toDouble() + 273.15
            }
        }
        return res
    }


    override fun onItemSelected(p0: AdapterView<*>?, p1: View?, pos: Int, p3: Long) {
        var tempSpin = view?.findViewById<Spinner>(R.id.quantities)
        var index = tempSpin?.selectedItemPosition
        var inputView=view?.findViewById<EditText>(R.id.inputValue)
        val input=inputView?.text.toString()
        when (p0?.id) {
            R.id.quantities -> {
                loadSpinners(pos,inputView)
            }

            R.id.outputType -> {
                convertFromSpinner(index,inputView)
            }
            R.id.inputType->{
                convertFromSpinner(index,inputView)
            }
        }
    }

    private fun convertFromSpinner(index: Int?, inputView: EditText?) {
        if(index==0){
                    convertTemp(view,inputView?.text.toString())

                }
                else if(index==1){
                    convertDistance(view,inputView?.text.toString())
                }
                else if(index==2){
                    convertMass(view,inputView?.text.toString())
                }
                else if(index==3){
                    convertVolume(view,inputView?.text.toString())
                }

    }

    private fun loadSpinners(pos: Int, inputView: EditText?) {
        inputView?.setText("")
        val output = view?.findViewById<TextView>(R.id.textView)
        output?.text=""
                when (pos) {
                    0 -> {
                        loadTempSpin(R.id.inputType, temperatureValues)
                        loadTempSpin(R.id.outputType, temperatureValues)
                    }
                    1 -> {
                        loadTempSpin(R.id.inputType, distanceValues)
                        loadTempSpin(R.id.outputType, distanceValues)
                    }
                    2 -> {
                        loadTempSpin(R.id.inputType, massValues)
                        loadTempSpin(R.id.outputType, massValues)
                    }
                    3 -> {
                        loadTempSpin(R.id.inputType, volumeValues)
                        loadTempSpin(R.id.outputType, volumeValues)
                    }
                }
    }

    private fun loadTempSpin(elementeId:Int,listOfValues:ArrayList<String?>) {
        var tempSpin=view?.findViewById<Spinner>(elementeId)
        val temp_adapter = ArrayAdapter<String>(requireActivity(), android.R.layout.simple_spinner_item, listOfValues)

        temp_adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        tempSpin?.adapter=temp_adapter

    }

    override fun onNothingSelected(p0: AdapterView<*>?) {
        TODO("Not yet implemented")
    }
}

    private fun printConvertedResult(res: Double, output: TextView) {
           if(res.toInt() >9999999){
                val numberFormat= DecimalFormat("0.###E0")
                val exponentialResult=numberFormat.format(res)
                output.text= exponentialResult
            }
            else if((res>0 && res<1)){
                if(res.toString().length <7){
                    output.text=res.toString()
                }
                else{
                    val numberFormat= DecimalFormat("0.###E0")
                    val exponentialResult=numberFormat.format(res)
                    output.text= exponentialResult
                }
            }
            else{
                var formated= String.format("%.2f",res)
                output.text= formated
            }
        }


    fun EditText.afterTextChanged(afterTextChanged: (String) -> Unit) {
    this.addTextChangedListener(object : TextWatcher {
        override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
        }

        override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
        }

        override fun afterTextChanged(editable: Editable?) {
            afterTextChanged.invoke(editable.toString())
        }
    })
}
