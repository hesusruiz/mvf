
import com.beust.klaxon.Json
import id.walt.common.prettyPrint
import JesusCredential.JesusCredentialSubject
import id.walt.vclib.model.*
import id.walt.vclib.registry.VcTypeRegistry
import id.walt.vclib.registry.VerifiableCredentialMetadata

fun main(){
    customCredential()
}

fun customCredential() {
    // Registering a custom credential template
    VcTypeRegistry.register(JesusCredential.Companion, JesusCredential::class)

    // Creating custom credential and set the data to be issued
    val myCustomCredential = JesusCredential(
        credentialSubject = JesusCredential.JesusCredentialSubject(
            id = "did:example:subject-did",
            type = listOf(
                "Vendor",
                "Person"
            ),
            givenName = "SUSAN",
            birthDate = "2002-02-20"
        ),
        issuer = "did:example:issuer-did"
    )

    println("This is my custom credential: $myCustomCredential")

    // Encoding a custom credential to JSON
    val encodedCredential = myCustomCredential.encode()
    println("Encoding credential ...")
    println(encodedCredential.prettyPrint())

    // Decoding a JSON credential
    println("Decoding credential ...")
    val decodedCredential: VerifiableCredential = encodedCredential.toCredential()
    println(decodedCredential)

    // Check if Mister John is a Vendor
    val (givenName, isVendor) = checkIfVendor(decodedCredential)

    println("Mister \"$givenName\" ${if (isVendor == true) "is" else "isn't"} a vendor.")

    // Check if Mister James is a Vendor
    myCustomCredential.credentialSubject!!.givenName = "JAMES"
    myCustomCredential.credentialSubject!!.type = listOf("Person")

    val someInput = myCustomCredential.encode()

    val decodedCredential2 = someInput.toCredential()

    val (givenName2, isVendor2) = checkIfVendor(decodedCredential2)

    println("Mister \"$givenName2\" ${if (isVendor2 == true) "is" else "isn't"} a vendor.")
}

// This is our custom credential
data class JesusCredential(
    @Json(name = "@context")
    var context: List<String> = listOf("https://www.w3.org/2018/credentials/v1"),
    override var id: String? = null,
    override var issuer: String?,
    @Json(serializeNull = false) override var issued: String? = null,
    @Json(serializeNull = false) override var validFrom: String? = null,
    @Json(serializeNull = false) override var expirationDate: String? = null,
    @Json(serializeNull = false) override var credentialSubject: JesusCredentialSubject?,
    @Json(serializeNull = false) override var credentialSchema: CredentialSchema? = null,
    @Json(serializeNull = false) override var proof: Proof? = null,
) : AbstractVerifiableCredential<JesusCredentialSubject>(type) {
    data class JesusCredentialSubject(
        @Json(serializeNull = false) override var id: String? = null,
        @Json(serializeNull = false) var type: List<String>? = null,
        @Json(serializeNull = false) var givenName: String? = null,
        @Json(serializeNull = false) var birthDate: String? = null
    ) : CredentialSubject()

    // The following is the default-data, which can be substituted when issuing the credential.
    companion object : VerifiableCredentialMetadata(
        type = listOf("VerifiableCredential", "JesusCredential"),
        template = {
            JesusCredential(
                credentialSubject = JesusCredentialSubject(
                    id = "did:example:123",
                    type = listOf(
                        "Vendor",
                        "Person"
                    ),
                    givenName = "JOHN",
                    birthDate = "1958-08-17"
                ),
                issuer = "did:example:456"
            )
        }
    )
}

fun checkIfVendor(decodedCredential: VerifiableCredential): List<Any> = when (decodedCredential) {
    is JesusCredential -> {
        val subject: JesusCredential.JesusCredentialSubject = decodedCredential.credentialSubject!!

        listOf(subject.givenName!!, subject.type!!.contains("Vendor"))
    }
    else -> throw Error("Invalid credential was supplied!")
}



