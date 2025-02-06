package sk.oracle.secrets.fn;

import java.io.IOException;
import java.security.*;
import java.security.interfaces.RSAPublicKey;
import java.util.Base64;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.oracle.bmc.auth.AbstractAuthenticationDetailsProvider;
import com.oracle.bmc.auth.ResourcePrincipalAuthenticationDetailsProvider;
import com.oracle.bmc.vault.VaultsClient;
import com.oracle.bmc.vault.model.*;
import com.oracle.bmc.vault.requests.*;
import com.oracle.bmc.vault.responses.*;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class SecretsFunction {
    private static final Logger log = LoggerFactory.getLogger(SecretsFunction.class);

    SecretsFnInput input;

    // Create an ObjectMapper instance
    ObjectMapper objectMapper = new ObjectMapper();

    public String handleRequest(String jsonInput) {
        ResourcePrincipalAuthenticationDetailsProvider provider = ResourcePrincipalAuthenticationDetailsProvider.builder().build();
        KeyPair keyPair;
        String output;
        log.info("### BEGIN FUNCTION ###");

        // Parse the JSON into the Java object
        try {
            input = objectMapper.readValue(jsonInput, SecretsFnInput.class);
            log.info("@@@ Input parsed.");
            input.validate();
            log.info("@@@ Input validated.");
            log.debug("Input: {}", input);
            keyPair = generateKeyPair();
            log.info("@@@ Key pair generated.");
            output = createSecret(provider, keyPair);
            log.info("@@@ Secret created.");
        } catch (NoSuchAlgorithmException | IOException | IllegalArgumentException e) {
            log.error("Error occurred: {}", e.getMessage());
            return prepareError(e.getMessage());
        }

        log.info("### END FUNCTION ###");
        return output;
    }

    public KeyPair generateKeyPair() throws NoSuchAlgorithmException {
        // Generate SSH key pair
        KeyPairGenerator keyGen = KeyPairGenerator.getInstance("RSA");
        keyGen.initialize(2048);

        // Get the private key
        return keyGen.generateKeyPair();

    }

    public byte[] convertPrivateKeyToPem(PrivateKey privateKey) {
        // Base64 encode the private key
        String base64EncodedKey = Base64.getEncoder().encodeToString(privateKey.getEncoded());

        // Add PEM format headers and footers
        StringBuilder pem = new StringBuilder();
        pem.append("-----BEGIN PRIVATE KEY-----\n");

        int chunkSize = 64; // Split into 64-character lines
        for (int i = 0; i < base64EncodedKey.length(); i += chunkSize) {
            int endIndex = Math.min(base64EncodedKey.length(), i + chunkSize);
            pem.append(base64EncodedKey, i, endIndex).append("\n");
        }

        pem.append("-----END PRIVATE KEY-----");
        return pem.toString().getBytes();
    }

    public String convertPublicKeyToOpenSSH(PublicKey publicKey) throws IOException {
        if (!(publicKey instanceof RSAPublicKey rsaPublicKey)) {
            throw new IllegalArgumentException("Only RSA public keys are supported");
        }

        byte[] exponent = rsaPublicKey.getPublicExponent().toByteArray();
        byte[] modulus = rsaPublicKey.getModulus().toByteArray();

        // Prefix the OpenSSH "ssh-rsa" identifier
        String keyType = "ssh-rsa";

        // Combine key components as per SSH format (length-prefixed fields)
        byte[] sshPublicKey = createSSHKey(modulus, exponent);

        // Base64 encode and construct in OpenSSH format
        return keyType + " " + Base64.getEncoder().encodeToString(sshPublicKey);
    }

    private byte[] createSSHKey(byte[] modulus, byte[] exponent) throws IOException {
        // Length-prefixed format: (length)(data)(length)(data)
        try (java.io.ByteArrayOutputStream out = new java.io.ByteArrayOutputStream()) {
            out.write(encodeLengthAndString("ssh-rsa".getBytes()));
            out.write(encodeLengthAndString(exponent));
            out.write(encodeLengthAndString(modulus));
            return out.toByteArray();
        }
    }

    private byte[] encodeLengthAndString(byte[] bytes) throws IOException {
        try (java.io.ByteArrayOutputStream out = new java.io.ByteArrayOutputStream()) {
            // Write the 4-byte length field
            out.write(new byte[]{
                    (byte) (bytes.length >> 24),
                    (byte) (bytes.length >> 16),
                    (byte) (bytes.length >> 8),
                    (byte) (bytes.length)
            });
            out.write(bytes); // Write data
            return out.toByteArray();
        }
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
                            .content(Base64.getEncoder().encodeToString(convertPrivateKeyToPem(keyPair.getPrivate())))
                            .build())
                    .secretName(input.getKeyPairName() + "_private_key")
                    .vaultId(input.getVaultId())
                    .enableAutoGeneration(false)
                    .build();

            CreateSecretRequest createSecretRequest = CreateSecretRequest.builder()
                    .createSecretDetails(createSecretDetails)
                    .build();
            CreateSecretResponse response = client.createSecret(createSecretRequest);
            secretOcid = response.getSecret().getId();
        }

        SecretsFnOutput jsonData = new SecretsFnOutput();
        jsonData.setStatus("success");
        jsonData.setMessage("all good");
        jsonData.setPrivateKeySecretName(input.getKeyPairName() + "_private_key");
        jsonData.setPrivateKeySecretOcid(secretOcid);
        jsonData.setPublicKeySecretName(input.getKeyPairName() + "_public_key");
        jsonData.setPublicKeySecretValue(convertPublicKeyToOpenSSH(keyPair.getPublic()));

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