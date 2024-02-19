import React, { useRef, useEffect } from "react";
import { useSelector } from "react-redux";
// import { selectChatUser } from "../../../../Redux/features/chatUserSlice";
import { selectUser } from "../../../../Redux/features/userSlice";

function Message({ message }) {
  const user = useSelector(selectUser);
  console.log(message + "this is a message");
  console.log(message.timestamp);
  const API_ENDPOINT = process.env.REACT_APP_API_ENDPOINT;
  // const API_ENDPOINT = process.env.REACT_APP_API_ENDPOINT;
  // const chatUser = useSelector(selectChatUser);
  const ref = useRef();

  useEffect(() => {
    ref.current?.scrollIntoView({ behavior: "smooth" });
  }, [message]);
  // console.log(message);
  return (
    <div
      ref={ref}
      className={`message ${message.senderId === user.email && "owner"}`}
    >
      <div className="messageInfo">
        <img src={`${API_ENDPOINT}/${message.senderId}.jpg`} alt="" />
        {/* <span>{JSON.stringify(message.timestamp)_+}</span> */}
      </div>
      <div className="messageContent">
        <p>{message.content}</p>
      </div>
    </div>
    // <p>{message.content}</p>
  );
}

export default Message;
