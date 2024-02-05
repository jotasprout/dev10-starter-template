package learn.theater.data;

import learn.theater.models.Theater;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class TheaterMapper implements RowMapper<Theater> {
    @Override
    public Theater mapRow(ResultSet rs, int rowNum) throws SQLException {
        Theater theater = new Theater();
        theater.setTheaterId(rs.getInt("theater_id"));
        theater.setName(rs.getString("theater_name"));
        theater.setAddress(rs.getString("address"));
        theater.setPhone(rs.getString("phone"));
        theater.setEmailAddress(rs.getString("email_address"));
        return theater;
    }
}
