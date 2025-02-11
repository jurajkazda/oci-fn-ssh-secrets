locals {
  # prefix:region-AD-<numnber>
  availability_domain = "zYuO:UK-LONDON-1-AD-2"

  # jkazda/cmp-dev
  compartment_ocid = "ocid1.compartment.oc1..aaaaaaaasluc2z5magbxoa3ozfptgz5bwglk7jee4mbecc2il6hrnh7tq6ra"

  # name
  display_name = "vm-fn-secrets-3"

  # base AF shape
  shape = "VM.Standard.E4.Flex"
#   shape = "VM.Standard.E2.1.Micro"

  # image OCID: Oracle-Linux-8.10-2024.09.30-0 (https://docs.oracle.com/en-us/iaas/images/oracle-linux-8x/oracle-linux-8-10-2024-09-30-0.htm)
  image_id = "ocid1.image.oc1.uk-london-1.aaaaaaaayzqftcsgvgcy42rrmndmtjohkuvb2u4zdtb2u7sslae6fvuo3hfq"

  # id-rsa
  ssh_authorized_keys = file("~/.ssh/id_rsa.pub")

  # sn-dev-public
  subnet_id = "ocid1.subnet.oc1.uk-london-1.aaaaaaaaeggtoa3vvcdsjjuqzsp5dtjuyfwbqmsms36okajof3c3nu7ow4xq"

  fn_input = {
    "keyPairName": "vm-fn-secrets-3",
    "compartmentId": "ocid1.compartment.oc1..aaaaaaaappd5di5sx5kr4s7gcdkc7mlbicrn3ydkkjhjc4zyhcc4u3hdqxqa",
    "vaultId": "ocid1.vault.oc1.uk-london-1.ertrmuzkaagpk.abwgiljttvlqsststysrk45vdkbtg4kgby4rtwnu4lfzz5eqkbpdgd5jw3cq",
    "keyId": "ocid1.key.oc1.uk-london-1.ertrmuzkaagpk.abwgiljrpqazigekhz47dgwhbzvtsi6j3lazo6secuuqh622w5n6h7jfk4aa",
    "description": "vm-fn-secrets-1 keyPair desc."
  }

  public_key = jsondecode(oci_functions_invoke_function.invoke_example.content).publicKeySecretValue
}

# data "oci_functions_function" "example_function" {
#   application_id = "ocid1.fnapp.oc1.uk-london-1.aaaaaaaaixrzx35muip2hcrp3l6rj2vr4hhzif2oaiybpa4lmdiweau2hx2q" #var.function_application_id
#   function_id = "ocid1.fnfunc.oc1.uk-london-1.aaaaaaaah5jvzgvwg2uvcjxwjkwlrhancjya4ewjirwk6vx3b3sno3es7wwa"
# }

resource "oci_functions_invoke_function" "invoke_example" {
  function_id = "ocid1.fnfunc.oc1.uk-london-1.aaaaaaaah5jvzgvwg2uvcjxwjkwlrhancjya4ewjirwk6vx3b3sno3es7wwa"
  invoke_function_body = jsonencode(local.fn_input)
#   function_id = "ocid1.fnfunc.oc1.uk-london-1.aaaaaaaah5jvzgvwg2uvcjxwjkwlrhancjya4ewjirwk6vx3b3sno3es7wwa"
}

output "function_invoke_response" {
  value = oci_functions_invoke_function.invoke_example.content
}

output "function_invoke_response_private_key_secret_ocid" {
  value = jsondecode(oci_functions_invoke_function.invoke_example.content).privateKeySecretOcid
}


# Data source to retrieve the SSH private key from the Vault
data "oci_secrets_secretbundle" "ssh_private_key" {
  secret_id = jsondecode(oci_functions_invoke_function.invoke_example.content).privateKeySecretOcid
}

output "private_key" {
  value = base64decode(data.oci_secrets_secretbundle.ssh_private_key.secret_bundle_content.0.content)
}


resource "oci_core_instance" "my_compute" {
  availability_domain = local.availability_domain
  compartment_id      = local.compartment_ocid
  shape               = local.shape

  create_vnic_details {
    subnet_id = local.subnet_id
  }

  metadata = {
    ssh_authorized_keys = local.public_key
#     ssh_authorized_keys = base64decode(data.oci_secrets_secretbundle.ssh_private_key.secret_bundle_content.0.content) #local.ssh_authorized_keys
  }

  shape_config {
    memory_in_gbs = "4"
    ocpus = "1"
  }

  source_details {
    source_type = "image"
    source_id   = local.image_id
  }

  display_name = local.display_name
}
