package pe.cibertec.dami.recyclerapplication.proxy.interfaces

import pe.cibertec.dami.recyclerapplication.model.Carro
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.PATCH
import retrofit2.http.POST
import retrofit2.http.Path

interface CarroService {

    @GET("cars")
    // http://10.0.2.2:8080/renting-car/v1/cars
    suspend fun getCarros(): Response<List<Carro>>

    @GET("car/{carroId}")
    // http://10.0.2.2:8080/renting-car/v1/cars/1
    suspend fun getCarro(@Path("carroId") carroId: Int): Response<Carro>

    @POST("car")
    // http://10.0.2.2:8080/renting-car/v1/car
    suspend fun saveCarro(@Body carro: Carro): Response<Carro>

    @DELETE("car/{carroId}")
    // http://10.0.2.2:8080/renting-car/v1/car/1
    suspend fun deleteCarro(@Path("carroId") carroId: Int): Response<Int>

    @PATCH("car")
    // http://10.0.2.2:8080/renting-car/v1/car
    suspend fun updateCarro(@Body carro: Carro): Response<Carro>


}