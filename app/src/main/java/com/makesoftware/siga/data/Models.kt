package com.makesoftware.siga.data

import androidx.compose.ui.text.style.TextAlign
import com.makesoftware.siga.ui.commons.components.DataGridColumnProperties
import com.makesoftware.siga.ui.commons.components.DataGridRowContent

data class Teacher(
    val name: String,
    val cpf: String? = null, // TODO: Make the age and degree optional, but the cpf and email not.
    val email: String? = null,
    val age: Int,
    val degree: String,
    val isUpdate: Boolean = false,
) : DataGridView {
    override fun toDataGridView(): DataGridRowContent {
        return DataGridRowContent(
            listOf(
                this.name, this.degree, this.age.toString()
            )
        )
    }
}

data class Course(
    val name: String,
    val acronym: String,
    val description: String = "",
    val semestersAmount: Int = 0,
    val semestersToFinish: Int = 0,
    val isUpdate: Boolean = false,
) : DataGridView {

    override fun toDataGridView(): DataGridRowContent {
        return DataGridRowContent(
            listOf(
                this.name, this.acronym
            )
        )
    }
}

data class Student(
    val name: String = "",
    val isUpdate: Boolean = false,
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

interface DataGridView {
    fun toDataGridView(): DataGridRowContent
}