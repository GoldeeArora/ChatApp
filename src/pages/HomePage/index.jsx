import React from "react";
import Sidebar from "../../components/Sidebar";
import Chat from "../../components/Chat";
import "./styles.scss";
function HomePage() {
  return (
    <div className="home">
      <div className="container">
        <Sidebar />
        <Chat />
      </div>
    </div>
  );
}

export default HomePage;
