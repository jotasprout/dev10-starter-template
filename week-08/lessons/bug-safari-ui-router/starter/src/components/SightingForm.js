import { useState } from "react";
import { Link } from "react-router-dom";

const DEFAULT_SIGHTING = {
  bugType: "",
  order: "",
  description: "",
  date: "",
  interest: "",
  imageUrl: ""
};

function SightingForm() {
  const [sighting, setSighting] = useState(DEFAULT_SIGHTING);

  const handleChange = (event) => {
    const updatedSighting = {...sighting};
    updatedSighting[event.target.name] = event.target.value;
    setSighting(updatedSighting);
  };

  const handleSubmit = (event) => {
    event.preventDefault();

    const updatedSighting = {...sighting};

    console.log(updatedSighting);
  };

  return (
    <form onSubmit={handleSubmit}>
      <h2>{sighting.sightingId ? "Update A Sighting" : "Add A Sighting"}</h2>

      <div>
        <label htmlFor="bugType">Bug Type</label>
        <input
          type="text"
          id="bugType"
          required
          value={sighting.bugType}
          name="bugType"
          onChange={handleChange}
        />
      </div>

      <div>
        <label htmlFor="order">Order</label>
        <select id="order" required
            value={sighting.order} name="order"
            onChange={handleChange}>
          <option value="">-- Choose --</option>
          <option value="Coleptera">Coleptera (beetles)</option>
          <option value="Lepidoptera">Lepidoptera (butterflies, moths)</option>
          <option value="Hymenoptera">Hymenoptera (ants, bees, wasps)</option>
          <option value="Diptera">Diptera (flies, gnats, mosquitoes)</option>
          <option value="Orthoptera">
            Orthoptera (grasshoppers, crickets)
          </option>
          <option value="Hemiptera">
            Hemiptera ("true bugs", cicadas, aphids)
          </option>
          <option value="Odonata">Odonata (dragonflies, damselflies)</option>
        </select>
      </div>

      <div>
        <label htmlFor="description">Description</label>
        <textarea
          id="description"
          required
          value={sighting.description}
          name="description"
          onChange={handleChange}
        />
      </div>

      <div>
        <label htmlFor="date">Date</label>
        <input
          type="date"
          id="date"
          required
          value={sighting.date}
          name="date"
          onChange={handleChange}
        />
      </div>

      <div>
        <label htmlFor="interest">Interest</label>
        <input
          type="number"
          id="interest"
          min="0.00"
          step="0.01"
          required
          value={sighting.interest}
          name="interest"
          onChange={handleChange}
        />
      </div>

      <div>
        <label htmlFor="imageURL">Image URL</label>
        <input
          type="url"
          id="imageURL"
          defaultValue={sighting.imageUrl}
          name="imageUrl"
          onChange={handleChange}
        />
      </div>

      <div>
        <button type="submit">Save</button>
        <Link to="/">Cancel</Link>
      </div>
    </form>
  );
}

export default SightingForm;
