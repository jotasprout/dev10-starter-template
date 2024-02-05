package learn.fund.data;

import learn.fund.models.Campaign;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class CampaignMapper implements RowMapper<Campaign> {
    @Override
    public Campaign mapRow(ResultSet rs, int rowNum) throws SQLException {
        Campaign campaign = new Campaign();
        campaign.setCampaignId(rs.getInt("campaign_id"));
        campaign.setName(rs.getString("name"));
        campaign.setDescription(rs.getString("description"));
        campaign.setStartDate(rs.getDate("start_date").toLocalDate());
        campaign.setEndDate(rs.getDate("end_date").toLocalDate());
        campaign.setFundingGoal(rs.getBigDecimal("funding_goal"));
        return campaign;
    }
}
