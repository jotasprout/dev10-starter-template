package learn.solarfarm.domain;

import learn.solarfarm.data.DataAccessException;
import learn.solarfarm.data.SolarPanelRepositoryDouble;
import learn.solarfarm.models.Material;
import learn.solarfarm.models.SolarPanel;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.Year;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class SolarPanelServiceTest {

    SolarPanelService service;

    @BeforeEach
    void setup() {
        SolarPanelRepositoryDouble repository = new SolarPanelRepositoryDouble();
        service = new SolarPanelService(repository);
    }

    @Test
    void shouldFindTwoSolarPanelsForSectionOne() throws DataAccessException {
        List<SolarPanel> solarPanels = service.findBySection("Section One");
        assertEquals(2, solarPanels.size());
    }

    @Test
    void shouldFindSolarPanelInSectionTwoRow10Column11() throws DataAccessException {
        SolarPanel solarPanel = service.findByKey("Section Two", 10, 11);
        assertNotNull(solarPanel);
    }

    @Test
    void shouldNotCreateNull() throws DataAccessException {
        // Arrange
        SolarPanel solarPanel = null;

        // Act
        SolarPanelResult result = service.create(solarPanel);

        // Assert
        assertFalse(result.isSuccess());
        assertEquals(1, result.getErrorMessages().size());
        assertTrue(result.getErrorMessages().get(0).contains("cannot be null"));
    }

    @Test
    void shouldNotCreateNullSection() throws DataAccessException {
        // Arrange
        SolarPanel solarPanel = new SolarPanel();
        solarPanel.setSection(null);
        solarPanel.setRow(1);
        solarPanel.setColumn(1);
        solarPanel.setMaterial(Material.POLY_SI);

        // Act
        SolarPanelResult result = service.create(solarPanel);

        // Assert
        assertFalse(result.isSuccess());
        assertEquals(1, result.getErrorMessages().size());
        assertTrue(result.getErrorMessages().get(0).contains("`section`"));
    }

    @Test
    void shouldNotCreateEmptySection() throws DataAccessException {
        // Arrange
        SolarPanel solarPanel = new SolarPanel();
        solarPanel.setSection("");
        solarPanel.setRow(1);
        solarPanel.setColumn(1);
        solarPanel.setMaterial(Material.POLY_SI);

        // Act
        SolarPanelResult result = service.create(solarPanel);

        // Assert
        assertFalse(result.isSuccess());
        assertEquals(1, result.getErrorMessages().size());
        assertTrue(result.getErrorMessages().get(0).contains("`section`"));
    }

    @Test
    void shouldNotCreateNullMaterial() throws DataAccessException {
        // Arrange
        SolarPanel solarPanel = new SolarPanel();
        solarPanel.setSection("Section One");
        solarPanel.setRow(1);
        solarPanel.setColumn(1);
        solarPanel.setYearInstalled(2000);

        // Act
        SolarPanelResult result = service.create(solarPanel);

        // Assert
        assertFalse(result.isSuccess());
        assertEquals(1, result.getErrorMessages().size());
        assertTrue(result.getErrorMessages().get(0).contains("`material`"));
    }

    @Test
    void shouldNotCreateNonPositiveRow() throws DataAccessException {
        // Arrange
        SolarPanel solarPanel = new SolarPanel();
        solarPanel.setSection("Section One");
        solarPanel.setRow(0);
        solarPanel.setColumn(1);
        solarPanel.setMaterial(Material.POLY_SI);

        // Act
        SolarPanelResult result = service.create(solarPanel);

        // Assert
        assertFalse(result.isSuccess());
        assertEquals(1, result.getErrorMessages().size());
        assertTrue(result.getErrorMessages().get(0).contains("`row`"));
    }

    @Test
    void shouldNotCreateGreaterThanMaxRow() throws DataAccessException {
        // Arrange
        SolarPanel solarPanel = new SolarPanel();
        solarPanel.setSection("Section One");
        solarPanel.setRow(SolarPanelService.MAX_ROW_COLUMN + 1);
        solarPanel.setColumn(1);
        solarPanel.setMaterial(Material.POLY_SI);

        // Act
        SolarPanelResult result = service.create(solarPanel);

        // Assert
        assertFalse(result.isSuccess());
        assertEquals(1, result.getErrorMessages().size());
        assertTrue(result.getErrorMessages().get(0).contains("`row`"));
    }

    @Test
    void shouldNotCreateNonPositiveColumn() throws DataAccessException {
        // Arrange
        SolarPanel solarPanel = new SolarPanel();
        solarPanel.setSection("Section One");
        solarPanel.setRow(1);
        solarPanel.setColumn(0);
        solarPanel.setMaterial(Material.POLY_SI);

        // Act
        SolarPanelResult result = service.create(solarPanel);

        // Assert
        assertFalse(result.isSuccess());
        assertEquals(1, result.getErrorMessages().size());
        assertTrue(result.getErrorMessages().get(0).contains("`column`"));
    }

    @Test
    void shouldNotCreateGreaterThanMaxColumn() throws DataAccessException {
        // Arrange
        SolarPanel solarPanel = new SolarPanel();
        solarPanel.setSection("Section One");
        solarPanel.setRow(1);
        solarPanel.setColumn(SolarPanelService.MAX_ROW_COLUMN + 1);
        solarPanel.setMaterial(Material.POLY_SI);

        // Act
        SolarPanelResult result = service.create(solarPanel);

        // Assert
        assertFalse(result.isSuccess());
        assertEquals(1, result.getErrorMessages().size());
        assertTrue(result.getErrorMessages().get(0).contains("`column`"));
    }

    @Test
    void shouldNotCreateYearInstalledInTheFuture() throws DataAccessException {
        // Arrange
        SolarPanel solarPanel = new SolarPanel();
        solarPanel.setSection("Section One");
        solarPanel.setRow(1);
        solarPanel.setColumn(1);
        solarPanel.setYearInstalled(Year.now().plusYears(1).getValue());
        solarPanel.setMaterial(Material.POLY_SI);

        // Act
        SolarPanelResult result = service.create(solarPanel);

        // Assert
        assertFalse(result.isSuccess());
        assertEquals(1, result.getErrorMessages().size());
        assertTrue(result.getErrorMessages().get(0).contains("`yearInstalled`"));
    }

    @Test
    void shouldNotCreateNonUniqueSectionRowColumn() throws DataAccessException {
        SolarPanel solarPanel = new SolarPanel();
        solarPanel.setSection("Section One");
        solarPanel.setRow(1);
        solarPanel.setColumn(1);
        solarPanel.setYearInstalled(2000);
        solarPanel.setMaterial(Material.POLY_SI);

        SolarPanelResult result = service.create(solarPanel);

        assertFalse(result.isSuccess());
        assertEquals(1, result.getErrorMessages().size());
        assertTrue(result.getErrorMessages().get(0).contains("must be unique"));
    }

    @Test
    void shouldNotCreatePositiveId() throws DataAccessException {
        SolarPanel solarPanel = new SolarPanel(1, "Section One", 1, 1, 2020,
                Material.POLY_SI, true);

        SolarPanelResult result = service.create(solarPanel);

        assertFalse(result.isSuccess());
        assertEquals(1, result.getErrorMessages().size());
        assertTrue(result.getErrorMessages().get(0).contains("`id`"));
    }

    @Test
    void shouldCreate() throws DataAccessException {
        SolarPanel solarPanel = new SolarPanel();
        solarPanel.setSection("Section One");
        solarPanel.setRow(1);
        solarPanel.setColumn(3);
        solarPanel.setYearInstalled(2000);
        solarPanel.setMaterial(Material.POLY_SI);

        SolarPanelResult result = service.create(solarPanel);

        assertTrue(result.isSuccess());
    }

    @Test
    void shouldNotUpdateEmptySection() throws DataAccessException {
       // TODO: complete
    }

    @Test
    void shouldNotUpdateNonPositiveId() throws DataAccessException {
        // TODO: complete
    }

    @Test
    void shouldNotUpdateNonExistentSolarPanel() throws DataAccessException {
        // TODO: complete
    }

    @Test
    void shouldUpdate() throws DataAccessException {
        // TODO: complete
    }

    @Test
    void shouldNotDeleteNonExistentSolarPanel() throws DataAccessException {
        // TODO: complete
    }

    @Test
    void shouldDelete() throws DataAccessException {
        // TODO: complete
    }
}
