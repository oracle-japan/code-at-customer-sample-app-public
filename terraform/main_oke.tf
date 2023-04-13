resource "oci_containerengine_cluster" "code_at_customer_cluster" {
  compartment_id     = var.compartment_ocid
  kubernetes_version = (var.kubernetes_version == "Latest") ? local.node_pool_k8s_latest_version : var.kubernetes_version
  name               = var.cluster_name
  vcn_id             = oci_core_vcn.code_at_customer_vcn.id
  endpoint_config {
    subnet_id            = oci_core_subnet.k8s_api_endpoint_regional_subnet.id
    is_public_ip_enabled = "true"
  }
  options {
    service_lb_subnet_ids = [oci_core_subnet.lb_regional_subnet.id]
    add_ons {
      is_kubernetes_dashboard_enabled = var.cluster_options_add_ons_is_kubernetes_dashboard_enabled
      is_tiller_enabled               = var.cluster_options_add_ons_is_tiller_enabled
    }
    kubernetes_network_config {
      pods_cidr     = var.cluster_options_kubernetes_network_config_pods_cidr
      services_cidr = var.cluster_options_kubernetes_network_config_services_cidr
    }
  }
}
resource "oci_containerengine_node_pool" "code_at_customer_node_pool" {
  cluster_id         = oci_containerengine_cluster.code_at_customer_cluster.id
  compartment_id     = var.compartment_ocid
  kubernetes_version = (var.kubernetes_version == "Latest") ? local.node_pool_k8s_latest_version : var.kubernetes_version
  name               = var.node_pool_name
  node_shape         = var.node_pool_node_shape
  initial_node_labels {
    key   = var.node_pool_initial_node_labels_key
    value = var.node_pool_initial_node_labels_value
  }
  node_source_details {
    source_type             = "IMAGE"
    image_id                = [for image in data.oci_containerengine_node_pool_option.code_at_customer_node_pool_option.sources : image.image_id if length(regexall("Oracle-Linux-8.6-20[0-9].*OKE.*", image.source_name)) > 0][0]
    boot_volume_size_in_gbs = var.node_pool_boot_volume_size_in_gbs
  }
  node_config_details {
    placement_configs {
      availability_domain = data.oci_identity_availability_domain.ad.name
      subnet_id           = oci_core_subnet.node_pool_regional_subnet.id
    }
    size = var.node_pool_instance_number
  }
  dynamic "node_shape_config" {
    for_each = local.is_flexible_node_shape ? [1] : []
    content {
      ocpus         = var.node_pool_node_shape_config_ocpus
      memory_in_gbs = var.node_pool_node_shape_config_memory_in_gbs
    }
  }
}
