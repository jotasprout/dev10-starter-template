package learn.theater.models;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Objects;

public class Performance {
    private int performanceId;
    private LocalDate date;
    private BigDecimal ticketPrice;
    private Theater theater;
    private Show show;
    private int availableSeats;

    public int getPerformanceId() {
        return performanceId;
    }

    public void setPerformanceId(int performanceId) {
        this.performanceId = performanceId;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public BigDecimal getTicketPrice() {
        return ticketPrice;
    }

    public void setTicketPrice(BigDecimal ticketPrice) {
        this.ticketPrice = ticketPrice;
    }

    public Theater getTheater() {
        return theater;
    }

    public void setTheater(Theater theater) {
        this.theater = theater;
    }

    public Show getShow() {
        return show;
    }

    public void setShow(Show show) {
        this.show = show;
    }

    public int getAvailableSeats() {
        return availableSeats;
    }

    public void setAvailableSeats(int availableSeats) {
        this.availableSeats = availableSeats;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Performance that = (Performance) o;

        if (performanceId != that.performanceId) return false;
        if (!Objects.equals(date, that.date)) return false;
        if (!Objects.equals(ticketPrice, that.ticketPrice)) return false;
        if (!Objects.equals(theater, that.theater)) return false;
        return Objects.equals(show, that.show);
    }

    @Override
    public int hashCode() {
        int result = performanceId;
        result = 31 * result + (date != null ? date.hashCode() : 0);
        result = 31 * result + (ticketPrice != null ? ticketPrice.hashCode() : 0);
        result = 31 * result + (theater != null ? theater.hashCode() : 0);
        result = 31 * result + (show != null ? show.hashCode() : 0);
        return result;
    }
}
