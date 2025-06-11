package sk.oracle.usergroup.fn;

import com.fasterxml.jackson.annotation.JsonProperty;

public class UserGroupFnOutput {
    @JsonProperty("status")
    private String status;

    @JsonProperty("message")
    private String message;

    @JsonProperty("userOcid")
    private String userOcid;

    @JsonProperty("keyPairName")
    private String keyPairName;

    @JsonProperty("privateKeySecretName")
    private String privateKeySecretName;

    @JsonProperty("privateKeySecretOcid")
    private String privateKeySecretOcid;

    @JsonProperty("publicKeySecretName")
    private String publicKeySecretName;

    @JsonProperty("publicKeySecretOcid")
    private String publicKeySecretOcid;

    @JsonProperty("publicKeySecretValue")
    private String publicKeySecretValue;

    // Getters and setters
    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getKeyPairName() {
        return keyPairName;
    }

    public void setKeyPairName(String keyPairName) {
        this.keyPairName = keyPairName;
    }

    public String getPrivateKeySecretName() {
        return privateKeySecretName;
    }

    public void setPrivateKeySecretName(String publicKeySecretName) {
        this.privateKeySecretName = publicKeySecretName;
    }

    public String getPrivateKeySecretOcid() {
        return privateKeySecretOcid;
    }

    public void setPrivateKeySecretOcid(String privateKeySecretOcid) {
        this.privateKeySecretOcid = privateKeySecretOcid;
    }

    public String getPublicKeySecretName() {
        return publicKeySecretName;
    }

    public void setPublicKeySecretName(String publicKeySecretName) {
        this.publicKeySecretName = publicKeySecretName;
    }

    public String getPublicKeySecretOcid() {
        return publicKeySecretOcid;
    }

    public void setPublicKeySecretOcid(String publicKeySecretOcid) {
        this.publicKeySecretOcid = publicKeySecretOcid;
    }

    public String getPublicKeySecretValue() {
        return publicKeySecretValue;
    }

    public void setPublicKeySecretValue(String publicKeySecretValue) {
        this.publicKeySecretValue = publicKeySecretValue;
    }

    public String getUserOcid() {
        return userOcid;
    }

    public void setUserOcid(String userOcid) {
        this.userOcid = userOcid;
    }
}
