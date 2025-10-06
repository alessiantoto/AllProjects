package be.esi.prj.controller;

import be.esi.prj.model.Connection.ConnectionManager;
import be.esi.prj.model.Facade;
import be.esi.prj.model.Items.Item;
import be.esi.prj.model.Repository.TicketRepository;
import be.esi.prj.model.Repository.UsersRepository;
import be.esi.prj.util.Observer;

/**
 * Cette classe gère les actions principales de l'application, comme la gestion des utilisateurs,
 * des tickets et l'interaction avec la base de données.
 */
public class Controller {


  private TicketRepository ticketRepository;
  private UsersRepository usersRepository;
  private Facade facade;

  /**
   * Constructeur de la classe, initialise les connexions nécessaires à la base de données
   * et les référentiels pour les tickets et utilisateurs.
   */
  public Controller() {
    this.ticketRepository = new TicketRepository();
    this.usersRepository = new UsersRepository();
    this.facade = new Facade(ticketRepository, usersRepository);
  }

  /**
   * Récupère l'identifiant de l'utilisateur actuellement connecté.
   *
   * @return l'ID de l'utilisateur
   */
  public int fetchUserId() {
    return facade.getUserId();
  }

  /**
   * Récupère l'élément actuel (par exemple, un ticket ou un objet similaire).
   *
   * @return l'élément actuel
   */
  public Item getCurrentItem() {
    return facade.fetchCurrentItem();
  }


  /**
   * Lance un processus de scan (par exemple, pour scanner des fichiers) dans un thread séparé.
   *
   * @param path le chemin du dossier à scanner
   * @param userId l'identifiant de l'utilisateur qui effectue le scan
   */
  public void startScan(String path, int userId) {
    facade.startScanThread(path, userId);
  }

  /**
   * Enregistre un observateur pour être notifié de certains événements dans l'application.
   *
   * @param o l'observateur à ajouter
   */
  public void registerObserver(Observer o) {
    facade.addObserver(o);
  }

  /**
   * Traite une catégorie donnée (par exemple, pour une fonctionnalité spécifique de l'application).
   *
   * @param parameter un paramètre qui définira le traitement de la catégorie
   */
  public void processCategory(String parameter) {
    facade.processCategory(parameter);
  }

  /**
   * Traite un prix associé à une catégorie.
   *
   * @param category la catégorie concernée
   * @param price le prix à traiter
   */
  public void processCategoryPrice(String category, Double price) {
    facade.processCategoryPrice(category, price);
  }
}
