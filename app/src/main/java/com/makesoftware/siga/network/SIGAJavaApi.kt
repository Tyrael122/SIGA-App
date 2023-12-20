package com.makesoftware.siga.network

import com.makesoftware.siga.data.Course
import com.makesoftware.siga.data.Student
import com.makesoftware.siga.data.Teacher
import com.makesoftware.siga.data.User
import kotlinx.coroutines.delay

class SIGAJavaApi : SIGAApi {
    override suspend fun fetchCourses(): List<Course> {
        delay(5000)

        return listOf(
            Course("Análise e Desenvolvimento de Sistemas", "ADS"),
            Course("Ciência da Computação", "CC"),
            Course("Engenharia de Software", "ES"),
            Course("Sistemas de Informação", "SI"),
        )
    }

    override suspend fun fetchTeachers(): List<Teacher> {
        delay(5000)

        return listOf(
//            Teacher("Humberto", degree = "Faixa preta", age = 25),
//            Teacher("Rodrigo Amorim", degree = "Doutor", age = 45),
//            Teacher("Viotti", degree = "Mestre", age = 35),
        )
    }

    override suspend fun fetchStudent(): List<Student> {
        delay(5000)

        return listOf(
            Student("João"),
            Student("Maria"),
            Student("José"),
            Student("Pedro"),
            Student("Ana"),
            Student("Paulo"),
            Student("Lucas"),
            Student("Luciana"),
            Student("Mariana"),
            Student("Joaquim"),
            Student("Joaquina"),
            Student("Joaquino"),
            Student("Joaquim"),
            Student("Joaquina"),
            Student("Joaquino"),
            Student("Joaquim"),
            Student("Joaquina"),
            Student("Joaquino"),
            Student("Joaquim"),
            Student("Joaquina"),
            Student("Joaquino"),
            Student("Joaquim"),
            Student("Joaquina"),
            Student("Joaquino"),
            Student("Joaquim"),
            Student("Joaquina"),
            Student("Joaquino"),
        )
    }

    override suspend fun postTeacher(userId: Long, teacher: Teacher) {
        TODO("Not yet implemented")
    }

    override suspend fun postUser(user: User): User {
        TODO("Not yet implemented")
    }
}