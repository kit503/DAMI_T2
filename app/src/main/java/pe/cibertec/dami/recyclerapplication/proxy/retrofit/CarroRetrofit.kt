package pe.cibertec.dami.recyclerapplication.proxy.retrofit

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class CarroRetrofit {

    companion object {
        fun getRetrofit(): Retrofit {
            return Retrofit.Builder()
                .baseUrl("http://10.0.2.2:8080/renting-car/v1/") // 10.0.2.2 = localhost
                .addConverterFactory(GsonConverterFactory.create())
                .build()
        }
    }

}