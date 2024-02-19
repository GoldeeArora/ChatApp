import React, { useState } from "react";
import "../../styles.scss";
import axios from "axios";
import { useDispatch } from "react-redux";
import { updateToken } from "../../Redux/features/tokenSlice";
import { updateUser } from "../../Redux/features/userSlice";
import { useNavigate } from "react-router-dom";

function Login() {
  const API_ENDPOINT = process.env.REACT_APP_API_ENDPOINT;
  const dispatch = useDispatch();
  const navigate = useNavigate();
  const [email, setEmail] = useState();
  const [password, setPassword] = useState();
  const login = (e) => {
    e.preventDefault();
    const requestData = {
      email: email,
      password: password,
    };

    axios
      .post(`${API_ENDPOINT}/api/auth/login`, requestData)
      .then((res) => {
        const data = res.data;
        if (res.data.message === "Please enter correct email and password") {
          alert(data.message);
          return;
        }
        dispatch(updateToken(res.data.token));
        dispatch(updateUser(res.data.user));
        navigate("/home");
      })
      .catch((e) => console.log(e));
  };

  return (
    <div className="formContainer">
      <div className="formWrapper">
        <span className="logo">Lama Chat</span>
        <span className="logo">Login</span>
        <form onSubmit={login}>
          <input
            type="email"
            placeholder="email"
            value={email}
            onChange={(e) => setEmail(e.target.value)}
          />
          <input
            type="password"
            placeholder="password"
            value={password}
            onChange={(e) => setPassword(e.target.value)}
          />
          <button type="submit">Sign in</button>
        </form>
        <p>You don't have an account? Register</p>
      </div>
    </div>
  );
}

export default Login;
