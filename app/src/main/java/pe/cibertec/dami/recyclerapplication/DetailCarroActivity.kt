package pe.cibertec.dami.recyclerapplication

import android.content.DialogInterface
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.LinearLayout
import android.widget.Spinner
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.android.material.textfield.TextInputEditText
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import pe.cibertec.dami.recyclerapplication.model.Carro
import pe.cibertec.dami.recyclerapplication.proxy.interfaces.CarroService
import pe.cibertec.dami.recyclerapplication.proxy.retrofit.CarroRetrofit
import java.util.Arrays

class DetailCarroActivity : AppCompatActivity() {

    private lateinit var btnEdit: FloatingActionButton
    private lateinit var btnDelete: FloatingActionButton
    private lateinit var txtMarca: TextInputEditText
    private lateinit var txtModelo: TextInputEditText
    private lateinit var txtAnio: TextInputEditText
    private lateinit var txtCarroId: TextInputEditText
    private lateinit var txtAsientos: TextInputEditText
    private lateinit var txtColor: TextInputEditText
    private lateinit var btnGuardar: Button
    private lateinit var btnCancelar: Button
    private lateinit var lyActions: LinearLayout
    private var carroId = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail_carro)
        btnEdit = findViewById(R.id.btnEdit)
        btnDelete = findViewById(R.id.btnDelete)

        txtCarroId = findViewById(R.id.txtCarroId)
        txtMarca = findViewById(R.id.txtCarroMarca)
        txtModelo = findViewById(R.id.txtCarroModelo)
        txtAnio = findViewById(R.id.txtAnioCarro)
        txtAsientos = findViewById(R.id.txtAsientosCarro)
        txtColor = findViewById(R.id.txtColorCarro)

        btnGuardar = findViewById(R.id.btnSaveCarro)
        btnCancelar = findViewById(R.id.btnClearCarro)

        lyActions = findViewById(R.id.lyActionsDt)


        btnEdit.setOnClickListener {
            txtCarroId.isEnabled = true
            txtMarca.isEnabled = true
            txtModelo.isEnabled = true
            txtAnio.isEnabled = true
            txtAsientos.isEnabled = true
            txtColor.isEnabled = true
            lyActions.visibility = View.VISIBLE
        }

        btnCancelar.setOnClickListener {
            txtCarroId.setText("")
            txtMarca.setText("")
            txtModelo.setText("")
            txtAnio.setText("")
            txtAsientos.setText("")
            txtColor.setText("")
            txtCarroId.isEnabled = false
            txtMarca.isEnabled = false
            txtModelo.isEnabled = false
            txtAnio.isEnabled = false
            txtAsientos.isEnabled = false
            txtColor.isEnabled = false
            lyActions.visibility = View.INVISIBLE
        }

        val extras = intent.extras
        if (extras != null) {
            carroId = extras.getInt("carroId")
//            Toast.makeText(this, "carroId ${carroId}", Toast.LENGTH_LONG).show()
            CoroutineScope(Dispatchers.IO).launch {
                val retrofit =
                    CarroRetrofit.getRetrofit().create(CarroService::class.java)
                        .getCarro(carroId)
                val data = retrofit.body()
                runOnUiThread {
//                    Toast.makeText(this@DetailDocenteActivity, "${data!!}", Toast.LENGTH_LONG).show()
                    if (retrofit.isSuccessful) {
                        txtCarroId.setText(data!!.carroId.toString())
                        txtMarca.setText(data!!.marca)
                        txtModelo.setText(data!!.modelo)
                        txtAnio.setText(data!!.creationYear.toString())
                        txtAsientos.setText(data!!.asientos.toString())
                        txtColor.setText(data!!.color)
                    }
                }
            }

            btnDelete.setOnClickListener {

                val alertDialog = AlertDialog.Builder(this)

                alertDialog.setMessage("¿Estás seguro que quieres eliminar el carro con ID ${carroId}?")
                    .setCancelable(false)
                    .setPositiveButton(
                        "Si",
                        DialogInterface.OnClickListener { dialogInterface, i ->
                            CoroutineScope(Dispatchers.IO).launch {
                                val retrofit =
                                    CarroRetrofit.getRetrofit().create(CarroService::class.java)
                                        .deleteCarro(carroId)
                                val data = retrofit.body()
                                runOnUiThread {
                                    val intent =
                                        Intent(
                                            this@DetailCarroActivity,
                                            ListaCarroActivity::class.java
                                        )
                                    Toast.makeText(
                                        this@DetailCarroActivity,
                                        "Carro eliminado correctamente",
                                        Toast.LENGTH_SHORT
                                    ).show()
                                    startActivity(intent)
                                }
                            }

                        }

                    )
                    .setNegativeButton("No",
                        DialogInterface.OnClickListener { dialogInterface, i ->
                            dialogInterface.cancel()
                        })
                alertDialog.create().show()
            }
        }

        btnGuardar.setOnClickListener {

            val carro = Carro(
                carroId = txtCarroId.text.toString().trim().toInt(),
                marca = txtMarca.text.toString().trim(),
                modelo = txtModelo.text.toString().trim(),
                creationYear = txtAnio.text.toString().trim().toInt(),
                asientos = txtAsientos.text.toString().trim().toInt(),
                color = txtColor.text.toString().trim(),
            )

            val alertDialog = AlertDialog.Builder(this)
            alertDialog.setMessage("¿Estás seguro que quieres actualizar el carro ${carro.marca} ${carro.modelo}?")
                .setCancelable(false)
                .setPositiveButton(
                    "Si",
                    DialogInterface.OnClickListener { dialogInterface, i ->
                        CoroutineScope(Dispatchers.IO).launch {
                            val retrofit =
                                CarroRetrofit.getRetrofit().create(CarroService::class.java)
                                    .saveCarro(carro)
                            val data = retrofit.body()
                            runOnUiThread {
                                val intent =
                                    Intent(
                                        this@DetailCarroActivity,
                                        ListaCarroActivity::class.java
                                    )
                                Toast.makeText(
                                    this@DetailCarroActivity,
                                    "Carro actualizado correctamente",
                                    Toast.LENGTH_SHORT
                                ).show()
                                startActivity(intent)
                            }
                        }

                    }

                )
                .setNegativeButton("No",
                    DialogInterface.OnClickListener { dialogInterface, i ->
                        dialogInterface.cancel()
                    })
            alertDialog.create().show()
        }

    }

}
