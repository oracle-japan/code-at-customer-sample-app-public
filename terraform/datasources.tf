### Network
data "oci_identity_availability_domain" "ad" {
  compartment_id = var.compartment_ocid
  ad_number      = var.availability_domain_number
}
data "oci_core_services" "service_gateway" {
}

### OKE
data "oci_containerengine_cluster_option" "code_at_customer_cluster_option" {
  cluster_option_id = "all"
  compartment_id    = var.compartment_ocid
}
data "oci_containerengine_node_pool_option" "code_at_customer_node_pool_option" {
  node_pool_option_id = "all"
  compartment_id      = var.compartment_ocid
}
data "oci_core_images" "shape_specific_images" {
  compartment_id = var.compartment_ocid
  shape          = var.node_pool_node_shape
}
data "oci_core_services" "tutorial_services" {
}
data "oci_containerengine_cluster_kube_config" "code_at_customer_cluster_kube_config" {
  cluster_id    = oci_containerengine_cluster.code_at_customer_cluster.id
  expiration    = var.cluster_kube_config_expiration
  token_version = var.cluster_kube_config_token_version
}

# ATP
data "oci_database_autonomous_db_versions" "db_versions" {
  compartment_id = var.compartment_ocid
  db_workload = var.adb_workload
  filter {
    name   = "version"
    values = ["19c"]
  }
}
