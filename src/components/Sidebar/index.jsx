import React from "react";
import NavBar from "./NavBar";
import Search from "./Search";
import Chats from "./Chats";
function Sidebar({ users, setLogout, newMessageUser, setNewMessageUser }) {
  return (
    <div className="sidebar">
      <NavBar setLogout={setLogout} />
      <Search />
      <Chats
        users={users}
        newMessageUser={newMessageUser}
        setNewMessageUser={setNewMessageUser}
      />
    </div>
  );
}

export default Sidebar;
