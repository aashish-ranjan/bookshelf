package com.aashish.bookshelf.model

data class JsonResponse(val data: Map<String, CountryData>)

data class CountryData(val country: String, val region: String)