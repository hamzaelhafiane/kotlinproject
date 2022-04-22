package com.example.myapplication

import android.content.DialogInterface
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.appcompat.app.AlertDialog
import com.android.volley.Request
import com.android.volley.RequestQueue
import com.android.volley.Response
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import org.json.JSONObject

class register_page : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register_page)

        val emailinput: EditText = findViewById(R.id.edtemail)
        val passwordinput: EditText = findViewById(R.id.edtPassword)
        val usernameinput: EditText = findViewById(R.id.edtusername)
        val btnLogin: Button = findViewById(R.id.btnLogin)
        var txtsignin:TextView=findViewById(R.id.txtsignin)

        txtsignin.setOnClickListener{
            startActivity(Intent(this,MainActivity::class.java))
        }

        btnLogin.setOnClickListener {
            val email: String = emailinput.text.toString()
            val pass: String = passwordinput.text.toString()
            val usernm: String = usernameinput.text.toString()
            val url: String = "http://172.16.1.141/API%20PHP/Operations/Register.php"
            val params = HashMap<String, String>()
            params["email"] = email
            params["password"] = pass
            params["username"] = usernm
            val jO = JSONObject(params as Map<*, *>)
            val rq: RequestQueue = Volley.newRequestQueue(this)
            val jor = JsonObjectRequest(Request.Method.POST, url, jO, Response.Listener { res ->
                try {
                    if (res.getString("success").equals("1")) {
                        val intent = Intent(this, home::class.java)
                        intent.putExtra("UserName", res.getString("user"))
                        startActivity(intent)
                        emailinput.text.clear()
                        passwordinput.text.clear()
                    } else {
                        alert("Message d'Erreur !", res.getString("message"))
                    }

                } catch (e: Exception) {
                    alert("Message d'Erreur !", "" + e.message)
                }
            }, Response.ErrorListener { err ->
                alert("Message d'Erreur !", "" + err.message)
            })
            rq.add(jor)
        }

    }

    private fun alert(title: String, message: String) {
        val builder = AlertDialog.Builder(this)
        builder.setTitle(title)
        builder.setMessage(message)
        builder.setPositiveButton("Ok", { dialogInterface: DialogInterface, i: Int -> }).create()
        builder.show()
    }
}
