package com.makesoftware.siga.network

import com.makesoftware.siga.data.Course
import com.makesoftware.siga.data.Teacher
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

    override suspend fun fetchTeachers(): List<Teacher> {
        return withContext(Dispatchers.IO) {
            delay(5000)

            listOf(
                Teacher("Humberto", degree = "Faixa preta", age = 25),
                Teacher("Rodrigo Amorim", degree = "Doutor", age = 45),
                Teacher("Viotti", degree = "Mestre", age = 35),
            )
        }
    }
}