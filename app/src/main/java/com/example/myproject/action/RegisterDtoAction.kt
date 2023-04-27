import com.example.myproject.dataclass.RegisterInfo
import com.example.myproject.dataclass.UserDto
import com.example.myproject.network.ApiService
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

fun register(registerInfo: RegisterInfo, loginCallback: (UserDto) -> Unit, errorCallback: (String?)->Unit) {
    var call: Call<UserDto>? = ApiService.getApi().register(registerInfo)
    call?.enqueue(object : Callback<UserDto> {
        override fun onResponse(call: Call<UserDto>, response: Response<UserDto>) {
            response.body()?.let {
                loginCallback(it)
            }
        }

        override fun onFailure(call: Call<UserDto>, t: Throwable) {
            errorCallback.invoke(t.message)
        }

    })
}