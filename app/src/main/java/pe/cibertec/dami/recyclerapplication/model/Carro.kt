package pe.cibertec.dami.recyclerapplication.model

data class Carro(
    val carroId: Int,
    val marca: String,
    val modelo: String,
    val creationYear: Int,
    val asientos: Int,
    val color: String
)