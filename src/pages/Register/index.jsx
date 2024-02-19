import React from "react";
import "../../styles.scss";
import Add from "../../img/addAvatar.png";
import { useNavigate } from "react-router-dom";
import { useDispatch } from "react-redux";
import { updateToken } from "../../Redux/features/tokenSlice";
import { updateUser } from "../../Redux/features/userSlice";
import axios from "axios";
function Register() {
  const navigate = useNavigate();
  const dispatch = useDispatch();
  const API_ENDPOINT = process.env.REACT_APP_API_ENDPOINT;
  const handleSubmit = (e) => {
    e.preventDefault();
    const displayName = e.target[0].value;
    const email = e.target[1].value;
    const password = e.target[2].value;
    const file = e.target[3].files[0];
    const requestData = {
      fullName: displayName,
      email: email,
      password: password,
    };

    axios
      .post(`${API_ENDPOINT}/api/auth/register`, requestData)
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
        <span className="logo">Chat App</span>
        <span className="logo">Register</span>
        <form onSubmit={handleSubmit}>
          <input type="text" placeholder="display name" />
          <input type="email" placeholder="email" />
          <input type="password" placeholder="password" />
          {/* Here we have used label to take the file input using id file and putting the image and not displaying the input of type file */}
          <input style={{ display: "none" }} type="file" id="file" />
          <label htmlFor="file">
            <img src={Add} alt="input file" />
            <span>Add an avatar</span>
          </label>
          <button>Sign up</button>
        </form>
        <p>You do have an account? Login</p>
      </div>
    </div>
  );
}

export default Register;
