package com.makesoftware.siga.network

import com.makesoftware.siga.data.datasources.Course

interface SIGAApi {
    suspend fun fetchCourses(): List<Course>

}
