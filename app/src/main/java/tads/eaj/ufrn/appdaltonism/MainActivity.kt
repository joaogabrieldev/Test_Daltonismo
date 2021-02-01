package tads.eaj.ufrn.appdaltonism

import android.app.Activity
import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.CheckBox
import android.widget.TextView
import android.widget.Toast


class MainActivity : AppCompatActivity(), View.OnClickListener {
    val MY_RESULT_CODE1 = 99
    val MY_RESULT_CODE2 = 98
    val MY_RESULT_CODE3 = 97

    val CODE1 = 1
    val CODE2 = 2
    val CODE3 = 3

    var resposta_1 = 0
    var resposta_2 = 0
    var resposta_3 = 0

    var resultado = ""

    lateinit var textResp1: TextView
    lateinit var textResp2: TextView
    lateinit var textResp3: TextView
    lateinit var btnTeste1: Button
    lateinit var btnTeste2: Button
    lateinit var btnTeste3: Button
    lateinit var btnVerificar: Button
    lateinit var textResultado: TextView
    lateinit var idCkc: CheckBox


    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putString("resposta1", textResp1.text.toString())
        outState.putString("resposta2", textResp2.text.toString())
        outState.putString("resposta3", textResp3.text.toString())
        outState.putString("resultado", textResultado.text.toString())

    }

    override fun onRestoreInstanceState(savedInstanceState: Bundle) {
        super.onRestoreInstanceState(savedInstanceState)

        textResp1.text = savedInstanceState.getString("resposta1")
        textResp2.text = savedInstanceState.getString("resposta2")
        textResp3.text = savedInstanceState.getString("resposta3")
        textResultado.text = savedInstanceState.getString("resultado")
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        textResp1 = findViewById(R.id.textResp1)
        textResp2 = findViewById(R.id.textResp2)
        textResp3 = findViewById(R.id.textResp3)
        textResultado = findViewById(R.id.textResultado)
        btnTeste1 = findViewById(R.id.btnTeste1)
        btnTeste2 = findViewById(R.id.btnTeste2)
        btnTeste3 = findViewById(R.id.btnTeste3)
        btnVerificar = findViewById(R.id.btnVerificar)
        idCkc = findViewById(R.id.idCkc)

        textResp1.text = resposta_1.toString()
        textResp2.text = resposta_2.toString()
        textResp3.text = resposta_3.toString()
        textResultado.text = ("$resultado")

        btnTeste1.setOnClickListener(this)
        btnTeste2.setOnClickListener(this)
        btnTeste3.setOnClickListener(this)

        btnVerificar.setOnClickListener {
            try {
                if (teste()) textResultado.text = "Sem sintomas" else textResultado.text = "DALTÔNICO"
            } catch (e: NumberFormatException) {
                textResultado.text = "Dados incorretos!"
                Toast.makeText(this, "VALORES INCORRETOS!", Toast.LENGTH_SHORT).show()
            }
        }

        val settings = getSharedPreferences("Default", Context.MODE_PRIVATE)
        val salvar = settings.getBoolean("salvar", false)
        if (salvar){
            idCkc.isChecked = salvar
            textResp1.text= settings.getString("resposta1", "")
            textResp2.text= settings.getString("resposta2", "")
            textResp3.text= settings.getString("resposta3", "")
            textResultado.text= settings.getString("resultado", "")
        }

    }

    override fun onStop() {
        super.onStop()
        val settings = getSharedPreferences("Default",Context.MODE_PRIVATE)
        val editor = settings.edit()
        if (idCkc.isChecked){
            editor.putBoolean("salvar", idCkc.isChecked)
            editor.putString("resposta1",textResp1.text.toString())
            editor.putString("resposta2",textResp2.text.toString())
            editor.putString("resposta3",textResp3.text.toString())
            editor.putString("resultado",textResultado.text.toString())
            editor.commit()
        }else{
            editor.remove("salvar")
            editor.remove("resposta1")
            editor.remove("resposta2")
            editor.remove("resposta3")
            editor.remove("resultado")
            editor.commit()
        }
    }

    override fun onClick(view: View) {
        val id = view.id
        var i = Intent(this, TesteActivity::class.java)
        var cod = 0
        var result = 0

        when (id) {
            R.id.btnTeste1 -> {
                cod = CODE1
                result = MY_RESULT_CODE1
            }
            R.id.btnTeste2 -> {
                cod = CODE2
                result = MY_RESULT_CODE2
            }
            R.id.btnTeste3 -> {
                cod = CODE3
                result = MY_RESULT_CODE3
            }
        }

        var b = Bundle()
        b.putInt("codImagem", cod)
        i.putExtras(b)
        startActivityForResult(i, result)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        val t = data?.getStringExtra("RESPOSTA")

        when (requestCode) {
            MY_RESULT_CODE1 -> {
                when (resultCode) {
                    Activity.RESULT_OK -> {
                        textResp1.text = t
                    }
                }
            }
            MY_RESULT_CODE2 -> {
                when (resultCode) {
                    Activity.RESULT_OK -> {
                        textResp2.text = t
                    }
                }
            }
            MY_RESULT_CODE3 -> {
                when (resultCode) {
                    Activity.RESULT_OK -> {
                        textResp3.text = t
                    }
                }
            }

        }


    }

    private fun teste(): Boolean = textResp1.text.toString().toInt() == 2 && textResp2.text.toString().toInt() == 29 && textResp3.text.toString().toInt() == 74


}