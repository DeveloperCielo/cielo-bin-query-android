package br.com.cielo.cielobinquery

import com.google.gson.annotations.SerializedName

data class BinQueryResponse (
    @SerializedName("Status")
    val status: String,

    @SerializedName("Provider")
    val provider: String,

    @SerializedName("CardType")
    val cardType: String,

    @SerializedName("ForeignCard")
    val foreignCard: Boolean,

    @SerializedName("CorporateCard")
    val corporateCard: Boolean,

    @SerializedName("Issuer")
    val issuer: String,

    @SerializedName("IssuerCode")
    val issuerCode: String,

    @SerializedName("PrePaid")
    val prePaid: Boolean
)