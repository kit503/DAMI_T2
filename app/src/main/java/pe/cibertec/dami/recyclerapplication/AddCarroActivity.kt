package pe.cibertec.dami.recyclerapplication

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.Spinner
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.textfield.TextInputEditText
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import pe.cibertec.dami.recyclerapplication.model.Carro
import pe.cibertec.dami.recyclerapplication.proxy.interfaces.CarroService
import pe.cibertec.dami.recyclerapplication.proxy.retrofit.CarroRetrofit
import pe.cibertec.dami.recyclerapplication.util.LoadingDialog
import java.util.Arrays

class AddCarroActivity : AppCompatActivity() {

    private lateinit var loading: LoadingDialog

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_carro)
        loading = LoadingDialog(this)
//        loading.startLoading()

        var maxCarroId = 0

        val extras = intent.extras
        if (extras != null) {
            maxCarroId = extras.getInt("maxCarroId") + 1
        }

        val btnAddCarro: Button = findViewById(R.id.btnAddCarro)
        val btnCancelCarro: Button = findViewById(R.id.btnCancelCarro)
//        val txtCarroId: TextInputEditText = findViewById(R.id.txtCarroId)
        val txtCarroMarca: TextInputEditText = findViewById(R.id.txtMarca)
        val txtCarroModelo: TextInputEditText = findViewById(R.id.txtCarroModelo)
        val txtAnioCarro: TextInputEditText = findViewById(R.id.txtAnioCarro)
        val txtAsientosCarro: TextInputEditText = findViewById(R.id.txtAsientosCarro)
        val txtColorCarro: TextInputEditText = findViewById(R.id.txtColorCarro)

        btnAddCarro.setOnClickListener {

//            val carroId = txtCarroId.text.toString().trim().toInt()
            val marca = txtCarroMarca.text.toString().trim()
            val modelo = txtCarroModelo.text.toString().trim()
            val creationYear = txtAnioCarro.text.toString().trim().toInt()
            val asientos = txtAsientosCarro.text.toString().trim().toInt()
            val color = txtColorCarro.text.toString().trim()

            val carro = Carro(
                carroId = maxCarroId,
                marca = marca,
                modelo = modelo,
                creationYear = creationYear,
                asientos = asientos,
                color = color,
            )

            CoroutineScope(Dispatchers.IO).launch {
                val retrofit =
                    CarroRetrofit.getRetrofit().create(CarroService::class.java)
                        .saveCarro(carro)
                val data = retrofit.body()
                runOnUiThread {
                    Toast.makeText(this@AddCarroActivity,
                        carro.toString(), Toast.LENGTH_LONG).show()
                    startActivity(Intent(this@AddCarroActivity, ListaCarroActivity::class.java))
                }

            }

        }

        btnCancelCarro.setOnClickListener {
//            txtCarroId.setText("")
            txtCarroMarca.setText("")
            txtCarroModelo.setText("")
            txtAnioCarro.setText("")
            txtAsientosCarro.setText("")
            txtColorCarro.setText("")
        }

    }
}