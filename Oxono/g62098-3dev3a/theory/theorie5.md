### JAVA FX BASE

## Question 1
1. En augmentant ces valeurs, vous élargissez la zone d'affichage de l'interface graphique. Vous aurez donc plus d'espace pour afficher les composants de l'interface (boutons, labels, etc.).
Si vous réduisez les valeurs, vous diminuez la zone visible, et les éléments graphiques peuvent se retrouver partiellement cachés si la scène est trop petite.

2.  En utilisant StageStyle.TRANSPARENT, vous indiquez que la fenêtre de votre application doit être rendue transparente.

StageStyle.DECORATED :

Style de fenêtre par défaut avec bordures et titres. Ce style est le plus couramment utilisé.
StageStyle.UNDECORATED :

Une fenêtre sans bordure et sans titre. Utilisée souvent lorsque vous souhaitez un style personnalisé.
StageStyle.TRANSPARENT :

Fenêtre avec fond transparent, sans bordures ni titre.
StageStyle.UTILITY :

Fenêtre de style utilitaire, généralement utilisée pour des boîtes de dialogue ou des fenêtres secondaires. Ce style peut avoir une bordure simplifiée.
StageStyle.UNIFIED :

Style unifié qui fusionne les caractéristiques des styles décorés et utilitaires.

## Question 2

1. 
CheckBox	États possibles	Comportement
checkBox1	Sélectionnée, Désélectionnée	              Se comporte comme une case à cocher classique avec uniquement deux états (sélectionnée/désélectionnée).
checkBox2	Indéterminé, Sélectionnée, Désélectionnée	  Commence en étant indéterminée. Clic change l'état à sélectionnée, puis à désélectionnée.
checkBox3	Désélectionnée, Sélectionnée, Indéterminée	  Commence désélectionnée. Clics alternent entre sélectionnée, indéterminée et désélectionnée.

2. 
Sans les lignes de code d'alignement :

checkBox1 (ajoutée à la zone left) :
Si vous supprimez la ligne BorderPane.setAlignment(checkBox1, Pos.CENTER);, la case à cocher sera par défaut alignée en haut de la zone gauche du BorderPane. Cela peut donner l'apparence que la case à cocher est déplacée vers le haut, plutôt qu'au centre de la zone.
checkBox3 (ajoutée à la zone right) :
En supprimant BorderPane.setAlignment(checkBox3, Pos.CENTER);, cette case à cocher sera également alignée en haut de la zone droite du BorderPane. Elle ne sera donc pas centrée verticalement dans sa zone.

## Question 3

1. Le comportement de base reste similaire à celui d'un TextField, ce qui signifie que les utilisateurs peuvent entrer du texte, mais le texte est masqué. Les autres fonctionnalités comme la validation de la saisie ou l'obtention de la valeur du champ restent identiques.

2. Dans le code, le champ de texte (TextField) a une action associée qui se déclenche lorsque l'utilisateur appuie sur Enter. Cela est réalisé grâce à la méthode setOnAction() qui prend un EventHa
ndler<ActionEvent> comme paramètre.
Affichage du message :
Le code root.setBottom(test); place l'étiquette test (qui affiche "User name saved! You can't change it") en bas de la fenêtre. Cela signifie que, après avoir pressé Enter, un message s'affiche pour indiquer que le nom d'utilisateur a été sauvegardé.
Désactivation du champ de texte :

La ligne tfdUserName.setEditable(false); rend le champ de texte non modifiable. Cela signifie que l'utilisateur ne pourra plus changer le texte saisi après avoir appuyé sur Enter. Cela est utile pour verrouiller la valeur entrée, ce qui peut être souhaitable dans un contexte où l'utilisateur doit confirmer ou finaliser une entrée.

## Question 4

Lorsqu'un utilisateur clique sur le bouton "Print":
Récupération du texte : La méthode getText() est appelée sur l'objet txaUserName, qui est un TextArea. Cela récupère tout le texte actuellement saisi dans le TextArea.

Affichage dans la console : Le texte récupéré est ensuite affiché dans la console via System.out.println(). Cela signifie que le contenu du TextArea est imprimé dans la sortie standard (généralement la console ou le terminal).

## Question 5

Type de retour : La méthode getChildren() retourne un objet de type ObservableList<Node>, qui est une liste observable de nœuds (éléments UI).
Manipulation dynamique : Vous pouvez facilement ajouter ou retirer des nœuds à un conteneur, ce qui permet de modifier l'interface utilisateur à la volée.
La méthode getChildren() facilite la gestion des éléments UI de manière flexible et réactive, améliorant ainsi l'expérience utilisateur.

## Question 6

1. Changer les Vbox par Hbox

2. La méthode addAll() permet d'ajouter plusieurs éléments à la fois dans une liste, ce qui peut rendre le code plus concis.

3. La remarque sur le clipping dans VBox signifie que :

Débordement de contenu : Si un enfant est plus grand que la VBox, il peut déborder et ne pas être visible.
Problèmes d'interaction : Les éléments débordants peuvent devenir difficiles à cliquer ou à interagir.
Difficultés de responsivité : Cela peut nuire à l'apparence et à la fonctionnalité sur différentes tailles d'écran.

Pour gérer le clipping, voici quelques méthodes :

Utiliser le clip :

Définissez un clip sur la VBox :
vbox.setClip(new Rectangle(vbox.getWidth(), vbox.getHeight()));

-----------------------------------------
Ajuster les tailles minimales :


Ajustez les tailles minimales des enfants pour éviter le débordement :

button.setMinSize(100, 30);
------------------------------------------
Utiliser un ScrollPane :

Enveloppez la VBox dans un ScrollPane pour permettre le défilement :
ScrollPane scrollPane = new ScrollPane(vbox);

------------------------------------------
Contrôler la taille de la fenêtre :

Ajustez la taille de la fenêtre pour qu'elle soit suffisante pour contenir le contenu.


## Question 7

1. Lorsque vous placez plusieurs composants dans une même cellule d'un GridPane en JavaFX, ces composants sont superposés les uns sur les autres, par défaut. Contrairement à certains autres layouts (comme VBox ou HBox), un GridPane ne gère pas automatiquement la disposition des composants au sein d'une même cellule.

2. Alignement centré : Le label lblPassword (qui correspond au texte "Password" dans votre formulaire) sera maintenant centré horizontalement dans sa cellule du GridPane. Cela signifie que le texte "Password" ne sera plus aligné à droite, mais au milieu de la cellule où il se trouve.

3. Remplissage horizontal : Avec GridPane.setFillWidth(tfdPassword, true), le composant tfdPassword (le champ de texte pour le mot de passe) occupera toute la largeur disponible dans la cellule du GridPane où il est placé. Cela signifie que le champ de saisie s'étendra pour remplir complètement la largeur de sa colonne, en fonction de l'espace disponible.

