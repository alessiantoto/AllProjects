package be.esi.prj.model;

import be.esi.prj.model.Items.Batch;
import be.esi.prj.model.Items.Item;
import be.esi.prj.model.Repository.TicketRepository;
import be.esi.prj.model.Repository.UsersRepository;
import be.esi.prj.util.Observer;
import be.esi.prj.util.State;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.nio.file.Paths;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class FacadeTest {

  private Facade facade;

  @Mock
  private TicketRepository ticketRepository;

  @Mock
  private UsersRepository usersRepository;

  @Mock
  private Batch batch;

  @Mock
  private Checkup checkup;

  @Mock
  private Item item;

  @Mock
  private Observer observer;

  @Mock
  private State state;

  @BeforeEach
  void setUp() {
    MockitoAnnotations.openMocks(this);
    facade = new Facade(ticketRepository, usersRepository);
  }

  @Test
  void testStartScanThread() {
    String path = Paths.get("..", "images", "ticket").toString();
    int userId = 123;
    facade.startScanThread(path, userId);
    assertEquals(userId, facade.getUserId(), "userId should be set correctly");
    assertNotNull(getBatchField(), "Batch should be initialized");
  }

  @Test
  void testAddObserver() {
    Observer newObserver = mock(Observer.class);
    facade.addObserver(newObserver);
    assertTrue(getObserversField().contains(newObserver), "Observer should be added");
  }

  @Test
  void testAddObserverAlreadyExists() {
    facade.addObserver(observer);
    int initialSize = getObserversField().size();
    facade.addObserver(observer);
    assertEquals(initialSize, getObserversField().size(), "Duplicate observer should not be added");
  }

  @Test
  void testRemoveObserver() {
    facade.addObserver(observer);
    facade.removeObserver(observer);
    assertFalse(getObserversField().contains(observer), "Observer should be removed");
  }

  @Test
  void testNotifyObservers() {
    facade.addObserver(observer);
    facade.notifyObservers(state);
    verify(observer, times(1)).update(state);
  }

  private Batch getBatchField() {
    try {
      var field = Facade.class.getDeclaredField("batch");
      field.setAccessible(true);
      return (Batch) field.get(facade);
    } catch (NoSuchFieldException | IllegalAccessException e) {
      throw new RuntimeException("Failed to access batch field", e);
    }
  }

  private Checkup getCheckupField() {
    try {
      var field = Facade.class.getDeclaredField("checkup");
      field.setAccessible(true);
      return (Checkup) field.get(facade);
    } catch (NoSuchFieldException | IllegalAccessException e) {
      throw new RuntimeException("Failed to access checkup field", e);
    }
  }

  private ArrayList<Observer> getObserversField() {
    try {
      var field = Facade.class.getDeclaredField("observers");
      field.setAccessible(true);
      return (ArrayList<Observer>) field.get(facade);
    } catch (NoSuchFieldException | IllegalAccessException e) {
      throw new RuntimeException("Failed to access observers field", e);
    }
  }
}