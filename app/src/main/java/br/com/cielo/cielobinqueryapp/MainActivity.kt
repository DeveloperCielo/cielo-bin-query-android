package br.com.cielo.cielobinqueryapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import br.com.braspag.cieloecommerceoauth.network.Environment as OAuthEnvironment
import br.com.braspag.cieloecommerceoauth.network.HttpCredentialsClient
import br.com.cielo.cielobinquery.CieloBinQuery
import br.com.cielo.cielobinquery.Environment as CieloBinQueryEnvironment
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        getOAuthToken()
    }

    private fun getOAuthToken() {
        val client = HttpCredentialsClient(
            OAuthEnvironment.SANDBOX,
            "<CLIENT-ID>",
            "<CLIENT-SECRET>"
        )

        client.getOAuthCredentials({
            binQuery(it.token)
        },{
            content.visibility = View.INVISIBLE
            progress_bar.visibility = View.INVISIBLE
            errorContent.visibility = View.VISIBLE
            errorMessage.text = it
        })
    }

    private fun binQuery(token: String) {
        CieloBinQuery("<MERCHANT-ID>", CieloBinQueryEnvironment.SANDBOX)
            .query(
                "001040",
                token,
                {
                    status.text = it.status
                    provider.text = it.provider
                    cardtype.text = it.cardType
                    foreigncard.text = it.foreignCard.toString()
                    corporatecard.text = it.corporateCard.toString()
                    issuer.text = it.issuer
                    issuercode.text = it.issuerCode
                    content.visibility = View.VISIBLE
                    progress_bar.visibility = View.INVISIBLE
                }, {
                    content.visibility = View.INVISIBLE
                    progress_bar.visibility = View.INVISIBLE
                    errorContent.visibility = View.VISIBLE
                    errorMessage.text = it
                }
            )
    }
}