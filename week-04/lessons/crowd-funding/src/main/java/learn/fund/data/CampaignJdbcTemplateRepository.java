package learn.fund.data;

import learn.fund.models.Campaign;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;

public class CampaignJdbcTemplateRepository implements CampaignRepository {

    private final JdbcTemplate jdbcTemplate;

    public CampaignJdbcTemplateRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public Campaign findById(int campaignId) {

        final String sql = """
                select campaign_id, `name`, `description`,
                start_date, end_date, funding_goal
                from campaign
                where campaign_id = ?;""";

        return jdbcTemplate.query(sql, new CampaignMapper(), campaignId)
                .stream().findFirst().orElse(null);
    }

    @Override
    public List<Campaign> findActive() {

        final String sql = """
                select campaign_id, `name`, `description`,
                start_date, end_date, funding_goal
                from campaign
                where ? between start_date and end_date
                order by start_date asc;
                """;

        return jdbcTemplate.query(sql, new CampaignMapper(), LocalDate.now());
    }

    @Override
    public List<Campaign> findActiveAndFuture() {

        final String sql = """
                select campaign_id, `name`, `description`,
                start_date, end_date, funding_goal
                from campaign
                where end_date >= ?
                order by start_date asc;
                """;

        return jdbcTemplate.query(sql, new CampaignMapper(), LocalDate.now());
    }

    @Override
    public List<Campaign> findByDateRange(LocalDate start, LocalDate end) {

        final String sql = """
                select campaign_id, `name`, `description`,
                    start_date, end_date, funding_goal
                from campaign
                where not (end_date < ?
                    or start_date > ?)
                order by start_date asc;
                """;

        return jdbcTemplate.query(sql, new CampaignMapper(), start, end);
    }

    @Override
    public Campaign add(Campaign campaign) {

        SimpleJdbcInsert insert = new SimpleJdbcInsert(jdbcTemplate)
                .withTableName("campaign")
                .usingGeneratedKeyColumns("campaign_id");

        HashMap<String, Object> args = new HashMap<>();
        args.put("name", campaign.getName());
        args.put("description", campaign.getDescription());
        args.put("start_date", campaign.getStartDate());
        args.put("end_date", campaign.getEndDate());
        args.put("funding_goal", campaign.getFundingGoal());

        int campaignId = insert.executeAndReturnKey(args).intValue();
        campaign.setCampaignId(campaignId);
        return campaign;
    }

    @Override
    public boolean update(Campaign campaign) {

        final String sql = """
                update campaign set
                    `name` = ?,
                    `description` = ?,
                    start_date = ?,
                    end_date = ?,
                    funding_goal = ?
                where campaign_id = ?;
                """;

        return jdbcTemplate.update(sql,
                campaign.getName(),
                campaign.getDescription(),
                campaign.getStartDate(),
                campaign.getEndDate(),
                campaign.getFundingGoal(),
                campaign.getCampaignId()) > 0;
    }

    @Override
    @Transactional
    public boolean deleteById(int campaignId) {
        jdbcTemplate.update("delete from pledge where campaign_id = ?;", campaignId);
        return jdbcTemplate.update("delete from campaign where campaign_id = ?;", campaignId) > 0;
    }
}
