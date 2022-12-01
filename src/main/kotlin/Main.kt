
import id.walt.auditor.AuditorRestAPI
import id.walt.auditor.PolicyRegistry
import id.walt.servicematrix.ServiceMatrix
import id.walt.signatory.DataProviderRegistry
import id.walt.signatory.ProofConfig
import id.walt.signatory.SignatoryDataProvider
import id.walt.signatory.rest.SignatoryRestAPI
import id.walt.vclib.model.VerifiableCredential
import id.walt.vclib.registry.VcTypeRegistry
import java.util.*

import id.walt.rest.core.CoreAPI
import id.walt.rest.custodian.CustodianAPI
import id.walt.rest.essif.EssifAPI

fun main(){
    customDataRest()
}

fun customDataRest() {
    // Load walt.id SSI-Kit services from "$workingDirectory/service-matrix.properties"
    ServiceMatrix("service-matrix.properties")

    // Register custom data provider
    DataProviderRegistry.register(JesusCredential::class, CustomDataProvider())

    // Registering custom verification policy
    PolicyRegistry.register(MyCustomPolicy::class, "My custom policy")

    // Registering a custom Credential Template
    VcTypeRegistry.register(JesusCredential.Companion, JesusCredential::class)
    VcTypeRegistry.register(PacketDeliveryService.Companion, PacketDeliveryService::class)

    // Starting REST Services
    val bindAddress = "0.0.0.0"
    CoreAPI.start(7000, bindAddress)
    SignatoryRestAPI.start(7001, bindAddress)
    AuditorRestAPI.start(7002, bindAddress)
    CustodianAPI.start(7003, bindAddress)
    EssifAPI.start(7010, bindAddress)

    println(" walt.id Core API: http://${bindAddress}:7000")
    println(" walt.id Signatory API: http://${bindAddress}:7001")
    println(" walt.id Auditor API:   http://${bindAddress}:7002")
    println(" walt.id Custodian API:   http://${bindAddress}:7003")
    println(" walt.id ESSIF API:   http://${bindAddress}:7010")
}

// Custom Data Provider
class CustomDataProvider : SignatoryDataProvider {
    override fun populate(template: VerifiableCredential, proofConfig: ProofConfig): VerifiableCredential {
        if (template is JesusCredential) {
            template.apply {
                id = "identity#verifiableID#${UUID.randomUUID()}"
                issuer = proofConfig.issuerDid
                credentialSubject?.apply {
                    givenName = "John"
                    birthDate = "1958-08-17"
                }
            }
            return template
        } else {
            throw IllegalArgumentException("Only VerifiableId is supported by this data provider")
        }
    }
}
