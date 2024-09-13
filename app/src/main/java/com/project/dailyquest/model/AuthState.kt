package com.project.dailyquest.model

data class AuthState(
    val status: Status = Status.IDLE,
    val msg: String? = null
) {
    sealed class Status {

        data object IDLE: Status()

        data object LOADING: Status()

        data class COMPLETE(val result: Result): Status() {
            sealed class Result {

                data object SUCCESS: Result()

                data class ERROR(val exception: Exception): Result()
            }
        }
    }

    companion object {
        fun loading(msg: String? = null) = AuthState(status = Status.LOADING, msg)

        fun success(msg: String? = null) = AuthState(status = Status.COMPLETE(
            result = Status.COMPLETE.Result.SUCCESS), msg)

        fun error(exception: Exception, msg: String? = null) = AuthState(status = Status.COMPLETE(
            result = Status.COMPLETE.Result.ERROR(exception)), msg)
    }
}
