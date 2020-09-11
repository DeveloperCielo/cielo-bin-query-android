package br.com.cielo.cielobinquery.internal.network

import br.com.cielo.cielobinquery.*
import br.com.cielo.cielobinquery.internal.extensions.beared
import com.google.gson.Gson
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

internal class BinQueryClient (private val environment: Environment, private val merchantId: String) {

    fun query(
        bin: String,
        token: String,
        callback: (model: ClientResult<BinQueryResponse>) -> Unit) {

        val webClient = WebClient(getEnvironmentUrl(environment))

        val xSdkVersion = BuildConfig.X_SDK_VERSION
        val call = webClient.createService(BinQueryApi::class.java).query(bin, token.beared(), merchantId, xSdkVersion)

        call.enqueue(object : Callback<BinQueryResponse> {
            override fun onFailure(call: Call<BinQueryResponse>, t: Throwable) {
                t.localizedMessage?.let {
                    callback.invoke(
                        ClientResult(
                            result = null,
                            statusCode = HttpStatusCode.Unknown,
                            errors = listOf(
                                ErrorResponse(
                                    code = null,
                                    message = it
                                )
                            )
                        )
                    )
                }
            }

            override fun onResponse(
                call: Call<BinQueryResponse>,
                responseCielo: Response<BinQueryResponse>
            ) {
                when (responseCielo.isSuccessful) {
                    true -> {
                        callback.invoke(
                            ClientResult(
                                result = responseCielo.body(),
                                statusCode = responseCielo.code().toStatusCode()
                            )
                        )
                    }

                    false -> callback.invoke(ClientResult(
                        result = null,
                        statusCode = responseCielo.code().toStatusCode(),
                        errors = Gson().fromJson(responseCielo.errorBody()?.string(), Array<ErrorResponse>::class.java).toList()
                    ))
                }
            }
        })
    }

    private fun getEnvironmentUrl(environment: Environment): String {
        return if (environment == Environment.SANDBOX)
            BuildConfig.URL_QUERY_SANDBOX
        else
            BuildConfig.URL_QUERY_PRODUCTION
    }
}