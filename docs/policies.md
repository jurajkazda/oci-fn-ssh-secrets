# Policies

## Working

These are working for creation of a secret:

```
Allow service faas to use apm-domains in tenancy
Allow service faas to read repos in tenancy where request.operation='ListContainerImageSignatures'
Allow service faas to use keys in tenancy
Allow service faas to manage secret-family in tenancy
Allow service faas to use vaults in tenancy
Allow dynamic-group dg-functions-world-private to manage secret-family in tenancy
Allow dynamic-group dg-functions-world-private to manage secrets in tenancy
Allow dynamic-group dg-functions-world-private to manage key-family in tenancy
Allow dynamic-group dg-functions-world-private to manage keys in tenancy
Allow dynamic-group dg-functions-world-private to manage vaults in tenancy
```

## Minimal working

```
Allow service faas to use apm-domains in tenancy
Allow service faas to read repos in tenancy where request.operation='ListContainerImageSignatures'
Allow service faas to {KEY_READ} in tenancy where request.operation='GetKeyVersion'
Allow service faas to {KEY_VERIFY} in tenancy where request.operation='Verify'
Allow dynamic-group dg-functions-world-private to manage secrets in tenancy
Allow dynamic-group dg-functions-world-private to use keys in tenancy
Allow dynamic-group dg-functions-world-private to use vaults in tenancy
```

For some reason, keys-family and secrets-family resource types were not working.
See [Key Policy Reference](https://docs.oracle.com/en-us/iaas/Content/Identity/Reference/keypolicyreference.htm) for details.

> The documentation says: "You're viewing OCI IAM documentation for tenancies in regions that have not been updated to use identity domains.", but there is no section for Vault service details under _IAM with Domains_ section.
> There are two section in the left menu (just scroll):
> - IAM with Identity Domains
> - IAM without Identity Domains
> 
> So just choose accordingly, or if missing, just use the one available
