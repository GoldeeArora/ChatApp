import { createSlice } from "@reduxjs/toolkit";
const initialState = {
  token: "",
};

export const tokenSlice = createSlice({
  name: "token",
  initialState,
  reducers: {
    updateToken: (state, action) => {
      state.token = action.payload;
    },
  },
});

export const { updateToken } = tokenSlice.actions;

// Other code such as selectors can use the imported `RootState` type
export const selectToken = (state) => state.token;

export default tokenSlice.reducer;
