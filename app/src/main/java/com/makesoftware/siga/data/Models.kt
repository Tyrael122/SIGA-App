package com.makesoftware.siga.data

import androidx.compose.ui.text.style.TextAlign
import com.makesoftware.siga.ui.commons.components.DataGridColumnProperties
import com.makesoftware.siga.ui.commons.components.DataGridRowContent
import kotlinx.serialization.Serializable

@Serializable
data class Teacher(
    val isUpdate: Boolean = false,
    val user: User,
) : DataGridView {
    override fun toDataGridView(): DataGridRowContent {
        return DataGridRowContent(
            listOf(
                this.user.firstName
            )
        )
    }
}

@Serializable
open class User(
    var firstName: String = "",
    var lastName: String = "",
    var cpf: String = "",
    var email: String = "",
    var password: String = "",
)

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