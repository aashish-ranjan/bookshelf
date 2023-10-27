package com.aashish.bookshelf.repository

import com.aashish.bookshelf.api.CountryApi
import java.lang.Exception

class CountryRepositoryImpl(private val countryApi: CountryApi) : CountryRepository {
    override suspend fun getCountryList(): List<String> {
        return try {
            val response = countryApi.getCountryList()
            response.takeIf { it.isSuccessful }?.body()?.data?.values?.map { it.country }
                ?: listOf()
        } catch (e: Exception) {
            listOf()
        }
    }

    override suspend fun getCountryFromIpGeoLocation(): String? {
        return try {
            val response = countryApi.getIpGeolocation()
            response.takeIf { it.isSuccessful }?.body()?.country
        } catch (e: Exception) {
            null
        }
    }

}