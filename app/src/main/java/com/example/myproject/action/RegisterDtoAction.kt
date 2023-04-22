import com.example.myproject.dataclass.LoginInfo
import com.example.myproject.dataclass.RegisterInfo
import com.example.myproject.dataclass.RegisterDto
import com.example.myproject.network.ApiService
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

fun register(registerInfo: RegisterInfo, loginCallback: (RegisterDto) -> Unit, errorCallback: (String?)->Unit) {
    var call: Call<RegisterDto>? = ApiService.getApi().register(registerInfo)
    call?.enqueue(object : Callback<RegisterDto> {
        override fun onResponse(call: Call<RegisterDto>, response: Response<RegisterDto>) {
            response.body()?.let {
                loginCallback(it)
            }
        }

        override fun onFailure(call: Call<RegisterDto>, t: Throwable) {
            errorCallback.invoke(t.message)
        }

    })
}