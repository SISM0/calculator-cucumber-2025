package calculator;

import visitor.Visitor;

/**
 * Racine de notre AST : toute Expression peut
 *  - être visitée (pour évaluation, affichage…)
 *  - être évaluée directement
 *  - être introspectée (profondeur, compte d’opérations et de nombres)
 */
public interface Expression {

   /**
    * Pour le pattern Visitor (évaluation, affichage, comptage…).
    */
   void accept(Visitor v);

   /**
    * Évalue récursivement l’expression.
    * @return résultat du calcul
    * @throws IllegalConstruction si la structure est invalide
    */
   double eval() throws IllegalConstruction;

   /**
    * Profondeur maximale de sous-expressions (Composite).
    */
   int countDepth();

   /**
    * Nombre total d’opérations contenues.
    */
   int countOps();

   /**
    * Nombre total de littéraux MyNumber contenus.
    */
   int countNbs();
}
