package com.koby.blazepodskotlin.util


data class RemoteResult<out T>(val status: Status, val data: T?, val message: String?) {

    companion object {

        fun <T> success(data: T?): RemoteResult<T> {
            return RemoteResult(Status.SUCCESS, data, null)
        }

        fun <T> error(msg: String): RemoteResult<T> {
            return RemoteResult(Status.ERROR,null, msg)
        }

        fun <T> loading(msg: String):RemoteResult<T>{
            return RemoteResult(Status.LOADING,null,msg)
        }
    }

    enum class Status {
        SUCCESS,
        ERROR,
        LOADING
    }
}