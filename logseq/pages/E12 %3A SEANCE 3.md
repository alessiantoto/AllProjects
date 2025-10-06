- **Date : 8/10/2024**
- **Structure du rapport base sûr : E13 -> Moussa**
-
- ## **Plan de la réunion**
	- Présentation des mises en place organisationnelle
	  logseq.order-list-type:: number
	- Présentation des objectifs & rapport de chaque équipe
	  logseq.order-list-type:: number
	- Feedback du professeur
	  logseq.order-list-type:: number
	- Objectifs pour la prochaine séance
	  logseq.order-list-type:: number
-
- ### **Personnel présent à la réunion **
	- **Heure de début 8H15**
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
- ## 1. **Présentation des mises en place organisationnelle**
	- **Réunion**
	  Nous avons mis en place des réunions hebdomadaires pour suivre l'avancement des projets et préparer la prochaine rencontre avec le client. Une semaine à l'avance, un vote détermine la date de la réunion. Si celle-ci est annulée le jour J par manque de présence, elle est reportée au lendemain. Un événement Discord est créé pour permettre à chaque membre de confirmer sa présence. En cas d'absences répétées, un email est envoyé au membre concerné pour en comprendre les raisons.
	- **Rôles secondaires**
	  étant donnez que nous avons une petite équipe, pour pallier au manque de personnel dans d'autre équipe et pour partager les connaissances entre elle nous avons fait en sorte que chaque employer à 1 rôle prioritaire et 1 rôle secondaire, cela permait en autre de remplacer quelqu'un si il venais à être absent.
	- **Découpage des sprints**
	  Le personnel s'est plaint du manque de clarté du sprint, qui comprenait un seul sprint avec plusieurs stories. Chaque équipe devait soit travailler sur toutes les stories, soit se voir attribuer une story spécifique. Cela n'était pas efficace, car le retard d'une équipe sur une story ralentissait les autres. Pour améliorer la clarté et la collaboration, nous avons décidé de créer un sprint par équipe. Chaque story est désormais découpée et structurée pour chaque équipe avec des tâches spécifiques. Par exemple, la story 1, qui consiste à éteindre un serveur Hetzner, est répartie entre : l'infrastructure pour la mise en place de la VM, les tests pour vérifier l'état du serveur, l'automatisation pour automatiser chaque étape, et la sécurité pour vérifier la conformité des playbooks.
	- **Clarté pomodoro**
	  Nous avons mis en place un tableau pour savoir comment le personnel organisais leur propre pomodoro
	- **Planning poker**
	  Un test d'un planning poker à été mise en place lors de la séance mais il faut revoir l'efficacité de celui-ci vis à vis du projet et de notre équipe.
-
	- Toute ces mises en place nous à permis de voir une meilleur activité de la part du personnel.
-
	- **Problème en terme d'automatisation** : Pour l'instant les données mise sur l'excel sont fait automatiquement, il serais judicieux de pouvoir programmer quelques automatisme sur l'excel, tel que par
		- - Si une tâche est fini sur le git, quel soit marqué directement sur l'excel.
		- - Un membre qui change de rôle dois avoir son rôle directement à jour sur le tableau de l'équipe.
		- - Des statistiques présentant l'évolution des pomodors pour chaque story.
	-
- ## 2. Présentation des objectifs & rapport de chaque équipe
	- **Objectifs séance 3 : #[[E12 : TEST]]**
	- **Présenté par :**
	  
	  | Nom                | Prénom             | Matricule | Numéro de ligne |
	  |--------------------|--------------------|-----------|-----------------|
	  | Akhazzan | Mohamed| 59227| 41|
	  | Jan| Eggers | 56165 | 47|
	- **Préparé par :**
	  
	  | Nom                | Prénom             | Matricule | Numéro de ligne |
	  |--------------------|--------------------|-----------|-----------------|
	  | Akhazzan | Mohamed| 59227| 41|
	  | Otten | Soulêyman | 60155| 33|
	- **Résumé :**  
	  étude des recherches sur le TDD et le full TDD, l'équipe dois tester chaque étapes des autres équipes.
-
	- **Objectifs séance & résultat 3 : #[[E12 : INFRASTRUCTURE]]**
	- **Présenté par :**
	  | **Nom**            | **Prénom**         | **Matricule** | **Numéro de ligne** | 
	  |--------------------|--------------------|---------------|---------------------|
	  | Kowalski       | roch| 57998| 32| 
	  | El Adel | Hamza | 60396| 39 |  
	  | Margaryan | Seyran| 57984| 38|  
	  | Drissi | Rayan| 58923 | 48|
	- **Préparé par :**
	  | **Nom**            | **Prénom**         | **Matricule** | **Numéro de ligne** | 
	  |--------------------|--------------------|---------------|---------------------|
	  | Kowalski       | roch| 57998| 32| 
	  | Margaryan | Seyran| 57984| 38|  
	  | Drissi | Rayan| 58923 | 48|
	- **Résumé :**  
	  L'équipe infrastructure à recommandé d'utilisé virtualBox 7.0 ou inférieur pour le projet du client, effet la version 7.1 n'est pas compatible. une recherche sur les VagrantFile à été faite.
-
	- **Objectifs séance & résultat 3 : #[[E12 : SECURITE]]**
	- **Présenté par :**
	  | **Nom**            | **Prénom**         | **Matricule** | **Numéro de ligne** |
	  |--------------------|--------------------|---------------|---------------------|
	  | Morari | Augustin-Constantin| 61689| 34 |
	  | Paraschiv | Olivia | 60085| 35| |
	- **Préparé par :**
	  | **Nom**            | **Prénom**         | **Matricule** | **Numéro de ligne** |
	  |--------------------|--------------------|---------------|---------------------|
	  | Morari | Augustin-Constantin| 61689| 34 |
	  | Paraschiv | Olivia | 60085| 35|
	  | Alime | Bobray | 62048| 43| 
	  | Zair | Yasine | 60957 | 45 |
-
	- **Résumé :** Recherche sur la technologie Ansible Vault, sur le firewall avec OpenSense, l'équipe est au courant qu'un maitre des clés dois être choisis (Augustin) et qu'une clé publique devra être envoyé au client. Un rapport expliquant les intervenants et les méthodes utilisées doit être présenté au client afin qu’il soit au courant de la gestion du serveur (fermeture et ouverture).
-
	- **Objectifs séance & résultat 3 : #[[E12 : AUTOMATISATION]]**
	- **Présenté par :**
	  | **Nom**            | **Prénom**         | **Matricule** | **Numéro de ligne** | 
	  |--------------------|--------------------|---------------|---------------------|
	  | Noje | Alessian | 62098 | 46 | 
	  | Guclu| Cimen | 60531 | 31 |
	- **Préparé par :**
	  | **Nom**            | **Prénom**         | **Matricule** | **Numéro de ligne** | 
	  |--------------------|--------------------|---------------|---------------------|
	  | Noje | Alessian | 62098 | 46 | 
	  | Guclu| Cimen | 60531 | 31 |
	- **Résumé :** Présentation des recherches sur le pipeline, Ansible , CI/CD, sur le faite qu'il faut crée un fichier yml pour mettre en place une système automatique de test quant-on push et commit.
-
	- **Objectifs séance & résultat 3 : #[[E12 : DEPLOIEMENT]]**
	- **Préparé par :**
	  > Equipe Déploiement
	  | **Nom**            | **Prénom**         | **Matricule** | **Numéro de ligne** | **Responsabilité équipe** |
	  |--------------------|--------------------|---------------|---------------------|---------------------|
	  |  El Harrasi | Jawad | 60177| 37 | superviseur |
	  |  Azoioui | Iman | 61434| 30|
	- **Résumé** : L'équipe de déploiement à faire des recherches sur les différentes technologies
- ## 3. Feedback du professeur
	- Le professeur à remarqué que le projet est en bonne voie, mais :
		- Que tout nos actions doivent s'appuyer des questions typique tel que (la motivation, les test, les résultat, les problèmes, les ressources, la coordination, le contrôle, etc..)
		- Cas chaque séance il faut un reporting
		- Qu'il faut constamment une preuve scientifique sur notre mise en place vis à vis du projet, de notre équipe, de l'environne mènent, et de toujours chercher des points clés à améliorer.
		-
- ## 4. **Objectifs pour la prochaine séance**
	- Un sprint mise en place pour les prochaines story
	- Chaque équipe dois mettre en place un reporting claire, détaillé, scientifique (question typique)