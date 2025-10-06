### JAVA FX EVENTS

## Question 1
Chaque fois que vous cliquez sur le bouton Insert, l'événement de clic passe par plusieurs "niveaux" de l'interface graphique, du plus grand au plus petit composant.
 Voici ce que ces messages signifient :

Stage Filter, Scene Filter, BorderPane Filter, HBox Filter : Ce sont des filtres ajoutés à différents composants de l'interface,
 et ils interceptent l'événement de clic avant que le bouton ne le traite.
Insert Button Filter, Insert Button Handler : Ces deux lignes montrent que l'événement de clic a finalement atteint le bouton Insert. 
Le filtre du bouton le capture en premier, puis le gestionnaire du bouton (Handler) l'exécute, ajoutant le texte dans la zone de texte.

## Question 2

1. Avec ce filtre en place, le TextField ne permettra pas à l'utilisateur de saisir du texte.
Tout caractère tapé dans le champ sera ignoré, et l'utilisateur ne verra pas le texte qu'il essaie d'entrer.
 Cela peut être utile dans des scénarios spécifiques où vous ne souhaitez pas que l'utilisateur saisisse des données,
  mais vous devez être prudent quant à son utilisation, car cela peut nuire à l'expérience utilisateur.

2. 
 
tfdCharacter.addEventFilter(KeyEvent.KEY_TYPED, e -> {
    String character = e.getCharacter();
    // Vérifie si le caractère saisi n'est pas un chiffre
    if (!character.matches("[0-9]")) {
        e.consume(); // Consomme l'événement si ce n'est pas un chiffre
    }
});

