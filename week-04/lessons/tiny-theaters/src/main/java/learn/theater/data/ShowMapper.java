package learn.theater.data;

import learn.theater.models.Show;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class ShowMapper implements RowMapper<Show> {
    @Override
    public Show mapRow(ResultSet rs, int rowNum) throws SQLException {
        Show show = new Show();
        show.setShowId(rs.getInt("show_id"));
        show.setName(rs.getString("show_name"));
        return show;
    }
}
