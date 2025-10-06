# Reponses aux questions

## Question 1

### 1. Quelle erreur leve le compilateur si vous tentez d ajouter la ligne ci-dessus ? box.setElement(new Double(43));

error: incompatible types: Double cannot be converted to Integer
La classe Box<T> est definie de maniere a contenir un element de type T. Dans votre cas, T est defini comme Integer a la declaration : Box<Integer>.

### 2. Lors de l’instanciation et de l’assignation à la ligne  Box<Integer> box = new Box<Integer>(new Integer(42))

warning: [deprecation] Integer(int) in Integer has been deprecated

cette facon de creer un objet Integer est ancienne et n'est plus recommandee dans les nouvelles versions de Java.

Pourquoi cet avertissement ?
Java a introduit une fonctionnalite appelee autoboxing. Cela signifie que Java peut automatiquement convertir un type primitif comme int en un objet Integer sans que vous ayez a utiliser new Integer(42).

Box<Integer> box = new Box<>(42);  // Plus simple et sans avertissement

### Question 2

#### A votre avis, le type Box<Integer> est-il un sous-type du type Box<Object> ? En d autres mots, peut-on ecrire
Box<Object> box = new Box<Integer>(42);

En Java, les types generiques ne suivent pas la relation de sous-typage classique. Ce qui signifie que meme si Integer est un sous-type de Object, Box<Integer> n'est pas un sous-type de Box<Object>.

Si cela fonctionnait, cela voudrait dire que vous pourriez ajouter n'importe quel type d'objets dans la boite box qui est supposee contenir des Integer, ce qui provoquerait des incoherences. Par exemple :

box.setElement(new Double(43));  // Ajoute un Double dans une Box qui contient des Integer

Wildcard avec un supertype :
Box<? super Integer> box = new Box<Integer>(42);

Cela signifie que la boite peut contenir des elements de type Integer ou de n'importe quel supertype de Integer (comme Object).

Wildcard avec un sous-type :
Box<? extends Object> box = new Box<Integer>(42);

### Question 3

1. error: incompatible types: Box<Integer> cannot be converted to Box<Object>

2. Box<Integer> box = new Box<Integer>(new Integer(42));
Le compilateur ne levera pas d'erreur a cette ligne. En effet, cette ligne est tout a fait correcte, car elle cree une instance de Box avec un type generique Integer, et elle stocke un objet Integer (42).

une erreur a la ligne 11 :
error: incompatible types: Double cannot be converted to Integer

box.setElement(new Double(43));

tente d'inserer un objet de type Double dans une boite (Box<Integer>) qui est parametree pour ne contenir que des Integer

Difference principale :
T : Un type parametre qui est nommee et utilise de manniere cohhrente a travers une classe ou une methode. Il est fixe une fois defini.
? : Un joker qui represente un type inconnu. Il est plus general et utilise pour les situations ou vous ne voulez pas vous engager sur un type specifique, mais vous pouvez le restreindre avec des bornes comme extends ou super.

La methode peut lire les elements de la liste, mais ne peut pas modifier la liste, car le type exact des elements n'est pas connu.

### Question 4

ourquoi l'erreur a la ligne 12 ?
La methode setElement(T element) dans la classe Box<T> ne peut pas etre utilisee ici, car box a ete declare avec le type Box<?>. Le joker ? signifie que le compilateur ne sait pas quel type exact est contenu dans box. Par consequent, il ne peut pas determiner si Integer est un type compatible avec le type contenu dans box.

Pour permettre l'ajout d'elements, vous devriez utiliser des types specifiques ou des bornes, comme Box<? super Integer>, qui permettrait d'ajouter des Integer tout en garantissant que le type de base est adequat



# Borne superieure

public class TestUpperBoundedWildcard {
    public static void printNumbers(List<? extends Number> list) {
        for (Number n : list) {
            System.out.println(n);
        }
    }

    public static void main(String[] args) {
        List<Integer> intList = List.of(1, 2, 3);
        List<Double> doubleList = List.of(1.1, 2.2, 3.3);

        printNumbers(intList); // valide, Integer est un sous-type de Number
        printNumbers(doubleList); // valide, Double est un sous-type de Number
    }
}

# Borne inferieure

public class TestLowerBoundedWildcard {
    public static void addNumbers(List<? super Integer> list) {
        list.add(1);
        list.add(2);
    }

    public static void main(String[] args) {
        List<Number> numberList = new ArrayList<>();
        List<Object> objectList = new ArrayList<>();

        addNumbers(numberList); // valide, Number est un super-type d'Integer
        addNumbers(objectList); // valide, Object est un super-type d'Integer
    }
}

## Question 5

En retirant extends Comparable<T>, vous supprimez la garantie que les objets de type T sont comparables. car le compilateur ne peut pas garantir que T implemente cette methode

## Question 6

error: incompatible types: Integer cannot be converted to T
entraine une erreur de compilation parce que le compilateur ne peut pas garantir que le type Integer est compatible avec le type Number dans le contexte de l'instanciation de la classe generique Pair. Chaque instance de Pair doit etre homogene, ce qui signifie que les deux valeurs doivent etre du meme type ou d'un type compatible, respectant ainsi les contraintes de typage imposees par la classe Pair.
Le type Number est trop large, et Integer est un type specifique. Le compilateur attend des objets de type Number mais ne peut pas garantir qu'un Integer est approprie dans un Pair<Number> sans un cast explicite.

Borne inferieure (super) : Utilisee pour restreindre un parametre de type a un type specifique ou a ses sous-types.
Borne superieure (extends) : Permet d'accepter des types specifiques ou des sous-types.

## Question 7


1.


class Animal {  qui est un super-type de Dog
    @Override
    public String toString() {
        return "Animal";
    }
}

class Dog extends Animal {
    @Override
    public String toString() {
        return "Dog";
    }
}


public class TestCopy {
    public static void main(String[] args) {
        List<Dog> dogs = new ArrayList<>();
        dogs.add(new Dog());
        dogs.add(new Dog());

        // Liste d'animaux
        List<Animal> animals = new ArrayList<>();

        // Utilisation de la méthode copy
        copy(animals, dogs); // Appel valide
    }

    static <T> void copy(List<? super T> d, List<? extends T> s) {
        for (T item : s) {
            d.add(item);
        }
    }
}

copy

List<? super T> d :
Cela signifie que d peut etre une liste de type T ou de n'importe quel super-type de T.
Par exemple, si T est Dog, alors d pourrait etre une List<Dog> ou une List<Animal>.
List<? extends T> s :
Cela signifie que s peut etre une liste de type T ou de n'importe quel sous-type de T.
Ainsi, si T est Dog, alors s pourrait etre une List<Dog> ou une List<Puppy> (ou Puppy est un sous-type de Dog).

copy2(animals, dogs); // Cela ne compile pas
Le compilateur signalerait une erreur, car animals et dogs ne sont pas du meme type (List<Animal> n'est pas le meme type que List<Dog>), et copy2 exige que les deux listes soient du meme type.

La methode copy est plus generale car elle permet de copier des elements d'une liste de sous-types (ici Dog) vers une liste de super-types (ici Animal).
En revanche, copy2 necessite que les deux listes soient du meme type, ce qui limite son utilisation.

2.

public class TestSort {
    public static void main(String[] args) {
        // Création d'une liste d'Integer
        List<Integer> list = new ArrayList<Integer>();
        list.add(3);
        list.add(1);
        list.add(4);
        list.add(2);

        // Création d'un comparateur pour Integer
        Comparator<Integer> comparator = new Comparator<Integer>() {
            @Override
            public int compare(Integer o1, Integer o2) {
                return o1.compareTo(o2); // Comparaison naturelle
            }
        };

        // Appel de la méthode sort
        Collections.<Integer>sort(list, comparator);

        // Affichage de la liste triée
        System.out.println(list); // Affichera : [1, 2, 3, 4]
    }
}