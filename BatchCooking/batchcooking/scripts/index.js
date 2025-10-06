"use strict";
fetch("https://git.esi-bru.be/api/v4/projects/40922/repository/files/recipes-it3.json/raw")
    .then((response) => response.json())
    .then((data) => {
      ingredientDataGlobal=data.ingredientsData;
      categoriesGlobales=data.categories;
        loadCards(data);
        loadShoping(data);
    })
    .catch(alert);
    /**
     * @type {Recipe[]} recipeInPlanningForShopping La liste des recettes.
    */

    // Initialiser une liste vide pour les recettes planifiées pour les courses
    let recipeInPlanningForShopping = [];

    /**
     * @type {Object<string, IngredientData>} 
    */
    let ingredientDataGlobal;

    /**
     * @type {Object<string, string>} categories Description des catégories relatives aux ingrédients.
    */
    let categoriesGlobales;

  /**
 * Charger les cartes de recettes à partir des données fournies. 
 * @param {RecipesAndRelatedData} data
 */
  function loadCards(data) {
    const contenaireRecettes = $("#container-cards");

    for (let i = 0; i < data.recipes.length; i++) {   //boucle pour toutes les recettes
      const totalIngredients = new Set(); // Déclarer le Set en dehors de la fonction

      //fillShopping(data.recipes[i],data.units, data.ingredientsData, data.categories,totalIngredients)
      const nouvelleCarte = $("#card").contents().clone();  // Cloner le contenu de la carte de recette
      nouvelleCarte.attr("data-id", i);   //donne un id a la carte
      nouvelleCarte.find("img").attr("src", data.recipes[i].imageLink);
      nouvelleCarte.find(".text").text(data.recipes[i].recipeName);

      const nutritionalValuesSet = new Set(); // Set to store unique nutritional values

      // Parcourir les ingrédients de la recette actuelle
      for (let ingredient of data.recipes[i].ingredients) {
        const recette = data.ingredientsData[ingredient.ingredientName]
        const intakes = recette.intakes;

        for (let intake of intakes) {
              nutritionalValuesSet.add(intake);   // Ajouter les valeurs nutritionnelles à l'ensemble
          } 
      }
      for(let element of nutritionalValuesSet){
        const fixingContent = $("<li>").text(element);
        nouvelleCarte.find(".listeTags").append(fixingContent)
        fixingContent.addClass(element);    //on met les noms dans les li de l'ul et on ajoute la meme classe que leur nom
      }

        nutritionalValuesSet.clear(); // Réinitialiser le Set pour la prochaine itération
        nouvelleCarte.find('.buttonSelectioner').on("click", function () {
          
          if (nouvelleCarte.hasClass("selected")) {
            // La carte est déjà sélectionnée, donc la désélectionner
            nouvelleCarte.removeClass("selected");
          } else {
            // Retirez la classe "selected" de toutes les autres cartes

            // Désélectionner toutes les autres cartes
            contenaireRecettes.find('.parentCartes').removeClass("selected")
            // Ajoutez la classe "selected" à la carte sélectionnée
            nouvelleCarte.addClass("selected");     //donc au .parentCartes de la recette actuelle

            const dataId = nouvelleCarte.index();   // renvoie l'index (position) de l'élément actuel par rapport à ses pairs dans la collection.
            console.log("Numéro de la carte sélectionnée:", dataId);      //on affiche le numero
          }
        })
        //c'est en dehors car pas besoin de cliquer un autre button avant de celui la
          nouvelleCarte.find('.buttonCartes').on("click", function () {
          showRecipeAction(data.recipes[i], data.units, data.ingredientsData, data.categories)();
      });
  
      contenaireRecettes.append(nouvelleCarte);   // Ajouter la carte à la liste de cartes
    }

    // Fonction pour ajouter la fiche sélectionnée au planning
  function addToPlanning() {
    const selectedCard = $("#container-cards .selected");   //on travaille avec ce qui est selectionne
    const selectedRecipeIndex = selectedCard.attr("data-id");     //on prend l'id de la carte
    const selectedRecipe = data.recipes[Number(selectedRecipeIndex)];   //convertit l'index de la recette selectionne en entier. Ex "4"  =>  4
    /*
    recherche les ancêtres de l'élément actuel dans l'arbre DOM, en commençant par l'élément lui-même,
     puis en remontant dans la hiérarchie parentale jusqu'à ce qu'un élément correspondant au sélecteur spécifié soit trouvé.
    */
    const dayElement = $(this).closest(".jour").find(".contenu");     //trouve l'élément HTML correspondant à la classe ".contenu" à l'intérieur de l'élément parent ayant la classe .jour
    const menuItem = $("<p>").addClass("menu").text(selectedRecipe.recipeName);     //on prend le nom de la recette selectionne

    dayElement.append(menuItem);     //ajoute au jour qui correspond la recette

    selectedCard.addClass("isInPlanning"); // Ajoute la classe "isInPlanning" à la carte sélectionnée
    recipeInPlanningForShopping.push(selectedRecipe);    //Ajoute la recette selectionne(place) a la liste de recettes qui sont dans le planing
  }
  function removeRecipe() {
    const clickedRecipe = $(this).text();     // récupère le texte de l'élément sur lequel l'événement de clic a été déclenché.

   // Trouver la recette spécifique à supprimer
   const menuItem = $(this);      // fait référence à l'élément sur lequel le clic a été effectué.

    // Supprimer la recette spécifique du jour
    menuItem.remove();
    // Trouver la carte de recette correspondante et supprimer la classe isInPlanning

    const selectedCard = $("#container-cards .selected");   //on travaille avec ce qui est selectionne
    const selectedRecipeName = selectedCard.find(".text").text(); // On récupère le nom de la recette sélectionnée

    recipeInPlanningForShopping = recipeInPlanningForShopping.filter((recipe) => recipe.recipeName !== selectedRecipeName); //ca supprime de la liste

    /*
    La méthode .filter() est utilisée pour filtrer les éléments qui ont un texte correspondant à clickedRecipe. 
    La condition de filtrage $(this).text() === clickedRecipe compare le texte de l'élément de carte de recette avec la valeur de clickedRecipe (la recette specifique à supprimer).
    */
    const cardInPlanning = $(".parentCartes .text").filter(function() {
      return $(this).text() === clickedRecipe;
    }).closest(".parentCartes");      //est appelée sur le résultat filtré pour rechercher les ancêtres de l'élément actuel dans l'arbre DOM, en commençant par l'élément lui-même,
    // Vérifier si la carte de recette a d'autres occurrences dans le planning
    const otherOccurrences = $(".menu").filter(function() {
    return $(this).text() === clickedRecipe;
    });
    // Si aucune autre occurrence de la recette n'est trouvée dans le planning, supprimer la classe isInPlanning de la carte
    if (otherOccurrences.length === 0) {      //on ne dois pas remove la Class si il y a autres occurences!!
    cardInPlanning.removeClass("isInPlanning");     
  }
  }
  // Lorsque vous cliquez sur un jour du planning
  $(".title").on("click", addToPlanning);
  //il faut utiliser document pour que l'evenement est associe au nouveau bouton cree sinon ca marche pas car il vient d'etre cree
  $(document).on("click", ".menu", removeRecipe);
}


/**
 * @param {RecipesAndRelatedData} data

 */

function loadShoping(data){
  const shoppingListContainer = $("#list-container");
  const shoppingList = shoppingListContainer.find(".listeJours");
  shoppingList.empty(); // Supprimer le contenu précédent de la liste de courses
    for (let categoryKey of Object.keys(data.categories))  {
    const nouvelleCarte = $("<div>").addClass("listeJours");
    const ingredientList = $("<ul>").addClass("jours");
    const categoryTitle = $("<h3>").addClass("titreCategorie").text(categoryKey);
    
    ingredientList.append(categoryTitle);  //ca fait le titre
    nouvelleCarte.append(ingredientList);  //toute la categorie
    shoppingList.append(nouvelleCarte);   //nouveau emplacement?
    nouvelleCarte.attr("id",categoryKey)  //donne un id a chaque emplacement pour pouvoir mettre ce qui correspond dedans
  }
}
  
