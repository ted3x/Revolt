package chat.revolt.app.network

import android.util.Log

class HttpLoggerImpl(private val debugMode: Boolean) : HttpLogger {

    override fun logMessage(name: String, message: String?) {
        if(debugMode)
            Log.d("HTTP Message ------>>>>>>$name", message ?: "")
    }

    override fun logException(name: String, exception: Exception) {
        if(debugMode)
            Log.e("HTTP Message ------>>>>>>", name, exception)
    }

}