package learn.fund.data;

import learn.fund.models.Pledge;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.List;

@Repository
public class PledgeJdbcTemplateRepository implements PledgeRepository {
    private final JdbcTemplate jdbcTemplate;

    public PledgeJdbcTemplateRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public Pledge findById(int pledgeId) {

        final String sql = """
                select p.pledge_id, p.amount,
                    c.campaign_id, c.`name` campaign_name, c.`description`,
                    c.start_date, c.end_date, c.funding_goal,
                    b.backer_id, b.`name` backer_name, b.email_address
                from pledge p
                inner join campaign c on p.campaign_id = c.campaign_id
                inner join backer b on p.backer_id = b.backer_id
                where p.pledge_id = ?;
                """;

        return jdbcTemplate.query(sql, new PledgeMapper(), pledgeId)
                .stream().findFirst().orElse(null);
    }

    @Override
    public List<Pledge> findByCampaignId(int campaignId) {

        final String sql = """
                select p.pledge_id, p.amount,
                    c.campaign_id, c.`name` campaign_name, c.`description`,
                    c.start_date, c.end_date, c.funding_goal,
                    b.backer_id, b.`name` backer_name, b.email_address
                from pledge p
                inner join campaign c on p.campaign_id = c.campaign_id
                inner join backer b on p.backer_id = b.backer_id
                where p.campaign_id = ?;
                """;

        return jdbcTemplate.query(sql, new PledgeMapper(), campaignId);
    }

    @Override
    public List<Pledge> findByBackerId(int backerId) {

        final String sql = """
                select p.pledge_id, p.amount,
                    c.campaign_id, c.`name` campaign_name, c.`description`,
                    c.start_date, c.end_date, c.funding_goal,
                    b.backer_id, b.`name` backer_name, b.email_address
                from pledge p
                inner join campaign c on p.campaign_id = c.campaign_id
                inner join backer b on p.backer_id = b.backer_id
                where p.backer_id = ?;
                """;

        return jdbcTemplate.query(sql, new PledgeMapper(), backerId);
    }

    @Override
    public Pledge add(Pledge pledge) {

        SimpleJdbcInsert insert = new SimpleJdbcInsert(jdbcTemplate)
                .withTableName("pledge")
                .usingColumns("campaign_id", "backer_id", "amount")
                .usingGeneratedKeyColumns("pledge_id");

        HashMap<String, Object> args = new HashMap<>();
        args.put("campaign_id", pledge.getCampaign().getCampaignId());
        args.put("backer_id", pledge.getBacker().getBackerId());
        args.put("amount", pledge.getAmount());

        int pledgeId = insert.executeAndReturnKey(args).intValue();
        pledge.setPledgeId(pledgeId);
        return pledge;
    }

    @Override
    public boolean update(Pledge pledge) {

        final String sql = """
                update pledge set
                    amount = ?
                where pledge_id = ?;
                """;

        return jdbcTemplate.update(sql,
                pledge.getAmount(),
                pledge.getPledgeId()) > 0;
    }

    @Override
    public boolean deleteById(int pledgeId) {
        return jdbcTemplate.update("delete from pledge where pledge_id = ?;", pledgeId) > 0;
    }
}
