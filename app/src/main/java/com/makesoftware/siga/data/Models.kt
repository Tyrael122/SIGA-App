package com.makesoftware.siga.data

import com.makesoftware.siga.ui.commons.components.DataGridRowContent

data class Teacher(
    val name: String,
    val cpf: String? = null, // TODO: Make the age and degree optional, but the cpf and email not.
    val email: String? = null,
    val age: Int,
    val degree: String,
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
) : DataGridView {

    override fun toDataGridView(): DataGridRowContent {
        return DataGridRowContent(
            listOf(
                this.name, this.acronym
            )
        )
    }
}

interface DataGridView {
    fun toDataGridView(): DataGridRowContent
}