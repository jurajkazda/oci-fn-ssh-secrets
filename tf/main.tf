locals {
  # prefix:region-AD-<numnber>
  availability_domain = "zYuO:UK-LONDON-1-AD-2"

  # jkazda/cmp-dev
  compartment_ocid = "ocid1.compartment.oc1..aaaaaaaasluc2z5magbxoa3ozfptgz5bwglk7jee4mbecc2il6hrnh7tq6ra"

  # name
  display_name = "vm-osmh"

  # base AF shape
  shape = "VM.Standard.E4.Flex"
#   shape = "VM.Standard.E2.1.Micro"

  # image OCID: Oracle-Linux-8.10-2024.09.30-0 (https://docs.oracle.com/en-us/iaas/images/oracle-linux-8x/oracle-linux-8-10-2024-09-30-0.htm)
  image_id = "ocid1.image.oc1.uk-london-1.aaaaaaaayzqftcsgvgcy42rrmndmtjohkuvb2u4zdtb2u7sslae6fvuo3hfq"

  # id-rsa
  ssh_authorized_keys = file("~/.ssh/id_rsa.pub")

  # sn-dev-public
  subnet_id = "ocid1.subnet.oc1.uk-london-1.aaaaaaaaeggtoa3vvcdsjjuqzsp5dtjuyfwbqmsms36okajof3c3nu7ow4xq"
}

# data "oci_functions_function" "example_function" {
#   application_id = "ocid1.fnapp.oc1.uk-london-1.aaaaaaaaixrzx35muip2hcrp3l6rj2vr4hhzif2oaiybpa4lmdiweau2hx2q" #var.function_application_id
#   function_id = "ocid1.fnfunc.oc1.uk-london-1.aaaaaaaah5jvzgvwg2uvcjxwjkwlrhancjya4ewjirwk6vx3b3sno3es7wwa"
# }

resource "oci_functions_invoke_function" "invoke_example" {
  function_id = "ocid1.fnfunc.oc1.uk-london-1.aaaaaaaah5jvzgvwg2uvcjxwjkwlrhancjya4ewjirwk6vx3b3sno3es7wwa"
}

output "function_invoke_response" {
  value = oci_functions_invoke_function.invoke_example.content
}

/*
resource "oci_core_instance" "my_compute" {
  availability_domain = local.availability_domain
  compartment_id      = local.compartment_ocid
  shape               = local.shape

  create_vnic_details {
    subnet_id = local.subnet_id
  }

  metadata = {
    ssh_authorized_keys = local.ssh_authorized_keys
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

  agent_config {
    is_management_disabled = false
    is_monitoring_disabled = false

    plugins_config {
      name          = "OS Management Hub Agent"
      desired_state = "ENABLED"
    }

    plugins_config {
      name          = "OS Management Service Agent"
      desired_state = "DISABLED"
    }

    plugins_config {
      name          = "Management Agent"
      desired_state = "ENABLED"
    }
  }
}


output "my_compute_osmh" {
  value = data.oci_os_management_hub_managed_instance.my_compute
}
# resource "oci_os_management_hub_managed_instance" "test_managed_instance" {
#   #Required
#   managed_instance_id = oci_core_instance.my_compute.id
# }

output "profile_id" {
  value = data.oci_os_management_hub_profiles.osmhp_ol8_amd64
}

*/