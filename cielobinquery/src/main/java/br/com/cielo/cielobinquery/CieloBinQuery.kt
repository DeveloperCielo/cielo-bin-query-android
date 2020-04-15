package br.com.cielo.cielobinquery

import br.com.cielo.cielobinquery.internal.network.BinQueryClient

class CieloBinQuery (private val merchantId: String, private val environment: Environment) {
    fun query (
        bin: String, token: String,
        onSuccessCallback: (model: CieloBinQueryResponse) -> Unit,
        onError: (error: String) -> Unit
    ) {

        val binQueryClient = BinQueryClient(this.environment, merchantId)

        binQueryClient.query(
            bin, token,
            {
                onSuccessCallback.invoke(it)
            })
            {
                onError.invoke(it)
            }
    }
}