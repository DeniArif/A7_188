
import com.example.tugasakhir.model.Pasien
import com.example.tugasakhir.service.PasienApiService
import okio.IOException

interface PasienRepository{
    suspend fun  getPasien():List<Pasien>

    suspend fun insertPasien(pasien: Pasien)

    suspend fun updatePasien(idpasien: String,pasien: Pasien)

    suspend fun deletePasien(idpasien: String)

    suspend fun getPasienByid(idpasien: String):Pasien
}

class NetworkMahasiswaRepository(
    private val pasienApiService: PasienApiService
) : PasienRepository {
    override suspend fun getPasien(): List<Pasien> =
        pasienApiService.getAllPasien()

    override suspend fun insertPasien(pasien: Pasien) {
        pasienApiService.insertPasien(pasien)
    }

    override suspend fun updatePasien(idpasien: String, pasien: Pasien) {
        pasienApiService.updatePasien(idpasien, pasien)
    }

    override suspend fun deletePasien(idPasien: String) {
        try {
            val response = pasienApiService.deletePasien(idPasien)
            if (!response.isSuccessful) {
                throw IOException(
                    "Failed to delete Pasien. HTTP Status Code: ${response.code()}"
                )
            }else   {
                println(response.message())
            }
        }catch (e: Exception){
            throw e
        }
    }

    override suspend fun getPasienByid(idpasien: String): Pasien {
        return pasienApiService.getPasienById(idpasien)
    }



}