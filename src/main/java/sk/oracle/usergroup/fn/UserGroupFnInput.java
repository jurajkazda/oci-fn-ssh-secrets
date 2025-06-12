package sk.oracle.usergroup.fn;

import com.fasterxml.jackson.annotation.JsonProperty;

public class UserGroupFnInput {
    private static final String DOMAIN_ENDPOINT_DEFAULT = PropertyLoader.getProperty("domain.endpoint");//, "https://idcs-84e74f972de24d9c96f1f5560503cd22.identity.oraclecloud.com:443");
    private static final String DOMAIN_OCID_DEFAULT = PropertyLoader.getProperty("domain.ocid");//, "ocid1.domain.oc1..aaaaaaaawp6br3ewvpfjowx3rs56amqrzmvipkds4vx3gtntcbcqtcbz6ita");

    @JsonProperty(value = "regionCode", required = true)
    private String regionCode;

    @JsonProperty(value = "client", required = true)
    private String client;

    @JsonProperty(value = "environment", required = true)
    private String environment;

    @JsonProperty(value = "appName", required = true)
    private String appName;

    @JsonProperty(value = "tagNamespace", required = true)
    private String tagNamespace;

    @JsonProperty(value = "tagKey", required = true)
    private String tagKey;

    @JsonProperty(value = "tagValue", required = true)
    private String tagValue;

    @JsonProperty(value = "domainEndpoint", required = true)
    private String domainEndpoint;

    @JsonProperty(value = "domainOcid", required = true)
    private String domainOcid;

    @JsonProperty(value = "userName")
    private String userName;

    @JsonProperty(value = "compartmentId", required = true)
    private String compartmentId;

    @JsonProperty(value = "vaultId", required = true)
    private String vaultId;

    @JsonProperty(value = "keyId", required = true)
    private String keyId;

    @JsonProperty("description")
    private String description;

    public String getRegionCode() {
        return regionCode;
    }

    public void setRegionCode(String regionCode) {
        this.regionCode = regionCode;
    }

    public String getClient() {
        return client;
    }

    public void setClient(String client) {
        this.client = client;
    }

    public String getEnvironment() {
        return environment;
    }

    public void setEnvironment(String environment) {
        this.environment = environment;
    }

    public String getAppName() {
        return appName;
    }

    public void setAppName(String appName) {
        this.appName = appName;
    }

    public String getTagNamespace() {
        return tagNamespace;
    }

    public void setTagNamespace(String tagNamespace) {
        this.tagNamespace = tagNamespace;
    }

    public String getTagKey() {
        return tagKey;
    }

    public void setTagKey(String tagKey) {
        this.tagKey = tagKey;
    }

    public String getTagValue() {
        return tagValue;
    }

    public void setTagValue(String tagValue) {
        this.tagValue = tagValue;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
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
        if (isEmpty(regionCode)) {
            throw new IllegalArgumentException("regionCode cannot be null or empty");
        }
        if (isEmpty(client)) {
            throw new IllegalArgumentException("client cannot be null or empty");
        }
        if (isEmpty(environment)) {
            throw new IllegalArgumentException("environment cannot be null or empty");
        }
        if (isEmpty(tagNamespace)) {
            throw new IllegalArgumentException("tagNamespace cannot be null or empty");
        }
        if (isEmpty(tagKey)) {
            throw new IllegalArgumentException("tagKey cannot be null or empty");
        }
        if (isEmpty(tagValue)) {
            throw new IllegalArgumentException("tagValue cannot be null or empty");
        }
        if (isEmpty(domainEndpoint)) {
            throw new IllegalArgumentException("domainEndpoint cannot be null or empty");
        }
        if (isEmpty(domainOcid)) {
            throw new IllegalArgumentException("domainOcid cannot be null or empty");
        }
        if (isEmpty(userName)) {
            throw new IllegalArgumentException("userName cannot be null or empty");
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

    public String getUsername() {
        return regionCode + "-sa-" + client + "-" + environment + "-" + appName;
    }

    public String getGroupname() {
        return regionCode + "-grp-" + client + "-" + environment + "-" + appName;
    }

    public String getDomainEndpoint() {
        return domainEndpoint;
    }

    public void setDomainEndpoint(String domainEndpoint) {
        if (domainEndpoint == null || domainEndpoint.trim().isEmpty()) {
            domainEndpoint = DOMAIN_ENDPOINT_DEFAULT;
        } else
            this.domainEndpoint = domainEndpoint;
    }

    public String getDomainOcid() {
        return domainOcid;
    }

    public void setDomainOcid(String domainOcid) {
        if (domainOcid == null || domainOcid.trim().isEmpty()) {
            domainOcid = DOMAIN_OCID_DEFAULT;
        } else
            this.domainOcid = domainOcid;
    }
}
