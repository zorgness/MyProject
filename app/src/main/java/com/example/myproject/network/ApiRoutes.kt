package com.example.myproject.network

object ApiRoutes {

    const val BASE_URL = "http://10.0.2.2:8000"
    //const val BASE_URL = "https://project-dev-id-backend.herokuapp.com"
    const val REGISTER = "/api/users"
    const val UPDATE = "/api/users/{userId}"
    const val LOGIN = "/api/login"
    const val CATEGORY = "/api/categories"
    const val ACTIVITY_EVENT = "/api/activity_events"
    const val ACTIVITY_EVENT_DELETE = "/api/activity_events/{activityId}"
    const val ACTIVITY_EVENT_BY_ID = "/api/activity_events/custom/{activityEventId}"
    const val PROFILE = "/api/profile/{userId}"
    const val BOOKING = "/api/bookings"

}