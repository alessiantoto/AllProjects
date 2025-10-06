package be.esi.prj.model;

import be.esi.prj.model.DTO.UsersDTO;
import be.esi.prj.model.Items.Batch;
import be.esi.prj.model.Items.Item;
import be.esi.prj.model.Repository.TicketRepository;
import be.esi.prj.model.Repository.UsersRepository;
import be.esi.prj.model.Strategy.StrategyEnum;
import be.esi.prj.util.Observable;
import be.esi.prj.util.Observer;
import be.esi.prj.util.State;
import java.sql.Connection;
import java.util.ArrayList;

/**
 * La classe Facade est responsable de la gestion des utilisateurs, des tickets,
 * du démarrage du processus de scan et de l'interaction avec les différentes
 * stratégies d'extraction et les threads de traitement des images.
 * Elle implémente le patron de conception Observateur pour notifier les changements d'état.
 */
public class Facade implements Observable {
  private Batch batch;
  private Checkup checkup;
  private TicketRepository ticketRepository;
  private UsersRepository usersRepository;
  private StrategyEnum strategyEnum;
  private ArrayList<Observer> observers;
  private int userId;


  /**
   * Constructeur principal pour initialiser la façade avec les dépendances nécessaires.
   *
   * @param ticketRepository le dépôt des tickets
   * @param usersRepository le dépôt des utilisateurs
   */
  public Facade(TicketRepository ticketRepository, UsersRepository usersRepository) {

    this.ticketRepository = ticketRepository;
    this.usersRepository = usersRepository;
    this.observers = new ArrayList<>();
    this.strategyEnum = StrategyEnum.REGEX;
    this.userId = -1;
  }

  public int getUserId() {
    return userId;
  }

  public TicketRepository getTicketRepository() {
    return this.ticketRepository;
  }

  /**
   * Démarre un thread pour scanner un lot d'images.
   *
   * @param path le chemin des images à scanner
   * @param userId l'identifiant de l'utilisateur
   */
  public void startScanThread(String path, int userId) {
    this.userId = userId;

    this.batch = new Batch(this, path);
  }

  /**
   * Appelée lorsque tous les threads de scan sont terminés.
   * Initialise le processus de vérification des tickets.
   */
  public void allThreadsAreFinished() {
    this.checkup = new Checkup(this, batch);
    this.checkup.start();
  }


  /**
   * Récupère l'élément actuel à traiter dans le processus de vérification.
   *
   * @return l'élément actuellement traité
   */
  public Item fetchCurrentItem() {
    return checkup.getCurrentItem();
  }

  public StrategyEnum getStrategyEnum() {
    return strategyEnum;
  }


  /**
   * Traite la catégorie sélectionnée pour l'élément actuel.
   *
   * @param parameter la catégorie à traiter
   */
  public void processCategory(String parameter) {
    Category category = Category.valueOf(parameter);
    checkup.processCategory(category);
  }


  /**
   * Traite la catégorie et le prix sélectionnés pour l'élément actuel.
   *
   * @param categoryText la catégorie à traiter
   * @param price le prix sélectionné
   */
  public void processCategoryPrice(String categoryText, Double price) {
    Category category = Category.valueOf(categoryText);
    checkup.processCategoryPrice(category, price);
  }

  @Override
  public void addObserver(Observer observer) {
    if (!observers.contains(observer)) {
      this.observers.add(observer);
    }
  }

  @Override
  public void removeObserver(Observer observer) {
    this.observers.remove(observer);
  }


  @Override
  public void notifyObservers(State state) {
    // TODO: Check use of the updateStep parameter
    observers.forEach(o -> o.update(state));
  }
}
