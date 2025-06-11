package sk.oracle.helpers;

import com.oracle.bmc.ConfigFileReader;
import com.oracle.bmc.auth.AuthenticationDetailsProvider;
import com.oracle.bmc.auth.ConfigFileAuthenticationDetailsProvider;
import com.oracle.bmc.identitydomains.IdentityDomainsClient;
import com.oracle.bmc.identitydomains.model.Tags;
import com.oracle.bmc.identitydomains.model.User;
import com.oracle.bmc.identitydomains.model.UserEmails;
import com.oracle.bmc.identitydomains.model.UserName;
import com.oracle.bmc.identitydomains.requests.CreateUserRequest;
import com.oracle.bmc.identitydomains.responses.CreateUserResponse;
import sk.oracle.usergroup.fn.UserGroupFunction;

import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.Arrays;

public class UserGroup {

    /* Create a service client */

    public static void main(String[] args) throws NoSuchAlgorithmException, IOException {
        ConfigFileReader.ConfigFile configFile = ConfigFileReader.parseDefault("AFP");
        AuthenticationDetailsProvider provider = new ConfigFileAuthenticationDetailsProvider(configFile);

        try (IdentityDomainsClient client = IdentityDomainsClient.builder().endpoint("https://idcs-84e74f972de24d9c96f1f5560503cd22.identity.oraclecloud.com:443").build(provider)) {
            User user = User.builder()
                    .schemas(new ArrayList<>(Arrays.asList("urn:ietf:params:scim:schemas:core:2.0:User")))
                    .tags(new ArrayList<>(Arrays.asList(Tags.builder()
                            .key("userTagName")
                            .value("userTagValue").build())))
                    .domainOcid("ocid1.domain.oc1..aaaaaaaawp6br3ewvpfjowx3rs56amqrzmvipkds4vx3gtntcbcqtcbz6ita") // idd-roxolid-cloud
                    .compartmentOcid("ocid1.compartment.oc1..aaaaaaaappd5di5sx5kr4s7gcdkc7mlbicrn3ydkkjhjc4zyhcc4u3hdqxqa")
                    .userName("janedoe")
                    .name(UserName.builder()
                            .formatted("Jane Doe")
                            .familyName("Doe")
                            .givenName("Jane")
                            .build())
                    .emails(new ArrayList<>(Arrays.asList(UserEmails.builder()
                            .value("janedoe@mailinator.com")
                            .type(UserEmails.Type.Work)
                            .primary(true)
                            .secondary(false)
                            .verified(true)
                            .build())))
                    .description("Fn created user")
                    .displayName("jane doe")
                    .active(true)
                    .password("Platne/Heslo!#1")
                    .build();

            System.out.println("== User object created");
            CreateUserRequest createUserRequest = CreateUserRequest.builder()
//                .authorization("EXAMPLE-authorization-Value")
//                .resourceTypeSchemaVersion("EXAMPLE-resourceTypeSchemaVersion-Value")
//                .attributes("EXAMPLE-attributes-Value")
//                .attributeSets(new ArrayList<>(Arrays.asList(AttributeSets.Always)))
                    .user(user)
//                .opcRetryToken("EXAMPLE-opcRetryToken-Value")
                    .build();
            System.out.println("== Request created");

            /* Send request to the Client */
            CreateUserResponse response = client.createUser(createUserRequest);
            System.out.println("R: " + response.getUser().getId());
        } catch (Exception e) {
            System.out.println("E: " + e.getMessage());
        }
        System.out.println("DONE.");
    }
}
