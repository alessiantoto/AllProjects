## **Automatisation de la VM**
	- **Outils nécessaire pour la mise en place**
		- `Ansible`
	- **Automatisation via Ansible**
		- vous pouvez intégrer **Ansible** pour gérer le provisionnement de vos machines via des playbooks Ansible exécutés directement depuis Vagrant. ceci est un exemple dans le `VagrantFile`
		- ```ruby
		  config.vm.provision "ansible" do |ansible|
		    ansible.playbook = "playbook.yml"
		  end
		  ```
		- Expliquez dans votre rapport le fonctionnement, pourquoi comme ceci, il y à t'il mieux que cette implémentation ? ect..
	- **Automatisation via GIT CI/ID**
		- vous pouvez automatiser l'intégration continue via GitLab CI/CD. Avec un fichier `yml` pour automatiser la création de la VM et pousser les changements.
- Merci pour votre travail !
-
- ## Résultat S3 -> S4 
  **Pour l’automatisation via Gitlab CI/CD : **
  
  **Continuous Integration/Continuous Deployment**
  
  Il s’agit d’un outil intégré dans GitLab qui va automatiser le processus de développement logiciels. En gros il va nous permettre de s’assurer que chaque changement de code dans une application est testé, validé, et déployé de manière automatique et continue. 
  
  **CI (Continuous Integration) c’est quoi? **
- En gros, chaque fois qu’il y a un commit/push effectué dans GitLab → les tests automatiques seront déclenchés pour vérifier que ces modifications ne plantent pas l’application. 
  
  **CD(Continuous Deployment) c’est quoi?**
- Il permet donc de mettre à jour rapidement le code si la phase d’intégration est réussie. 
  
  **Comment utilisons-nous GitLab CI/CD? **
  
  Il repose sur l’utilisation de pipelines.
  
  C’est quoi un ***pipeline***? Il s’agit d’une série de jobs(c’est une tâche) qui sont exécutés pour chaque modification de code dans le dépôt. 
  
  *Pour mettre au clair sur le terme job → C’est bien une tâche précise, exécutée de manière isolée. *
  
  **Exemple de job : job de compilation (build) **
- ***Langage utilisé YAML***
  
  ![](https://lh7-rt.googleusercontent.com/docsz/AD_4nXdVsH-ye6yeqfDglQpbVcWAesKHFczKS-66sxrEgtwmJgznuzepQV10xx-lK1ogRAesIfAPo4zo_ZzIhxRsEkwQS2OaS5egmy3yVao6B1VP1Ms8aqkp9unIsR7Qf3LzfCAUZ3TPo5MXZNM5tYrzgJ9OgbZu?key=Dcjj4Oy5Oj-Cy-kL2DHrzw)
  
  **Comment allons-nous appliquer sur GitLab? **
- **Crée un fichier .gitlab-ci.yml **
  
  C’est ce fichier qui contiendra toutes les instructions que GitLab doit faire après chaque commit/push. 
  
  Voici un exemple de code pour s’inspirer : 
  
  ![](https://lh7-rt.googleusercontent.com/docsz/AD_4nXfktgZ7D58DcY5fiZHI91RvvSLxtGEFZ7gbDaeyEhhVdSnLlV9XfHLNndtsFMiXThcPE1JVb_bBjRWwul--pYkhs8VaWIHWMmFP9hI89fjVuZyDJ47rYNx2QMLwGNxHHv0MAmCLvddVnlr-MZDXhKCrdt6Y?key=Dcjj4Oy5Oj-Cy-kL2DHrzw)
  
  ***Stage ***: nous permet de voir les différentes étapes du pipelines.
- Donc ici on sait qu' après chaque commit/push il y aura une compilation, le code sera testé et enfin déployé. 
  
  ***Job ***: Tâches exécuter dans chaque étapes.
- **Comment déclencher le pipeline automatiquement? **
	- Après chaque commit/push le fichier .gitlab-ci.yml est lu est le pipeline est déclenché automatiquement
	  
	  Donc si je résume un peu… cet outil va nous permettre d’automatiser la création, les tests et le déploiement des applications. 
	  
	  **Ansible **
	  
	  **Qu’est ce que c’est Ansible? **
	  
	  C’est un outil open-source d’automatisation informatique qui permet de gérer les configurations, déployer des applications. Il ne nécessite pas d’installer un logiciel sur chaque machine (d’agents)  distantes. 
	  
	  **Pourquoi utilisons-nous Ansible? **
- Il permet d’automatiser des tâches répétitives, exemple l’installation et màj des logiciels etc.
- Permet de gérer la configuration des systèmes de manières centralisée → on sait donc que tous les serveurs seront configurés de la même manière (plus cohérents)
- Facilite le déploiement d’applications sur plusieurs serveurs simultanément.
- Il n’a pas besoin d’installer un logiciel sur chaque machine (on appel ca un agent) → il fonctionne via SSH (plus facile à mettre en place) 
  
  **Comment configurer Ansible? **
- à travers des fichiers de textes écrit en **YAML **→ on appel ceci de **PLAYBOOKS** 
  
  **Comment faire fonctionner Ansible?**
- Organiser le fichier “**inventory**”, il s’agit d’un fichier qui contient la liste de tous les serveurs sur lesquels Ansible doit exécuter les tâches. C’est comme ça qu'il gère plusieurs machines.
- Mettre en place le “**playbook**” (fichier en YAML), c’est donc dans ce fichier qu’il définit toutes une série de tâches que je veux exécuter sur toutes les machines.
  
  Rappel : chaque tâches est une action bien spécifique. 
  
  Un playbook ressemble à ceci : 
  
  ![](https://lh7-rt.googleusercontent.com/docsz/AD_4nXcZCWwRRYM4ZQFB65i9ukWq_powR-dNxvgkr5lENiWdr1uLlnqUxc8HD1l6EXU2ocmBPrXCy-eQcp93HNgORm9JS8tTYzu1QIngH7PUIxbV_vmrV5ZLMHX-x3sSNiliGzEoXYeiCLIlH9cxjBH-jmORCxAp?key=Dcjj4Oy5Oj-Cy-kL2DHrzw)
  
  Ici le playbook définit des tâches pour installer le serveur web Apache sur toutes les machines du webservers. 
  
  **Comment exécuter le playbook? **
  
  → via une commande Ansible : **ansible-playbook my-playbook.yml**
  
  **Comment verifier?** 
  
  → Ansible après l’exécution de la commande précédente affiche le résultat de chaque tâche.
  
  **Avantages ?**
- **Scalabilité **: que ce soit 10 ou 10000 serveurs il fait exactement pareil.
- **Reproductibilité **: Nous garantit que chaque machine est configurée de manière identique
- **Facilité de maintenance: **il s’agit simplement de modifier le playbook et de l'exécuter. 
  
  **Comment allons-nous mettre en place tout ça?**
- Mettre en place GitLab CI/CD (l’appel à Ansible s’y trouvera)
- mettre en place le fichier de git, pour définir quand et comment Ansible sera exécuté.
  
  mettre en place le terraform pour les machines distantes 
  
  installer les prerequis
- ### **Qui ? Quoi ? Comment ? Pourquoi ? Quelles difficultés ?**
- **Qui** : L'équipe de développement est composée de développeurs, d'un Scrum Master, et d'un Product Owner.
- **Quoi** : Nous allons implémenter un pipeline d'intégration continue (CI/CD) avec GitLab, en utilisant Ansible pour automatiser la configuration des serveurs, et Vagrant pour gérer les environnements de développement local.
- **Comment** : Les développeurs commit leurs changements dans GitLab, déclenchant automatiquement le pipeline CI/CD qui va provisionner une machine virtuelle avec Vagrant, exécuter un playbook Ansible pour installer les dépendances (Apache, Git, Python), et tester l'application.
- **Pourquoi** : L'objectif est de réduire les erreurs humaines, d'accélérer le déploiement et d'assurer que le même environnement est reproduit en développement et en production. Cela permet aussi de maintenir une meilleure qualité du code à chaque changement intégré.
- **Difficultés** : La principale difficulté pourrait être l'intégration des différents outils (Vagrant, Ansible, GitLab CI/CD) et de garantir que le pipeline fonctionne de manière fluide sans rupture de compatibilité ou erreurs.
- ### **Plan**
- **Objectifs** :
	- Mettre en place un système CI/CD automatisé.
	- Utiliser des outils comme Vagrant et Ansible pour reproduire l'environnement de développement en local et en production.
- **Avantages** :
	- Réduction des erreurs manuelles.
	- Automatisation complète du processus de déploiement.
	- Environnements identiques entre développement et production.
- **Inconvénients** :
	- Complexité de la mise en place initiale.
	- Courbe d'apprentissage pour maîtriser les outils (Ansible, GitLab CI/CD).
- ### **Do**
- **Action** :
	- Implémenter le pipeline CI/CD sur GitLab avec les fichiers .gitlab-ci.yml qui déclencheront l'installation automatique via Ansible et la gestion des VM via Vagrant.
- **Indicateurs** :
	- **KPI** : Temps de déploiement réduit, taux de succès des builds, nombre d'erreurs rencontrées.
	  
	  **SMART** :
- *Specific* : Le pipeline doit être capable d'automatiser 100 % des déploiements.
- *Measurable* : Réduire le temps de déploiement de 20 %.
- *Achievable* : Possible avec les compétences de l'équipe actuelle.
- *Relevant* : Répond aux besoins de l'équipe de développement.
- *Time-bound* : Doit être opérationnel dans les 2 mois.
  
  **CALMS** :
- **Culture** : Encourager l'équipe à adopter les bonnes pratiques DevOps.
- **Automation** : Automatisation complète du déploiement.
- **Lean** : Simplifier le processus de développement et de livraison.
- **Measurement** : Suivre les indicateurs clés de performance (KPI).
- **Sharing** : Partager les bonnes pratiques et les succès.
- ### **Check**
- **Évaluation** :
	- Vérifier si le pipeline CI/CD permet bien l'installation automatique de l'infrastructure avec Ansible et Vagrant.
	- Mesurer les indicateurs (temps de déploiement, taux d'échecs, etc.).
	- Ajuster les configurations selon les résultats des premiers tests.
- ### **Act**
- **Corrections** :
	- Si des problèmes surviennent (erreurs dans le pipeline, échec d'installation de paquets, etc.), faire les ajustements nécessaires dans les fichiers de configuration (playbook, Vagrantfile, .gitlab-ci.yml).
	- Améliorer les performances du pipeline si nécessaire en optimisant certaines étapes ou en automatisant encore plus de processus.
	  
	  Cela permet de structurer une approche basée sur Scrum pour gérer l'automatisation de l'infrastructure avec Vagrant, Ansible et GitLab CI/CD.
-
-
- ## **Seance 5**
	- **Objectifs :** D'un vagrantFile pour l'installation de la vm
	- **Date** : 03/11/2024
	- **Séance en cours :** 5
	- **Personnel ayant rédigé le rapport :** Alessian, çimenn, Berin
-
	- 1er problème : absence de la clé SSH pour la connexion et l’adresse IP 
	  
	  Solution : Le faire localement donc pas besoin d’établir la connexion.
	- Mettre en place les fichiers :
	- .gitlab-ci.yml (code qui s’exécute après chaque push) 
	  
	  ![](https://lh7-rt.googleusercontent.com/docsz/AD_4nXfBQM90M93g3ZocjIBiM8MBvPs64c148nkoyCSmNfqWjKswJ1rnE2X-O7l6y_vpyIIuX3l3o3FQrQx4cxqvPAI7HTqKgyBhynYVQXyzS4r4u7p5CojhR__X2TI4LF6F6xuS40cBX3656zFf4XXWyetWk0Zs?key=Dcjj4Oy5Oj-Cy-kL2DHrzw)
	- inventory (pour indiquer qu’on travail localement)
	  
	  ![](https://lh7-rt.googleusercontent.com/docsz/AD_4nXc78juorF7hldzOyB55-jr4GtpDDolEfnrSRBLZn7Qon90LrvIHZqUCK3bIvbElGv7fhyGdCydoGkpCvf65NT3Ntc4Le6HC6mW9CfVT810ilOBJQIITwJ0zy3nqTG80kUSXhoSnRGUVsqFajwWhg-khnwNg?key=Dcjj4Oy5Oj-Cy-kL2DHrzw)
	- ### **hetzner_servers :**
		- Cette partie indique le nom du groupe d'hôtes dans l'inventaire
	- ### **ansible_connection=local :**
		- Ce paramètre spécifie le type de connexion qu'Ansible doit utiliser pour se connecter à l'hôte spécifié. Dans ce cas,  local   signifie qu'Ansible doit exécuter les tâches localement sur l'hôte spécifié (qui est  localhost )
	- **shutdown_hetzner_ssh.yml**
		- (code qui sera appelé dans .gitlab-ci.yml)
		  
		  ![](https://lh7-rt.googleusercontent.com/docsz/AD_4nXci8CTzeJuuqwqgrB6Y2xu4k4EDyDIOxuI5As-keBI5sJbBbfc2jlfR0-QIj1jSmLcvmCmZ8C-KWHfqfm9JIZBlWAftBVlbtJu1Km_5APqdb1PY82Tt5kNzFRnxLdEOlBPZ2Y_VSxgi6saMJ9B_UAOsrR1-?key=Dcjj4Oy5Oj-Cy-kL2DHrzw)
	- ### **hosts: hetzner_servers**
		- **Ce que cela signifie** : Ici, cela signifie que la tâche sera exécutée sur tous les serveurs qui appartiennent au groupe nommé hetzner_servers.
	- ### **tasks:**
		- C'est une section qui contient une liste de tâches à exécuter.
	- ### **ansible.builtin.command: "shutdown now":**
		- C'est la commande qui sera exécutée sur le serveur.
		  
		  Dans l'ensemble, ce code dit à Ansible : **"Sur les serveurs de Hetzner, exécute la commande pour éteindre immédiatement le serveur, en utilisant des droits d'administrateur."**
		  
		  **Second problème : pipeline failed car on a pas de Runner actif **
		  
		  **On a généré un Runner et on a lié avec le pipeline via les tags ! **
		  
		  ![](https://lh7-rt.googleusercontent.com/docsz/AD_4nXdHqPggbxQIqR8VGX_W1dOqSEauO-iAbiM8spsaCkRoDoNWcU5NuGeiHCItAWOr1EDYcaieqAiV6IzfugG29kSA87d5zgdxv2sWylT8MBmRk424uyAzT73HBsBuqkRZg4pVS55GIjRk6WfGi8Il2UTp-qQN?key=Dcjj4Oy5Oj-Cy-kL2DHrzw)
		  
		  ps : quand j’ai configuré le runner j’ai mis “local, shutdown” pour les tags pour pouvoir lié avec le pipeline ! 
		  
		  PS: lors de la configuration j’ai du installer Gitlab-Runner, je dois le lancer localement ![](https://lh7-rt.googleusercontent.com/docsz/AD_4nXfKXbBZ5LEAznav4OYDLplYgVIdkjMCBk95Cv52pgjN3MEfP585ZaAqPUPs0wRYZfBt3g9FMd7IfW6yZ9gUAYMYuT8KG4TOFgJtptUty5jsf1qSmB8dxQwN-Ke8oPUYXwAfSmiJocg9K6vJh7PLWsIuxS0?key=Dcjj4Oy5Oj-Cy-kL2DHrzw)
		  
		  **Troisième problème : pipeline ne reconnaît pas ansible-playbook **
		  
		  **Solution : installation d’Ansible à la machine **
		  
		  ![](https://lh7-rt.googleusercontent.com/docsz/AD_4nXfSf1zHlY98qcqzuM5X-OGVe97AlyzjBjm1aRiJn23_2HTZYP9vYHH901Zfp9Zg6sYZoqBUaLsL5vXh_7plxmJ-KYlgWwz-ExC4Uun6bLT5BF1pFik1yWUpqdcP0560h2oQZuL1I4tYSb83hJwG35ANovGX?key=Dcjj4Oy5Oj-Cy-kL2DHrzw)
		  
		  On a du mettre une petite réunion avec les membres de l’équipe pour résoudre le problème. 
		  
		  Ajout des fichiers manquants pour le fonctionnement
		  
		  **Vagrantfile:**
		  
		  **![](https://lh7-rt.googleusercontent.com/docsz/AD_4nXex3svKlGTjtFVuSiph6CAU3FW__NkXKMfgiWwXppok946sms6_0USQ9Nwc0nDONRheVr--5RV23VX9KX6AuJcCSPvYf74HC11aDyUiMHoDFSNTybm33XbdHp1Vxyec8GWaymzdHjkpMAMUc8dX5qOXpE0?key=Dcjj4Oy5Oj-Cy-kL2DHrzw)**
		  
		  **Playbook.yml:**
		  
		  **![](https://lh7-rt.googleusercontent.com/docsz/AD_4nXfQibB_GA0ocQQey2598SIBAeQfFPmmWGMsfORtJFvobFgCJyPY_0ldsnHx5riMGPMoXRAQmDtr97hCYT4ubANUiuz-J14z1o9aM3ogQP6ZI2aLw7MJxYLSeExVCzWC6wRSPyxHwSmkPuG0-Zt5so2a2EHX?key=Dcjj4Oy5Oj-Cy-kL2DHrzw)**
	- ### **hosts: hetzner_servers**
		- Ce que c'est : Cela indique sur quels serveurs Ansible va exécuter les tâches.
	- ### **become: yes**
		- Ce que c'est : Cela indique qu'Ansible doit utiliser des privilèges administratifs (comme un super-utilisateur).
	- ### **tasks:**
		- Ce que c'est : C'est la section où l'on définit les actions à exécuter.
		- Ce que cela signifie : Vous allez lister les tâches que vous voulez que Ansible exécute.
		  
		  **.gitlab-ci.yml : **
		  
		  **![](https://lh7-rt.googleusercontent.com/docsz/AD_4nXc-WJhD6YcmSUhP-RpmyMkxVf-CF2eIH2UKqNc2epzbAQMWteIuamztUxo5NExRBFj_Egeo2oam8_5KkHBocm9AC8rWpe42uKuEXQ6ZsJXPU_5L9ud1CpvFEFZp_TEnU27U3WvGKxovJzg5eJzRsooGXzU?key=Dcjj4Oy5Oj-Cy-kL2DHrzw)**
		  
		  **Structure du pipeline :**
	- **Stages : Trois étapes principales :**
		- **build : Construction de l'infrastructure.**
		- **deploy : Déploiement de l'application.**
		- **shutdown : Arrêt du serveur Hetzner.**
	- ### **Étape ** **build** ** :**
		- **Actions :**
			- **Met à jour les paquets.**
			- **Installe Vagrant.**
			- **Démarre la VM avec Vagrant.**
		- **Condition : S'exécute uniquement sur la branche ****main****.**
	- ### **Étape ** **deploy** ** :**
		- **Actions :**
			- **Crée un dossier temporaire.**
			- **Copie des fichiers à déployer.**
			- **Exécute un playbook Ansible pour déployer l'application.**
		- **Sauvegarde : Conserve le dossier ****public****.**
		- **Condition : S'exécute uniquement sur la branche ****main****.**
	- ### **Étape ** **shutdown** ** :**
		- **Action : Exécute un playbook pour arrêter le serveur Hetzner.**
		- **Condition : S'exécute uniquement sur la branche ****main****.**
		  **Cela décrit les étapes et les actions de votre pipeline d'automatisation.**
	-
	- ### **Résumé des Liens**
	- **Vagrantfile :**
		- **Crée et configure la machine virtuelle (VM) Ubuntu.**
		- **Exécute des scripts pour installer Ansible sur cette VM.**
	- **Inventaire Ansible :**
		- **Définit les serveurs cibles (comme la VM) que Ansible doit gérer.**
		- **Utilisé par les playbooks pour identifier où exécuter les tâches.**
	- **Playbooks Ansible (playbook.yml et shutdown_hetzner_ssh.yml) :**
		- **Contiennent les instructions pour installer des logiciels, déployer l'application, ou arrêter le serveur.**
		- **Sont appelés par le pipeline pour exécuter des tâches spécifiques sur les serveurs.**
	- **Pipeline CI/CD :**
		- **Organise les étapes de construction, déploiement et arrêt du serveur.**
		- **Exécute le Vagrantfile pour créer l'environnement, les playbooks pour effectuer les installations et les déploiements, et enfin, pour arrêter le serveur.**
		-
	- ### **Ce que font tous ces fichiers**
	- **Vagrantfile :**
		- **Crée une machine virtuelle Ubuntu, installe Ansible et prépare l'environnement pour le déploiement.****	**
	- **Inventaire Ansible :**
		- **Indique à Ansible où exécuter les commandes, en listant les serveurs (dans ce cas, la VM créée par Vagrant).**
	- **Playbooks Ansible :**
		- **playbook.yml : Installe les paquets nécessaires (comme Apache, Python, Git) sur les serveurs.**
		- **shutdown_hetzner_ssh.yml : Arrête le serveur Hetzner ou toute autre cible définie dans l'inventaire.**
	- **Pipeline CI/CD :**
		- **Définit l'ordre des opérations à exécuter : construit l'environnement avec Vagrant, déploie l'application en utilisant Ansible, et arrête le serveur après le déploiement.**
		  
		  **Commandes pour utiliser Vagrant:**
		  
		  **vagrant up**** : Démarre et configure une nouvelle ou une VM existante, exécutant les scripts de provisionnement si nécessaire.**
		  
		  **vagrant provision**** : Exécute les scripts de provisionnement sur une VM déjà en cours d'exécution pour appliquer des changements ou mises à jour.**
		  
		  **vagrant ssh**** : Permet de se connecter à la VM pour exécuter des commandes directement sur le système d'exploitation de la machine virtuelle.**
		  
		  **vagrant halt:****est utilisée pour éteindre proprement la machine virtuelle.**
		  
		  **
	-