import { createSlice } from "@reduxjs/toolkit";
const initialState = {
  chatUser: null,
};

export const chatUserSlice = createSlice({
  name: "chatUser",
  initialState,
  reducers: {
    updateChatUser: (state, action) => {
      state.chatUser = action.payload;
      //   console.log(state.items);
    },
  },
});

export const { updateChatUser } = chatUserSlice.actions;
export const selectChatUser = (state) => state.chatUser.chatUser;

export default chatUserSlice.reducer;
