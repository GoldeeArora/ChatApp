import React, { useState, useEffect, useRef } from "react";
import Sidebar from "../../components/Sidebar";
import Chat from "../../components/Chat";
import "./styles.scss";
import { Stomp } from "@stomp/stompjs";
import SockJS from "sockjs-client";
import { useSelector, useDispatch } from "react-redux";
import { updateToken } from "../../Redux/features/tokenSlice";
import { updateUser } from "../../Redux/features/userSlice";
import { selectUser } from "../../Redux/features/userSlice";
import { selectChatUser } from "../../Redux/features/chatUserSlice";
function HomePage() {
  const [users, setUsers] = useState([]);
  const [messages, setMessages] = useState([]);
  const [newMessageUser, setNewMessageUser] = useState([]);
  const [log, setLog] = useState(false);
  const user = useSelector(selectUser);
  const chatUser = useSelector(selectChatUser);
  const dispatch = useDispatch();
  const stompClientRef = useRef(null);
  const [stomp, setStomp] = useState(null);
  const logout = () => {
    // createConnection();
    const userId = user.email; // Assuming this is your user ID
    const payload = JSON.stringify({ userId: userId });
    if (stompClientRef.current && stompClientRef.current.connected) {
      stompClientRef.current.send("/app/disconnect", {}, payload);
      dispatch(updateUser(null));
      dispatch(updateToken(""));
    } else {
      alert("Sorry,your connection is broken");
    }
  };
  useEffect(() => {
    const socket = new SockJS("http://localhost:8080/ws");
    const stompClient = Stomp.over(socket);

    stompClientRef.current = stompClient;
    stompClient.connect(
      {},
      (frame) => {
        // console.log("Connected to WebSocket", frame);
        setStomp(stompClient);
        messageSubscribe();
        subscribe();
        sendMessage();
      },
      (error) => {
        console.error("Error during WebSocket connection:", error);
      }
    );
    const sendMessage = () => {
      if (stompClient && stompClient.connected) {
        stompClient.publish({
          destination: "/app/new-joined",
          body: "Hello, Spring!",
        });
      }
    };

    const messageSubscribe = () => {
      // console.log(
      //   // "I have subscribed in the chat component before stompClient.subscribe"
      // );
      stompClient.subscribe(`/user/${user.email}/queue/messages`, (message) => {
        // console.log("Hello, I have subscribed chat component");
        const data = JSON.parse(message.body);
        // console.log(data);
        // console.log(
        // JSON.stringify(chatUser) + "this is the chat user from homepage"
        // );
        // console.log(data.senderId);
        if (chatUser && data.senderId === chatUser.email) {
          const newMessages = [...messages];
          newMessages.push(data);
          setMessages(newMessages);
        } else {
          if (!newMessageUser.includes(data.senderId)) {
            const tempNewMessageUser = [...newMessageUser];
            tempNewMessageUser.push(data.senderId);
            setNewMessageUser(tempNewMessageUser);
          }
        }
      });
    };
    const subscribe = () => {
      // console.log("I guess I have subscribed properly");
      stompClient.subscribe("/broadcast/listOfUsers", function (message) {
        // console.log("Hello, I have subscribed to this");
        const data = JSON.parse(message.body);
        setUsers(data.body ? data.body : data);
        // console.log(data);
        // outputDiv.innerHTML += "<p>" + data.body[0].fullName + "</p>";
      });
    };
    window.addEventListener("beforeunload", () => {
      logout();
      socket.close();
    });

    return () => {
      window.removeEventListener("beforeunload", logout);
      socket.close();
    };
  }, []);
  useEffect(() => {
    if (log) {
      // console.log(log);
      logout();
    }
  }, [log]);

  return (
    <div className="home">
      <div className="container">
        <Sidebar
          users={users}
          setLogout={setLog}
          newMessageUser={newMessageUser}
          setNewMessageUser={setNewMessageUser}
        />
        {stomp && (
          <Chat
            stompClient={stomp}
            messages={messages}
            setMessages={setMessages}
          />
        )}
      </div>
    </div>
  );
}

export default HomePage;
