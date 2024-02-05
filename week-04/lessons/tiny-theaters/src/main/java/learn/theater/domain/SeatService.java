package learn.theater.domain;

import learn.theater.data.SeatRepository;
import learn.theater.models.Seat;

import java.util.List;

public class SeatService {
    private final SeatRepository repository;

    public SeatService(SeatRepository repository) {
        this.repository = repository;
    }

    public List<Seat> findByPerformance(int performanceId) {
        return repository.findByPerformance(performanceId);
    }
}
