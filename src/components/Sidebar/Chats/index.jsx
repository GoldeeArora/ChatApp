import React from "react";
import Friend from "./Friend";
import { useSelector } from "react-redux";
import { selectUser } from "../../../Redux/features/userSlice";
function Chats({ users, newMessageUser, setNewMessageUser }) {
  const loggedUser = useSelector(selectUser);

  return (
    <div className="chats">
      {users?.map((user) => {
        if (user.email !== loggedUser.email) {
          return (
            <Friend
              key={user.email}
              name={user.fullName}
              email={user.email}
              status={user.status}
              newMessageUser={newMessageUser}
              setNewMessageUser={setNewMessageUser}
            />
          );
        }
        return null;
      })}
    </div>
  );
}

export default Chats;
