resource "oci_apigateway_gateway" "gateway" {
  compartment_id = var.compartment_ocid
  endpoint_type  = var.gateway_endpoint_type
  subnet_id      = oci_core_subnet.lb_regional_subnet.id
  display_name   = var.gateway_display_name
}

resource "oci_apigateway_deployment" "deployment" {
  compartment_id = var.compartment_ocid
  gateway_id     = oci_apigateway_gateway.gateway.id
  path_prefix    = var.deployment_path_prefix
  display_name   = var.deployment_display_name
  specification {
    routes {
      #Required
      backend {
        #Required
        type = var.deployment_specification_routes_backend_type
        url  = var.deployment_specification_routes_backend_url
      }
      path    = var.deployment_specification_routes_path
      methods = ["GET"]
    }
  }
}
