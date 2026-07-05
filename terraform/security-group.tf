resource "aws_security_group" "rds" {
  name = "${var.project_name}-rds-sg"
  description = "Allow MySQl access only from EKS nodes"
  vpc_id = module.vpc.vpc_id

  ingress {
    description = "MySQL from EKS nodes"
    from_port = 3306
    to_port = 3306
    protocol = "tcp"
    security_groups = [module.eks.node_security_group_id]
  }

  egress {
    from_port = 0
    to_port = 0
    protocol = "-1"
    cidr_blocks = ["0.0.0.0/0"]
  }

  tags = {
    Environment = var.environment
    Terraform = true
  }
}