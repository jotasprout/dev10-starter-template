package learn.theater.data;

import learn.theater.models.Ticket;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface TicketRepository {

    List<Ticket> findByPerformanceId(int performanceId);

    @Transactional
    List<Ticket> add(List<Ticket> tickets);


}
