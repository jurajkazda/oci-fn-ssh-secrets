terraform {
  required_providers {
    oci = {
      source  = "oracle/oci"
      version = ">= 6.9.0"
    }
  }
  required_version = ">= 1.2"
}

provider "oci" {
  region              = "uk-london-1"
  config_file_profile = "AFP"
  auth                = "APIKey"
}
