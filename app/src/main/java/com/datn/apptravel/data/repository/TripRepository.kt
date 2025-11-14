package com.datn.apptravel.data.repository

import com.datn.apptravel.data.api.TripApiService
import com.datn.apptravel.data.model.Plan
import com.datn.apptravel.data.model.Trip
import com.datn.apptravel.data.model.request.*

class TripRepository(private val tripApiService: TripApiService) {
    
    suspend fun createTrip(request: CreateTripRequest): Result<Trip> {
        return try {
            val response = tripApiService.createTrip(request)
            if (response.isSuccessful && response.body()?.success == true) {
                val trip = response.body()?.data
                if (trip != null) {
                    Result.success(trip)
                } else {
                    Result.failure(Exception("Trip data is null"))
                }
            } else {
                val errorMessage = response.body()?.message ?: "Failed to create trip"
                Result.failure(Exception(errorMessage))
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
    
    suspend fun getTripById(tripId: String): Result<Trip> {
        return try {
            val response = tripApiService.getTripById(tripId)
            if (response.isSuccessful && response.body()?.success == true) {
                val trip = response.body()?.data
                if (trip != null) {
                    Result.success(trip)
                } else {
                    Result.failure(Exception("Trip not found"))
                }
            } else {
                val errorMessage = response.body()?.message ?: "Failed to get trip"
                Result.failure(Exception(errorMessage))
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
    
    suspend fun getTripsByUserId(userId: String): Result<List<Trip>> {
        return try {
            val response = tripApiService.getTripsByUserId(userId)
            if (response.isSuccessful) {
                val trips = response.body() ?: emptyList()
                Result.success(trips)
            } else {
                Result.failure(Exception("Failed to get trips"))
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
    
    suspend fun updateTrip(tripId: String, request: CreateTripRequest): Result<Trip> {
        return try {
            val response = tripApiService.updateTrip(tripId, request)
            if (response.isSuccessful && response.body()?.success == true) {
                val trip = response.body()?.data
                if (trip != null) {
                    Result.success(trip)
                } else {
                    Result.failure(Exception("Trip data is null"))
                }
            } else {
                val errorMessage = response.body()?.message ?: "Failed to update trip"
                Result.failure(Exception(errorMessage))
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
    
    suspend fun deleteTrip(tripId: String): Result<Unit> {
        return try {
            val response = tripApiService.deleteTrip(tripId)
            if (response.isSuccessful) {
                Result.success(Unit)
            } else {
                Result.failure(Exception("Failed to delete trip"))
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
    
    // Plan methods
//    suspend fun createPlan(tripId: String, request: CreatePlanRequest): Result<Plan> {
//        return try {
//            val response = tripApiService.createPlan(tripId, request)
//            if (response.isSuccessful) {
//                val plan = response.body()
//                if (plan != null) {
//                    Result.success(plan)
//                } else {
//                    Result.failure(Exception("Plan data is null"))
//                }
//            } else {
//                Result.failure(Exception("Failed to create plan"))
//            }
//        } catch (e: Exception) {
//            Result.failure(e)
//        }
//    }
    
    suspend fun createFlightPlan(tripId: String, request: CreateFlightPlanRequest): Result<Plan> {
        return try {
            val response = tripApiService.createFlightPlan(tripId, request)
            if (response.isSuccessful) {
                val plan = response.body()
                if (plan != null) {
                    Result.success(plan)
                } else {
                    Result.failure(Exception("Flight plan data is null"))
                }
            } else {
                Result.failure(Exception("Failed to create flight plan"))
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
    
    suspend fun createRestaurantPlan(tripId: String, request: CreateRestaurantPlanRequest): Result<Plan> {
        return try {
            val response = tripApiService.createRestaurantPlan(tripId, request)
            if (response.isSuccessful) {
                val plan = response.body()
                if (plan != null) {
                    Result.success(plan)
                } else {
                    Result.failure(Exception("Restaurant plan data is null"))
                }
            } else {
                Result.failure(Exception("Failed to create restaurant plan"))
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
    
    suspend fun createLodgingPlan(tripId: String, request: CreateLodgingPlanRequest): Result<Plan> {
        return try {
            val response = tripApiService.createLodgingPlan(tripId, request)
            if (response.isSuccessful) {
                val plan = response.body()
                if (plan != null) {
                    Result.success(plan)
                } else {
                    Result.failure(Exception("Lodging plan data is null"))
                }
            } else {
                Result.failure(Exception("Failed to create lodging plan"))
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
    
    suspend fun createActivityPlan(tripId: String, request: CreateActivityPlanRequest): Result<Plan> {
        return try {
            val response = tripApiService.createActivityPlan(tripId, request)
            if (response.isSuccessful) {
                val plan = response.body()
                if (plan != null) {
                    Result.success(plan)
                } else {
                    Result.failure(Exception("Activity plan data is null"))
                }
            } else {
                Result.failure(Exception("Failed to create activity plan"))
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
    
    suspend fun createBoatPlan(tripId: String, request: CreateBoatPlanRequest): Result<Plan> {
        return try {
            val response = tripApiService.createBoatPlan(tripId, request)
            if (response.isSuccessful) {
                val plan = response.body()
                if (plan != null) {
                    Result.success(plan)
                } else {
                    Result.failure(Exception("Boat plan data is null"))
                }
            } else {
                Result.failure(Exception("Failed to create boat plan"))
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
    
    suspend fun createCarRentalPlan(tripId: String, request: CreateCarRentalPlanRequest): Result<Plan> {
        return try {
            val response = tripApiService.createCarRentalPlan(tripId, request)
            if (response.isSuccessful) {
                val plan = response.body()
                if (plan != null) {
                    Result.success(plan)
                } else {
                    Result.failure(Exception("Car rental plan data is null"))
                }
            } else {
                Result.failure(Exception("Failed to create car rental plan"))
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
    
    suspend fun getPlansByTripId(tripId: String): Result<List<Plan>> {
        return try {
            val response = tripApiService.getPlansByTripId(tripId)
            if (response.isSuccessful) {
                val plans = response.body() ?: emptyList()
                Result.success(plans)
            } else {
                Result.failure(Exception("Failed to get plans"))
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}
