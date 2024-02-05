package learn.theater.domain;

import learn.theater.data.PerformanceRepository;
import learn.theater.models.Performance;

import java.time.LocalDate;
import java.util.List;

public class PerformanceService {
    private final PerformanceRepository repository;

    public PerformanceService(PerformanceRepository repository) {
        this.repository = repository;
    }

    public List<Performance> findByFutureShowId(int showId) {
        return repository.findByFutureShowId(showId);
    }

    public List<Performance> findByPerformanceDateBetweenAndShowId(LocalDate start, LocalDate end, int showId) {
        return repository.findByPerformanceDateBetweenAndShowId(start, end, showId);
    }
}
