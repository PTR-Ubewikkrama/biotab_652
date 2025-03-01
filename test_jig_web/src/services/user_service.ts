import { createApi, fetchBaseQuery } from '@reduxjs/toolkit/query/react'
import { List } from 'reselect/es/types'
import { CommonResponse } from './device_service'
import { RolesResponse } from './login_service'
import config from '../config/config'

export interface UserListResponse {
    status: string
    statusDescription: string
    users: List<User>
}

export interface PasswordResetResponse {
    status: string
    statusDescription: string
}

export interface User {
    username: string
    group: string
    status: string
    supervisor: string
    passwordType: string
    createdBy: string
    createdDateTime: string
    lastUpdatedDateTime: boolean
}

export const userApi = createApi({
    reducerPath: 'userApi',
    baseQuery: fetchBaseQuery({
        baseUrl: config.apiBaseUrl,
        prepareHeaders: (headers) => {
            const token = localStorage.getItem("jwt") as string;
            if (!headers.has("Authorization") && token) {
                headers.set("Authorization", `Bearer ${token}`);
            }
            return headers;
        },
    }),
    tagTypes: ['userList'],
    endpoints: (build) => ({
        getUsers: build.query<UserListResponse, {}>({
            query(body) {
                return {
                    url: 'user/get/all',
                    method: 'POST',
                    body: body,
                }
            },
            providesTags: [{ type: 'userList', id: "getUsers" }]
        }),
        updateUser: build.mutation<CommonResponse, {}>({
            query(body) {
                return {
                    url: `user/update`,
                    method: 'POST',
                    body: body,
                }
            },
            invalidatesTags: [{ type: 'userList', id: "getUsers" }]
        }),
        resetPassword: build.mutation<UserListResponse, { username: string }>({
            query: (data) => `user/resetPassword/${data.username}`,
            invalidatesTags: [{ type: 'userList', id: "getUsers" }]
        }),
        resetDefaultPassword: build.mutation<PasswordResetResponse, {}>({
            query(body) {
                return {
                    url: `user/reset-password`,
                    method: 'POST',
                    body,
                };
            },
            invalidatesTags: [{ type: 'userList', id: "getUsers" }]
        }),
        addUser: build.mutation<CommonResponse, {}>({
            query(body) {
                return {
                    url: `user/add-user`,
                    method: 'POST',
                    body: body,
                }
            },
            invalidatesTags: [{ type: 'userList', id: "getUsers" }]
        }),
        getRoles: build.mutation<RolesResponse, {}>({
            query() {
                return {
                    url: `user/roles`,
                    method: 'GET'
                }
            },
        }),
    }),
})

export const {
    useGetUsersQuery,
    useUpdateUserMutation,
    useResetPasswordMutation,
    useResetDefaultPasswordMutation,
    useAddUserMutation,
    useGetRolesMutation
} = userApi