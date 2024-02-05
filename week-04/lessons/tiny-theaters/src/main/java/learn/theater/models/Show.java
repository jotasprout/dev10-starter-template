package learn.theater.models;

import java.util.Objects;

public class Show {
    private int showId;
    private String name;

    public Show() {
    }

    public Show(int showId, String name) {
        this.showId = showId;
        this.name = name;
    }

    public int getShowId() {
        return showId;
    }

    public void setShowId(int showId) {
        this.showId = showId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Show show = (Show) o;

        if (showId != show.showId) return false;
        return Objects.equals(name, show.name);
    }

    @Override
    public int hashCode() {
        int result = showId;
        result = 31 * result + (name != null ? name.hashCode() : 0);
        return result;
    }
}
