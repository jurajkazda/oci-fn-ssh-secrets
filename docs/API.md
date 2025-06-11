# API used

## Maven coordinates

```xml
        <dependency>
            <groupId>com.oracle.oci.sdk</groupId>
            <artifactId>oci-java-sdk-vault</artifactId>
        </dependency>
        <dependency>
            <groupId>com.oracle.oci.sdk</groupId>
            <artifactId>oci-java-sdk-secrets</artifactId>
        </dependency>
        <dependency>
            <groupId>com.oracle.oci.sdk</groupId>
            <artifactId>oci-java-sdk-identitydomains</artifactId>
        </dependency>
        <dependency>
            <groupId>com.oracle.oci.sdk</groupId>
            <artifactId>oci-java-sdk-common-httpclient-jersey</artifactId>
            <exclusions>
                <exclusion>
                    <groupId>org.slf4j</groupId>
                    <artifactId>slf4j-api</artifactId>
                </exclusion>
            </exclusions>
        </dependency>
```

## Packages imports

```java
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
```

## Invocations

### IdentityClient

```java

```

### UserRequest

```java
    private CreateUserResponse createUser(IdentityDomainsClient client) {
        String output;
        /* Create a request and dependent object(s). */
        User user = User.builder()
//                .id("ocid1.test.oc1..<unique_ID>EXAMPLE-id-Value")
//                .ocid("EXAMPLE-ocid-Value")
                .schemas(new ArrayList<>(Arrays.asList("urn:ietf:params:scim:schemas:core:2.0:User")))
//                .meta(Meta.builder()
//                        .resourceType("EXAMPLE-resourceType-Value")
//                        .created("EXAMPLE-created-Value")
//                        .lastModified("EXAMPLE-lastModified-Value")
//                        .location("EXAMPLE-location-Value")
//                        .version("EXAMPLE-version-Value").build())
//                .idcsCreatedBy(IdcsCreatedBy.builder()
//                        .value("EXAMPLE-value-Value")
//                        .ref("EXAMPLE-$ref-Value")
//                        .type(IdcsCreatedBy.Type.App)
//                        .display("EXAMPLE-display-Value")
//                        .ocid("EXAMPLE-ocid-Value").build())
//                .idcsLastModifiedBy(IdcsLastModifiedBy.builder()
//                        .value("EXAMPLE-value-Value")
//                        .ref("EXAMPLE-$ref-Value")
//                        .type(IdcsLastModifiedBy.Type.App)
//                        .display("EXAMPLE-display-Value")
//                        .ocid("EXAMPLE-ocid-Value").build())
//                .idcsPreventedOperations(new ArrayList<>(Arrays.asList(IdcsPreventedOperations.Delete)))
                .tags(new ArrayList<>(Arrays.asList(Tags.builder()
                        .key(input.getTagName())
                        .value(input.getTagValue()).build())))
                .urnIetfParamsScimSchemasOracleIdcsExtensionOciTags(ExtensionOCITags.builder()
                        .freeformTags(Collections.singletonList(
                                FreeformTags.builder()
                                        .key("FreeformTagKey")
                                        .value("FreeformTagValue")
                                        .build()
                        ))
                        .definedTags(Collections.singletonList(
                                DefinedTags.builder()
                                        .namespace("MainTags")
                                        .key(input.getTagName())
                                        .value(input.getTagValue())
                                        .build()
                        ))
                        .build()
                )
//                .deleteInProgress(false)
//                .idcsLastUpgradedInRelease("EXAMPLE-idcsLastUpgradedInRelease-Value")
                .domainOcid("ocid1.domain.oc1..aaaaaaaawp6br3ewvpfjowx3rs56amqrzmvipkds4vx3gtntcbcqtcbz6ita") // idd-roxolid-cloud
                .compartmentOcid(input.getCompartmentId())
//                .tenancyOcid("EXAMPLE-tenancyOcid-Value")
//                .externalId("ocid1.test.oc1..<unique_ID>EXAMPLE-externalId-Value")
                .userName(input.getUserName())
                .description("Fn created user")
                .displayName(input.getUserName())
//                .nickName("EXAMPLE-nickName-Value")
//                .profileUrl("EXAMPLE-profileUrl-Value")
//                .title("EXAMPLE-title-Value")
//                .userType(User.UserType.Temp)
//                .locale("EXAMPLE-locale-Value")
//                .preferredLanguage("EXAMPLE-preferredLanguage-Value")
//                .timezone("EXAMPLE-timezone-Value")
                .active(true)
                .password("Platne/Heslo!#1")
                .name(UserName.builder()
                        .formatted("John Doe")
                        .familyName("Doe")
                        .givenName("John")
                        .build())
                .emails(new ArrayList<>(Arrays.asList(UserEmails.builder()
                        .value("johndoe@mailinator.com")
                        .type(UserEmails.Type.Work)
                        .primary(true)
                        .secondary(false)
                        .verified(true)
                        .build())))
//                .phoneNumbers(new ArrayList<>(Arrays.asList(UserPhoneNumbers.builder()
//                        .value("EXAMPLE-value-Value")
//                        .display("EXAMPLE-display-Value")
//                        .type(UserPhoneNumbers.Type.Pager)
//                        .primary(false)
//                        .verified(true).build())))
//                .ims(new ArrayList<>(Arrays.asList(UserIms.builder()
//                        .value("EXAMPLE-value-Value")
//                        .display("EXAMPLE-display-Value")
//                        .type(UserIms.Type.Gtalk)
//                        .primary(true).build())))
//                .photos(new ArrayList<>(Arrays.asList(UserPhotos.builder()
//                        .value("EXAMPLE-value-Value")
//                        .display("EXAMPLE-display-Value")
//                        .type(UserPhotos.Type.Thumbnail)
//                        .primary(false).build())))
//                .addresses(new ArrayList<>(Arrays.asList(Addresses.builder()
//                        .formatted("EXAMPLE-formatted-Value")
//                        .streetAddress("EXAMPLE-streetAddress-Value")
//                        .locality("EXAMPLE-locality-Value")
//                        .region("EXAMPLE-region-Value")
//                        .postalCode("EXAMPLE-postalCode-Value")
//                        .country("EXAMPLE-country-Value")
//                        .type(Addresses.Type.Work)
//                        .primary(false).build())))
//                .groups(new ArrayList<>(Arrays.asList(UserGroups.builder()
//                        .value("EXAMPLE-value-Value")
//                        .ocid("EXAMPLE-ocid-Value")
//                        .ref("EXAMPLE-$ref-Value")
//                        .display("EXAMPLE-display-Value")
//                        .nonUniqueDisplay("EXAMPLE-nonUniqueDisplay-Value")
//                        .externalId("ocid1.test.oc1..<unique_ID>EXAMPLE-externalId-Value")
//                        .type(UserGroups.Type.Direct)
//                        .membershipOcid("EXAMPLE-membershipOcid-Value")
//                        .dateAdded("EXAMPLE-dateAdded-Value").build())))
//                .entitlements(new ArrayList<>(Arrays.asList(UserEntitlements.builder()
//                        .value("EXAMPLE-value-Value")
//                        .display("EXAMPLE-display-Value")
//                        .type("EXAMPLE-type-Value")
//                        .primary(false).build())))
//                .roles(new ArrayList<>(Arrays.asList(UserRoles.builder()
//                        .value("EXAMPLE-value-Value")
//                        .display("EXAMPLE-display-Value")
//                        .type("EXAMPLE-type-Value")
//                        .primary(true).build())))
//                .x509Certificates(new ArrayList<>(Arrays.asList(UserX509Certificates.builder()
//                        .value("EXAMPLE-value-Value")
//                        .display("EXAMPLE-display-Value")
//                        .type("EXAMPLE-type-Value")
//                        .primary(false).build())))
                .build();

        CreateUserRequest createUserRequest = CreateUserRequest.builder()
//                .authorization("EXAMPLE-authorization-Value")
//                .resourceTypeSchemaVersion("EXAMPLE-resourceTypeSchemaVersion-Value")
//                .attributes("EXAMPLE-attributes-Value")
//                .attributeSets(new ArrayList<>(Arrays.asList(AttributeSets.Always)))
                .user(user)
//                .opcRetryToken("EXAMPLE-opcRetryToken-Value")
                .build();

        /* Send request to the Client */
        CreateUserResponse response = client.createUser(createUserRequest);

        return response;
    }

```

### GroupRequest

```java
    private CreateGroupResponse createGroup(IdentityDomainsClient client, CreateUserResponse user) {

        /* Create a request and dependent object(s). */
        Group group = Group.builder()
//                .id("ocid1.test.oc1..<unique_ID>EXAMPLE-id-Value")
//                .ocid("EXAMPLE-ocid-Value")
                .schemas(new ArrayList<>(Arrays.asList(
                        "urn:ietf:params:scim:schemas:core:2.0:Group",
                        "urn:ietf:params:scim:schemas:oracle:idcs:extension:group:Group"
                )))
//                .meta(Meta.builder()
//                        .resourceType("EXAMPLE-resourceType-Value")
//                        .created("EXAMPLE-created-Value")
//                        .lastModified("EXAMPLE-lastModified-Value")
//                        .location("EXAMPLE-location-Value")
//                        .version("EXAMPLE-version-Value").build())
//                .idcsCreatedBy(IdcsCreatedBy.builder()
//                        .value("EXAMPLE-value-Value")
//                        .ref("EXAMPLE-$ref-Value")
//                        .type(IdcsCreatedBy.Type.User)
//                        .display("EXAMPLE-display-Value")
//                        .ocid("EXAMPLE-ocid-Value").build())
//                .idcsLastModifiedBy(IdcsLastModifiedBy.builder()
//                        .value("EXAMPLE-value-Value")
//                        .ref("EXAMPLE-$ref-Value")
//                        .type(IdcsLastModifiedBy.Type.App)
//                        .display("EXAMPLE-display-Value")
//                        .ocid("EXAMPLE-ocid-Value").build())
//                .idcsPreventedOperations(new ArrayList<>(Arrays.asList(IdcsPreventedOperations.Delete)))
//                .tags(new ArrayList<>(Arrays.asList(Tags.builder()
//                        .key("EXAMPLE-key-Value")
//                        .value("EXAMPLE-value-Value").build())))
//                .deleteInProgress(true)
//                .idcsLastUpgradedInRelease("EXAMPLE-idcsLastUpgradedInRelease-Value")
                .domainOcid("ocid1.domain.oc1..aaaaaaaawp6br3ewvpfjowx3rs56amqrzmvipkds4vx3gtntcbcqtcbz6ita") // idd-roxolid-cloud
                .compartmentOcid(input.getCompartmentId())
//                .tenancyOcid("EXAMPLE-tenancyOcid-Value")
//                .externalId("ocid1.test.oc1..<unique_ID>EXAMPLE-externalId-Value")
                .displayName("BucketGroup")
//                .nonUniqueDisplayName("EXAMPLE-nonUniqueDisplayName-Value")
                .members(new ArrayList<>(Arrays.asList(GroupMembers.builder()
//                        .value("EXAMPLE-value-Value")
//                        .dateAdded("EXAMPLE-dateAdded-Value")
                        .ocid(user.getUser().getOcid())
//                        .membershipOcid("EXAMPLE-membershipOcid-Value")
//                        .ref("EXAMPLE-$ref-Value")
//                        .display("EXAMPLE-display-Value")
//                        .type(GroupMembers.Type.User)
//                        .name("EXAMPLE-name-Value")
                        .build()))).build();

        CreateGroupRequest createGroupRequest = CreateGroupRequest.builder()
//                .authorization("EXAMPLE-authorization-Value")
//                .resourceTypeSchemaVersion("EXAMPLE-resourceTypeSchemaVersion-Value")
//                .attributes("EXAMPLE-attributes-Value")
//                .attributeSets(new ArrayList<>(Arrays.asList(AttributeSets.Never)))
                .group(group)
//                .opcRetryToken("EXAMPLE-opcRetryToken-Value")
                .build();

        /* Send request to the Client */
        CreateGroupResponse response = client.createGroup(createGroupRequest);

        return response;
    }
```