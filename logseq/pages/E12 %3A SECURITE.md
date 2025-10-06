## **Audit de sécurité des configurations Vagrant et VirtualBox.**
	- Outils nécessaires pour la mise en place
		- `OpnSense` | `Ansible Vault`
	- **Vérification des configurations Vagrant**
	- **Utilisation de Ansible Vault, renseignez vous sur comment ca marche**
	- **Créer des règles de firewall avec OpnSense**
		- Définir des règles pour autoriser les ports nécessaires (SSH, HTTPS), exemple :
		  ```
		  Action: Pass
		  Interface: WAN
		  Protocol: TCP
		  Source: any
		  Destination: WAN address
		  Destination Port: 22 (SSH)
		  ```
		- Whitelist des adresses IP
	- une cle est partagee entre le client et les membres du departement informatique. cette cle donne acces au serveur. Une deuxieme cle sera partagee qu'au sein de l'equipe securite pour la gestion du server. Le partage de la cle se fait via un fichier kdbx qui pourra etre decripte avec un ensemble de mots secrets?
	- **Verifier la sécurite des images utilisée sur Docker**
- Merci pour votre travail !