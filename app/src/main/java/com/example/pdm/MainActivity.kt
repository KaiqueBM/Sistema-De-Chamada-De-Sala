package com.example.pdm

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val btnConectar: Button = findViewById(R.id.btnConectar)
        btnConectar.setOnClickListener {
            val edtRA: EditText = findViewById(R.id.edtRA)
            val edtSenha: EditText = findViewById(R.id.edtSenha)
            val sRA = edtRA.text
            val sSenha = edtSenha.text
            Toast.makeText(applicationContext, "Login realizado com sucesso!", Toast.LENGTH_LONG).show()
            // criar intent
            val telaAreaDoAluno = Intent(this, AreaDoAluno::class.java)

            // Passando parâmetros para a segunda tela
            val parametros = Bundle()
            parametros.putString("ra", "$sRA")
            telaAreaDoAluno.putExtras(parametros)

            // fazer a chamada
            startActivity(telaAreaDoAluno)
        }
    }
}