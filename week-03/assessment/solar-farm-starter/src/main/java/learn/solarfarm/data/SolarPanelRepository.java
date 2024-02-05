package learn.solarfarm.data;

import learn.solarfarm.models.SolarPanel;

import java.util.List;

public interface SolarPanelRepository {
    List<SolarPanel> findBySection(String section) throws DataAccessException;

    SolarPanel findByKey(String section, int row, int column) throws DataAccessException;

    SolarPanel create(SolarPanel solarPanel) throws DataAccessException;

    // TODO: add an update method

    // TODO: add a delete method
}
