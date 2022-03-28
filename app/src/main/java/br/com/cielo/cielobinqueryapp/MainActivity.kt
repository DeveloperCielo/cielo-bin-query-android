package br.com.cielo.cielobinqueryapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.ArrayAdapter
import br.com.cielo.cielobinquery.BinQuery
import br.com.cielo.cielobinquery.Environment
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val binQuery = BinQuery(
            merchantId = "MERCHANT-ID",
            clientId = "CLIENT-ID",
            clientSecret = "CLIENT-SECRET",
            environment = Environment.SANDBOX
        )

        binQuery.query("001040") {
            Log.d("CIELO_BIN_QUERY", it.toString())

            if (it.result != null) {
                with(it.result!!) {
                    tv_status.text = status
                    tv_provider.text = provider
                    cardtype.text = cardType
                    foreigncard.text = foreignCard.toString()
                    corporatecard.text = corporateCard.toString()
                    tv_issuer.text = issuer
                    issuercode.text = issuerCode
                    tv_prepaid.text = prePaid.toString()
                }
            } else {
                tv_status.text = null
                tv_provider.text = null
                cardtype.text = null
                foreigncard.text = null
                corporatecard.text = null
                tv_issuer.text = null
                issuercode.text = null
                tv_prepaid.text = null
            }

            if (it.errors.isNotEmpty()) {
                list_errors.adapter = ArrayAdapter(
                    this,
                    android.R.layout.simple_list_item_1,
                    android.R.id.text1,
                    it.errors
                )
            }
        }
    }
}