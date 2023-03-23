package response

sealed interface Response {
    data class Result<T>(val value: T) : Response {
        override fun toString(): String = value.toString()
    }

    object Success : Response {
        override fun toString() = "success"
    }

    object Failure : Response {
        override fun toString() = "failure"
    }
}