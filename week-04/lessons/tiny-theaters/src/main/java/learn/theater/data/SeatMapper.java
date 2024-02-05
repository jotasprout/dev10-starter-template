package learn.theater.data;

import learn.theater.models.Seat;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class SeatMapper implements RowMapper<Seat> {
    @Override
    public Seat mapRow(ResultSet rs, int rowNum) throws SQLException {
        Seat seat = new Seat();
        seat.setSeatId(rs.getInt("seat_id"));
        seat.setLabel(rs.getString("label"));
        seat.setTheaterId(rs.getInt("theater_id"));
        seat.setBooked(rs.getInt("booked") > 0);
        return seat;
    }
}
