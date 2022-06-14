package chat.revolt.app.network

interface HttpLogger {

    fun logMessage(name: String, message: String?)

    fun logException(name: String, exception: Exception)
}