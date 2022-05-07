package com.example.pdm

import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.location.Location
import android.location.LocationListener
import android.location.LocationManager
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import java.text.DecimalFormat
import java.text.SimpleDateFormat
import java.util.*
import java.util.jar.Manifest

class AreaDoAluno : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_area_do_aluno)

        // Recuperando os parâmetros passados
        val dados = intent
        val parametros = dados.extras
        val sRa = parametros!!.getString("ra")
        val sNome = parametros!!.getString("nome")
        var registro = false

        // Atualizando campos de texto com informações do aluno
        val txtNome: TextView = findViewById(R.id.nomeAluno)
        txtNome.text = "Nome: $sNome" //Mostrar nome do aluno
        val txtRa: TextView = findViewById(R.id.raAluno)
        txtRa.text = "RA: $sRa" //Mostrar Ra do aluno

        // Pegar dados de data e horário
        val date = Calendar.getInstance().time
        var horario = SimpleDateFormat("HH:mm", Locale.getDefault()) //Horario atual
        var data = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault()) //Dia atual
        var diaDaSemana = SimpleDateFormat("EEE", Locale.getDefault()) //Dia da semana atual

        // Mostrar dia e horário que foi feito o login atual
        val diaLogin: TextView = findViewById(R.id.diaLogin)
        diaLogin.text = "Login feito no dia " + data.format(date) + " às " + horario.format(date)

        // Array com as matérias que temos
        var arrayMaterias = arrayOf(
            "TRABALHO DE GRADUAÇÃO INTERDISCIPLINAR I",
            "PROGRAMAÇÃO PARA DISPOSITIVOS MÓVEIS",
            "FUNDAMENTOS DE INTELIGÊNCIA ARTIFICIAL",
            "LINGUAGENS FORMAIS E AUTÔMATOS")

        var arrayDiasDaSemanaComAula = arrayOf(
            "Mon", "Wed", "Thu", "Fri"
        )

        val materiaDoDia: TextView = findViewById(R.id.materiaDoDia)
        val situacaoAula: TextView = findViewById(R.id.situacaoAula)
        val localizacao: TextView = findViewById(R.id.localizacao)
        val presenca: TextView = findViewById(R.id.presenca)
        val btnPresenca: Button = findViewById(R.id.btnPresenca)

        // Informações da aula (horário e localização)
        var horarioAulaInicio = SimpleDateFormat("19:10", Locale.getDefault()) //Horário da aula 19:10 as 21:50
        var horarioAulaFim = SimpleDateFormat("21:50", Locale.getDefault()) //Horário da aula 19:10 as 21:50
        var latitudeSala = -23.5362 //Latitude da sala
        var longitudeSala = -46.5603 //Longitude da sala

        // While para verificar qual matéria temos hoje
        var contMaterias = 0 // Contador
        var quantMaterias = 4 // Quantidade de matérias
        var numMateria = 0 // Guardar o índice da matéria
        while(contMaterias < quantMaterias) {
            if(diaDaSemana.format(date) == arrayDiasDaSemanaComAula[contMaterias]) {
                materiaDoDia.text = arrayMaterias[contMaterias]
                numMateria = contMaterias
            break
            } else {
                materiaDoDia.text = "Hoje não temos aula"
                contMaterias++
            }
        }

        btnPresenca.setVisibility(View.INVISIBLE)
        var latitude: Double
        var longitude: Double
        // Checar permissões de localização
        if (ContextCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED
            && ActivityCompat.checkSelfPermission(this,android.Manifest.permission.ACCESS_COARSE_LOCATION ) != PackageManager.PERMISSION_GRANTED ) {
            ActivityCompat.requestPermissions(this,arrayOf(android.Manifest.permission.ACCESS_FINE_LOCATION),1)
        }
        try {
            val locationManager = getSystemService(Context.LOCATION_SERVICE) as LocationManager
            val locationListener: LocationListener = object : LocationListener {
                override fun onLocationChanged(location: Location) {
                    latitude = location.getLatitude(); // Obter latitude atual
                    longitude = location.getLongitude(); // Obter longitude atual
                    //latitude = -23.5362
                    //longitude = -46.5603
                    localizacao.text = "Localização atual - Lat: " + formatarGeopoint(latitude) + " | Long: " + formatarGeopoint(longitude)
                    // Primeiro IF verifica localização e o segundo o horário
                    if(((formatarGeopoint(latitude)) == formatarGeopoint(latitudeSala)) && (formatarGeopoint(longitude) == formatarGeopoint(longitudeSala))){
                        if((horario.format(date) >= horarioAulaInicio.format(date)) && (horario.format(date) <= horarioAulaFim.format(date)) && (registro == false)) {
                            btnPresenca.setVisibility(View.VISIBLE)
                            situacaoAula.text = "Sua aula começou, boa aula! Você tem até as 21:50 para registrar sua presença!"
                            presenca.text = "Presença: DISPONÍVEL"
                            btnPresenca.setOnClickListener {
                                situacaoAula.text = "Tudo certinho, muito obrigado!"
                                presenca.text = "Presença: REGISTRADA - Às: " + horario.format(date) + " em: " + formatarGeopoint(latitude) + ", " + formatarGeopoint(longitude)
                                registro = true
                                Toast.makeText(
                                    applicationContext,
                                    "Presença registrada: " + arrayMaterias[numMateria] + " - Dia: " + data.format(date) + " às " + horario.format(date) +
                                            " - Localização: " + formatarGeopoint(latitude) + ", " + formatarGeopoint(longitude),
                                    Toast.LENGTH_LONG
                                ).show()
                                btnPresenca.setVisibility(View.INVISIBLE)
                            }
                        } else{
                            btnPresenca.setVisibility(View.INVISIBLE)
                        }
                    } else{
                        if((horario.format(date) >= horarioAulaInicio.format(date)) && (horario.format(date) <= horarioAulaFim.format(date)) && (registro == false)) {
                            btnPresenca.setVisibility(View.VISIBLE)
                            situacaoAula.text = "Sua aula começou, boa aula! Você tem até as 21:50 para registrar sua presença!"
                            presenca.text = "Presença: Disponível"
                            btnPresenca.setOnClickListener {
                                Toast.makeText(
                                    applicationContext,
                                    "Presença não registrado, motivo: Sua localização diz que você não está na sala | " + formatarGeopoint(latitude) + ", " + formatarGeopoint(longitude),
                                    Toast.LENGTH_LONG
                                ).show()
                            }
                        }
                    }
                }
                override fun onStatusChanged(provider: String, status: Int, extras: Bundle) {}
                override fun onProviderEnabled(provider: String) {}
                override fun onProviderDisabled(provider: String) {}
            }
            locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER,1,1f,locationListener)
        } catch (ex: SecurityException) {
            Toast.makeText(this, "Erro:" + ex.message, Toast.LENGTH_LONG).show()
        }

        val btnCronograma: Button = findViewById(R.id.btnCronograma)
        btnCronograma.setOnClickListener {
            val telaCronogramaDeAulas = Intent(this, CronogramaDeAulas::class.java)
            startActivity(telaCronogramaDeAulas)
        }

        val btnDeslogar: Button = findViewById(R.id.btnDeslogar)
        btnDeslogar.setOnClickListener {
            Toast.makeText(applicationContext, "Deslogado com sucesso!", Toast.LENGTH_LONG).show()
            finish()
        }

    }

    // Função para formatar a localização
    private fun formatarGeopoint(valor: Double): String? {
        val decimalFormat = DecimalFormat("#.####")
        return decimalFormat.format(valor)
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


}

