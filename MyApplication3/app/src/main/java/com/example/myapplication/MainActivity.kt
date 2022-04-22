package com.example.myapplication

import android.content.DialogInterface
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.android.volley.Request
import com.android.volley.RequestQueue
import com.android.volley.Response
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import com.google.android.material.textfield.TextInputEditText
import org.json.JSONObject


class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)



        val txtsignup:TextView=findViewById(R.id.txtsignup)
        val btnLogin:Button=findViewById(R.id.btnLogin)
        val emailinput:EditText=findViewById(R.id.edtUserName)
        val passwordinput:EditText=findViewById(R.id.edtPassword)

        txtsignup.setOnClickListener{
            startActivity(Intent(this,register_page::class.java))
        }

        btnLogin.setOnClickListener{
            val email:String =emailinput.text.toString()
            val pass:String=passwordinput.text.toString()
            val url:String="http://172.16.1.141/API%20PHP/Operations/Login.php"
            val params=HashMap<String,String>()
            params["email"]=email
            params["password"]=pass
            val jO= JSONObject(params as Map<*, *>)
            val rq: RequestQueue = Volley.newRequestQueue(this@MainActivity)
            val jor= JsonObjectRequest(Request.Method.POST,url,jO, Response.Listener { res->
                try {
                    if(res.getString("success").equals("1")){
                        val intent=Intent(this@MainActivity,home::class.java)
                        intent.putExtra("UserName",res.getString("user"))
                        startActivity(intent)
                        emailinput.text.clear()
                        passwordinput.text.clear()
                    } else { alert("Message d'Erreur !",res.getString("message")) }

                }catch (e:Exception){
                    alert("Message d'Erreur !",""+e.message)
                }
            },Response.ErrorListener { err->
                alert("Message d'Erreur !",""+err.message)
            })
            rq.add(jor)
        }

    }

    private fun alert(title:String,message:String){
        val builder= AlertDialog.Builder(this@MainActivity)
        builder.setTitle(title)
        builder.setMessage(message)
        builder.setPositiveButton("Ok",{ dialogInterface: DialogInterface, i: Int -> }).create()
        builder.show()
    }

}