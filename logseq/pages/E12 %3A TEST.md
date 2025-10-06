## Objectif S3 **Test des rôles Ansible avec Molecule**
	- **Outils nécessaire pour la mise en place**
		- `Molecule` | `Ansible`
	- **Exemple d’un test Molecule** :
	  ```yaml
	  platforms:
	  	name: instance
	  	image: ubuntu:20.04
	  
	  provisioner:
	  	name: ansible
	  
	  verifier:
	  	name: testinfra
	  ```
	- Renseignez vous sur les différents point du code `verifier`, `provisioner`, `platforms` regarder si d'autre moyen de logiciel de test son possible, dite comment cela fonctionne, etc..
- Merci pour votre travail !
-
-
- ## Résultat S3->4S **Test des rôles Ansible avec Molecule**
- **### **Test Driven Development (TDD) - Développement Piloté par les Tests**
  
  Le **TDD** est une approche de développement où les tests sont écrits avant même de commencer à coder la fonctionnalité. L'idée principale est de créer un cycle de développement qui garantit que le code est constamment testé et que chaque fonctionnalité a un test qui valide son fonctionnement.
- #### **Les étapes du TDD :**
- **Écrire un test** : Avant de coder, un test est rédigé pour une nouvelle fonctionnalité ou un nouveau module.
- **Échouer le test** : Étant donné que la fonctionnalité n'est pas encore implémentée, le test doit échouer au départ.
- **Écrire le code** : Ensuite, le code nécessaire pour que le test réussisse est développé.
- **Rendre le test réussi** : Le test est réexécuté, et si le code est correct, le test doit passer.
- **Améliorer le code** : Une fois le test réussi, le code est refactorisé (amélioré) sans changer son comportement.
- ### **Full TDD**
  
  Le terme "Full TDD" fait référence à l’application complète et stricte de la méthodologie TDD pour l’ensemble d’un projet. Cela signifie que :
- Tous les aspects du code (backend, frontend, API, etc.) sont développés en suivant la méthode TDD.
- Chaque fonctionnalité, modification ou correction de bug est abordée en commençant par l'écriture d'un test.
- ### **Avantages de TDD :**
- **Qualité du code** : Le code est mieux testé et généralement de meilleure qualité.
- **Réduction des bugs** : Les tests écrits dès le début réduisent les erreurs potentielles.
  
  **Facilité de maintenance** : Le code e**
-
- **### **Intégration Continue (CI - Continuous Integration)**
  
  L'intégration continue est une pratique de développement où les développeurs intègrent fréquemment leurs modifications de code dans un référentiel centralisé, généralement plusieurs fois par jour. Chaque intégration est ensuite automatiquement testée à l'aide d'un pipeline de tests automatisés. L'objectif est d'identifier les erreurs rapidement, d'assurer la qualité du code, et d'éviter les problèmes d'intégration.
  
  Le déploiement continu peut signifier deux choses :
- **Continuous Delivery (Livraison Continue)** : Une fois que le code est validé et testé, il est automatiquement préparé pour être déployé en production. Le déploiement effectif nécessite une validation manuelle.
- **Continuous Deployment (Déploiement Continu)** : Le code validé et testé est automatiquement déployé en production sans intervention manuelle.
- ### **Avantages de CI/CD :**
- **Détection rapide des erreurs** : Les erreurs sont identifiées rapidement grâce aux tests automatisés.
- **Déploiement rapide** : Les nouvelles fonctionnalités et corrections de bugs peuvent être déployées plus rapidement.
- **Meilleure collaboration** : Les développeurs travaillent de manière plus cohérente et en continu, ce qui facilite l'intégration de leur code.
  
  Dans le contexte de notre projet, CI/CD permettra d'automatiser la construction, les tests et le déploiement de votre infrastructure, garantissant ainsi une configuration toujours à jour et cohérente.
- # **Tester ansible avec molecule**
- ## **Qu’est-ce que Molecule ?**
  
  Molecule est un outil qui te permet de tester tes playbooks Ansible dans un environnement contrôlé avant de les utiliser sur des serveurs réels. Il te permet de vérifier que tout fonctionne correctement avant de déployer tes changements.
- ## **Structure basique d’un fichier molecule.yml**
  
  Le fichier molecule.yml est un fichier de configuration utilisé par Molecule pour définir comment tu vas tester ton playbook Ansible. Il permet de spécifier où (sur quelle machine virtuelle, par exemple) et comment tu veux exécuter les tests.
  
  Un fichier molecule.yml contient plusieurs sections principales :
- **Driver** : Indique quelle technologie tu utilises pour créer les machines de test (par exemple Docker, VirtualBox, etc.).
- **Platforms** : Décrit les machines virtuelles sur lesquelles tu veux tester (comme une VM Ubuntu).
- **Provisioner** : Définit quel outil tu utilises pour exécuter tes playbooks Ansible (toujours Ansible ici).
- **Verifier** : Vérifie si les tests que tu écris passent ou échouent.
- **Dependency** (optionnel) : Gère les dépendances Ansible (ce qu'il faut installer avant d'exécuter le playbook).
- **Lint** (optionnel) : Vérifie que ton code est bien écrit.
- ## **Exemple du fichier molecule.yml**
  
  dependency:
  
  name: galaxy
  
  options:
  
    role_file: requirements.yml
  
  driver:
  
  name: docker
  
  platforms:
  
  - name: ubuntu_instance
  
    image: ubuntu:20.04
  
    pre_build_image: true
  
  lint:
  
  name: ansible-lint
  
  provisioner:
  
  name: ansible
  
  verifier:
  
  name: testinfra
- ## **1. Dependency (optionnel) :**
  
  Cette section s'occupe des dépendances nécessaires avant de lancer ton playbook. Par exemple, si ton playbook a besoin de rôles Ansible spécifiques, Molecule les installera avant d'exécuter les tests.
- **name: galaxy :** Galaxy est un dépôt public de rôles Ansible que tu peux installer. Cette ligne signifie que Molecule va vérifier s'il y a des dépendances à installer depuis Galaxy avant de lancer les tests.
- ## **2. Driver :**
  
  C'est la partie qui indique comment Molecule va créer les machines virtuelles où les tests seront effectués.
- **name: docker :** Cela signifie que Molecule va utiliser Docker pour créer une machine virtuelle (ou un container). Tu peux aussi utiliser d'autres options comme VirtualBox ou Vagrant, mais Docker est souvent plus simple pour les tests rapides.
- ## **3. Platforms :**
  
  Cette section décrit quelle machine virtuelle tu veux utiliser pour faire tes tests.
- **name: instance :** Le nom de la machine virtuelle que tu vas tester.
- **image: ubuntu:20.04 :** Cela signifie que tu veux utiliser une image Ubuntu 20.04. C’est la version d'Ubuntu sur laquelle ton playbook va s'exécuter.
- **pre_build_image: true :** Cela veut dire que Molecule va utiliser une image Docker déjà prête (pré-construite). Cela accélère les tests.
- ## **4. Lint (optionnel) :**
  
  Le Lint permet de vérifier la qualité de ton code Ansible, pour t'assurer qu'il respecte les bonnes pratiques. C'est comme un correcteur automatique qui te dit si ton code est propre et bien écrit.
- **name: ansible-lint :** Cela veut dire que Molecule va utiliser l’outil ansible-lint pour vérifier si ton playbook est bien écrit.
- ## **5. Provisionner :**
  
  Le provisioner indique quel outil tu utilises pour exécuter les tâches définies dans ton playbook. Ici, on utilise toujours Ansible.
- **name: ansible :** Cela signifie que c’est Ansible qui va être utilisé pour exécuter les tâches sur ta machine virtuelle (installer des paquets, copier des fichiers, etc.).
- ## **6. Verifier :**
  
  Le verifier est responsable de vérifier si les actions du playbook ont bien fonctionné. Ici, on utilise un outil appelé Testinfra pour écrire des tests automatisés.
- **name: testinfra :** Cela veut dire que Molecule va utiliser Testinfra (un outil basé sur Python) pour vérifier que tout est en ordre après l’exécution du playbook.
- ## **Pourquoi choisir Molecule ?**
  
  Molecule est un outil idéal pour tester des playbooks Ansible parce qu'il :
- Crée des environnements de test isolés (comme Docker).
- Automatise tout le processus de test.
- Vérifie si tes configurations fonctionnent correctement avec des tests automatisés.
- S'intègre bien avec des pipelines CI/CD.
- Est facile à utiliser pour les débutants avec des commandes simples.
- Te permet de tester sur différentes plateformes.
  
  **Notes prise lors de la séance du 8 octobre 2024:**
  
  **- Se mettre en lien avec l'équipe de sécurité. Si les tests ne sont pas sécurisé, ils ne doivent pas passé.**
  
  **- Tout tester via les tests. Par exemple, tester le serveur pour voir si il est allumé ensuite, une autre équipe l'éteint, et nous devons tester qu'il est bien éteint. Tester toute les fonctionnalités choisit. L'affichage peut se faire sous forme de message continu dans la console.**
  
  **- Bien regarder la notion de role chez aisnible, permet la structure des fichiers, mettre nos fichiers au bonne endroit.**
  
  **- Des qu'on peut essayer de faire nos test sur debian 64bit, par défaut. Si on utilise autre chose, avoir un argument fort. Soite tout le monde utilise le meme vagrant file, avec les meme config ou un fichier non versionné, qui permettra de customisé les paramètres **
  
  **