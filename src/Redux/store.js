import { configureStore } from "@reduxjs/toolkit";
import userReducer from "./features/userSlice";
import tokenReducer from "./features/tokenSlice";
import chatUserReducer from "./features/chatUserSlice";
export const store = configureStore({
  reducer: {
    user: userReducer,
    token: tokenReducer,
    chatUser: chatUserReducer,
  },
});
