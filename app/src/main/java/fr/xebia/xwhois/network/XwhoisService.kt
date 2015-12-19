package fr.xebia.xwhois.network

import fr.xebia.xwhois.person.PersonWrapper
import retrofit.Call
import retrofit.http.GET

interface XwhoisService {

    @GET("api/all")
    fun getXebians(): Call<PersonWrapper>

}