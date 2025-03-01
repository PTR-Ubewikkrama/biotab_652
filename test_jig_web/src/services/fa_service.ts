import { createApi, fetchBaseQuery } from '@reduxjs/toolkit/query/react'
import config from '../config/config';

export interface FinalAssemblyDto {
    id: number;
    deviceCode: string;
    category: string;
    bladderCode: string;
    uplNumber: string;
    udiNumber: string;
    adapterCode: string;
    cartoonNumber: string;
    createdAt: string;
    updatedAt: string;
    updatedBy: string;
    createdBy: string;
}

export interface ApiResponse {
    status: string;
    statusDescription: string;
    data: {
        fas: FinalAssemblyDto[];
        total: number;
    };
}

export const faApi = createApi({
    reducerPath: 'faApi',
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
    tagTypes: ['faList'],
    endpoints: (build) => ({
        getFAs: build.mutation<ApiResponse, { data: {}, page: string }>({
            query(data) {
                return {
                    url: `fa/get/${data.page}`,
                    method: 'POST',
                    body: data.data,
                }
            },
            invalidatesTags: [{ type: 'faList', id: "getFAs" }]
        }),
        getFAsQ: build.query<ApiResponse, { data: {}, page: string }>({
            query(data) {
                return {
                    url: `fa/get/${data.page}`,
                    method: 'POST',
                    body: data.data,
                }
            },
            providesTags: [{ type: 'faList', id: 'getFAsQ' }]
        }),
    }),

})

export const {
    useGetFAsMutation,
    useGetFAsQQuery
} = faApi