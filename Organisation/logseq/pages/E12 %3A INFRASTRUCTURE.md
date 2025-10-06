## Objectif S3 **installation d'une vm en local**
	- **Outils nécessaire pour la mise en place**
		- `VirtualBox` | `Vagrant`
	- **Document Vagrant :**
	  Dans le répertoire de votre projet, créez un fichier `Vagrantfile`. Ce fichier contiendra les instructions nécessaires pour que Vagrant installe Ubuntu dans VirtualBox.
	- **Initialiser et démarrer la VM :**
		- Ouvrez un terminal dans le répertoire contenant votre `Vagrantfile`. et tapez la commande vue au cours
		  ```bash
		  vagrant up
		  ```
		-
	- **Accéder à la VM** :
		- Une fois la VM démarrée, accédez-y avec la commande suivante :
		  ```bash
		  vagrant ssh
		  ```
		- Maintenant que nous avons accès à la VM nous pouvons ajoutez des scripts pour le futur.
	- **Mettre le projet sur Git**
		- Crée une banche story-0 et Mettez le `Vagrantfile` sur le projet git et push le sur cette branche
- Merci pour votre travail !
	- Attention !
		- -> Terraform ne fonctionne mieux sur proxmox
		- -> Vagrant n'est pas compétent sur proxmox
-
- ## Résultat S3->S4 **Installation d'une vm en local**
	- ### utils nécessaires pour la mise en place
	- `VirtualBox` (version 7.0 ou inférieure)
	- `Vagrant`
	  
	  **Attention** : VirtualBox 7.1 n'est pas compatible avec Vagrant. Assurez-vous d'utiliser la version 7.0 ou une version antérieure.
	- ### 
	  [](#document-vagrant) Document Vagrant
	  
	  Dans le répertoire de votre projet, créez un fichier `Vagrantfile` avec le contenu suivant :
	  
	  ```
	  # -*- mode: ruby -*-
	  # vi: set ft=ruby :
	  
	  Vagrant.configure("2") do |config|
	  # Spécifie l'image de base à utiliser
	  config.vm.box = "hashicorp/bionic64"
	  
	  # Crée un réseau privé avec DHCP
	  config.vm.network "private_network", type: "dhcp"
	  
	  # Configuration spécifique pour VirtualBox
	  config.vm.provider "virtualbox" do |vb|
	    # Personnalise la quantité de mémoire et le nombre de CPUs de la VM
	    vb.memory = "4096"
	    vb.cpus = 4
	  end
	  
	  # Message d'information sur le mot de passe
	  config.vm.post_up_message = "Pour vous connecter à la VM, utilisez le mot de passe 'vagrant'"
	  end
	  ```
	- vagrant up
	- ### 
	  [](#acc%C3%A9der-%C3%A0-la-vm) Accéder à la VM
	  
	  Une fois la VM démarrée, accédez-y avec la commande suivante :
	  
	  ```
	  vagrant ssh
	  ```
	  
	  Lorsque vous y êtes invité, entrez le mot de passe `vagrant` pour vous connecter à la VM.
- **Story 1 : shutdown hetzner server**
  
  Plusieurs moyens d'éteindre le serveur :
  
  1. Via SSH
- Se connecter au serveur et entrer le mot de passe / clé SSH
  
  *> $ ssh root@server_ip  *
- Une fois connecté, entrer :  
  
  - Pour éteindre directement
  
  *	**> $ shutdown -h now  *
  
  - Pour éteindre dans 5 minutes  
  
  *	**> $ shutdown -h +5  *
  
  - Forcer la mise en arrêt si le serveur ne répond pas  
  
  *	**> $ poweroff  *
  
  2. Via l'API Cloud Hetzner
- Générer / récupérer la clé de l'API pour l'éteindre via une requête HTTP
  
  *> $ curl -X POST "https://api.hetzner.cloud/v1/servers/SERVER_ID/actions/shutdown" \  *
  
  *-H "Authorization: Bearer API_KEY"  *
  
  Quelle méthode utiliser ?
  
  1. Les deux méthodes sont correctes.
  
  2. Il faut privilégier shutdown à poweroff pour éviter tout risque de perte de données ou de corruption.
  
  3. Toujours s'assurer que les données importantes ont été sauvegardées pour éviter une perte accidentelle.
  
  4. L'API est bien évidemment plus adapté car elle est directement fourni par Hetzner et présente de nombreux avantages :  
  
    - Programmation et automatisation optimale et intégrable via Terraform et Ansible (nos outils de travail)
  
    - Des permissions peuvent être établis sur la clé API renforçant ainsi la sécurité et le droit d'accès
  
     - Gestion de plusieurs serveurs de manière centralisée
  
     - Veuillez noter tout de même qu'il faut mettre tout cela en place et le configurer. Pour une mise à l'arrêt immédiate, SSH peut-être utilisé.
  
  Ces méthodes doivent être testées une fois qu'une connexion est possible au serveur du client.
-