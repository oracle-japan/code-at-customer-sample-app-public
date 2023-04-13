#### DEMO

### Common
variable "compartment_ocid" {
}
variable "region" {
}

### VCN variables
variable "cidr_block_all" {
  default = "0.0.0.0/0"
}
variable "cidr_block_vcn" {
  default = "10.0.0.0/16"
}
variable "cidr_block_node_pool_subnet" {
  default = "10.0.10.0/24"
}
variable "cidr_block_k8s_api_endpoint_subnet" {
  default = "10.0.0.0/28"
}
variable "cidr_block_lb_subnet" {
  default = "10.0.20.0/24"
}
variable "protocol_all" {
  default = "all"
}
variable "protocol_icmp" {
  default = "1"
}
variable "protocol_tcp" {
  default = "6"
}
variable "subnet_prohibit_public_ip_on_vnic" {
  default = "true"
}

### OKE Cluster variables
variable "availability_domain_name" {
  default = ""
}
variable "availability_domain_number" {
  default = 1
}
variable "cluster_name" {
  default = "code-at-customer-cluster"
}
variable "kubernetes_version" {
  default = "Latest"
}
variable "cluster_options_add_ons_is_kubernetes_dashboard_enabled" {
  default = false
}
variable "cluster_options_add_ons_is_tiller_enabled" {
  default = false
}
variable "cluster_options_kubernetes_network_config_pods_cidr" {
  default = "10.1.0.0/16"
}
variable "cluster_options_kubernetes_network_config_services_cidr" {
  default = "10.2.0.0/16"
}
variable "node_pool_initial_node_labels_key" {
  default = "ManagedBy"
}
variable "node_pool_initial_node_labels_value" {
  default = "ResourceManager"

}
variable "node_pool_name" {
  default = "Code at Customer Node Pool"
}
variable "node_pool_quantity_per_subnet" {
  default = 1
}
variable "node_pool_boot_volume_size_in_gbs" {
  default = "50"
}
variable "node_pool_instance_number" {
  default = 3
}
variable "node_pool_node_shape" {
  default = "VM.Standard.E3.Flex"
}
variable "node_pool_node_shape_config_ocpus" {
  default = "1"
}
variable "node_pool_node_shape_config_memory_in_gbs" {
  default = "16"
}

### API Gateway
variable "gateway_endpoint_type" {
  default = "PUBLIC"
}
variable "gateway_state" {
  default = "ACTIVE"
}
variable "gateway_display_name" {
  default = "code-at-customer-gateway"
}
variable "deployment_path_prefix" {
  default = "/api"
}
variable "deployment_specification_routes_backend_type" {
  default = "HTTP_BACKEND"
}
variable "deployment_specification_routes_backend_url" {
  default = "https://api.shukawam.com/api/items"
}
variable "deployment_specification_routes_path" {
  default = "/items"
}
variable "deployment_state" {
  default = "ACTIVE"
}
variable "deployment_display_name" {
  default = "items"
}

### ATP
variable "adb_name" {
  default = "cacatp"
}
variable "admin_password" {
  default = "Changeme#123"
}
variable "adb_workload" {
  default = "OLTP"
}
variable "freeform_tags" {
  default = {
    "ManagedBy" = "Resource Manager"
  }
}
variable "license_model" {
  default = "LICENSE_INCLUDED"
}
