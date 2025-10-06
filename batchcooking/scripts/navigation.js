/**
 * 
 * @param {Recipe} recipe 
 * @param {Object<string, string>} units 
 * @param {Object<string, IngredientData>} ingredientsData
* @param {Object<string, string>} categories Description des catÃ©gories relatives aux ingrÃ©dients.
 * @returns 
 */
function showRecipeAction(recipe,units,ingredientsData, categories){
    return () =>{ 
    $('.cacher').show();
    $('#container-planing').hide();
    $('#recipe-container').show();
    $('#list-container').hide();
    fillRecipe(recipe,units,ingredientsData, categories)
    }
};

/**
 * Affiche l'action du planning.
 */
function showPlanningAction(){
    $('#container-planing').show();
    $('#recipe-container').hide();
    $('#list-container').hide();
};

/**
 * Affiche l'action de la liste de courses.
 */
function showShoppingListAction(){
    $('.cacher').show();
    $('#container-planing').hide();
    $('#recipe-container').hide();
    $('#list-container').show();
    fillShopping()      //met les donnes dans la liste de course
};

/**
       * Remplit les détails de la recette.
       * @param {Recipe} recipe 
       * @param {Object<string, string>} units 
       * @param {Object<string, IngredientData>} ingredientsData
       * @param {Object<string, string>} categories Description des catÃ©gories relatives aux ingrÃ©dients.
       */

function fillRecipe(recipe, units, ingredientsData, categories){
    
    const contentRecette=$("#recipe-container");
    contentRecette.find(".titre").text(recipe.recipeName);
    contentRecette.find("img").attr("src", recipe.imageLink);
   
    recipe.steps.forEach(element => {
        const fixingStep = $("<li>").text(element);
        contentRecette.find(".etapes").append(fixingStep);      //met a jour les etapes de chaque fiche
    })
    const totalIngredients = new Set(); // Set to store unique ingredients          //peut etre inutile
    recipe.ingredients.forEach(element => {
        console.log("Voici "+element.ingredientName)        //ca affiche dans la console les ingredients
        const avantTraduite=element.ingredientName;         //exemple : potato
        const traduit=ingredientsData[avantTraduite];       //on choisi de la liste des Objets l'objet Potato
        
        const fixingIngredient = $("<li>").text(traduit.fr);    //on traduit et ca devien Pommes de terre
        contentRecette.find(".listeIngredients").append(fixingIngredient);  //on met a jour la liste des ingredients traduits
        //console.log(traduit.category); 
        totalIngredients.add(traduit.category);     //on ajoute toutes les categories des ingredients       //peut etre inutile
        
    });
    }


/**
 * Remplit la liste de courses.
 */
function fillShopping(){
    const totalIngredients = new Set(); // Set pour stocker les ingrédients uniques
    console.log(recipeInPlanningForShopping[0].ingredients)
    recipeInPlanningForShopping.forEach(recipe => {
        recipe.ingredients.forEach(element => {
            const avantTraduite=element.ingredientName;
            //const traduit=ingredientDataGlobal[avantTraduite].fr;
            totalIngredients.add(avantTraduite);
        });
    })
        //console.log(totalIngredients)

        totalIngredients.forEach(ingredient => {
            //console.log(ingredient)
            const apport = ingredientDataGlobal[ingredient].category
            for (let categoryKey of Object.keys(categoriesGlobales)){  //les differentes options de categories
                if(ingredientDataGlobal[ingredient].category === categoryKey){
                    //console.log(ingredientDataGlobal[ingredient].category)
                    //console.log(categoryKey)
                    const fixingStep = $("<li>").text(ingredientDataGlobal[ingredient].fr);
                    $(`#${categoryKey} ul`).append(fixingStep);
                    //console.log($(`#${categoryKey} ul`))
                    console.log("Ton code a execute ca")
                    break;

                }
                
            }
        });
    }

  $(document).ready(function(){
    $('.buttonRecipe').on("click", showPlanningAction);
    $('.buttonShopping').on("click", function () {
        showShoppingListAction();
      });
});
