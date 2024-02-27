package pe.cibertec.dami.recyclerapplication

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.floatingactionbutton.FloatingActionButton
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import pe.cibertec.dami.recyclerapplication.adapter.CarroAdapter
import pe.cibertec.dami.recyclerapplication.proxy.interfaces.CarroService
import pe.cibertec.dami.recyclerapplication.proxy.retrofit.CarroRetrofit
import pe.cibertec.dami.recyclerapplication.util.LoadingDialog

class ListaCarroActivity : AppCompatActivity() {

    private var maxCarroId = 0
    private lateinit var loading: LoadingDialog
    private lateinit var btnAddCarro: FloatingActionButton
    private lateinit var recyclerViewCarro: RecyclerView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_lista_carro)
        loading = LoadingDialog(this)
        loading.startLoading()
        recyclerViewCarro = findViewById(R.id.recyclerCarros)
        btnAddCarro = findViewById(R.id.btnAddCarro)
        setupComponents()
        loadData()
    }

    fun setupComponents() {
        recyclerViewCarro.layoutManager = LinearLayoutManager(this)
        btnAddCarro.setOnClickListener {
            val intent = Intent(this, AddCarroActivity::class.java)
            intent.putExtra("maxCarroId", maxCarroId)
            startActivity(intent)
        }
    }

    fun loadData() {
        CoroutineScope(Dispatchers.IO).launch {
            val retrofit =
                CarroRetrofit.getRetrofit().create(CarroService::class.java)
                    .getCarros()
            val data = retrofit.body()
            runOnUiThread {
                if (retrofit.isSuccessful) {
                    recyclerViewCarro.adapter = CarroAdapter(data!!) {
                        val carroId = it.carroId
                        val intent =
                            Intent(
                                this@ListaCarroActivity,
                                DetailCarroActivity::class.java
                            ).apply {
                                putExtra("carroId", it.carroId)
                            }
                        startActivity(intent)
                    }
                    maxCarroId = data!!.maxOf { it.carroId }
                    loading.finishLoading()
                }
            }
        }
    }

}