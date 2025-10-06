- Date : **21/10/2024**
-
- ## **Gestion des clés**
	- Le plan de gestion des clés est le suivant :
	   Les superviseurs auront accès au serveur en premier, et la clé sera correctement sécurisée. Ils créeront de nouvelles clés SSH pour les équipes ayant besoin d'accéder au serveur et les ajouteront dans le fichier "authorized_keys". À intervalles réguliers (1 jour / 3 jours), les nouvelles clés SSH seront régénérées pour réduire le risque d'attaque en cas de fuite de clé ou autre.
	  De plus, une personne en plus de l'équipe de sécurité dans chaque groupe :
	- E12 : Paraschiv Olivia (60085)
	- E11, E13 : Ghorafi Moncef (60947)
	  
	  vérifiera à intervalles très courts après son ouverture que le serveur n'est ni ouvert ni vulnérable.