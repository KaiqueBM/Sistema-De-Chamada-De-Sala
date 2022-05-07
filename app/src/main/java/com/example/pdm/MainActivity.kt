package com.example.pdm

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {

    private var arrayRgm = arrayOf("12345678", "20625308", "20613385", "20691564", "22255451", "21220051", "21152551", "20938594")
    private var arrayNome = arrayOf("Teste", "Giselle", "Kaique", "Mauricio", "Luis", "Lucas", "Kaue", "Miqueias" )
    private var arrayPassword = arrayOf("123", "123", "123", "123", "123", "123", "123", "123")

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val btnConectar: Button = findViewById(R.id.btnConectar)
        var btnFechar: Button = findViewById(R.id.btnFechar)

        btnConectar.setOnClickListener {
            val edtRA: EditText = findViewById(R.id.edtRA)
            val edtSenha: EditText = findViewById(R.id.edtSenha)
            val sRA = edtRA.text.toString()
            val sSenha = edtSenha.text.toString()

            // Limitador para while
            var contRgm = 0

            // Loop para verificar os dados de logins no array
            while(contRgm < arrayRgm.size) {
                // Verificar se o rgm e a senha estão no array salvo
                if(sRA == arrayRgm[contRgm] && sSenha == arrayPassword[contRgm]){
                    Toast.makeText(applicationContext, "Login realizado com sucesso!", Toast.LENGTH_LONG).show()
                    // Criar Intent
                    val telaAreaDoAluno = Intent(this, AreaDoAluno::class.java)
                    var sNome = arrayNome[contRgm]

                    // Passando parâmetros para a segunda tela
                    val parametros = Bundle()
                    parametros.putString("ra", "$sRA")
                    parametros.putString("nome", "$sNome")
                    telaAreaDoAluno.putExtras(parametros)

                    limpaCampos() // Limpar os campos
                    startActivity(telaAreaDoAluno) // Fazer a chamada
                    break
                } else{
                    limpaCampos()
                    // Mostrar mensagem de erro caso não digitar o RGM ou senha corretos
                    if(arrayRgm.indexOf(sRA) == -1 || sSenha != arrayPassword[0]){
                        Toast.makeText(applicationContext, "Login incorreto", Toast.LENGTH_SHORT).show()
                        break
                    }
                    contRgm += 1
                }
            }
        }
        btnFechar.setOnClickListener {
            finish()
        }
    }

    private fun limpaCampos() {
        val edtRA: EditText = findViewById(R.id.edtRA)
        val edtSenha: EditText = findViewById(R.id.edtSenha)
        edtRA.setText("")
        edtSenha.setText("")
    }

    // Ciclo de vida
    override fun onStart() {
        super.onStart()
        Toast.makeText(getApplicationContext(), "Bem vindo!", Toast.LENGTH_LONG).show()
    }
    override fun onRestart() {
        super.onRestart()
    }
    override fun onResume() {
        super.onResume()
    }
    override fun onPause() {
        super.onPause()
    }
    override fun onStop() {
        super.onStop()
    }
    override fun onDestroy() {
        super.onDestroy()
        Toast.makeText(getApplicationContext(), "Fechando aplicativo!", Toast.LENGTH_LONG).show()
    }

}
