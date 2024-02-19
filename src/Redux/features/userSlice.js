import { createSlice } from "@reduxjs/toolkit";
const initialState = {
  user: null,
};

export const userSlice = createSlice({
  name: "user",
  initialState,
  reducers: {
    updateUser: (state, action) => {
      state.user = action.payload;
      //   console.log(state.items);
    },
  },
});

export const { updateUser } = userSlice.actions;
export const selectUser = (state) => state.user.user;

export default userSlice.reducer;
