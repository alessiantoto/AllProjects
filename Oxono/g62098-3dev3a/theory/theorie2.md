## Question 1

# 1.
Un matricule de l’ESI

Regex : ^g[0-9]{5}$

# 2.
Une liste de matricules de l’ESI

Regex : ^(g[0-9]{5})(\s+g[0-9]{5})*$

# 3.
Un texte commençant par « Bonjour » et terminant par « merci. »

Regex : ^Bonjour.*merci\.$

# 4.
Commandes de type add circle 12 10 5 r

Regex : ^add\s+circle\s+[0-9]+\s+[0-9]+\s+[0-9]+\s+\w$

# 5.
Commandes de type move 11 -5 3

Regex : ^move\s+[0-9]+\s+-?[0-9]+\s+-?[0-9]+$

## Question 2


Le group zéro (group(0)) contient la correspondance complète de l'expression régulière, c'est-à-dire toute la chaîne qui a été trouvée par le modèle.

Si on décommente System.out.println("???: " + matcher.group(0));, cela affichera la chaîne complète correspondant à l'expression régulière, ici la date "16-09-2024".