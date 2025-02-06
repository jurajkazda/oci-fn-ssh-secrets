package sk.oracle.secrets.fn;

import com.fasterxml.jackson.annotation.JsonProperty;

public class SecretsFnInput {
    @JsonProperty(value = "keyPairName", required = true)
    private String keyPairName;

    @JsonProperty(value = "compartmentId", required = true)
    private String compartmentId;

    @JsonProperty(value = "vaultId", required = true)
    private String vaultId;

    @JsonProperty(value = "keyId", required = true)
    private String keyId;

    @JsonProperty("description")
    private String description;

    public String getKeyPairName() {
        return keyPairName;
    }

    public void setKeyPairName(String keyPairName) {
        this.keyPairName = keyPairName;
    }

    public String getCompartmentId() {
        return compartmentId;
    }

    public void setCompartmentId(String compartmentId) {
        this.compartmentId = compartmentId;
    }

    public String getVaultId() {
        return vaultId;
    }

    public void setVaultId(String vaultId) {
        this.vaultId = vaultId;
    }

    public String getKeyId() {
        return keyId;
    }

    public void setKeyId(String keyId) {
        this.keyId = keyId;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void validate() throws IllegalArgumentException {
        if (isEmpty(keyPairName)) {
            throw new IllegalArgumentException("keyPairName cannot be null or empty");
        }
        if (isEmpty(compartmentId)) {
            throw new IllegalArgumentException("compartmentId cannot be null or empty");
        }
        if (isEmpty(vaultId)) {
            throw new IllegalArgumentException("vaultId cannot be null or empty");
        }
        if (isEmpty(keyId)) {
            throw new IllegalArgumentException("keyId cannot be null or empty");
        }
    }

    private boolean isEmpty(String str) {
        return str == null || str.trim().isEmpty();
    }

}
