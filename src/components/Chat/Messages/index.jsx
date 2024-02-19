import React, { useEffect } from "react";
import Message from "./Message";
function Messages({ messages }) {
  // useEffect(() => {
  //   console.log(
  //     JSON.stringify(messages) + " this is inside the messages component"
  //   );
  // }, [messages]);
  console.log("I am calling the messages" + JSON.stringify(messages));
  useEffect(() => {}, [messages]);
  return (
    <div className="messages">
      {messages &&
        messages?.map((m) => {
          return <Message message={m} key={m?.id} />;
        })}
    </div>
  );
}

export default Messages;
