- Date : 14/10/2024
-
- ### **Personnel présent à la réunion **
	- **Heure de début 8H30**
	- **Absent** : 0
	- > Chef de projet
	  | **Nom**            | **Prénom**         | **Matricule** | **Numéro de ligne** |
	  |--------------------|--------------------|---------------|---------------------|
	  | Drustinac            | Berin | 58124| 36|
	  | Kowalski       | roch| 57998| 32|
	  | Drissi | Rayan| 58923 | 48|
	- > Equipe infrastructure
	  | **Nom**            | **Prénom**         | **Matricule** | **Numéro de ligne** | **Responsabilité équipe** |
	  |--------------------|--------------------|---------------|---------------------|---------------------|
	  | Kowalski       | roch| 57998| 32| superviseur |
	  | El Adel | Hamza | 60396| 39 |  |
	  | Margaryan | Seyran| 57984| 38|  |
	  | Drissi | Rayan| 58923 | 48|  |
	- > Equipe Automatisation
	  | **Nom**            | **Prénom**         | **Matricule** | **Numéro de ligne** | **Responsabilité équipe** |
	  |--------------------|--------------------|---------------|---------------------|---------------------|
	  | Noje | Alessian | 62098 | 46 | superviseur |
	  | Guclu| Cimen | 60531 | 31 | |
	- > Equipe Testing
	  | **Nom**            | **Prénom**         | **Matricule** | **Numéro de ligne** | **Responsabilité équipe** |
	  |--------------------|--------------------|---------------|---------------------|---------------------|
	  | Otten | Soulêyman | 60155| 33| superviseur |
	  | Jan| Eggers | 56165 | 47| |
	  | Akhazzan | Mohamed| 59227| 41| |
	- > Equipe Sécurité
	  | **Nom**            | **Prénom**         | **Matricule** | **Numéro de ligne** | **Responsabilité équipe** |
	  |--------------------|--------------------|---------------|---------------------|---------------------|
	  | Morari | Augustin-Constantin| 61689| 34 | superviseur |
	  | Paraschiv | Olivia | 60085| 35| |
	  | Alime | Bobray | 62048| 43| |
	  | Zair | Yasine | 60957 | 45 | |
-
	- > Equipe Déploiement
	  | **Nom**            | **Prénom**         | **Matricule** | **Numéro de ligne** | **Responsabilité équipe** |
	  |--------------------|--------------------|---------------|---------------------|---------------------|
	  |  El Harrasi | Jawad | 60177| 37 | superviseur |
	  |  Azoioui | Iman | 61434| 30| |
-
- ## **P.Management**
	- Nous avons remarquer qu'il étais difficile pour tout le monde de suivre les objectifs demander, et de moi même pouvoir trouver un endroit où gérer tout les objectifs et voir l'avancement.
	-
	- J'ai donc exploré git et j'ai trouver que toute l'interface était construire pour du Project management.
	-
	- La méthodologie de sprint ne fonctionne pas, car aucun sprint effectué dans les 2 semaines, peut-être qu'ils sont bcp trop grand, peut-être que la présentation de ceux-là n'est pas correct, j'ai donc arrêter le système de sprint pour passer à des issue et un tableau de kanban.
		- -> ce tableau est mise en place car beaucoup d'entre vous aviez déjà l'habitude de l'utilisé
		- -> tout le monde pourra voir où les autres équipes en sont (plus du manuel comme l'excel)
		- -> en tant que p.m, je m'informe aussi des objectifs
		- -> ceci remplis directement les milestones
	-
	- J'ai donc crée un **milestones** dans la partie **plan**, voilà les avantages que cela apporte :
		- **Structurer les objectifs** : Un milestone permet de définir des objectifs spécifiques à atteindre, comme la sortie d'une version du projet ou l'achèvement d'une fonctionnalité majeure.
		-
		- **Suivi des progrès** : Il permet de suivre les progrès vers l'accomplissement d'un objectif donné en visualisant quelles tâches ont été accomplies et lesquelles sont encore en cours.
		-
		- **Gestion des délais** : Un milestone peut avoir une date d'échéance, ce qui permet de définir des délais clairs pour certaines étapes du projet.
		-
		- **Priorisation** : En associant les issues et pull requests à un milestone, cela aide à prioriser les tâches à accomplir pour atteindre les objectifs.
	-
	- Nous avons organiser une réunion afin que tout le monde soit au courant des questions typique qu'il faut se poser à chaque rapport. cad le qui ou quoi comment ainsi que les motivations, recherche scientifique, preuve, comparaison.
	-
	- Nous avons remarquer un problème dans les rapports faite par les équipes, tous étais rédiger de manière différente et pour les rassembler dans un résumé logseq cela était compliquer car il fallait adapter. Nous avons une idée auprès de l'équipe qui est de **standardiser** les rapports et logseq, cela permettrait aux autres équipe de pouvoir lire le rapport rédiger par une autre équipe rapidement.
		- > Pour une équipe :
		- 1. Un logseq **rapport** contenant, leur activité, comment il s'y sont pris, c'est un peu leur brouillon avant le document officiel.
			- **Une section** :
				- -> Numéro de séance
				- -> Date du rapport
				- -> Personne ayant rédiger la partie de ce rapport
				- -> Sujet
				- -> Théorique
				- -> Pratique
				- -> Questions typique, problématique, résultat et solution
				-
		- 2. Un document **officiel** reprenant la démarche  (comme un tutoriel) pour mettre en oeuvre le projet, avec aussi des preuves.
-
	- Nous avons remarquer cas chaque réunion on manquais à poser des questions, nous n'avions pas eu une liste détaillé, et des fois la pression fait qu'on réponde un peut vite. nous avons donc mise en place un google form qui sera envoyé chaque semaine afin que chaque personne puisse le remplir à son aise (cad 1 semaine) avant la prochaine séance.
-
- ## **Sécurité**
	- Augustin à été désigné comme maitre des clés
	- Création de la clé publique pour le prof
-
-
- ## **Testing**
	-
- ## **Automatisation**
	- Mise en place d'un playbook ansible pour éteindre le serveur via SSH
	- Manque des clés pour commencer le projet client
	-
- ## **Infrastructure**
	- Mise en place d'une idée d'un environnement pour chaque département
	-
-
- # **Pour la Séance S5 Client**
	- Un script qui lance et qui lance tout, surtout pas d'entrée client. -> à la limite un paramètre
	- Un fichier --config qui spécifie quel environnement.
	- Expliquer les méthodes qu'on va mettre en place pour sécurisé le serveur.
	- Pas simplement coder des clés privé dans un git public, ci/id on à des espace de stockage sur git spécialement fait pour stocker, c'est un token qu'on va recevoir chez hetzner.
	- Renouveler la clé x temps
	- Présentation des gestions d'accès à ce serveur, et de la sécurité
		- Machin dois faire x action  chaque jour
		- Si quelqu'un à besoin de quelque chose machin x
		-
	- Toute l'infra dois déjà être déployer
	-
	- Mettre en place un document de préparation de réunion
	-
	- Le but des séances 
	   -> question typique, problématique, solution, motivation, méthodologie
	   ->
-
	- Tester à la main, chipoter pour que les truc fonctionne
	- Allumer éteindre le serveur hetzner, tester les url curl -> une fois qu'on à tester on crée un test qui permet de faire ça, puis un script qui vérifie qui fait bien.
-
- **Remarque**
	- -> Pomodoro, story -> pour objectif fait et dire quel action fait, faire une différence avec le délivrable
	- -> Semaine prochaine en ligne
	- ->ilyas.bakhat@ulb.be, permet de crée des bots qui discute sur notre serveur.
-