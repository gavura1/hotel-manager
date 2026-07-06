module "eks" {
  source = "terraform-aws-modules/eks/aws"
  version = "~> 21.0"

  name = var.project_name
  kubernetes_version = var.kubernetes_version

  endpoint_public_access = true
  enable_cluster_creator_admin_permissions = true

  vpc_id = module.vpc.vpc_id
  subnet_ids = module.vpc.private_subnets

  # EKS Managed Node Group(s)
  eks_managed_node_groups = {
    default = {
      ami_type = "AL2023_x86_64_STANDARD"
      instance_types = ["t3.small"]

      min_size = 1
      max_size = 2
      desired_size = 1
    }
  }

  addons = {
    vpc-cni = {
      before_compute = true
    }
    coredns = {}
    kube-proxy = {}
    eks-pod-identity-agent = {
      before_compute = true
    }
  }

  tags = {
    Environment = "dev"
    Terraform = "true"
  }
}