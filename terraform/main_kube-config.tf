variable "cluster_kube_config_expiration" {
  default = 2592000
}

variable "cluster_kube_config_token_version" {
  default = "2.0.0"
}

resource "local_file" "code_at_customer_cluster_kube_config_file" {
  content  = data.oci_containerengine_cluster_kube_config.code_at_customer_cluster_kube_config.content
  filename = "${path.module}/code_at_customer_cluster_kubeconfig"
}
