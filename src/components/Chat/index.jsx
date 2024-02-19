import React, { useEffect, useState } from "react";
import Messages from "./Messages";
import Input from "./Input";
import Cam from "../../img/cam.png";
import Add from "../../img/add.png";
import More from "../../img/more.png";
import { useSelector } from "react-redux";
import { selectChatUser } from "../../Redux/features/chatUserSlice";
import { selectUser } from "../../Redux/features/userSlice";
import axios from "axios";
import { selectToken } from "../../Redux/features/tokenSlice";
function Chat({ stompClient, messages, setMessages }) {
  const chatUser = useSelector(selectChatUser);
  const user = useSelector(selectUser);
  const { token } = useSelector(selectToken);
  // const [messages, setMessages] = useState([]);
  const API_ENDPOINT = process.env.REACT_APP_API_ENDPOINT;
  const getAllMessages = async () => {
    try {
      const response = await axios.get(
        `${API_ENDPOINT}/messages/${user.email}/${chatUser.email}`,
        {
          headers: {
            Authorization: `Bearer ${token}`,
          },
        }
      );
      setMessages(response.data);
      // console.log(response.data);
    } catch (error) {
      console.log(error.message);
    }
  };
  // const subscribe = () => {
  //   console.log(
  //     "I have subscribed in the chat component before stompClient.subscribe"
  //   );
  //   stompClient.subscribe(`/user/${user.email}/queue/messages`, (message) => {
  //     console.log("Hello, I have subscribed chat component");
  //     const data = JSON.parse(message.body);
  //     console.log(data);
  //     const newMessages = [...messages];
  //     newMessages.push(data);
  //     setMessages(newMessages);
  //     // outputDiv.innerHTML += "<p>" + data.body[0].fullName + "</p>";
  //   });
  // };
  useEffect(() => {
    if (chatUser != null) getAllMessages();
  }, [chatUser]);
  // useEffect(() => {
  //   stompClient.connect(
  //     {},
  //     (frame) => {
  //       console.log("Connected to WebSocket", frame);
  //       subscribe();
  //     },
  //     (error) => {
  //       console.error("Error during WebSocket connection:", error);
  //     }
  //   );
  // }, []);
  // useEffect(() => {
  //   subscribe();
  // }, []);
  return (
    <div className="chat">
      <div className="chatInfo">
        <span>{chatUser ? chatUser.name : "NoUser"}</span>
        <div className="chatIcons">
          <img src={Cam} alt="" />
          <img src={Add} alt="" />
          <img src={More} alt="" />
        </div>
      </div>
      <Messages messages={messages} />
      <Input
        messages={messages}
        setMessages={setMessages}
        stompClient={stompClient}
      />
    </div>
  );
}

export default Chat;
