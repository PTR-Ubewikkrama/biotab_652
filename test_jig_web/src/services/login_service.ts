import { createApi, fetchBaseQuery } from '@reduxjs/toolkit/query/react'
import config from '../config/config';

export interface LoginResponse {
    status: string;
    statusDescription: string;
    data: {
        token: string;
        username: string;
        group: string;
        roles: string[];
        status: string;
    };
}

export interface RolesResponse {
    status: string
    statusDescription: string
    user: SimpleUser
    roles: string[]
}

export interface SimpleUser {
    username: string
    group: string
    status: string
    createdBy: string
    createdDateTime: string
}

export const loginApi = createApi({
    reducerPath: 'loginApi',
    baseQuery: fetchBaseQuery({
        baseUrl: config.apiBaseUrl,
    }),
    tagTypes: ['loginRequest'],
    endpoints: (build) => ({
        login: build.mutation<LoginResponse, {}>({
            query(body) {
                return {
                    url: `login`,
                    method: 'POST',
                    body,
                }
            },
        }),
    }),
})

export const {
    useLoginMutation
} = loginApi