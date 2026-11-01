import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.netology.javaqa.AviaSouls;
import ru.netology.javaqa.Ticket;
import ru.netology.javaqa.TicketTimeComparator;

public class AviaSoulsTest {

    private AviaSouls manager;
    private Ticket t1; // цена 5000, в полёте 2
    private Ticket t2; // цена 3000, в полёте 4
    private Ticket t3; // цена 4000, в полёте 1
    private Ticket t4; // другой маршрут

    @BeforeEach
    public void setUp() {
        manager = new AviaSouls();
        t1 = new Ticket("MOW", "LED", 5000, 10, 12);
        t2 = new Ticket("MOW", "LED", 3000, 9, 13);
        t3 = new Ticket("MOW", "LED", 4000, 8, 9);
        t4 = new Ticket("MOW", "AER", 7000, 10, 14);
        manager.add(t1);
        manager.add(t2);
        manager.add(t3);
        manager.add(t4);
    }

    // --- compareTo ---
    @Test
    public void shouldBeLessWhenPriceIsLower() {
        Assertions.assertTrue(t2.compareTo(t1) < 0);
    }

    @Test
    public void shouldBeGreaterWhenPriceIsHigher() {
        Assertions.assertTrue(t1.compareTo(t2) > 0);
    }

    @Test
    public void shouldBeEqualWhenPriceIsSame() {
        Ticket same = new Ticket("MOW", "LED", 5000, 1, 2);
        Assertions.assertEquals(0, t1.compareTo(same));
    }

    // --- TicketTimeComparator ---
    @Test
    public void comparatorShouldReturnNegativeWhenFirstIsFaster() {
        TicketTimeComparator comparator = new TicketTimeComparator();
        Assertions.assertTrue(comparator.compare(t3, t1) < 0);
    }

    @Test
    public void comparatorShouldReturnPositiveWhenFirstIsSlower() {
        TicketTimeComparator comparator = new TicketTimeComparator();
        Assertions.assertTrue(comparator.compare(t2, t1) > 0);
    }

    @Test
    public void comparatorShouldReturnZeroWhenSameDuration() {
        TicketTimeComparator comparator = new TicketTimeComparator();
        Ticket same = new Ticket("MOW", "LED", 100, 1, 3); // тоже 2 часа
        Assertions.assertEquals(0, comparator.compare(t1, same));
    }

    // --- search ---
    @Test
    public void searchShouldSortByPrice() {
        Ticket[] expected = {t2, t3, t1};
        Assertions.assertArrayEquals(expected, manager.search("MOW", "LED"));
    }

    @Test
    public void searchShouldReturnSingleTicket() {
        Ticket[] expected = {t4};
        Assertions.assertArrayEquals(expected, manager.search("MOW", "AER"));
    }

    @Test
    public void searchShouldReturnEmptyWhenNothingFound() {
        Assertions.assertArrayEquals(new Ticket[0], manager.search("LED", "MOW"));
    }

    // --- searchAndSortBy ---
    @Test
    public void searchAndSortByShouldSortByFlightTime() {
        Ticket[] expected = {t3, t1, t2};
        Ticket[] actual = manager.searchAndSortBy("MOW", "LED", new TicketTimeComparator());
        Assertions.assertArrayEquals(expected, actual);
    }

    @Test
    public void searchAndSortByShouldReturnEmptyWhenNothingFound() {
        Ticket[] actual = manager.searchAndSortBy("LED", "MOW", new TicketTimeComparator());
        Assertions.assertArrayEquals(new Ticket[0], actual);
    }
}