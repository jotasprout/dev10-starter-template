import { BrowserRouter as Router, Route, Switch } from "react-router-dom";
import Confirmation from "./components/Confirmation";
import Error from "./components/Error";
import Home from "./components/Home";
import NavBar from "./components/NavBar";
import NotFound from "./components/NotFound";
import SightingForm from "./components/SightingForm";

function App() {
  return (
    <Router>
      <NavBar />

      <Switch>
        <Route path={["/edit/:id", "/add"]}>
          <SightingForm />
        </Route>

        <Route path="/confirmation">
          <Confirmation />
        </Route>

        <Route path="/error">
          <Error />
        </Route>

        <Route exact path="/">
          <Home />
        </Route>

        <Route>
          <NotFound />
        </Route>
      </Switch>
    </Router>
  );
}

export default App;
