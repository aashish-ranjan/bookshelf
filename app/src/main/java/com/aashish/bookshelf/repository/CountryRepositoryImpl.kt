package com.aashish.bookshelf.repository

import com.aashish.bookshelf.api.CountryApi
import com.aashish.bookshelf.utils.safeCall
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class CountryRepositoryImpl @Inject constructor(private val countryApi: CountryApi) : CountryRepository {
    override suspend fun getCountryList(): List<String> {
        return safeCall("getCountryList", TAG) {
            val response = countryApi.getCountryList()
            response.takeIf { it.isSuccessful }?.body()?.data?.values?.map { it.country }
        } ?: listOf()
    }

    override suspend fun getCountryFromIpGeoLocation(): String? {
        return safeCall("getCountryFromIpGeoLocation", TAG) {
            val response = countryApi.getIpGeolocation()
            response.takeIf { it.isSuccessful }?.body()?.country
        }
    }

    companion object {
        private const val TAG = "CountryRepositoryImpl"
    }

}