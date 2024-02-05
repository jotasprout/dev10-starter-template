package learn.fund.data;

import learn.fund.models.Backer;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class BackerMapper implements RowMapper<Backer> {
    @Override
    public Backer mapRow(ResultSet rs, int rowNum) throws SQLException {
        Backer backer = new Backer();
        backer.setBackerId(rs.getInt("backer_id"));
        backer.setName(rs.getString("name"));
        backer.setEmailAddress(rs.getString("email_address"));
        return backer;
    }
}
