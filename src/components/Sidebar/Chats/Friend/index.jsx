import React from "react";
import { useDispatch, useSelector } from "react-redux";
import {
  selectChatUser,
  updateChatUser,
} from "../../../../Redux/features/chatUserSlice";
function Friend({ name, email, status, newMessageUser, setNewMessageUser }) {
  const dispatch = useDispatch();
  const handleClick = () => {
    const tempNewMessageUser = newMessageUser.filter((item) => item != email);
    setNewMessageUser(tempNewMessageUser);
    dispatch(updateChatUser({ name, email, status }));
  };
  const isNewMessageUser = newMessageUser.includes(email);
  return (
    <div className="friend" onClick={() => handleClick()}>
      <div className="userChat">
        <img
          // src="https://images.pexels.com/photos/13743846/pexels-photo-13743846.jpeg?auto=compress&cs=tinysrgb&w=1260&h=750&dpr=1"
          src={`http://localhost:8080/${email}.jpg`}
          alt=""
        />
        <div className="userChatInfo">
          <span>{name}</span>
          <p>{status}</p>
          <div>
            <p style={{ display: isNewMessageUser ? "block" : "none" }}>N</p>
          </div>
        </div>
      </div>
    </div>
  );
}

export default Friend;
