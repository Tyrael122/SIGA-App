package com.makesoftware.siga.data.datasources

sealed class EndpointPrefixes {
    companion object {
        const val USER: String = "users"
        const val TEACHER: String = "teachers"
        const val SUBJECT: String = "subjects"
    }
}