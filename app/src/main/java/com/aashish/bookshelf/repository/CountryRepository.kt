package com.aashish.bookshelf.repository

interface CountryRepository {
    suspend fun getCountryList(): List<String>
    suspend fun getCountryFromIpGeoLocation(): String?
}