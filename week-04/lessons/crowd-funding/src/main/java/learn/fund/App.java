package learn.fund;

import learn.fund.data.BackerJdbcTemplateRepository;
import learn.fund.data.CampaignJdbcTemplateRepository;
import learn.fund.data.PledgeJdbcTemplateRepository;
import learn.fund.domain.BackerService;
import learn.fund.domain.CampaignService;
import learn.fund.domain.PledgeService;
import learn.fund.ui.Controller;
import org.springframework.jdbc.core.JdbcTemplate;

public class App {
    public static void main(String[] args) {

        JdbcTemplate jdbcTemplate = DataHelper.getJdbcTemplate();

        // repositories
        BackerJdbcTemplateRepository backerRepository
                = new BackerJdbcTemplateRepository(jdbcTemplate);
        CampaignJdbcTemplateRepository campaignRepository
                = new CampaignJdbcTemplateRepository(jdbcTemplate);
        PledgeJdbcTemplateRepository pledgeRepository
                = new PledgeJdbcTemplateRepository(jdbcTemplate);

        // services
        BackerService backerService = new BackerService(backerRepository);
        CampaignService campaignService = new CampaignService(campaignRepository);
        PledgeService pledgeService = new PledgeService(
                backerRepository, campaignRepository, pledgeRepository);

        // controller
        Controller controller = new Controller(backerService, campaignService, pledgeService);
        controller.run();
    }
}
