### VCN
resource "oci_core_vcn" "code_at_customer_vcn" {
  cidr_block     = var.cidr_block_vcn
  compartment_id = var.compartment_ocid
  display_name   = "Code at Customer VCN"
}

### Gateways
resource "oci_core_internet_gateway" "code_at_customer_igw" {
  compartment_id = var.compartment_ocid
  display_name   = "Internet Gateway for Code at Customer"
  vcn_id         = oci_core_vcn.code_at_customer_vcn.id
}

resource "oci_core_nat_gateway" "code_at_customer_ngw" {
  compartment_id = var.compartment_ocid
  display_name   = "Nat Gateway for OKE Tutorial"
  vcn_id         = oci_core_vcn.code_at_customer_vcn.id
}

resource "oci_core_service_gateway" "code_at_customer_svcgw" {
  compartment_id = var.compartment_ocid
  services {
    service_id = data.oci_core_services.tutorial_services.services.0.id
  }
  vcn_id       = oci_core_vcn.code_at_customer_vcn.id
  display_name = "Service Gateway for Code at Customer"
}


### Route Tables
resource "oci_core_default_route_table" "tcode_at_customer_public_route_table" {
  manage_default_resource_id = oci_core_vcn.code_at_customer_vcn.default_route_table_id
  compartment_id             = var.compartment_ocid
  display_name               = "Public Route Table for Code at Customer"
  route_rules {
    destination       = var.cidr_block_all
    destination_type  = "CIDR_BLOCK"
    network_entity_id = oci_core_internet_gateway.code_at_customer_igw.id
  }
}

resource "oci_core_route_table" "code_at_customer_private_route_table" {
  compartment_id = var.compartment_ocid
  vcn_id         = oci_core_vcn.code_at_customer_vcn.id
  display_name   = "Private Route Table for Code at Customer"
  route_rules {
    destination       = var.cidr_block_all
    destination_type  = "CIDR_BLOCK"
    network_entity_id = oci_core_nat_gateway.code_at_customer_ngw.id
  }
  route_rules {
    destination       = data.oci_core_services.tutorial_services.services.0.cidr_block
    destination_type  = "SERVICE_CIDR_BLOCK"
    network_entity_id = oci_core_service_gateway.code_at_customer_svcgw.id
  }
}

### Security Lists
resource "oci_core_security_list" "k8s_api_endpoint_security_list" {
  compartment_id = var.compartment_ocid
  vcn_id         = oci_core_vcn.code_at_customer_vcn.id
  display_name   = "K8s Endpoint Security List"
  ingress_security_rules {
    protocol = var.protocol_tcp
    source   = var.cidr_block_all
    tcp_options {
      max = "6443"
      min = "6443"
    }
  }
  ingress_security_rules {
    protocol = var.protocol_tcp
    source   = var.cidr_block_node_pool_subnet
    tcp_options {

      max = "6443"
      min = "6443"
    }
  }
  ingress_security_rules {
    protocol = var.protocol_tcp
    source   = var.cidr_block_node_pool_subnet
    tcp_options {
      max = "12250"
      min = "12250"
    }
  }
  ingress_security_rules {
    protocol = var.protocol_icmp
    source   = var.cidr_block_node_pool_subnet
    icmp_options {
      type = "3"
      code = "4"
    }
  }
  egress_security_rules {
    destination      = data.oci_core_services.tutorial_services.services.0.cidr_block
    description      = "Allow Kubernetes Control Plane to communicate with OKE"
    protocol         = var.protocol_tcp
    destination_type = "SERVICE_CIDR_BLOCK"
    tcp_options {
      max = "443"
      min = "443"
    }
  }
  egress_security_rules {
    destination = var.cidr_block_node_pool_subnet
    description = "All traffic to worker nodes"
    protocol    = var.protocol_tcp
  }
  egress_security_rules {
    destination = var.cidr_block_node_pool_subnet
    description = "Path discovery"
    protocol    = var.protocol_icmp
    icmp_options {
      type = "3"
      code = "4"
    }
  }
}

resource "oci_core_security_list" "node_pool_security_list" {
  compartment_id = var.compartment_ocid
  vcn_id         = oci_core_vcn.code_at_customer_vcn.id
  display_name   = "Node Pool Security List"
  ingress_security_rules {
    protocol = var.protocol_all
    source   = var.cidr_block_node_pool_subnet
  }
  ingress_security_rules {
    protocol = var.protocol_icmp
    source   = var.cidr_block_k8s_api_endpoint_subnet
    icmp_options {
      type = "3"
      code = "4"
    }
  }
  ingress_security_rules {
    protocol = var.protocol_tcp
    source   = var.cidr_block_k8s_api_endpoint_subnet
  }
  ingress_security_rules {
    protocol = var.protocol_tcp
    source   = var.cidr_block_all
    tcp_options {
      max = "22"
      min = "22"
    }
  }
  ingress_security_rules {
    protocol = var.protocol_tcp
    source   = var.cidr_block_lb_subnet
    tcp_options {
      max = "30805"
      min = "30805"
    }
  }
  ingress_security_rules {
    protocol = var.protocol_tcp
    source   = var.cidr_block_lb_subnet
    tcp_options {
      max = "10256"
      min = "10256"
    }
  }
  ingress_security_rules {
    protocol = var.protocol_tcp
    source   = var.cidr_block_lb_subnet
    tcp_options {
      max = "31346"
      min = "31346"
    }
  }
  ingress_security_rules {
    protocol = var.protocol_tcp
    source   = var.cidr_block_lb_subnet
    tcp_options {
      max = "31078"
      min = "31078"
    }
  }
  ingress_security_rules {
    protocol = var.protocol_tcp
    source   = var.cidr_block_lb_subnet
    tcp_options {
      max = "32734"
      min = "32734"
    }
  }
  ingress_security_rules {
    protocol = var.protocol_tcp
    source   = var.cidr_block_lb_subnet
    tcp_options {
      max = "30656"
      min = "30656"
    }
  }
  ingress_security_rules {
    protocol = var.protocol_tcp
    source   = var.cidr_block_lb_subnet
    tcp_options {
      max = "31480"
      min = "31480"
    }
  }
  ingress_security_rules {
    protocol = var.protocol_tcp
    source   = var.cidr_block_lb_subnet
    tcp_options {
      max = "30572"
      min = "30572"
    }
  }
  egress_security_rules {
    destination = var.cidr_block_node_pool_subnet
    description = "Allow pods on one worker node to communicate with pods on other worker nodes"
    protocol    = var.protocol_all
  }
  egress_security_rules {
    destination = var.cidr_block_k8s_api_endpoint_subnet
    description = "Access to Kubernetes API Endpoint"
    protocol    = var.protocol_tcp
    tcp_options {
      max = "6443"
      min = "6443"
    }
  }
  egress_security_rules {
    destination = var.cidr_block_k8s_api_endpoint_subnet
    description = "Kubernetes worker to control plane communication"
    protocol    = var.protocol_tcp
    tcp_options {
      max = "12250"
      min = "12250"
    }
  }
  egress_security_rules {
    destination = var.cidr_block_k8s_api_endpoint_subnet
    description = "Path discovery"
    protocol    = var.protocol_icmp
    icmp_options {
      type = "3"
      code = "4"
    }
  }
  egress_security_rules {
    destination = var.cidr_block_all
    description = "ICMP Access from Kubernetes Control Plane"
    protocol    = var.protocol_icmp
    icmp_options {
      type = "3"
      code = "4"
    }
  }
  egress_security_rules {
    destination      = data.oci_core_services.tutorial_services.services.0.cidr_block
    description      = "Allow nodes to communicate with OKE to ensure correct start-up and continued functioning"
    protocol         = var.protocol_tcp
    destination_type = "SERVICE_CIDR_BLOCK"
    tcp_options {
      max = "443"
      min = "443"
    }
  }
  egress_security_rules {
    destination = var.cidr_block_all
    description = "Worker Nodes access to Internet"
    protocol    = var.protocol_tcp
  }
}

resource "oci_core_security_list" "lb_security_list" {
  compartment_id = var.compartment_ocid
  vcn_id         = oci_core_vcn.code_at_customer_vcn.id
  display_name   = "Security List for Load Balancer Subnet"
  ingress_security_rules {
    protocol = var.protocol_tcp
    source   = var.cidr_block_all
    tcp_options {
      max = "443"
      min = "443"
    }
  }
  ingress_security_rules {
    protocol = var.protocol_tcp
    source   = var.cidr_block_all
    tcp_options {
      max = "80"
      min = "80"
    }
  }
  ingress_security_rules {
    protocol = var.protocol_tcp
    source   = var.cidr_block_all
    tcp_options {
      max = "15021"
      min = "15021"
    }
  }
  ingress_security_rules {
    protocol = var.protocol_tcp
    source   = var.cidr_block_all
    tcp_options {
      max = "31400"
      min = "31400"
    }
  }
  ingress_security_rules {
    protocol = var.protocol_tcp
    source   = var.cidr_block_all
    tcp_options {
      max = "15443"
      min = "15443"
    }
  }
  egress_security_rules {
    destination = var.cidr_block_node_pool_subnet
    protocol    = var.protocol_tcp
    tcp_options {
      max = "10256"
      min = "10256"
    }
  }
  egress_security_rules {
    destination = var.cidr_block_node_pool_subnet
    protocol    = var.protocol_tcp
    tcp_options {
      max = "31078"
      min = "31078"
    }
  }
  egress_security_rules {
    destination = var.cidr_block_node_pool_subnet
    protocol    = var.protocol_tcp
    tcp_options {
      max = "32734"
      min = "32734"
    }
  }
  egress_security_rules {
    destination = var.cidr_block_vcn
    protocol    = var.protocol_tcp
    tcp_options {
      max = "10256"
      min = "10256"
    }
  }
  egress_security_rules {
    destination = var.cidr_block_node_pool_subnet
    protocol    = var.protocol_tcp
    tcp_options {
      max = "31346"
      min = "31346"
    }
  }
  egress_security_rules {
    destination = var.cidr_block_node_pool_subnet
    protocol    = var.protocol_tcp
    tcp_options {
      max = "30805"
      min = "30805"
    }
  }
  egress_security_rules {
    destination = var.cidr_block_node_pool_subnet
    protocol    = var.protocol_tcp
    tcp_options {
      max = "30656"
      min = "30656"
    }
  }
  egress_security_rules {
    destination = var.cidr_block_node_pool_subnet
    protocol    = var.protocol_tcp
    tcp_options {
      max = "31480"
      min = "31480"
    }
  }
  egress_security_rules {
    destination = var.cidr_block_node_pool_subnet
    protocol    = var.protocol_tcp
    tcp_options {
      max = "30572"
      min = "30572"
    }
  }
}

### Subnets
resource "oci_core_subnet" "k8s_api_endpoint_regional_subnet" {
  cidr_block        = var.cidr_block_k8s_api_endpoint_subnet
  compartment_id    = var.compartment_ocid
  vcn_id            = oci_core_vcn.code_at_customer_vcn.id
  security_list_ids = [oci_core_security_list.k8s_api_endpoint_security_list.id]
  display_name      = "K8s API Endpoint Subnet"
  route_table_id    = oci_core_vcn.code_at_customer_vcn.default_route_table_id
}

resource "oci_core_subnet" "node_pool_regional_subnet" {
  cidr_block                 = var.cidr_block_node_pool_subnet
  compartment_id             = var.compartment_ocid
  vcn_id                     = oci_core_vcn.code_at_customer_vcn.id
  security_list_ids          = [oci_core_security_list.node_pool_security_list.id]
  display_name               = "Node Pool Subnet"
  route_table_id             = oci_core_vcn.code_at_customer_vcn.default_route_table_id
  prohibit_public_ip_on_vnic = var.subnet_prohibit_public_ip_on_vnic
}

resource "oci_core_subnet" "lb_regional_subnet" {
  cidr_block        = var.cidr_block_lb_subnet
  compartment_id    = var.compartment_ocid
  vcn_id            = oci_core_vcn.code_at_customer_vcn.id
  security_list_ids = [oci_core_security_list.lb_security_list.id]
  display_name      = "LoadBalancer Subnet"
  route_table_id    = oci_core_vcn.code_at_customer_vcn.default_route_table_id
}

### Route Table Attachment
resource "oci_core_route_table_attachment" "code_at_customer_private_route_table_attachment" {
  subnet_id      = oci_core_subnet.node_pool_regional_subnet.id
  route_table_id = oci_core_route_table.code_at_customer_private_route_table.id
}