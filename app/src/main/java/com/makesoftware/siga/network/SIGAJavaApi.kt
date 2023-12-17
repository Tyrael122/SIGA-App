package com.makesoftware.siga.network

import com.makesoftware.siga.data.datasources.Course
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.withContext

class SIGAJavaApi : SIGAApi {
    override suspend fun fetchCourses(): List<Course> {
        return withContext(Dispatchers.IO) {
            delay(5000)

            listOf(
                Course("Análise e Desenvolvimento de Sistemas", "ADS"),
                Course("Ciência da Computação", "CC"),
                Course("Engenharia de Software", "ES"),
                Course("Sistemas de Informação", "SI"),
            )
        }
    }
}