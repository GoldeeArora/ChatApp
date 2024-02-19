import Login from "./pages/Login";
import HomePage from "./pages/HomePage";
import Register from "./pages/Register";
import { BrowserRouter as Router, Routes, Route } from "react-router-dom";
import "./styles.scss";
import { useSelector } from "react-redux";
import { selectToken } from "./Redux/features/tokenSlice";
const App = () => {
  const { token } = useSelector(selectToken);
  return (
    <>
      <Router>
        <Routes>
          <Route path="/" element={<Login />} />
          <Route
            path="/home"
            element={token === "" ? <Login /> : <HomePage />}
          />
          <Route path="/register" element={<Register />} />
        </Routes>
      </Router>
    </>
  );
};

export default App;
