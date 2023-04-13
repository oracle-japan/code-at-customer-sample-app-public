resource "oci_database_autonomous_database" "autonomous_database" {
  #Required
  compartment_id           = var.compartment_ocid
  admin_password           = var.admin_password
  cpu_core_count           = "1"
  data_storage_size_in_tbs = "1"
  db_name                  = var.adb_name

  #Optional
  db_version                          = data.oci_database_autonomous_db_versions.db_versions.autonomous_db_versions[0].version
  db_workload                         = var.adb_workload
  display_name                        = "Code at Customer ATP"
  freeform_tags                       = var.freeform_tags
  is_auto_scaling_enabled             = "true"
  is_auto_scaling_for_storage_enabled = "true"
  license_model                       = var.license_model
  character_set                       = "AL32UTF8"
  ncharacter_set                      = "AL16UTF16"
}
