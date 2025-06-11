package sk.oracle.usergroup.fn;

import java.io.IOException;
import java.security.*;
import java.util.*;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.oracle.bmc.auth.AbstractAuthenticationDetailsProvider;
import com.oracle.bmc.auth.ResourcePrincipalAuthenticationDetailsProvider;
import com.oracle.bmc.vault.VaultsClient;
import com.oracle.bmc.vault.model.*;
import com.oracle.bmc.vault.requests.*;
import com.oracle.bmc.vault.responses.*;

import com.oracle.bmc.identitydomains.IdentityDomainsClient;
import com.oracle.bmc.identitydomains.model.*;
import com.oracle.bmc.identitydomains.requests.*;
import com.oracle.bmc.identitydomains.responses.*;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class UserGroupFunction {
    private static final Logger log = LoggerFactory.getLogger(UserGroupFunction.class);

    UserGroupFnInput input;

    // Create an ObjectMapper instance
    ObjectMapper objectMapper = new ObjectMapper();

    public String handleRequest(String jsonInput) {
        String output;
        ResourcePrincipalAuthenticationDetailsProvider provider = ResourcePrincipalAuthenticationDetailsProvider.builder().build();
        CreateUserResponse userResponse;
        CreateGroupResponse groupResponse;

        log.info("### BEGIN FUNCTION ###");

        // Parse the JSON into the Java object
        try {
            input = objectMapper.readValue(jsonInput, UserGroupFnInput.class);
            log.info("@@@ Input parsed.");
            input.validate();
            log.info("@@@ Input validated.");
            log.debug("Input: {}", input);

            /* Create a service client */
//            try (IdentityDomainsClient client = IdentityDomainsClient.builder().endpoint("https://idcs-84e74f972de24d9c96f1f5560503cd22.identity.oraclecloud.com:443").build(provider)) {
            try (IdentityDomainsClient client = IdentityDomainsClient.builder().endpoint(input.getDomainEndpoint()).build(provider)) {
                userResponse = createUser(client);
                groupResponse = createGroup(client, userResponse);
            }
        } catch (IOException | IllegalArgumentException e) {
            log.error("Error occurred: {}", e.getMessage());
            return prepareError(e.getMessage());
        }

        UserGroupFnOutput jsonData = new UserGroupFnOutput();
        jsonData.setStatus("success");
        jsonData.setMessage("all good");
        jsonData.setUserOcid(userResponse.getUser().getOcid() + "/" + groupResponse.getGroup().getOcid());

        // Serialize the object into a pretty JSON string
        try {
            output = objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(jsonData);
        } catch (JsonProcessingException e) {
            log.error("JPE: {}", e.getMessage());
            output = prepareError(e.getMessage());
        }


        log.info("### END FUNCTION ###");
        return output;
//        return "{ \"result\": \"OK\" }";
    }

    private CreateUserResponse createUser(IdentityDomainsClient client) {
        User user = User.builder()
                .schemas(new ArrayList<>(List.of("urn:ietf:params:scim:schemas:core:2.0:User")))
                .urnIetfParamsScimSchemasOracleIdcsExtensionOciTags(ExtensionOCITags.builder()
                        .definedTags(Collections.singletonList(
                                DefinedTags.builder()
                                        .namespace(input.getTagKey())
                                        .key(input.getTagKey())
                                        .value(input.getTagValue())
                                        .build()
                        ))
                        .build()
                )
                .domainOcid(input.getDomainOcid()) // idd-roxolid-cloud
//                .domainOcid("ocid1.domain.oc1..aaaaaaaawp6br3ewvpfjowx3rs56amqrzmvipkds4vx3gtntcbcqtcbz6ita") // idd-roxolid-cloud
                .compartmentOcid(input.getCompartmentId())
                .userName(input.getUserName())
                .description("Fn created user")
                .displayName(input.getUserName())
                .active(true)
                .password("Platne/Heslo!#1")
                .name(UserName.builder()
                        .formatted("John Doe")
                        .familyName("Doe")
                        .givenName("John")
                        .build())
                .emails(new ArrayList<>(Collections.singletonList(UserEmails.builder()
                        .value("johndoe@mailinator.com")
                        .type(UserEmails.Type.Work)
                        .primary(true)
                        .secondary(false)
                        .verified(true)
                        .build())))
                .build();

        CreateUserRequest createUserRequest = CreateUserRequest.builder()
                .user(user)
                .build();

        return client.createUser(createUserRequest);
    }

    private CreateGroupResponse createGroup(IdentityDomainsClient client, CreateUserResponse user) {
        Group group = Group.builder()
                .schemas(new ArrayList<>(Arrays.asList(
                        "urn:ietf:params:scim:schemas:core:2.0:Group",
                        "urn:ietf:params:scim:schemas:oracle:idcs:extension:group:Group"
                )))
                .domainOcid(input.getDomainOcid()) // idd-roxolid-cloud
//                .domainOcid("ocid1.domain.oc1..aaaaaaaawp6br3ewvpfjowx3rs56amqrzmvipkds4vx3gtntcbcqtcbz6ita") // idd-roxolid-cloud
                .compartmentOcid(input.getCompartmentId())
                .displayName("BucketGroup")
                .members(new ArrayList<>(Collections.singletonList(GroupMembers.builder()
                        .ocid(user.getUser().getOcid())
                        .build()))).build();

        CreateGroupRequest createGroupRequest = CreateGroupRequest.builder()
                .group(group)
                .build();

        return client.createGroup(createGroupRequest);
    }

    private String createSecret(AbstractAuthenticationDetailsProvider provider, KeyPair keyPair) throws IOException {
        String output;
        String secretOcid;
        try (VaultsClient client = VaultsClient.builder().build(provider)) {
            CreateSecretDetails createSecretDetails = CreateSecretDetails.builder()
                    .compartmentId(input.getCompartmentId())
                    .description(input.getDescription())
                    .keyId(input.getKeyId())
//                    .metadata(new HashMap<>() {
//                        {
//                            put("EXAMPLE_KEY_4mm1j", "EXAMPLE--Value");
//                        }
//                    })
                    .secretContent(Base64SecretContentDetails.builder()
                            .content(Base64.getEncoder().encodeToString("".getBytes()))
                            .build())
                    .secretName(input.getUserName() + "_private_key")
                    .vaultId(input.getVaultId())
                    .enableAutoGeneration(false)
                    .build();

            CreateSecretRequest createSecretRequest = CreateSecretRequest.builder()
                    .createSecretDetails(createSecretDetails)
                    .build();
            CreateSecretResponse response = client.createSecret(createSecretRequest);
            secretOcid = response.getSecret().getId();
        }

        UserGroupFnOutput jsonData = new UserGroupFnOutput();
        jsonData.setStatus("success");
        jsonData.setMessage("all good");
        jsonData.setPrivateKeySecretName(input.getUserName() + "_private_key");
        jsonData.setPrivateKeySecretOcid(secretOcid);
        jsonData.setPublicKeySecretName(input.getUserName() + "_public_key");
        jsonData.setPublicKeySecretValue("");

        // Serialize the object into a pretty JSON string
        try {
            output = objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(jsonData);
        } catch (JsonProcessingException e) {
            log.error("JPE: {}", e.getMessage());
            output = prepareError(e.getMessage());
        }

        return output;
    }

    public String prepareError(String message) {
        String output = """
                {
                    "status": "error",
                    "message": "%s",
                    "keyPairName": ""
                }
                """;
        return String.format(output, message);
    }

}