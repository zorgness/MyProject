package com.example.myproject.network

object ApiRoutes {

    const val BASE_URL = "http://10.0.2.2:8000"
    const val REGISTER = "/api/users"
    const val LOGIN = "/api/login"
    const val CATEGORY = "/api/categories"
    const val ACTIVITY_BY_CATEGORY = "/api/activity_events/"
    const val ACTIVITY_EVENT_BY_ID = "/api/activity_events/custom/{activityEventId}"
    const val USERPROFILE = "/api/users/profile/{userId}"

}