import com.beust.klaxon.Json
import id.walt.vclib.model.*
import id.walt.vclib.registry.VerifiableCredentialMetadata
import id.walt.vclib.schema.SchemaService.PropertyName
import id.walt.vclib.schema.SchemaService.Required

data class PacketDeliveryService(
    @Json(name = "@context") @field:PropertyName(name = "@context") @field:Required
    var context: List<String> = listOf("https://www.w3.org/2018/credentials/v1"),
    @Json(serializeNull = false) override var id: String? = null,
    @Json(serializeNull = false) override var issuer: String? = null,
    @Json(serializeNull = false) override var issued: String? = null,
    @Json(serializeNull = false) override var validFrom: String? = null,
    @Json(serializeNull = false) override var expirationDate: String? = null,
    override var credentialSubject: PacketDeliveryServiceSubject? = null,
    @Json(serializeNull = false) var credentialStatus: CredentialStatus? = null,
    override var credentialSchema: CredentialSchema? = null,
    @Json(serializeNull = false) override var proof: Proof? = null
) : AbstractVerifiableCredential<PacketDeliveryService.PacketDeliveryServiceSubject>(type) {
    companion object : VerifiableCredentialMetadata(
        type = listOf("VerifiableCredential", "PacketDeliveryService"),
        template = {
            PacketDeliveryService(
                id = "urn:uuid:3add94f4-28ec-42a1-8704-4e4aa51006b4",
                issuer = "did:ebsi:2A9BZ9SUe6BatacSpvs1V5CdjHvLpQ7bEsi2Jb6LdHKnQxaN",
                issued = "2021-08-31T00:00:00Z",
                validFrom = "2021-08-31T00:00:00Z",
                credentialSubject = PacketDeliveryServiceSubject(
                    id = "did:ebsi:2AEMAqXWKYMu1JHPAgGcga4dxu7ThgfgN95VyJBJGZbSJUtp",
                    familyName = "Ruiz",
                    firstName = "Jesus",
                    roles = listOf(
                        Role(target = "did:elsi:packetdelivery", names = listOf("P.Info.gold"))
                    )

                ),
                credentialSchema = CredentialSchema(
                    id = "https://raw.githubusercontent.com/hesusruiz/dsbamvf/main/schemas/PacketDeliveryService/2022-10/schema.json",
                    type = "FullJsonSchemaValidator2021"
                )
            )
        }
    )

    data class PacketDeliveryServiceSubject(
        @Json(serializeNull = false) override var id: String? = null,
        var familyName: String? = null,
        var firstName: String? = null,
        var roles: List<Role> = listOf(),
    ) : CredentialSubject() {

        data class Identifier(
            var schemeID: String,
            var value: String
        )
    }

    data class Role(
        var target: String,
        var names: List<String?>,
    )

    override fun newId(id: String) = "urn:uuid:${id}"
}
