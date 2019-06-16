package com.example.eucurrencyconverterapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.view.View.GONE
import android.view.View.VISIBLE
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.RadioGroup
import android.widget.Toast
import com.android.volley.DefaultRetryPolicy
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.VolleyError
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import kotlinx.android.synthetic.main.activity_main.*
import org.json.JSONArray
import org.json.JSONException
import org.json.JSONObject

class MainActivity : AppCompatActivity() {
    var url = "https://jsonvat.com/"
    var countryName:ArrayList<String> = ArrayList()
     var rateMain1: Double = 0.toDouble()
    var rateMain2:Double = 0.toDouble()
    var rateMain3:Double = 0.toDouble()
     var rateMain4:Double = 0.toDouble()
     var rateMain5:Double = 0.toDouble()
    var rateName1:String?= null
    var rateName2:String?= null
    var rateName3:String?= null
    var rateName4:String?= null
    var rateName5:String?= null
    var rateValue1:String?= null
    var rateValue2:String?= null
    var rateValue3:String?= null
    var rateValue4:String?= null
    var rateValue5:String?= null
      var taxValue:Double = 0.toDouble()
      var resultValue:Double = 0.toDouble()
     var amount:Double = 120.toDouble()





    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        loadSpinnerData(url)


        spinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>, view: View, position: Int, id: Long) {
               // Toast.makeText(applicationContext, "This is$position",Toast.LENGTH_LONG).show()
                resetValue();
                loadData(url,position);

            }

            override fun onNothingSelected(parent: AdapterView<*>) {

            }
        }


        radioGroupTaxes.setOnCheckedChangeListener { group, checkedId ->
            amount = java.lang.Double.parseDouble(textInputEditText.text.toString())
            val cAmount = java.lang.Double.toString(amount)
            originalAmount.text = cAmount
            try {


                if (checkedId == R.id.radio1) {
                    taxValue = amount * rateMain1 / 100
                    resultValue = amount + taxValue
                    tax.text = java.lang.Double.toString(taxValue)
                    total.text = java.lang.Double.toString(resultValue)
                } else if (checkedId == R.id.radio2) {
                    taxValue = amount * rateMain2 / 100
                    resultValue = amount + taxValue
                    tax.text = java.lang.Double.toString(taxValue)
                    total.text = java.lang.Double.toString(resultValue)
                } else if (checkedId == R.id.radio3) {
                    taxValue = amount * rateMain3 / 100
                    resultValue = amount+ taxValue
                    tax.text = java.lang.Double.toString(taxValue)
                    total.text = java.lang.Double.toString(resultValue)

                } else if (checkedId == R.id.radio4) {
                    taxValue = amount * rateMain4 / 100
                    resultValue = amount + taxValue
                    tax.text = java.lang.Double.toString(taxValue)
                    total.text = java.lang.Double.toString(resultValue)

                } else if (checkedId == R.id.radio5) {
                    taxValue = amount * rateMain5 / 100
                    resultValue = amount + taxValue
                    tax.text = java.lang.Double.toString(taxValue)
                    total.text = java.lang.Double.toString(resultValue)

                }
            } catch (e: Exception) {

            }
        }

    }

    private fun resetValue() {
         rateName1= null
        rateName2= null
        rateName3= null
        rateName4= null
        rateName5= null
        rateValue1= null
        rateValue2= null
        rateValue3= null
        rateValue4= null
        rateValue5= null

        radio1.text=null
        radio2.text=null
        radio3.text=null
        radio4.text=null
        radio5.text=null
        radio5.visibility= GONE
    }

    private fun loadSpinnerData(url:String) {
        val requestQueue = Volley.newRequestQueue(applicationContext)
        val stringRequest = StringRequest(Request.Method.POST, url,
            Response.Listener<String> { response ->
                try {
                    val jsonObject1 = JSONObject(response)
                    val jsonArray1 = jsonObject1.getJSONArray("rates")
                    for (i in 0 until jsonArray1.length()) {
                        val jsonObject2 = jsonArray1.get(i) as JSONObject
                        val country = jsonObject2.get("name") as String
                        countryName.add(country)
                    }

                    spinner.adapter =
                        ArrayAdapter<String>(
                            this@MainActivity,
                            android.R.layout.simple_spinner_dropdown_item,
                            countryName
                        )

                } catch (e: JSONException) {
                    e.printStackTrace()
                }
            }, Response.ErrorListener { error -> error.printStackTrace() })
        val socketTimeout = 30000
        val policy = DefaultRetryPolicy(socketTimeout, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT)
        stringRequest.retryPolicy = policy
        requestQueue.add(stringRequest)
    }
    private fun loadData(url:String,position:Int) {
        val requestQueue = Volley.newRequestQueue(applicationContext)
        val stringRequest = StringRequest(Request.Method.POST, url,
            Response.Listener<String> { response ->
                try {
                    val jsonObject1 = JSONObject(response)
                    val jsonArray1 = jsonObject1.getJSONArray("rates")

                    val jsonObject2 = jsonArray1.get(position) as JSONObject
                    var jsonArray2 = jsonObject2.get("periods") as JSONArray
                    var jsonObject3 = jsonArray2.getJSONObject(0) as JSONObject
                    var jsonObject4 = jsonObject3.getJSONObject("rates")
                    var jsonArray3 = jsonObject4.names()
                    rateName1 = jsonArray3.get(0).toString()
                   rateValue1 = jsonObject4.get(rateName1).toString()
                    radio1.text=rateName1+" ( "+rateValue1+" % )"
                    rateMain1 = java.lang.Double.parseDouble(rateValue1)
                    taxValue = amount * rateMain1 / 100
              resultValue = amount + taxValue
               tax.text = java.lang.Double.toString(taxValue)
                total.text = java.lang.Double.toString(resultValue)
                   rateName2 = jsonArray3.get(1).toString()
                   rateValue2 = jsonObject4.get(rateName2).toString()
                    radio2.text=rateName2+" ( "+rateValue2+" % )"
                   rateMain2 = java.lang.Double.parseDouble(rateValue2)
                   rateName3 = jsonArray3.get(2).toString()
                   rateValue3 = jsonObject4.get(rateName3).toString()
                   rateMain3 = java.lang.Double.parseDouble(rateValue3)
                    radio3.text=rateName3+" ( "+rateValue3+" % )"
                   rateName4 = jsonArray3.get(3).toString()
                    rateValue4 = jsonObject4.get(rateName4).toString()
                    radio4.text=rateName4+" ( "+rateValue4+" % )"
                   rateMain4 = java.lang.Double.parseDouble(rateValue4)
                   rateName5 = jsonArray3.get(4).toString()
                   rateValue5 = jsonObject4.get(rateName5).toString()
                    radio5.text=rateName5+" ( "+rateValue5+" % )"
                   rateMain5 = java.lang.Double.parseDouble(rateValue5)


                    if(rateName4==null){
                       radio4.visibility= GONE
                    }else{
                        radio4.visibility= VISIBLE
                    }
                    if(rateName5==null){
                        radio5.visibility= GONE
                    }else{
                        radio5.visibility= VISIBLE
                    }



                } catch (e: JSONException) {
                    e.printStackTrace()
                }
            }, Response.ErrorListener { error -> error.printStackTrace() })
        val socketTimeout = 30000
        val policy = DefaultRetryPolicy(socketTimeout, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT)
        stringRequest.retryPolicy = policy
        requestQueue.add(stringRequest)
    }


}
