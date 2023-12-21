package com.makesoftware.siga.data

import androidx.compose.ui.text.style.TextAlign
import com.makesoftware.siga.ui.commons.components.DataGridColumnProperties
import com.makesoftware.siga.ui.commons.components.DataGridRowContent
import kotlinx.serialization.Serializable

@Serializable
data class Teacher(
    val user: User = User(),
    val urlCurriculoLattes: String = "",
) : DataGridView {
    override fun toDataGridView(): DataGridRowContent {
        return DataGridRowContent(
            listOf(
                this.user.firstName
            )
        )
    }

    companion object {
        val columns: List<DataGridColumnProperties> = listOf(
            DataGridColumnProperties("Nome", 2F, TextAlign.Start),
            DataGridColumnProperties("Nível", 1.5F, TextAlign.Center),
            DataGridColumnProperties("Idade", 0.7F, TextAlign.Center),
        )
    }
}

@Serializable
data class Course(
    val name: String = "",
    val description: String = "",
    val semestersAmount: Int = 0,
    val semestersToFinish: Int = 0,
) : DataGridView {

    override fun toDataGridView(): DataGridRowContent {
        return DataGridRowContent(
            listOf(
                this.name, this.description
            )
        )
    }

    companion object {
        val columns: List<DataGridColumnProperties> = listOf(
            DataGridColumnProperties("Nome", 2F, TextAlign.Start),
            DataGridColumnProperties("Descrição", 1F, TextAlign.Center),
        )
    }
}

@Serializable
data class Student(
    val name: String = "",
) : DataGridView {

    override fun toDataGridView(): DataGridRowContent {
        return DataGridRowContent(
            listOf(
                this.name
            )
        )
    }

    companion object {
        val columns: List<DataGridColumnProperties> = listOf(
            DataGridColumnProperties("Nome", 2F, TextAlign.Start),
            DataGridColumnProperties("Curso", 1F, TextAlign.Left),
            DataGridColumnProperties("Semestre", 1F, TextAlign.Center)
        )
    }
}

@Serializable
data class Subject(
    val name: String = "",
    val description: String = "",
    val workload: Int = 0,
    val defaultSemester: Int = 0,
) : DataGridView {


    override fun toDataGridView(): DataGridRowContent {
        return DataGridRowContent(
            listOf(
                this.name, this.workload.toString(), this.defaultSemester.toString()
            )
        )
    }

    companion object {
        val columns: List<DataGridColumnProperties> = listOf(
            DataGridColumnProperties("Nome", 2F, TextAlign.Start),
            DataGridColumnProperties("Carga hor.", 1F, TextAlign.Center),
            DataGridColumnProperties("Semestre", 1F, TextAlign.Center),
        )
    }
}

@Serializable
data class User(
    val id: Long = 0,
    val firstName: String = "",
    val lastName: String = "",
    val cpf: String = "",
    val email: String = "",
    val password: String = "",
)

interface DataGridView {
    fun toDataGridView(): DataGridRowContent
}