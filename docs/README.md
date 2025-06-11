# fnf-user-group

The Oracle Fn function to create a user and a group with user assigned to it, in an identity domain of choice.

The input file is simple:

```json
{
  "userName": "johndoe5",
  "tagName": "bucket-id",
  "tagValue": "BucketIdentifier1",
  "compartmentId": "ocid1.compartment.oc1..aaaaaaaappd5di5sx5kr4s7gcdkc7mlbicrn3ydkkjhjc4zyhcc4u3hdqxqa",
  "vaultId": "ocid1.vault.oc1.uk-london-1.ertrmuzkaagpk.abwgiljttvlqsststysrk45vdkbtg4kgby4rtwnu4lfzz5eqkbpdgd5jw3cq",
  "keyId": "ocid1.key.oc1.uk-london-1.ertrmuzkaagpk.abwgiljrpqazigekhz47dgwhbzvtsi6j3lazo6secuuqh622w5n6h7jfk4aa",
  "description": "John Doe User"
}
```

## Deployment

```shell
$ fn deploy --app fna-user-group
```

## Invocation

```shell
$ cat input_afp.json | fn invoke fna-user-group fnf-user-group
```