variable "aws_region" {
  type = string
  description = "AWS region"
  default = "eu-central-1"
}

variable "project_name" {
  type = string
  description = "Name of the project"
  default = "hotel-manager"
}

variable "environment" {
  type = string
  description = "Development environment"
  default = "dev"
}

variable "vpc_cidr" {
  type = string
  description = "AWS VPC"
  default = "10.0.0.0/16"
}

variable "kubernetes_version" {
  type = string
  description = "Kubernetes version"
  default = "1.33"
}