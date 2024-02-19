import React from "react";
import { useSelector } from "react-redux";
import { selectUser } from "../../../Redux/features/userSlice";

function NavBar({ setLogout }) {
  const user = useSelector(selectUser);
  // console.log(user.email + " this is my email");
  const API_ENDPOINT = process.env.REACT_APP_API_ENDPOINT;
  // console.log(API_ENDPOINT + " THIS IS endpoint");
  return (
    <div className="navbar">
      <span className="logo">Chat App</span>
      <div className="user">
        <img src={`${API_ENDPOINT}/${user.email}.jpg`} alt="image of user" />
        <span>{user.fullName}</span>
        <button
          onClick={() => {
            // console.log("logging out...");
            setLogout(true);
          }}
        >
          logout
        </button>
      </div>
    </div>
  );
}

export default NavBar;
