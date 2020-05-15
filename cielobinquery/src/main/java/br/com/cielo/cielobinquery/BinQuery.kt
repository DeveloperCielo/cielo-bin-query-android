package br.com.cielo.cielobinquery

import br.com.braspag.cieloecommerceoauth.network.HttpCredentialsClient
import br.com.cielo.cielobinquery.internal.network.BinQueryClient
import br.com.braspag.cieloecommerceoauth.network.Environment as OAuthEnvironment

class BinQuery(
    private val merchantId: String,
    private val clientId: String,
    private val clientSecret: String,
    private val environment: Environment = Environment.SANDBOX
) {
    fun query(
        bin: String,
        callback: (model: ClientResult<BinQueryResponse>) -> Unit
    ) {
        val oAuthEnvironment = if (environment == Environment.PRODUCTION) {
            OAuthEnvironment.PRODUCTION
        } else {
            OAuthEnvironment.SANDBOX
        }

        val oAuthClient = HttpCredentialsClient(
            oAuthEnvironment,
            clientId,
            clientSecret
        )

        oAuthClient.getOAuthCredentials({ accessToken ->
            val binQueryClient = BinQueryClient(this.environment, merchantId)

            binQueryClient.query(bin, accessToken.token, callback)
        }, {
            callback.invoke(
                ClientResult(
                    result = null,
                    statusCode = HttpStatusCode.Unknown,
                    errors = listOf(
                        ErrorResponse(code = null, message = it)
                    )
                )
            )
        })
    }
}