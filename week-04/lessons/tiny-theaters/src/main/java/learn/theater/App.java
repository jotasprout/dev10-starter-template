package learn.theater;

import com.mysql.cj.jdbc.MysqlDataSource;
import learn.theater.data.*;
import learn.theater.domain.*;
import learn.theater.ui.Controller;
import learn.theater.ui.View;
import org.springframework.jdbc.core.JdbcTemplate;

public class App {
    public static void main(String[] args) {

        // JDBC, Spring JDBC
        MysqlDataSource dataSource = new MysqlDataSource();
        dataSource.setUrl(System.getenv("DB_URL"));
        dataSource.setUser(System.getenv("DB_USERNAME"));
        dataSource.setPassword(System.getenv("DB_PASSWORD"));

        JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);

        // repositories
        CustomerJdbcTemplateRepository customerRepository =
                new CustomerJdbcTemplateRepository(jdbcTemplate);
        PerformanceJdbcTemplateRepository performanceRepository =
                new PerformanceJdbcTemplateRepository(jdbcTemplate);
        SeatJdbcTemplateRepository seatRepository =
                new SeatJdbcTemplateRepository(jdbcTemplate);
        ShowJdbcTemplateRepository showRepository =
                new ShowJdbcTemplateRepository(jdbcTemplate);
        TicketJdbcTemplateRepository ticketRepository =
                new TicketJdbcTemplateRepository(jdbcTemplate);

        // services
        CustomerService customerService = new CustomerService(customerRepository);
        PerformanceService performanceService = new PerformanceService(performanceRepository);
        SeatService seatService = new SeatService(seatRepository);
        ShowService showService = new ShowService(showRepository);
        TicketService ticketService = new TicketService(customerRepository,
                performanceRepository, seatRepository, ticketRepository);

        View view = new View();
        Controller controller = new Controller(view, customerService,
                performanceService, seatService, showService, ticketService);
        controller.run();
    }
}
