package tads.eaj.ufrn.appdaltonism

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView



class TesteActivity : AppCompatActivity() {

    lateinit var idImagem: ImageView
    lateinit var btnOk:Button
    lateinit var btnCancelar: Button
    lateinit var editText: EditText


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_teste)

        idImagem = findViewById(R.id.idImagem)
        btnOk = findViewById(R.id.btnOk)
        btnOk = findViewById(R.id.btnOk)
        btnCancelar = findViewById(R.id.btnCancelar)
        editText = findViewById(R.id.editText)

        val params = intent.extras
        val texto = params?.getInt("codImagem")
        when (texto) {
            1 -> {idImagem.setImageResource(R.drawable.test2)}
            2 -> { idImagem.setImageResource(R.drawable.test29)}
            3 -> {idImagem.setImageResource(R.drawable.test74)}
        }

        btnOk.setOnClickListener {
            val i = Intent()
            i.putExtra("RESPOSTA", editText.text.toString())
            setResult(Activity.RESULT_OK, i)
            finish()
        }

        btnCancelar.setOnClickListener {
            finish()
        }
    }

}