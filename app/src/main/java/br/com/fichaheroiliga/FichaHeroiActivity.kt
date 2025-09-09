package br.com.fichaheroiliga

import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.*
import com.google.gson.Gson

class FichaHeroiActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_ficha_heroi)

        val root = findViewById<LinearLayout>(R.id.root)
        val imgAvatar = findViewById<ImageView>(R.id.imgAvatarFicha)
        val txtNome = findViewById<TextView>(R.id.txtNomeFicha)
        val txtAlinhamento = findViewById<TextView>(R.id.txtAlinhamentoFicha)
        val txtPoderes = findViewById<TextView>(R.id.txtPoderesFicha)

        val gson = Gson()
        val heroiJson = intent.getStringExtra("heroi")
        val heroi = gson.fromJson(heroiJson, Heroi::class.java)

        imgAvatar.setImageResource(heroi.avatar)
        txtNome.text = "Codinome: ${heroi.nome}"
        txtAlinhamento.text = "Alinhamento: ${heroi.alinhamento}"
        txtPoderes.text = "Poderes: ${heroi.poderes.joinToString(", ")}"

        when(heroi.alinhamento){
            "Herói" -> root.setBackgroundColor(Color.parseColor("#ADD8E6"))
            "Vilão" -> root.setBackgroundColor(Color.parseColor("#FF6961"))
            "Anti-herói" -> root.setBackgroundColor(Color.parseColor("#D3D3D3"))
        }
    }
}
