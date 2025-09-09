package br.com.fichaheroiliga

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.*
import com.google.gson.Gson

class CriacaoHeroiActivity : AppCompatActivity() {

    private val avatares = arrayOf(
        R.drawable.batman,
        R.drawable.superman,
        R.drawable.flash,
        R.drawable.mulher_maravilha,
        R.drawable.darkseid,
        R.drawable.apocalypse,
        R.drawable.constantine,
        R.drawable.lobo
    )

    private val alinhamentos = arrayOf(
        "Herói","Herói","Herói","Herói",
        "Vilão","Vilão","Anti-herói","Anti-herói"
    )

    private val poderes = arrayOf(
        arrayOf("Voo",),
        arrayOf("Voo","Super-força","Super-velocidade"),
        arrayOf("Super-velocidade"),
        arrayOf("Voo","Super-força"),
        arrayOf("Rajadas de Energia","Super-força","Voo"),
        arrayOf("Super-força","Rajadas de Energia","Voo","Super-velocidade"),
        arrayOf("Magia"),
        arrayOf("Super-força","Rajadas de Energia"),

    )

    private var avatarIndex = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_criacao_heroi)

        val txtBemVindo = findViewById<TextView>(R.id.txtBemVindo)
        val rgAlinhamento = findViewById<RadioGroup>(R.id.rgAlinhamento)
        val cbVoo = findViewById<CheckBox>(R.id.cbVoo)
        val cbForca = findViewById<CheckBox>(R.id.cbForca)
        val cbVelocidade = findViewById<CheckBox>(R.id.cbVelocidade)
        val cbTelepatia = findViewById<CheckBox>(R.id.cbTelepatia)
        val cbEnergia = findViewById<CheckBox>(R.id.cbEnergia)
        val cbMagia = findViewById<CheckBox>(R.id.cbMagia)
        val imgAvatar = findViewById<ImageView>(R.id.imgAvatar)
        val btnGerar = findViewById<Button>(R.id.btnGerar)

        val nome = intent.getStringExtra("nome")
        txtBemVindo.text = "Personalize o perfil de: $nome"

        val prefs = getSharedPreferences("dados", MODE_PRIVATE)
        val gson = Gson()

        val json = prefs.getString("ultimoHeroi", null)
        if(json != null){
            val heroi = gson.fromJson(json, Heroi::class.java)
            when(heroi.alinhamento){
                "Herói" -> rgAlinhamento.check(R.id.rbHeroi)
                "Vilão" -> rgAlinhamento.check(R.id.rbVilao)
                "Anti-herói" -> rgAlinhamento.check(R.id.rbAntiHeroi)
            }
            cbVoo.isChecked = heroi.poderes.contains("Voo")
            cbForca.isChecked = heroi.poderes.contains("Super-força")
            cbVelocidade.isChecked = heroi.poderes.contains("Super-velocidade")
            cbTelepatia.isChecked = heroi.poderes.contains("Telepatia")
            cbEnergia.isChecked = heroi.poderes.contains("Rajadas de Energia")
            cbMagia.isChecked = heroi.poderes.contains("Magia")
            avatarIndex = avatares.indexOf(heroi.avatar)
            imgAvatar.setImageResource(heroi.avatar)
        } else {
            imgAvatar.setImageResource(avatares[avatarIndex])
        }

        imgAvatar.setOnClickListener {
            avatarIndex = (avatarIndex + 1) % avatares.size
            imgAvatar.setImageResource(avatares[avatarIndex])
            // atualizar RadioGroup
            when(alinhamentos[avatarIndex]){
                "Herói" -> rgAlinhamento.check(R.id.rbHeroi)
                "Vilão" -> rgAlinhamento.check(R.id.rbVilao)
                "Anti-herói" -> rgAlinhamento.check(R.id.rbAntiHeroi)
            }
            // atualizar CheckBoxes
            cbVoo.isChecked = poderes[avatarIndex].contains("Voo")
            cbForca.isChecked = poderes[avatarIndex].contains("Super-força")
            cbVelocidade.isChecked = poderes[avatarIndex].contains("Super-velocidade")
            cbTelepatia.isChecked = poderes[avatarIndex].contains("Telepatia")
            cbEnergia.isChecked = poderes[avatarIndex].contains("Rajadas de Energia")
            cbMagia.isChecked = poderes[avatarIndex].contains("Magia")
        }

        btnGerar.setOnClickListener {
            val alinhamento = when(rgAlinhamento.checkedRadioButtonId){
                R.id.rbHeroi -> "Herói"
                R.id.rbVilao -> "Vilão"
                else -> "Anti-herói"
            }

            val listaPoderes = ArrayList<String>()
            if(cbVoo.isChecked) listaPoderes.add("Voo")
            if(cbForca.isChecked) listaPoderes.add("Super-força")
            if(cbVelocidade.isChecked) listaPoderes.add("Super-velocidade")
            if(cbTelepatia.isChecked) listaPoderes.add("Telepatia")
            if(cbEnergia.isChecked) listaPoderes.add("Rajadas de Energia")
            if(cbMagia.isChecked) listaPoderes.add("Magia")

            val heroi = Heroi(nome ?: "", alinhamento, listaPoderes, avatares[avatarIndex])
            prefs.edit().putString("ultimoHeroi", gson.toJson(heroi)).apply()

            val i = Intent(this, FichaHeroiActivity::class.java)
            i.putExtra("heroi", gson.toJson(heroi))
            startActivity(i)
            finish()
        }
    }
}
