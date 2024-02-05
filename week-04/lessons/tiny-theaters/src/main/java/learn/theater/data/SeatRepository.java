package learn.theater.data;

import learn.theater.models.Seat;

import java.util.List;

public interface SeatRepository {
    List<Seat> findByPerformance(int performanceId);
}
