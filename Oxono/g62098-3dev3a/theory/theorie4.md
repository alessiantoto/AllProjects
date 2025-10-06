#### Lambda
Classe nommée (MajorPredicate) :
Utilise une classe préexistante.
Pratique si le critère doit être réutilisé ailleurs dans le code.
Classe anonyme :
Définit la logique directement sans créer une nouvelle classe séparée.
Plus rapide à écrire si le critère est utilisé une seule fois.

Une expression lambda en Java est un moyen simple et rapide d'écrire du code, principalement pour les interfaces fonctionnelles, c'est-à-dire les interfaces avec une seule méthode. C'est un raccourci qui rend le code plus lisible et évite d'écrire des classes ou des méthodes anonymes.

Exemple avant les lambdas (classe anonyme) :
Imaginons qu'on veuille filtrer une liste de personnes pour trouver celles qui ont plus de 18 ans. On peut utiliser une classe anonyme comme dans l'exemple suivant :

List<Person> filteredList = filter(myList, new PersonPredicate() {
    @Override
    public boolean keepIt(Person p) {
        return p.getAge() > 18;
    }
});

Inconvénient : Le code est plus long, car on doit écrire toute la structure de la classe anonyme, même si on n'a besoin de cette logique qu'une seule fois.

List<Person> filteredList = filter(myList, p -> p.getAge() > 18);

p est le paramètre de la méthode (Person p dans keepIt).

## Question 1

1. List<Person> filteredList = filter(myList, p -> p.getName().startsWith("J"));

2. List<Person> filteredList = filter(myList, p -> p.getName().startsWith("J") && p.getAge() < 50);


public static <T> void sort(List<T> list, Comparator<? super T> c)

e type ? super T signifie que le comparateur peut accepter des types qui sont superclasses de T (c'est-à-dire T lui-même ou toute superclasse de T)

## Question 2

// Tri en fonction de la longueur avec une lambda
1. Collections.sort(strings, (first, second) -> Integer.compare(first.length(), second.length()));

// Tri en fonction de la première lettre avec une lambda
2. Collections.sort(strings, (first, second) -> Character.compare(first.charAt(0), second.charAt(0)));

## Question 3

1. Suppression du type d’un des paramètres :

Non accepté par le compilateur

Les types doivent être déclarés de manière cohérente pour tous les paramètres,
soit on omet tous les types, soit on les inclut tous. Ici, le type est spécifié pour w2
mais pas pour w1, ce qui entraîne une incohérence.

2. Suppression du type d’un des paramètres :

Même problème que précédemment 

3. Suppression du type des 2 paramètres :

Accepté par le compilateur

C'est une syntaxe valide. Si les types des paramètres peuvent être déduits par le compilateur, 
il n’est pas nécessaire de les spécifier explicitement. 
Le compilateur saura que w1 et w2 sont des chaînes de caractères (de type String).

4. Suppression du type et des parenthèses des paramètres :
Les parenthèses sont obligatoires autour des paramètres dans une expression lambda, même s’il n’y a que deux paramètres.
 On ne peut omettre les parenthèses que lorsqu'il y a un seul paramètre non typé.

5. Suppression du return :
Le mot-clé return est nécessaire lorsque le corps de la lambda est délimité par des accolades. 
Dans ce cas, il manque le return pour que la lambda fonctionne.

6. Suppression des accolades :
Non accepté par le compilateur

Les accolades {} sont obligatoires lorsque l’on utilise le mot-clé return. Ici, vous ne pouvez pas combiner return et omettre les accolades.

7. Suppression des accolades et du point-virgule :
Non accepté par le compilateur

Même problème que précédemment : sans accolades, le mot-clé return n’est pas autorisé. De plus, il manque un point-virgule à la fin de l’instruction.

8. Suppression du return et des accolades :
Accepté par le compilateur

C’est une syntaxe valide. Si l'expression lambda ne comporte qu’une seule instruction, 
il n'est pas nécessaire d'utiliser des accolades, ni le mot-clé return. La valeur de l’expression est implicitement renvoyée.

9. Suppression du return, des accolades et du point-virgule :
Non accepté par le compilateur

En Java, même dans une lambda, chaque instruction doit se terminer par un point-virgule. Ici, il manque le point-virgule à la fin de l'expression.
