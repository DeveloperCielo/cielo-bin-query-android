# Cielo Bin Query [![Status](https://travis-ci.com/DeveloperCielo/cielo-bin-query.svg?branch=master)](https://travis-ci.com/DeveloperCielo/cielo-bin-query) [![Download](https://api.bintray.com/packages/braspag/cielo-bin-query/cielo-bin-query/images/download.svg)](https://bintray.com/braspag/cielo-bin-query/cielo-bin-query/_latestVersion)

**Cielo Bin Query auxilia ao realizar consultas de bins.**


## Instalação

### Dependências

Adicione esta dependência ao *build.gradle* do seu módulo dentro do nó dependencies 

```kotlin
dependencies {
    ...
    implementation 'br.com.cielo:cielo-bin-query:1.0.0'
}
```

> Não esqueça de verificar se **jcenter()** está configurado como repositório.

- Ou baixe *cielo-bin-query-release.aar* from [releases](https://github.com/DeveloperCielo/cielo-bin-query/releases), mova para a pasta libs do seu módulo e adicione esta dependência ao *build.gradle* do seu módulo dentro do nó dependencies

```kotlin
dependencies {
    ...
    implementation files('libs/cielo-bin-query-release.aar')
}
```

### Internet Permission

É necessário ter adicionado ao **AndroidManifest.xml** do seu módulo a seguinte permissão:

```kotlin
    <uses-permission android:name="android.permission.INTERNET" />
```

## Utilização

### Inicialização

É necessário ter uma instância de **CieloBinQuery**

```kotlin
        val cieloBinQuery = CieloBinQuery("<MERCHANT-ID>", Environment.SANDBOX)
```

### Consulta

A consulta ocorre através do método **query**, para isto, basta informar o bin do cartão, OAuth Token, um callback de sucesso e um callback de erro:

```kotlin
        cieloBinQuery.query(
                "000000",
                "<OAUTH-TOKEN>",
                {
                    status.text = it.status
                    provider.text = it.provider
                    cardtype.text = it.cardType
                    foreigncard.text = it.foreignCard.toString()
                    corporatecard.text = it.corporateCard.toString()
                    issuer.text = it.issuer
                    issuercode.text = it.issuerCode
                }, {
                    errorMessage.text = it
                }
            )
```