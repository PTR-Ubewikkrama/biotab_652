import { createApi, fetchBaseQuery } from '@reduxjs/toolkit/query/react'
import config from '../config/config';

export interface CableTest {
    testId: number;
    deviceId: number;
    serialNumber: string;
    cableSelection: number | null;
    visualInspection: boolean | null;
    cable1: boolean | null;
    cable2: boolean | null;
    cable3: boolean | null;
    cable4: boolean | null;
    cable5: boolean | null;
    cable6: boolean | null;
    cable7: boolean | null;
    cable8: boolean | null;
    cable9: boolean | null;
    cable10: boolean | null;
    overallCableState: boolean | null;
    status: boolean | null;
    dateTime: string;
}

export interface ApiResponse {
    status: string;
    statusDescription: string;
    data: {
        tests: CableTest[];
        totalRecords: number;
        totalFailed: number;
    };
}

export const cableTestApi = createApi({
    reducerPath: 'cableTestApi',
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
    tagTypes: ['valveCardList'],
    endpoints: (build) => ({
        getCableTests: build.mutation<ApiResponse, { data: {}, page: string }>({
            query(data) {
                return {
                    url: `test/get/cable-test/${data.page}`,
                    method: 'POST',
                    body: data.data,
                }
            },
            invalidatesTags: [{ type: 'valveCardList', id: "getValveTests" }]
        }),
        getCableTestQ: build.query<ApiResponse, { data: {}, page: string }>({
            query(data) {
                return {
                    url: `test/get/cable-test/${data.page}`,
                    method: 'POST',
                    body: data.data,
                }
            },
            providesTags: [{ type: 'valveCardList', id: 'getValveTestQ' }]
        }),
    }),
})

export const {
    useGetCableTestQQuery,
    useGetCableTestsMutation
} = cableTestApi