package learn.theater.data;

import learn.theater.models.Performance;

import java.time.LocalDate;
import java.util.List;

public interface PerformanceRepository {
    List<Performance> findByFutureShowId(int showId);

    List<Performance> findByPerformanceDateBetweenAndShowId(LocalDate start, LocalDate end, int showId);

    Performance findById(int performanceId);

    Performance add(Performance performance);

    boolean update(Performance performance);

    boolean deleteById(int performanceId);
}
