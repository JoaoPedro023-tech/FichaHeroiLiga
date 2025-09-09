package br.com.fichaheroiliga

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val edtNome = findViewById<EditText>(R.id.edtNome)
        val btnCriar = findViewById<Button>(R.id.btnCriar)

        val prefs = getSharedPreferences("dados", MODE_PRIVATE)

        val nomeSalvo = prefs.getString("nomeHeroi", "")
        edtNome.setText(nomeSalvo)

        btnCriar.setOnClickListener {
            val nome = edtNome.text.toString()
            if (nome.isEmpty()) {
                Toast.makeText(this, "Digite um codinome", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            prefs.edit().putString("nomeHeroi", nome).apply()

            val i = Intent(this, CriacaoHeroiActivity::class.java)
            i.putExtra("nome", nome)
            startActivity(i)
        }
    }
}