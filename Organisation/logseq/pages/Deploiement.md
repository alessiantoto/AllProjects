
# Rapport : Déploiement d'un Serveur Local en Serveur Remote avec Proxmox, Hetzner API, et Terraform

## Introduction

Ce rapport décrit comment déployer un serveur local, configuré initialement avec **VirtualBox** et **Vagrant**, vers un serveur distant en utilisant **Proxmox**, **Hetzner API**, et **Terraform**. 
Il inclut les étapes d'installation, de configuration et les commandes nécessaires pour chaque technologie afin de réussir le déploiement et la gestion du serveur distant.
---

## 1. Configuration Initiale du Serveur Local avec VirtualBox et Vagrant

Le serveur local est configuré à l'aide de **VirtualBox** et **Vagrant**. Voici les étapes pour initialiser et tester votre serveur local.

### Étapes d'installation et de configuration

1. **Installer VirtualBox**  
   Commande pour installer VirtualBox sur Ubuntu :
   ```bash
   sudo apt update
   sudo apt install virtualbox
   ```
   

2. **Installer Vagrant**  
   Commande pour installer Vagrant sur Ubuntu :
   ```bash
   sudo apt install vagrant
   ```
   Pour d'autres systèmes : [Vagrant Download](https://www.vagrantup.com/downloads)

3. **Initialiser le projet Vagrant**  
   Créez un répertoire de projet pour votre machine virtuelle :
   ```bash
   mkdir mon-serveur
   cd mon-serveur
   ```
   Initialiser Vagrant dans ce répertoire :
   ```bash
   vagrant init
   ```
   Modifier le fichier `Vagrantfile` pour spécifier la box et les configurations requises :
   ```ruby
   Vagrant.configure("2") do |config|
     config.vm.box = "ubuntu/focal64"
     config.vm.network "public_network"
     config.vm.provider "virtualbox" do |vb|
       vb.memory = "2048"
       vb.cpus = 2
     end
   end
   ```

4. **Démarrer la machine virtuelle**  
   Lancer la machine virtuelle avec Vagrant :
   ```bash
   vagrant up
   ```
   Vous pouvez vous connecter à la machine via SSH pour tester :
   ```bash
   vagrant ssh
   ```

5. **Arrêter et détruire la machine**  
   Arrêter la machine si besoin :
   ```bash
   vagrant halt
   ```
   Si vous souhaitez détruire la machine virtuelle :
   ```bash
   vagrant destroy
   ```

---

## 2. Migration et Déploiement avec Proxmox

Proxmox est utilisé pour migrer et gérer la machine virtuelle sur un serveur distant.

### Étapes d'installation et de configuration de Proxmox

1. **Installer Proxmox VE sur le serveur distant**  
   Accédez au serveur distant (via SSH) et suivez les étapes d'installation pour Proxmox VE :
   ```bash
   echo "deb http://download.proxmox.com/debian/pve buster pve-no-subscription" | sudo tee /etc/apt/sources.list.d/pve-install-repo.list
   wget http://download.proxmox.com/debian/proxmox-release-buster.gpg -O- | sudo apt-key add -
   sudo apt update && sudo apt full-upgrade
   sudo apt install proxmox-ve postfix open-iscsi
   ```
   Redémarrez le serveur pour démarrer sur le kernel Proxmox :
   ```bash
   sudo reboot
   ```

2. **Migrer la machine virtuelle de Vagrant vers Proxmox**  
   Exportez votre machine virtuelle Vagrant au format OVA :
   ```bash
   vagrant package --output mon-serveur.ova
   ```
   Importez cette machine dans Proxmox via l'interface Web ou en ligne de commande :
   ```bash
   qm importovf <VMID> mon-serveur.ova <storage>
   ```
   Démarrez la machine virtuelle sur Proxmox.

3. **Configurer les ressources du serveur distant**  
   Via l'interface web Proxmox (https://<ip_du_serveur_proxmox>:8006), ajustez les ressources (CPU, RAM, etc.) et les réseaux selon vos besoins.

---

## 3. Provisionnement et Gestion des Ressources avec Hetzner API

Hetzner API est utilisé pour gérer dynamiquement les ressources du serveur distant sur Hetzner Cloud.

### Étapes d'installation et de configuration de Hetzner API

1. **Créer un compte sur Hetzner Cloud**  
   Accédez à [Hetzner Cloud](https://www.hetzner.com/cloud) et créez un compte.

2. **Créer une clé API**  
   Allez dans votre tableau de bord Hetzner Cloud, sous **API**, et créez une nouvelle clé API. Notez cette clé car elle sera utilisée pour interagir avec l'API Hetzner.

3. **Installer l'outil CLI hcloud**  
   Pour interagir avec l'API Hetzner depuis votre terminal, installez le client CLI :
   ```bash
   sudo apt install hcloud
   ```
   Configurez votre clé API :
   ```bash
   hcloud context create mon-contexte
   ```
   Vous pouvez maintenant utiliser des commandes `hcloud` pour gérer vos serveurs distants Hetzner.

4. **Provisionner un serveur distant avec Hetzner API**  
   Créer un serveur cloud Hetzner avec la commande suivante :
   ```bash
   hcloud server create --name mon-serveur --image ubuntu-20.04 --type cx21
   ```

---

## 4. Automatisation du Déploiement avec Terraform

Terraform permet d'automatiser la configuration et la gestion du serveur distant en utilisant Hetzner API et Proxmox.

### Étapes d'installation et de configuration de Terraform

1. **Installer Terraform**  
   Téléchargez et installez Terraform sur votre machine :
   ```bash
   sudo apt-get update && sudo apt-get install -y gnupg software-properties-common curl
   curl -fsSL https://apt.releases.hashicorp.com/gpg | sudo apt-key add -
   sudo apt-add-repository "deb [arch=amd64] https://apt.releases.hashicorp.com $(lsb_release -cs) main"
   sudo apt-get update && sudo apt-get install terraform
   ```

2. **Configurer Terraform pour Hetzner et Proxmox**  
   Créez un fichier `main.tf` pour configurer votre serveur distant :
   ```hcl
   provider "hcloud" {
     token = "<votre_hetzner_api_token>"
   }

   resource "hcloud_server" "mon_serveur" {
     name        = "mon-serveur"
     image       = "ubuntu-20.04"
     server_type = "cx21"
   }

   provider "proxmox" {
     pm_api_url      = "https://<ip_proxmox>:8006/api2/json"
     pm_user         = "root@pam"
     pm_password     = "<votre_password_proxmox>"
   }

   resource "proxmox_vm_qemu" "vm1" {
     name       = "mon-vm"
     target_node = "proxmox-node"
     ...
   }
   ```

3. **Déployer avec Terraform**  
   Initialisez le répertoire Terraform :
   ```bash
   terraform init
   ```
   Planifiez le déploiement :
   ```bash
   terraform plan
   ```
   Appliquez la configuration :
   ```bash
   terraform apply
   ```

---

## Conclusion

En combinant **Proxmox**, **Hetzner API**, et **Terraform**, vous pouvez transformer efficacement un serveur local, initialement configuré avec **VirtualBox** et **Vagrant**, en un serveur distant 
tout en automatisant les étapes de migration et de gestion. Grâce à ces outils, vous bénéficiez d'une infrastructure entièrement automatisée, scalable et flexible.


---


