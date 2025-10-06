package be.esi.prj.model;

import be.esi.prj.model.DTO.TicketDTO;
import be.esi.prj.model.Items.Batch;
import be.esi.prj.model.Items.Item;
import be.esi.prj.model.Repository.TicketRepository;
import be.esi.prj.model.Strategy.ExtractStrategy;
import be.esi.prj.model.Strategy.ExtractWithAI;
import be.esi.prj.model.Strategy.ExtractWithRegex;
import be.esi.prj.model.Strategy.StrategyEnum;
import be.esi.prj.util.State;
import java.time.LocalDate;

/**
 * Classe permettant de gérer le processus de vérification des tickets à partir d'une série d'images.
 * Elle initialise la stratégie d'extraction, gère le traitement des prix et des catégories,
 * et interagit avec le dépôt de tickets pour ajouter des tickets traités.
 */
public class Checkup {
  private Batch batch;
  private Facade facade;
  private TicketRepository ticketRepository;
  private ExtractStrategy strategy;
  private int currentIndex;
  private Item currentItem;
  private Double price;


  /**
   * Crée un objet Checkup pour gérer la vérification des tickets.
   *
   * @param facade l'interface pour notifier l'état du traitement
   * @param batch le lot d'images à traiter
   */

  public Checkup(Facade facade, Batch batch) {
    this.facade = facade;
    this.batch = batch;
    this.ticketRepository = facade.getTicketRepository();
    this.currentIndex = 0;
  }
  /**
   * Démarre le processus de vérification des tickets.
   * Initialise la stratégie d'extraction et prépare le premier élément pour la vérification manuelle.
   */
  public void start() {
    initExtractStrategy();
    prepareItemForManualVerification();
  }

  /**
   * Initialise la stratégie d'extraction des prix en fonction de la configuration.
   */
  private void initExtractStrategy() {
    if (facade.getStrategyEnum() == StrategyEnum.REGEX) {
      this.strategy = new ExtractWithRegex();
    } else if (facade.getStrategyEnum() == StrategyEnum.AI) {
      this.strategy = new ExtractWithAI();
    }
  }

  /**
   * Récupère l'élément actuel à traiter à partir de l'indice.
   *
   * @return true si l'élément a été récupéré avec succès, false sinon
   */
  private boolean retrieveCurrentItem() {
    try {
      int seriesIndex = currentIndex / Batch.NUMBER_OF_IMAGES;
      int itemInSeriesIndex = currentIndex % Batch.NUMBER_OF_IMAGES;

      currentItem = batch.getSeries(seriesIndex).getItem(itemInSeriesIndex);
      return true;
    } catch (Exception e) {
      facade.notifyObservers(State.RESULT);
      return false;
    }
  }

  /**
   * Prépare l'élément actuel pour la vérification manuelle en extrayant son prix et sa catégorie.
   * Si le prix ne peut être extrait, il demande une vérification manuelle du prix.
   */
  public void prepareItemForManualVerification() {
    if (!retrieveCurrentItem()) {
      return;
    }
    String text = currentItem.getText();
    price = strategy.extractPrice(text);
    if (price == null) {
      facade.notifyObservers(State.MANUAL_CATEGORY_PRICE);
    } else {
      facade.notifyObservers(State.MANUAL_CATEGORY);
    }
  }

  /**
   * Traite la catégorie sélectionnée pour l'élément actuel et ajoute un ticket au dépôt.
   *
   * @param category la catégorie du ticket
   */
  public void processCategory(Category category) {
    if (currentItem == null) {
      if (!retrieveCurrentItem()) {
        return;
      }
    }

    LocalDate date = LocalDate.now();
    TicketDTO ticketDTO = new TicketDTO(facade.getUserId(), date, category, price);
    ticketRepository.addTicket(ticketDTO);
    currentIndex++;
    prepareItemForManualVerification();
  }

  /**
   * Traite un prix sélectionné pour l'élément actuel et ajoute un ticket au dépôt.
   *
   * @param category la catégorie du ticket
   * @param selectedPrice le prix sélectionné pour l'élément
   */
  public void processCategoryPrice(Category category, Double selectedPrice) {
    LocalDate date = LocalDate.now();
    TicketDTO ticketDTO = new TicketDTO(facade.getUserId(), date, category, selectedPrice);
    ticketRepository.addTicket(ticketDTO);
    currentIndex++;
    prepareItemForManualVerification();
  }

  /**
   * Retourne l'élément actuellement traité.
   *
   * @return l'élément actuellement traité
   */
  public Item getCurrentItem() {
    return currentItem;
  }
}