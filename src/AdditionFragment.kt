package com.example.quantitymeasurement

import android.icu.number.Notation
import android.icu.number.ScientificNotation
import android.os.Build
import android.os.Bundle
import android.text.Editable
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.annotation.RequiresApi
import java.text.DecimalFormat


class AdditionFragment : Fragment(), AdapterView.OnItemSelectedListener {
    val units= arrayListOf<String?>("Temperature","Distance","Mass","Volume")
    val temperatureValues= arrayListOf<String?>("Celsius","Fahrenheit","Kelvin")
    val volumeValues= arrayListOf<String?>("Liter","Gallon","Milli liter")
    val massValues= arrayListOf<String?>("KG","Grams","Pound")
    val distanceValues= arrayListOf<String?>("Meter","killo meter","centi meter")

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view=inflater.inflate(R.layout.fragment_addition, container, false)
        val addSpinner=view.findViewById<Spinner>(R.id.addMainSpinner)
        val addValue1=view.findViewById<Spinner>(R.id.addSpinner1)
        val addValue2=view.findViewById<Spinner>(R.id.addSpinner2)
        val addSResult=view.findViewById<Spinner>(R.id.addResultSpinner)
        addSpinner.onItemSelectedListener=this
        addValue1.onItemSelectedListener=this
        addValue2.onItemSelectedListener=this
        addSResult.onItemSelectedListener=this
        val array_adapter = ArrayAdapter<String>(view.context, android.R.layout.simple_spinner_item, units)
        array_adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        addSpinner.adapter=array_adapter
        val value1=view.findViewById<EditText>(R.id.addInput1)
        val value2=view.findViewById<EditText>(R.id.addInput2)
        value1.afterTextChanged {
            if(value1.text !=null){
                computeResult(view,value1.text,value2.text,addSpinner)
            }
        }
        value2.afterTextChanged {
            if(value2.text!=null){
                computeResult(view,value1.text,value2.text,addSpinner)
            }
        }
        return view
    }


    private fun computeResult(view: View?, value1: Editable, value2: Editable, addSpinner: Spinner?) {
        val addValue1=view?.findViewById<Spinner>(R.id.addSpinner1)
        val addValue2=view?.findViewById<Spinner>(R.id.addSpinner2)
        var converted1=0.0
        var converted2=0.0

        if(value1.equals("") && value2.equals("")){
            printNoValue()
            return
        }
        if(value1.toString() !=""){
            converted1=when(addSpinner?.selectedItemPosition){
                0->convertTemp(view, value1.toString(),addValue1)
                1->convertDistance(view, value1.toString(),addValue1)
                2->convertMass(view, value1.toString(),addValue1)
                3->convertVolume(view, value1.toString(),addValue1)
                else -> 0.0
            }
        }
        if(value2.toString() !=""){
            converted2=when(addSpinner?.selectedItemPosition){
                0->convertTemp(view, value2.toString(),addValue2)
                1->convertDistance(view, value2.toString(),addValue2)
                2->convertMass(view, value2.toString(),addValue2)
                3->convertVolume(view, value2.toString(),addValue2)
                else -> 0.0
            }
        }
        val output=view?.findViewById<TextView>(R.id.addResult)
        var total=converted1+converted2
        printTotal(total,output)
    }

    private fun convertVolume(view: View?, volume: String, spinner: Spinner?): Double {
        var result=0.0
        if(volume!=""){
            val tempSpin:Int=spinner?.selectedItemPosition as Int
            when(tempSpin){
                0 -> {
                    result=convertFromLiter(view,volume)

                }
                1 -> {
                    result=convertFromGallon(view,volume)

                }
                2 -> {
                    result=convertFromML(view,volume)

                }
            }
        }
return result
    }

    private fun convertFromML(view: View?, volume: String): Double {
        var result=0.0
        if (view != null) {
            when (view.findViewById<Spinner>(R.id.addResultSpinner).selectedItemPosition) {
                2 -> {
                    result=volume.toDouble()
                }
                0 -> {
                   result = volume.toDouble() / 1000

                }
                1 -> {
                   result= volume.toDouble() / 3785

                }

            }
        }
        return result

    }

    private fun convertFromGallon(view: View?, volume: String): Double {
        var result=0.0
        if (view != null) {
            when (view.findViewById<Spinner>(R.id.addResultSpinner).selectedItemPosition) {
                1 -> {
                    result=volume.toDouble()
                }
                0 -> {
                    result = volume.toDouble() * 3.785

                }
                2 -> {
                    result = volume.toDouble() * 3785

                }

            }
        }
        return result

    }

    private fun convertFromLiter(view: View?, volume: String): Double {
        var result=0.0
        if (view != null) {
            when (view.findViewById<Spinner>(R.id.addResultSpinner).selectedItemPosition) {
                0 -> {
                    result=volume.toDouble()
                }
                1 -> {
                    result= volume.toDouble() / 3.785

                }
                2 -> {
                    result= volume.toDouble() * 1000
                }

            }
        }

        return result

    }

    private fun convertMass(view: View?, weight: String, spinner: Spinner?): Double {
        var result=0.0
        if(weight!=""){
            val tempSpin:Int=spinner?.selectedItemPosition as Int
            when(tempSpin){
                0 -> {
                    result=convertFromKG(view,weight)

                }

                1 -> {
                    result=convertFromGrams(view,weight)

                }
                2 -> {
                    result=convertFromPound(view,weight)

                }
            }
        }
        return result
    }

    private fun convertFromPound(view: View?, weight: String): Double {
        var result=0.0
        if (view != null) {
            when (view.findViewById<Spinner>(R.id.addResultSpinner).selectedItemPosition) {
                2 -> {
                    result=weight.toDouble()
                }
                0 -> {
                     result = weight.toDouble() / 2.205

                }
                1 -> {
                     result = weight.toDouble() * 454

                }

            }
        }
        return result

    }

    private fun convertFromGrams(view: View?, weight: String): Double {
        var result=0.0
        if (view != null) {

            when (view.findViewById<Spinner>(R.id.addResultSpinner).selectedItemPosition) {
                1 -> {
                    result=weight.toDouble()
                }
                0 -> {
                    result = weight.toDouble() / 1000

                }
                2 -> {
                    result = weight.toDouble() /454

                }

            }
        }
        return result

    }

    private fun convertFromKG(view: View?, weight: String): Double {
        var result=0.0
        if (view != null) {

            when (view.findViewById<Spinner>(R.id.addResultSpinner).selectedItemPosition) {
                0 -> {
                    result=weight.toDouble()
                }
                1 -> {
                    result= weight.toDouble() * 1000

                }
                2 -> {
                   result= weight.toDouble() * 2.205

                }

            }
        }
        return result

    }

    private fun convertDistance(view: View?, length: String, spinner: Spinner?): Double {
        var result=0.0
        if(length!=""){
            val tempSpin:Int=spinner?.selectedItemPosition as Int
            when(tempSpin){
                0 -> {
                    result=convertFromMeter(view,length)

                }
                1 -> {
                    result=convertFromKm(view,length)

                }
                2 -> {
                    result=convertFromCM(view,length)

                }
            }
        }
        return result
    }

    private fun convertFromCM(view: View?, length: String): Double {
        var result=0.0
        if (view != null) {
            when (view.findViewById<Spinner>(R.id.addResultSpinner).selectedItemPosition) {
                2 -> {
                    result=length.toDouble()
                }
                0 -> {
                    result = length.toDouble() / 100

                }
                1 -> {
                    result = length.toDouble() / 100000

                }
            }
        }
        return result

    }

    private fun convertFromKm(view: View?, length: String): Double {
        var result=0.0
        if (view != null) {
            when (view.findViewById<Spinner>(R.id.addResultSpinner)?.selectedItemPosition) {
                1 -> {
                    result=length.toDouble()
                }
                0 -> {
                   result = length.toDouble() * 1000

                }
                2 -> {
                    result= length.toDouble() * 100000

                }
            }

        }
        return result
    }

    private fun convertFromMeter(view: View?, length: String): Double {
        var result=0.0
        when (view?.findViewById<Spinner>(R.id.addResultSpinner)?.selectedItemPosition) {
                        0 -> {
                            result=length.toDouble()
                        }
                        1 -> {
                            result = length.toDouble() / 1000

                        }
                        2 -> {
                            result = length.toDouble() * 100

                        }
        }
        return result


    }

    private fun convertTemp(view: View?, temp: String, spinner: Spinner?):Double {
        var result=0.0
        if(temp!=""){
            val tempSpin:String=spinner?.selectedItem as String
            when(tempSpin){
                "Celsius" -> {
                    result=convertFromCelsius(view,temp)

                }
                "Fahrenheit" -> {
                    result=convertFromFahrenheit(view,temp)

                }
                "Kelvin" -> {
                    result=convertFromKelvin(view,temp)

                }

            }
        }
        return result

    }

    private fun convertFromKelvin(view: View?, temp: String): Double {
        var result=0.0
        if (view != null) {
            when (view.findViewById<Spinner>(R.id.addResultSpinner).selectedItem as String) {
                "Kelvin" -> {
                    result=temp.toDouble()
                }
                "Celsius" -> {
                    result= temp.toDouble() - 273.15
                }
                "Fahrenheit" -> {
                    result= (temp.toDouble() - 273.15) * 9 / 5 + 32
                }
            }
        }
        return result
    }

    private fun convertFromFahrenheit(view: View?, temp: String): Double {
        var result=0.0
        if (view != null) {
            when (view.findViewById<Spinner>(R.id.addResultSpinner).selectedItem as String) {
                "Fahrenheit" -> {
                    result=temp.toDouble()
                }
                "Celsius" -> {
                    result=(temp.toDouble() - 32) * 5 / 9

                }
                "Kelvin" -> {
                    result=(temp.toDouble() - 32) * 5 / 9 + 273.15

                }
            }
        }
        return result
    }

    private fun convertFromCelsius(view: View?, temp: String): Double {
        var result=0.0
        if (view != null) {
            when (view.findViewById<Spinner>(R.id.addResultSpinner).selectedItem as String) {
                "Celsius" -> {
                    result=temp.toDouble()

                }
                "Fahrenheit" -> {
                    result=temp.toInt() * 1.8 + 32

                }
                "Kelvin" -> {
                    result= temp.toInt() + 273.15

                }
            }
        }
        return result
    }


    private fun printNoValue() {
        val output=view?.findViewById<TextView>(R.id.addResult)
        output?.text=""
    }


    override fun onItemSelected(p0: AdapterView<*>?, p1: View?, p2: Int, p3: Long) {
        val mainType=view?.findViewById<Spinner>(R.id.addMainSpinner)
        val index=mainType?.selectedItemPosition
        val addValue1=view?.findViewById<Spinner>(R.id.addSpinner1)
        val addValue2=view?.findViewById<Spinner>(R.id.addSpinner2)
        val value1=view?.findViewById<EditText>(R.id.addInput1)
        val value2=view?.findViewById<EditText>(R.id.addInput2)
        val output=view?.findViewById<TextView>(R.id.addResult)
        var total=0.0
        when(p0?.id) {
            R.id.addMainSpinner ->{
                value1?.setText("")
                value2?.setText("")
                loadSpinners(p2)
            }
            R.id.addSpinner1->{
                total=totalFromSpinnerChange(index,value1,value2,view,addValue1,addValue2)
            }
            R.id.addSpinner2->{
                total=totalFromSpinnerChange(index,value1,value2,view,addValue1,addValue2)

            }
            R.id.addResultSpinner->{
                total=totalFromSpinnerChange(index,value1,value2,view,addValue1,addValue2)
            }
        }
        printTotal(total,output)
        }

    private fun loadSpinners(p2: Int) {
            when (p2) {
                    0 -> {
                        loadRemainingSpinners(R.id.addSpinner1, temperatureValues)
                        loadRemainingSpinners(R.id.addSpinner2, temperatureValues)
                        loadRemainingSpinners(R.id.addResultSpinner, temperatureValues)
                    }
                    1 -> {
                        loadRemainingSpinners(R.id.addSpinner1, distanceValues)
                        loadRemainingSpinners(R.id.addSpinner2, distanceValues)
                        loadRemainingSpinners(R.id.addResultSpinner, distanceValues)
                    }
                    2 -> {
                        loadRemainingSpinners(R.id.addSpinner1, massValues)
                        loadRemainingSpinners(R.id.addSpinner2, massValues)
                        loadRemainingSpinners(R.id.addResultSpinner, massValues)
                    }
                    3 -> {
                        loadRemainingSpinners(R.id.addSpinner1, volumeValues)
                        loadRemainingSpinners(R.id.addSpinner2, volumeValues)
                        loadRemainingSpinners(R.id.addResultSpinner, volumeValues)
                    }
                }

    }

    private fun printTotal(res: Double, output: TextView?) {

        if(res.toInt() >99999){
            val numberFormat= DecimalFormat("0.###E0")
            val exponentialResult=numberFormat.format(res)
            output?.text= exponentialResult
        }
        else if((res>0 && res<1)){
            if(res.toString().length <7){
                output?.text=res.toString()
            }
            else{
                val numberFormat= DecimalFormat("0.###E0")
                val exponentialResult=numberFormat.format(res)
                output?.text= exponentialResult
            }
        }
        else{
            var formated= String.format("%.2f",res)
            output?.text= formated
        }

    }


    private fun totalFromSpinnerChange(
        index: Int?,
        value1: EditText?,
        value2: EditText?,
        view: View?,
        addValue1: Spinner?,
        addValue2: Spinner?
    ): Double {
        var converted1=0.0
        var converted2=0.0
        if(index==0 && value1?.text !=null && value2?.text !=null){
            converted1=convertTemp(view,value1.text.toString(),addValue1)
            converted2=convertTemp(view,value2.text.toString(),addValue2)
        }
        else if(index==1 && value1?.text !=null && value2?.text !=null){
            converted1=convertDistance(view,value1.text.toString(),addValue1)
            converted2= convertDistance(view,value2.text.toString(),addValue2)
        }
        else if(index==2 && value1?.text !=null && value2?.text !=null){
            converted1=convertMass(view,value1.text.toString(),addValue1)
            converted2=convertMass(view,value2.text.toString(),addValue2)
        }
        else if(index==3 && value1?.text !=null && value2?.text !=null){
            converted1=convertVolume(view,value1.text.toString(),addValue1)
            converted2=convertVolume(view,value2.text.toString(),addValue2)
        }
        return  converted1+converted2
    }


    private fun loadRemainingSpinners(elementeId: Int, listOfValues: ArrayList<String?>) {
        var tempSpin=view?.findViewById<Spinner>(elementeId)
        val temp_adapter = ArrayAdapter<String>(requireActivity(), android.R.layout.simple_spinner_item, listOfValues)
        temp_adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        tempSpin?.adapter=temp_adapter

    }

    override fun onNothingSelected(p0: AdapterView<*>?) {
        TODO("Not yet implemented")
    }
}


