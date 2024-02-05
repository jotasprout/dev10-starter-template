import { useEffect, useState, useContext } from "react";
import { useHistory } from "react-router-dom";
import Sighting from "./Sighting";
import AuthContext from "../context/AuthContext";

function Home() {
  const [sightings, setSightings] = useState([]);

  const auth = useContext(AuthContext);

  const history = useHistory();

  const canEdit = auth.user !== null;
  const canDelete = auth.user && auth.user.hasRole("ADMIN");

  useEffect(() => {
    fetch("http://localhost:8080/sighting")
      .then((response) => response.json())
      .then((data) => setSightings(data));
  }, []); // this will happen only once when the component is loaded

  const handleDelete = (sightingId) => {
    fetch(`http://localhost:8080/sighting/${sightingId}`, {
      method: "DELETE",
      headers: {
        Authorization: `Bearer ${auth.user.token}`,
      },
    }).then((response) => {
      if (response.status !== 204) {
        return Promise.reject("response is not 204 No Content");
      }
      return null;
    })
    .then(() => {
      history.push("/confirmation", { msg: "👍🏾" });
    })
    .catch(() => {
      history.push("/error", { msg: "👎🏾" });
    });
  };

  return sightings.map((sighting) => (
    <Sighting key={sighting.sightingId} sighting={sighting} 
      handleDelete={handleDelete} canEdit={canEdit} canDelete={canDelete} />
  ));
}

export default Home;
