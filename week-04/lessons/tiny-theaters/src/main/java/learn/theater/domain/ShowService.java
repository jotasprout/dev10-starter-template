package learn.theater.domain;

import learn.theater.data.ShowRepository;
import learn.theater.models.Show;

import java.time.LocalDate;
import java.util.List;

public class ShowService {
    private final ShowRepository repository;

    public ShowService(ShowRepository repository) {
        this.repository = repository;
    }

    public List<Show> findByFuturePerformanceDate() {
        return repository.findByFuturePerformanceDate();
    }

    public List<Show> findByPerformanceDateBetween(LocalDate start, LocalDate end) {
        return repository.findByPerformanceDateBetween(start, end);
    }
}
