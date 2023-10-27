package com.aashish.bookshelf.api

import com.aashish.bookshelf.model.IpGeolocationResponse
import com.aashish.bookshelf.model.JsonResponse
import com.aashish.bookshelf.utils.Constants.COUNTRY_LIST_COMPLETE_URL
import com.aashish.bookshelf.utils.Constants.IP_GEOLOCATION_COMPLETE_URL
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Url

interface CountryApi {
    @GET
    suspend fun getCountryList(@Url url: String = COUNTRY_LIST_COMPLETE_URL): Response<JsonResponse>

    @GET
    suspend fun getIpGeolocation(@Url url: String = IP_GEOLOCATION_COMPLETE_URL): Response<IpGeolocationResponse>
}