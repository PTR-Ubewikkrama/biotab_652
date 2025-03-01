import { createSlice } from '@reduxjs/toolkit'
import type { PayloadAction } from '@reduxjs/toolkit'
import { LoginResponse, RolesResponse, SimpleUser } from '../../services/login_service'

interface LoginSlice {
    simpleUser: SimpleUser | null
    jwt: string | null,
    roles: string[]
}

const initialState: LoginSlice = {
    simpleUser: null,
    jwt: null,
    roles: []
}

export const loginSlice = createSlice({
    name: 'login',
    initialState,
    reducers: {
        loginSuccess: (state, loginResponse: PayloadAction<LoginResponse>) => {
            state.jwt = loginResponse.payload.data.token
            // state.simpleUser = loginResponse.payload.user
            // localStorage.setItem("jwt", loginResponse.payload.jwt)
            localStorage.setItem("roles", loginResponse.payload.data.roles.join(', '));
        },
        setJWT: (state, jwt: PayloadAction<string>) => {
            state.jwt = jwt.payload
        },
        logout: (state) => {
            state = initialState
            localStorage.removeItem("jwt")
            localStorage.removeItem("roles")
            localStorage.removeItem("user");
        },
        rolesGetSuccess: (state, rolesResponse: PayloadAction<RolesResponse>) => {
            state.simpleUser = rolesResponse.payload.user
            state.roles = rolesResponse.payload.roles
        },
    },
})

export const { loginSuccess, logout, setJWT, rolesGetSuccess } = loginSlice.actions

export default loginSlice.reducer