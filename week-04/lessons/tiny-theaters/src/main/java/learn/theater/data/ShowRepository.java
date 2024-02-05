package learn.theater.data;

import learn.theater.models.Show;

import java.time.LocalDate;
import java.util.List;

public interface ShowRepository {
    List<Show> findAll();

    List<Show> findByFuturePerformanceDate();

    List<Show> findByPerformanceDateBetween(LocalDate start, LocalDate end);

    Show findById(int showId);

    Show add(Show show);

    boolean update(Show show);

    boolean deleteById(int showId);
}
