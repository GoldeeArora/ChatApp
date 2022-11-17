import React from "react";

function NavBar() {
  return (
    <div className="navbar">
      <span className="logo">Chat App</span>
      <div className="user">
        <img
          src="https://images.pexels.com/photos/13743846/pexels-photo-13743846.jpeg?auto=compress&cs=tinysrgb&w=1260&h=750&dpr=1"
          alt="image of John"
        />
        <span>John</span>
        <button>logout</button>
      </div>
    </div>
  );
}

export default NavBar;
