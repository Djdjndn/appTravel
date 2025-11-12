package com.datn.apptravel.data.repository

import com.datn.apptravel.BuildConfig
import com.datn.apptravel.data.api.ApiService
import com.datn.apptravel.data.api.NetworkResult
import com.datn.apptravel.data.model.response.MapPlace

class PlacesRepository(private val apiService: ApiService) {
    
    companion object {
        private val API_KEY = BuildConfig.GEOAPIFY_API_KEY
        private const val DEFAULT_RADIUS = 30000
    }
    
    /**
     * Generate sample image URL based on category
     * In production, you would fetch real images from the place API or database
     */
    private fun getSampleImageUrl(category: String, placeName: String): String {
        // Using placeholder image service with category-specific images
        val categoryKey = when {
            category.contains("accommodation") || category.contains("lodging") -> "hotel"
            category.contains("restaurant") || category.contains("catering") -> "food"
            category.contains("tourism") || category.contains("attraction") -> "architecture"
            category.contains("airport") || category.contains("flight") -> "airport"
            category.contains("railway") || category.contains("train") -> "train"
            category.contains("boat") || category.contains("rental") -> "boat"
            category.contains("shopping") || category.contains("mall") -> "shopping"
            category.contains("religion") -> "temple"
            category.contains("theatre") || category.contains("entertainment") -> "theatre"
            else -> "city"
        }
        
        // Using Unsplash Source for placeholder images (you can also use Lorem Picsum)
        return "https://source.unsplash.com/800x600/?$categoryKey,travel"
    }
    
    /**
     * Generate sample description
     * In production, you would fetch real descriptions from the place API or database
     */
    private fun getSampleDescription(placeName: String, address: String?): String {
        return "$placeName is a wonderful place to visit. Located at ${address ?: "a great location"}, " +
                "it offers unique experiences and memorable moments for all visitors."
    }
    
    /**
     * Generate sample gallery images
     * In production, you would fetch real gallery from the place API or database
     */
    private fun getSampleGalleryImages(category: String): List<String> {
        val categoryKey = when {
            category.contains("accommodation") || category.contains("lodging") -> "hotel"
            category.contains("restaurant") || category.contains("catering") -> "food"
            category.contains("tourism") || category.contains("attraction") -> "architecture"
            else -> "travel"
        }
        
        return listOf(
            "https://source.unsplash.com/400x300/?$categoryKey,interior",
            "https://source.unsplash.com/400x300/?$categoryKey,view"
        )
    }

    suspend fun getPlacesByCategory(
        category: String,
        latitude: Double,
        longitude: Double,
        radius: Int = DEFAULT_RADIUS,
        limit: Int = 20
    ): NetworkResult<List<MapPlace>> {
        return try {
            val filter = "circle:$longitude,$latitude,$radius"
            val response = apiService.getPlaces(
                categories = category,
                filter = filter,
                limit = limit,
                apiKey = API_KEY
            )
            
            if (response.isSuccessful && response.body() != null) {
                val places = response.body()!!.features?.mapNotNull { feature ->
                    val properties = feature.properties
                    if (properties?.lat != null && properties.lon != null) {
                        val placeName = properties.name ?: "Unknown"
                        MapPlace(
                            id = properties.placeId ?: "",
                            name = placeName,
                            latitude = properties.lat,
                            longitude = properties.lon,
                            address = properties.formatted ?: properties.addressLine1,
                            category = category,
                            imageUrl = getSampleImageUrl(category, placeName),
                            description = getSampleDescription(placeName, properties.formatted ?: properties.addressLine1),
                            galleryImages = getSampleGalleryImages(category)
                        )
                    } else null
                } ?: emptyList()
                
                NetworkResult.Success(places)
            } else {
                NetworkResult.Error("Failed to fetch places: ${response.message()}")
            }
        } catch (e: Exception) {
            NetworkResult.Error("Network error: ${e.message}")
        }
    }

    suspend fun searchPlaces(
        query: String,
        latitude: Double,
        longitude: Double,
        radius: Int = DEFAULT_RADIUS,
        limit: Int = 20
    ): NetworkResult<List<MapPlace>> {
        return try {
            val filter = "circle:$longitude,$latitude,$radius"
            val response = apiService.searchPlaces(
                text = query,
                filter = filter,
                limit = limit,
                apiKey = API_KEY
            )
            
            if (response.isSuccessful && response.body() != null) {
                val places = response.body()!!.features?.mapNotNull { feature ->
                    val properties = feature.properties
                    if (properties?.lat != null && properties.lon != null) {
                        val placeName = properties.name ?: "Unknown"
                        val categoryName = properties.categories?.firstOrNull() ?: "general"
                        MapPlace(
                            id = properties.placeId ?: "",
                            name = placeName,
                            latitude = properties.lat,
                            longitude = properties.lon,
                            address = properties.formatted ?: properties.addressLine1,
                            category = categoryName,
                            imageUrl = getSampleImageUrl(categoryName, placeName),
                            description = getSampleDescription(placeName, properties.formatted ?: properties.addressLine1),
                            galleryImages = getSampleGalleryImages(categoryName)
                        )
                    } else null
                } ?: emptyList()
                
                NetworkResult.Success(places)
            } else {
                NetworkResult.Error("Search failed: ${response.message()}")
            }
        } catch (e: Exception) {
            NetworkResult.Error("Network error: ${e.message}")
        }
    }
}
