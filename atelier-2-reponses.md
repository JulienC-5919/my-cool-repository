Question 1

Est-ce que ce bout de code est légal?
```java
try {

}
finally{ 

}
```
Réponse : oui

Question 2

Est-ce une bonne façon de faire? Pourquoi?
```java
try {

}
catch (Exception e) {

}
```
Réponse : Non, il est mieux d'utiliser un type d'exception précis.

Question 3
Est-ce qu'il y a un problème avec le gestionnaire d'exceptions du code suivant?

```java
try {

}
catch (Exception e) {

}
catch (ArithmeticException e) {

}
```
Réponse : Oui, toutes les exceptions sont attrapées par le bloc 'catch (Exception e) ...', alors le bloc 'catch (ArithmeticException e) ...' ne sera jamais exécuté.

Question 4
Quel type de message va s'afficher en ligne de commande suite à l'exécution de ceci:

```java
int[] A;
A[0] = 0;
```

Réponse : Erreur de compilation

