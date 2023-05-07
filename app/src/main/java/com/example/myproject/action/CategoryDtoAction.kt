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
