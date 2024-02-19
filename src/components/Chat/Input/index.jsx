import React, { useState, useRef, useEffect } from "react";
import Img from "../../../img/img.png";
import Attach from "../../../img/attach.png";
import SockJS from "sockjs-client";
import { Stomp } from "@stomp/stompjs";
import { useSelector } from "react-redux";
import { selectUser } from "../../../Redux/features/userSlice";
import { selectChatUser } from "../../../Redux/features/chatUserSlice";
function Input({ messages, setMessages, stompClient }) {
  const [message, setMessage] = useState("");
  const user = useSelector(selectUser);
  const chatUser = useSelector(selectChatUser);
  //   stompClient.connect(
  //     {},
  //     (frame) => {
  //       console.log("Connected to WebSocket", frame);
  //     },
  //     (error) => {
  //       console.error("Error during WebSocket connection:", error);
  //     }
  //   );
  // };
  // const socket = new SockJS(`${API_ENDPOINT}/ws`);
  // const stompClient = Stomp.over(socket);

  // stompClientRef.current = stompClient;
  // stompClient.connect(
  //   {},
  //   () => {},
  //   (error) => {
  //     console.error("Error during WebSocket connection:", error);
  //   }
  // );
  const sendMessage = () => {
    // console.log("hello from input");
    if (message === "") {
      alert("message is empty");
      return;
    }
    const chatMessage = {
      content: message,
      senderId: user.email,
      receipentId: chatUser.email,
      timestamp: new Date(),
    };
    if (stompClient && stompClient.connected) {
      // console.log("publishing chat message");
      stompClient.publish({
        destination: "/app/chat",
        body: JSON.stringify({
          content: message,
          senderId: user.email,
          recipientId: chatUser.email,
          timestamp: new Date(),
        }),
      });
      setMessage("");
      setMessages([...messages, chatMessage]);
    }
  };
  // useEffect(() => {
  //   connected();
  // }, []);
  // const subscribe = () => {
  //   console.log("I guess I have subscribed properly");
  //   stompClient.subscribe(
  //     `/user/${user.email}/queue/messages`,
  //     function (message) {
  //       console.log("Hello, I have subscribed to this");
  //       const data = JSON.parse(message.body);
  //       console.log(data);
  //       setMessages(data.body ? [...messages, data.body] : messages);
  //       // console.log(data);
  //       // outputDiv.innerHTML += "<p>" + data.body[0].fullName + "</p>";
  //     }
  //   );
  // };
  return (
    <div className="input">
      <input
        type="text"
        placeholder="Type something..."
        value={message}
        onChange={(event) => setMessage(event.target.value)}
      />
      <div className="send">
        <img src={Attach} alt="" />
        <input type="file" style={{ display: "none" }} id="file" />
        <label htmlFor="file">
          <img src={Img} alt="" />
        </label>
        <button onClick={sendMessage}>Send</button>
      </div>
    </div>
  );
}

export default Input;
