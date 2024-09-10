package com.project.dailyquest.model

data class AuthState(
    val status: Status = Status.IDLE,
    val msg: String? = null
) {
    enum class Status {
        IDLE,
        LOADING,
        COMPLETE
    }
}
