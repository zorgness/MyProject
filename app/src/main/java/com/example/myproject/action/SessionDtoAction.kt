import com.example.myproject.dataclass.LoginInfo
import com.example.myproject.dataclass.SessionDto
import com.example.myproject.network.ApiService
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

fun login(loginInfo: LoginInfo, loginCallback: (SessionDto) -> Unit, errorCallback: (String?)->Unit) {
    var call: Call<SessionDto>? = ApiService.getApi().login(loginInfo)
    call?.enqueue(object : Callback<SessionDto> {
        override fun onResponse(call: Call<SessionDto>, response: Response<SessionDto>) {
            response.body()?.let {
                loginCallback(it)
            }
        }

        override fun onFailure(call: Call<SessionDto>, t: Throwable) {
            errorCallback.invoke(t.message)
        }

    })
}