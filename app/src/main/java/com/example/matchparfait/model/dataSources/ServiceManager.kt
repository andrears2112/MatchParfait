package com.example.matchparfait.model.dataSources

import android.content.Context
import android.os.Build
import com.example.matchparfait.R
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.RequestBody
import okhttp3.Response
import okio.Buffer
import org.json.JSONObject
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.io.IOException
import java.security.cert.X509Certificate
import javax.net.ssl.HostnameVerifier
import javax.net.ssl.SSLContext
import javax.net.ssl.SSLSession
import javax.net.ssl.X509TrustManager
import kotlin.jvm.internal.Intrinsics

public class Wrapper<T>(
    var tokenSession : String = "",
    var userMsg : String = "",
    var data : List<T> =  listOf()
)

public interface ResultInterface<Wrapper> {
    fun success(body:retrofit2.Response<Wrapper>){}
    fun successWithList(body:retrofit2.Response<Wrapper>){}
    fun notFound(message:String){}
    fun failWithError(message:String) {}
}

fun <T> CheckObjectResult(wrapper:Wrapper<T>): Boolean{
    return wrapper.data.isNotEmpty()
}

public class ServiceResponse<Wrapper, T>(){
    fun GetRequestForList(call: Call<Wrapper>, res:ResultInterface<Wrapper>){
        call.enqueue(object: Callback<Wrapper> {
            override fun onFailure(call: Call<Wrapper>, t: Throwable) {
                res.failWithError(t.message.toString())
            }
            override fun onResponse(call: Call<Wrapper>, response: retrofit2.Response<Wrapper>) {
                if (response.code() == 404){
                    res.notFound("NOT_FOUND")
                }
                if (response.code() == 401){
                    res.failWithError("Usuario o contraseña incorrecta")
                }
                if (response.code() == 400){
                    res.failWithError("BAD_REQUEST")
                }
                if (response.code() == 200) {
                    res.successWithList(response)
                }
            }
        })
    }
    fun GetRequestForObject(call: Call<Wrapper>, res:ResultInterface<Wrapper>){
        call.enqueue(object: Callback<Wrapper> {
            override fun onFailure(call: Call<Wrapper>, t: Throwable) {
                if (t.cause.toString().uppercase().contains("NETWORK") || t.cause.toString().uppercase().contains("HOST")) {
                    res.failWithError("Ocurrió un error, favor de revisar tus ajustes de comunicación a internet.")
                    return
                }
                res.failWithError(t.message.toString())
            }
            override fun onResponse(call: Call<Wrapper>, response: retrofit2.Response<Wrapper>) {
                if (response.code() == 404){
                    res.notFound("NOT_FOUND")
                }
                if (response.code() == 401){
                    res.failWithError("Usuario o contraseña incorrecta")
                }
                if (response.code() == 400){
                    res.failWithError("BAD_REQUEST")
                }
                if (response.code() == 200) {
                    res.success(response)
                }
            }
        })
    }
}

class AppServiceClient(context: Context) {
    var tempContext = context
    fun GetDefaultConnectionWithServices(): Retrofit {
        val retrofit = Retrofit.Builder()
            .baseUrl(this.tempContext.getString(R.string.app_url))
            .addConverterFactory(GsonConverterFactory.create())
            .client(
                OkHttpClient.Builder()
                    .sslSocketFactory(
                        SSLContext.getDefault().socketFactory,
                        TrustAllCerts()
                    )
                    .connectTimeout(60L, java.util.concurrent.TimeUnit.SECONDS)
                    .readTimeout(300L, java.util.concurrent.TimeUnit.SECONDS)
                    .writeTimeout(300L, java.util.concurrent.TimeUnit.SECONDS)
                    .hostnameVerifier { _, _ -> true }
                    .build()
            )
            .build()
        return retrofit
    }
}

class ServiceManager {
    companion object {
        fun ConnectWithAPI(): Retrofit {
            val retrofit = Retrofit.Builder()
                .baseUrl("https://024wgz7p-3000.usw3.devtunnels.ms")
                .addConverterFactory(GsonConverterFactory.create())
                .client(
                    OkHttpClient.Builder()
                        .sslSocketFactory(
                            SSLContext.getDefault().socketFactory,
                            TrustAllCerts()
                        )
                        .connectTimeout(60L, java.util.concurrent.TimeUnit.SECONDS)
                        .readTimeout(300L, java.util.concurrent.TimeUnit.SECONDS)
                        .writeTimeout(300L, java.util.concurrent.TimeUnit.SECONDS)
                        .hostnameVerifier { _, _ -> true }
                        .build()
                )
                .build()
            return retrofit
        }

        private fun getSignedRequest(obj: String): JSONObject {
            for (i in 0 until obj.length) {
                val x = obj.substring(i)
            }
            return JSONObject(obj)
        }

        fun bodyToString(request: Request): String {
            return try {
                val copy: Request = request.newBuilder().build()
                val buffer = Buffer()
                val body: RequestBody? = copy.body
                Intrinsics.checkNotNull(body)
                body!!.writeTo(buffer)
                buffer.readUtf8()
            } catch (e: IOException) {
                "did not work"
            }
        }
    }

}

private class TrustAllCerts : X509TrustManager, HostnameVerifier {
    override fun checkClientTrusted(chain: Array<out X509Certificate>?, authType: String?) {}

    override fun checkServerTrusted(chain: Array<out X509Certificate>?, authType: String?) {}

    override fun getAcceptedIssuers(): Array<X509Certificate> = emptyArray()

    override fun verify(hostname: String?, session: SSLSession?): Boolean = true
}
