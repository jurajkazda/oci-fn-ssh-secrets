package sk.oracle.helpers;

import com.oracle.bmc.ConfigFileReader;
import com.oracle.bmc.auth.AuthenticationDetailsProvider;
import com.oracle.bmc.auth.ConfigFileAuthenticationDetailsProvider;
import com.oracle.bmc.identitydomains.IdentityDomainsClient;
import com.oracle.bmc.identitydomains.model.Tags;
import com.oracle.bmc.identitydomains.model.User;
import com.oracle.bmc.identitydomains.requests.CreateUserRequest;
import com.oracle.bmc.identitydomains.responses.CreateUserResponse;
import com.oracle.bmc.objectstorage.ObjectStorageClient;
import com.oracle.bmc.objectstorage.requests.GetNamespaceRequest;
import com.oracle.bmc.objectstorage.responses.GetNamespaceResponse;

import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.Arrays;

public class ObjectStorageNamespace {

    /* Create a service client */

    public static void main(String[] args) throws NoSuchAlgorithmException, IOException {
        ConfigFileReader.ConfigFile configFile = ConfigFileReader.parseDefault("AFP");
        AuthenticationDetailsProvider provider = new ConfigFileAuthenticationDetailsProvider(configFile);

        try (ObjectStorageClient client = ObjectStorageClient.builder().build(provider)) {
            GetNamespaceRequest getNamespaceRequest = GetNamespaceRequest.builder()
                    .compartmentId("ocid1.compartment.oc1..aaaaaaaappd5di5sx5kr4s7gcdkc7mlbicrn3ydkkjhjc4zyhcc4u3hdqxqa").build();

            /* Send request to the Client */
            GetNamespaceResponse response = client.getNamespace(getNamespaceRequest);
            System.out.println("R: " + response.getValue());
        } catch (Exception e) {
            System.out.println("E: " + e.getMessage());
        }
        System.out.println("DONE.");
    }
}
