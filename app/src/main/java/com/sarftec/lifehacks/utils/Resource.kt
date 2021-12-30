package com.sarftec.lifehacks.utils

class Resource <out T> private constructor(
    val data: T? = null,
    val message: String? = null,
    private val status: Status
) {

    fun isSuccess() : Boolean = this.status == Status.SUCCESS

    fun isError() : Boolean = this.status == Status.ERROR

    companion object {
        fun <R> success(data: R) : Resource<R> {
            return Resource(data, status = Status.SUCCESS)
        }

        fun <R> error(message: String) : Resource<R> {
            return Resource(message = message, status = Status.ERROR)
        }
    }

    enum class Status { SUCCESS, ERROR }
}