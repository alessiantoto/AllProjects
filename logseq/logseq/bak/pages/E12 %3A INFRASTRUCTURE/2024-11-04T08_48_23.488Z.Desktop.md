## Structure du projet et utilisation des Vagrantfiles

Ce projet utilise une approche modulaire pour la gestion des environnements virtuels avec Vagrant. Chaque environnement dispose de son propre Vagrantfile, permettant une gestion indépendante et flexible.

**Note:** La structure actuelle du projet n'est qu'un point de départ pour le développement et les tests initiaux. L'équipe devra réévaluer régulièrement la structure du projet et proposer des améliorations en vue d'atteindre un environnement de développement efficace.
- ## **Structure du projet**
  
  ```
  project-root/
  ├── environments/
  │   ├── user/
  │   │   └── Vagrantfile  
  │   ├── it/
  │   │   └── Vagrantfile
  │   ├── cyber/
  │   │   └── Vagrantfile
  │   └── learning/
  │       └── Vagrantfile
  ├── ansible/
  └── README.md
  ```
-
- ## **Pourquoi cette approche ?**
	- **Modularité maximale** : Chaque environnement est complètement indépendant.
	- **Flexibilité** : Permet de travailler sur des environnements spécifiques sans affecter les autres.
	- **Simplicité** : Chaque Vagrantfile est dédié à un seul environnement, ce qui le rend plus simple à comprendre et à maintenir.
	- **Isolation** : Réduit les risques de conflits entre les configurations des différents environnements.
	- **Personnalisation** : Permet d'adapter précisément chaque environnement à ses besoins spécifiques.
	-
- ## **Comment utiliser cette configuration**
- ### **Pour travailler sur un environnement spécifique :**
	- Naviguez vers le dossier de l'environnement souhaité :
	  
	  ```
	  cd environments/user
	  ```
	- Utilisez les commandes Vagrant suivantes :
		- `vagrant up` : Crée et démarre la machine virtuelle selon les spécifications du Vagrantfile. Si la VM existe déjà, elle sera simplement démarrée.
		- `vagrant ssh` : Se connecte à la machine virtuelle en cours d'exécution via SSH. Vous pouvez alors interagir avec la VM comme si vous étiez directement connecté à un serveur distant.
		- `vagrant halt` : Arrête proprement la machine virtuelle en cours d'exécution, équivalent à un arrêt normal du système.
		- `vagrant destroy` : Supprime complètement la machine virtuelle et toutes ses ressources associées. Utilisez cette commande avec précaution car elle efface toutes les données de la VM.
		- `vagrant status` : Affiche l'état actuel de la machine virtuelle (en cours d'exécution, arrêtée, etc.).
		- `vagrant reload` : Redémarre la machine virtuelle, en rechargeant la configuration du Vagrantfile. Utile après des modifications du Vagrantfile.
		- `vagrant provision` : Exécute uniquement les provisionneurs définis dans le Vagrantfile sur une VM déjà en cours d'exécution.
		-
- ## **Pour ajouter un nouvel environnement :**
	- Créez un nouveau dossier dans le répertoire `environments/`.
	- Ajoutez un `Vagrantfile` spécifique à cet environnement dans le nouveau dossier.
	- Configurez le Vagrantfile selon les besoins de l'environnement.
-
- ## **Bonnes pratiques**
	- Assurez-vous que chaque Vagrantfile est autonome et contient toutes les configurations nécessaires pour son environnement.
	- Utilisez des commentaires dans chaque Vagrantfile pour expliquer les configurations spécifiques.
	- Maintenez une cohérence dans la nomenclature des machines virtuelles et des réseaux entre les différents environnements.
	- Documentez toute configuration spéciale ou dépendance dans un README spécifique à chaque environnement.
	-
- ## **Gestion des ressources**
	- Soyez attentif à l'utilisation des ressources de votre machine hôte lors du démarrage de plusieurs environnements simultanément.
	- Considérez l'utilisation de profils de ressources différents (CPU, RAM) selon les besoins de chaque environnement.
	-
- ## **Résolution de problèmes**
  
  Si vous rencontrez des problèmes :
	- Vérifiez que vous êtes dans le bon répertoire d'environnement.
	- Assurez-vous que le Vagrantfile est correctement formaté.
	- Vérifiez les logs pour des messages d'erreur spécifiques.
	- Utilisez `vagrant status` pour vérifier l'état des machines virtuelles.
	  
	  Pour toute question ou problème persistant, veuillez contacter l'équipe d'infrastructure.