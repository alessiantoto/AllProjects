## Question 1
# 1.
Le point p est créé avec le constructeur sans paramètre, ce qui initialise le point à 
(0.0, 0.0)
move(2, 2) est appelée, ce qui déplace le point de
(2.0, 2.0)

DONC
(0.0, 0.0)
(2.0, 2.0)


# 2.

TestPoint.java:1: error: class TestPoint is public, should be declared in a file named TestPoint.java

car une classe public doit être définie dans un fichier dont le nom correspond au nom de la classe.

# 3.

la classe devient package-private, ce qui signifie qu'elle est accessible uniquement dans le même package
En l'occurrence, le fichier contient encore la classe TestPoint dans le même package (oo_basics), donc il n'y a pas d'erreur dans ce cas. Le programme fonctionnera toujours correctement.

## Question 2

# 1.
TestPoint.java:31: error: x has private access in Point
        System.out.println(p.x);

# 2.

(0.0, 0.0)
méthode move(int, int)
(2.0, 2.0)

# 3.

Point.java:22: error: move(double,double) is already defined in class Point
    public boolean move(double dx, double dy) {
           ^
1 error

## Question 3

# 1.
Recursive call to constructor 'Point()'
Explication : Le constructeur sans paramètre Point() appelle récursivement this(0, 0), ce qui entraîne une boucle infinie de récursivité et une erreur de compilation.

# 2.
No constructor 'Point()' exists
Explication : Lorsque le constructeur sans paramètre est supprimé, vous ne pouvez plus créer un objet Point sans fournir des arguments, ce qui entraîne une erreur si vous essayez de créer un objet sans arguments.

# 3.
Point() has no default constructor
Explication : Si aucun constructeur n'est défini, Java crée un constructeur par défaut. Cependant, si la classe ne définit aucun constructeur explicite et que vous essayez de créer un objet sans arguments (par exemple new Point()), Java génère un constructeur par défaut si aucune surcharge n'existe. Sans constructeur défini explicitement, cette situation peut générer une erreur si des paramètres sont attendus.


## Question 4

# 1.

Circle : [(0.0, 0.0), 5.0]
Circle : [(2.0, 5.0), 5.0]
Circle : [(2.0, 5.0), 10.0]


# 2.

Il y a une seule instance de la classe Point créée dans ce programme :

Ligne 46 : Point p = new Point();
Cette ligne crée un seul objet Point avec les coordonnées (0, 0).
Ce même objet Point est utilisé comme centre du cercle dans l'instance Circle à la ligne 47. Aucun autre objet Point n'est créé pendant l'exécution du programme.


## Question 5

# 1.
Circle : [(0.0, 0.0), 5.0]
Circle : [(2.0, 5.0), 5.0]
Circle : [(0.0, 0.0), 5.0]

# 2.
2 de point et une de cercle

# 3.
Circle : [(0.0, 0.0), 5.0]
Circle : [(0.0, 0.0), 5.0]
Circle : [(-2.0, -5.0), 5.0]

# 4.
il n'y a plus de deplacement
Circle : [(0.0, 0.0), 5.0]
Circle : [(0.0, 0.0), 5.0]
Circle : [(0.0, 0.0), 5.0]

## Question 6

# 1.
(3.0, 6.0) - FF0000FF
x: 3.0
color: FF0000FF

# 2.
Erreur de compilation : La méthode getColor() n’est pas définie dans la classe Point.
Si on supprime/comment cette ligne problématique :

Il n’y a plus d’erreur. Les autres appels aux méthodes définies dans Point (move, getX, etc.) fonctionnent correctement.

# 3.

Non, erreur de compilation :

La classe Point ne peut pas être castée en ColoredPoint car tous les objets Point ne sont pas des ColoredPoint.
Raison : Un objet Point n’a pas l’attribut color ni les méthodes spécifiques à ColoredPoint.

# 4.

Erreur de compilation :

Les attributs x et y de Point sont private. Ils ne sont pas accessibles directement dans ColoredPoint.
Solution : Utiliser les méthodes getX() et getY()

# 5.

Erreur de compilation :

Cela crée une récursion d’héritage (une boucle infinie) car Point devient la sous-classe et la superclasse de ColoredPoint en même temps.
Java ne permet pas les relations d’héritage cycliques.

# 6.

Une classe déclarée final ne peut pas être héritée. Cela empêche la déclaration de ColoredPoint comme une sous-classe de Point.

## Question 7

# 1. 
Le programme affiche les informations suivantes : l'objet ColoredPoint, la valeur de la coordonnée x et la couleur associée au point.

# 2. 
Cette ligne ne génère aucune erreur, car une variable de type Point peut référencer une instance de ColoredPoint grâce à la relation d'héritage entre les deux classes.
# 3.
Non, cette ligne n'est pas valide. Point n'étant pas une sous-classe de ColoredPoint, il est impossible de convertir ou d'assigner un objet Point à une variable de type ColoredPoint.
# 4.
Non, il est impossible d'utiliser directement this.x et this.y dans ColoredPoint, car ces attributs sont définis avec un accès private dans la classe Point, ce qui les rend inaccessibles aux sous-classes.
# 5.
Une erreur de compilation survient si Point tente d'étendre ColoredPoint, car cela crée une contradiction dans l'héritage en Java, où une classe ne peut pas être à la fois une sous-classe et une superclasse.
# 6.
Une erreur de compilation apparaît dans la classe ColoredPoint, car elle ne peut pas hériter de Point si Point est déclaré final, rendant l'héritage impossible.

## Question 8
# 1.
Cela entraîne une erreur de compilatio l'appel à super() doit obligatoirement être la première instruction dans un constructeur.
# 2.
Une erreur de compilation survient si la classe Point ne possède pas de constructeur sans paramètres. La ligne super(x, y) appelle explicitement le constructeur de la super-classe Point pour initialiser les attributs avec les valeurs spécifiées.
# 3.
Non, l'erreur est résolue lorsque le constructeur par défaut appelle un autre constructeur avec des paramètres.


## Question 9

# 1.
Affichage :
constructor of A
constructor of B
constructor of C

# 2.
Affichage :
constructor of A
constructor of B

# 3.
Ici, le constructeur de C est remplacé par un constructeur par défaut.
La classe Object dispose d’un constructeur par défaut.

## Question 10

# 1.
Affichage :
(0.0, 0.0) - not pinned
(1.0, 1.0) - pinned

# 2.
Il s’agit de celle de PinnablePoint car l’objet référencé par la variable à ce moment-là est une instance de PinnablePoint.

# 3.
Cela génère une erreur de compilation.

# 4.
Non, car IllegalStateException est une exception non vérifiée (unchecked exception).

# 5.
Oui, une erreur de compilation apparaît, car la méthode move doit déclarer l'exception qu'elle peut lancer.
# 6.
Oui, cela génère également une erreur de compilation.
# 7.
Oui, une autre erreur de compilation apparaît : dans une méthode redéfinie, le type de retour ne peut pas être plus général que celui de la méthode définie dans la super-classe.
# 8.
Cela produit une erreur de compilation.
# 9.
Cela appelle la méthode move définie dans la super-classe Point, ce qui déplace le point conformément à son comportement d’origine.


## Question 11

# 1.
Cela provoque une erreur de compilation, car les méthodes dans une interface sont implicitement public et ne peuvent pas être protégées (protected).

# 2.
Cela fonctionne normalement.

# 3.
Affichage :
(2.0, 3.0)
(3.0, 4.0)