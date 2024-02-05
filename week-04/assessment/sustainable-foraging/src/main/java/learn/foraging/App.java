package learn.foraging;

import learn.foraging.data.ForageJdbcTemplateRepository;
import learn.foraging.data.ForagerJdbcTemplateRepository;
import learn.foraging.data.ItemJdbcTemplateRepository;
import learn.foraging.domain.ForageService;
import learn.foraging.domain.ForagerService;
import learn.foraging.domain.ItemService;
import learn.foraging.ui.ConsoleIO;
import learn.foraging.ui.Controller;
import learn.foraging.ui.View;
import org.springframework.jdbc.core.JdbcTemplate;

public class App {
    public static void main(String[] args) {

        ConsoleIO io = new ConsoleIO();
        View view = new View(io);

        JdbcTemplate jdbcTemplate = DataHelper.getJdbcTemplate();
        ForageJdbcTemplateRepository forageRepository =
                new ForageJdbcTemplateRepository(jdbcTemplate);
        ForagerJdbcTemplateRepository foragerRepository =
                new ForagerJdbcTemplateRepository(jdbcTemplate);
        ItemJdbcTemplateRepository itemRepository =
                new ItemJdbcTemplateRepository(jdbcTemplate);

        ForagerService foragerService = new ForagerService(foragerRepository);
        ForageService forageService =
                new ForageService(forageRepository, foragerRepository, itemRepository);
        ItemService itemService = new ItemService(itemRepository);

        Controller controller = new Controller(foragerService, forageService, itemService, view);
        controller.run();
    }
}
