"use strict";

/**
 * @typedef {Object} RecipesAndRelatedData Liste des recettes et donnÃ©es associÃ©es.
 * @property {Object<string, string>} units Description des unitÃ©s utilisÃ©es dans les recettes.
 * @property {Object<string, string>} categories Description des catÃ©gories relatives aux ingrÃ©dients.
 * @property {Object<string, string>} intakes Description des apports nutritionnels.
 * @property {Object<string, IngredientData>} ingredientsData
 * @property {Recipe[]} recipes La liste des recettes.
 */

/**
 * @typedef {Object} IngredientData Description d'un ingrÃ©dient.
 * @property {string} fr Nom de l'ingrÃ©dient en franÃ§ais.
 * @property {string} category CatÃ©gorie de l'ingrÃ©dient.
 * @property {Supplie[]} intakes Apports nutritionnels de l'ingrÃ©dient.
 */

/**
 * @typedef {Object} Recipe Description d'une recette.
 * @property {string} recipeName Nom de la recette.
 * @property {string} imageLink Lien vers une image reprÃ©sentant la recette.
 * @property {string} link Lien vers la source de la recette.
 * @property {number} qp Nombre de personnes comptÃ©es pour la quantitÃ© d'ingrÃ©dients.
 * @property {Array<Ingredient | IngredientWithQuantity>} ingredients Liste des ingrÃ©dients nÃ©cessaires Ã  la recette.
 * @property {string[]} steps Marche Ã  suivre pour rÃ©aliser la recette.
 */

/**
 * @typedef {"proteins" | "starches" | "vegetables"} Supplie Apports nutritionnels existants.
 */

/**
 * @typedef {Object} Ingredient IngrÃ©dient d'une recette sans quantitÃ© prÃ©cise.
 * @property {string} ingredientName Nom de l'ingrÃ©dient.
 */

/**
 * @typedef {Object} IngredientWithQuantity IngrÃ©dient d'une recette avec une quantitÃ©.
 * @property {string} ingredientName Nom de l'ingrÃ©dient.
 * @property {number} quantity QuantitÃ© de l'ingrÃ©dient nÃ©cessaire Ã  la recette.
 * @property {string} unit UnitÃ© de mesure de la quantitÃ©.
 */