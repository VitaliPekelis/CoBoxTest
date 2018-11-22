package com.vitali.coboxtestapp

import okhttp3.Interceptor
import okhttp3.Response
import okhttp3.ResponseBody

class LogJsonInterceptor : Interceptor
{

    override fun intercept(chain: Interceptor.Chain?): Response
    {
        val request = chain?.request()
        var response: Response? = null
        var raw = "{empty body}"

        request?.let {
            /*url*/
            if (BuildConfig.DEBUG)
                Logger.logInfo("URL", request.url().toString())

            response = chain.proceed(request)
            raw = response?.body()!!.string()

            /*json*/
            if (BuildConfig.DEBUG)
                Logger.logInfo("JSON", raw)

        }

        // Re-create the response before returning it because body can be read only once
        return response?.newBuilder()!!
                .body(ResponseBody.create(response!!.body()!!.contentType(), raw)).build()
    }
}