module "db" {
  source = "terraform-aws-modules/rds/aws"

  identifier = "${var.project_name}-database"

  engine = "mysql"
  engine_version = "8.0"
  instance_class = "db.t3.micro"
  allocated_storage = 20

  db_name = "hotel_manager_database"
  username = "admin"
  port = "3306"

  manage_master_user_password = true

  vpc_security_group_ids = [aws_security_group.rds.id]

  create_db_subnet_group = true
  subnet_ids = module.vpc.private_subnets

  family = "mysql8.0"
  major_engine_version = "8.0"

  multi_az = false
  deletion_protection = false
  skip_final_snapshot = true

  tags = {
    Environment = var.environment
    Terraform = "true"
  }

}