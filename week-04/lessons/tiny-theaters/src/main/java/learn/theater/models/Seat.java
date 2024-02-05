package learn.theater.models;

import java.util.Objects;

public class Seat {
    private int seatId;
    private String label;
    private int theaterId;
    private boolean booked;

    public Seat() {
    }

    public Seat(int seatId, String label, int theaterId, boolean booked) {
        this.seatId = seatId;
        this.label = label;
        this.theaterId = theaterId;
        this.booked = booked;
    }

    public int getSeatId() {
        return seatId;
    }

    public void setSeatId(int seatId) {
        this.seatId = seatId;
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public int getTheaterId() {
        return theaterId;
    }

    public void setTheaterId(int theaterId) {
        this.theaterId = theaterId;
    }

    public boolean isBooked() {
        return booked;
    }

    public void setBooked(boolean booked) {
        this.booked = booked;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Seat seat = (Seat) o;

        if (seatId != seat.seatId) return false;
        if (theaterId != seat.theaterId) return false;
        if (booked != seat.booked) return false;
        return Objects.equals(label, seat.label);
    }

    @Override
    public int hashCode() {
        int result = seatId;
        result = 31 * result + (label != null ? label.hashCode() : 0);
        result = 31 * result + theaterId;
        result = 31 * result + (booked ? 1 : 0);
        return result;
    }
}
