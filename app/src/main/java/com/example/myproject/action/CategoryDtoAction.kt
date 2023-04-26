import android.util.Log
import com.example.myproject.dataclass.CategoryDto
import com.example.myproject.dataclass.GetCategoriesDto
import com.example.myproject.network.ApiService
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

/*
fun getRemoteCategories(categoriesDtoCallback: (List<CategoryDto>) -> Unit) {
    val call: Call<GetCategoriesDto>? = ApiService.getApi().getAllCategories()
    call?.enqueue(object : Callback<GetCategoriesDto> {
        override fun onResponse(call: Call<GetCategoriesDto>, response: Response<GetCategoriesDto>) {

            response.body()?.let {

                    categoriesDtoCallback.invoke(it.categories)
            }
        }

        override fun onFailure(call: Call<GetCategoriesDto>, t: Throwable) {
            Log.e("failure", t.message ?: "lol")
        }
    })
}*/
