import { createApi, fetchBaseQuery } from '@reduxjs/toolkit/query/react'
import config from '../config/config';

export interface FanTest {
    testId: number;
    deviceMac: string;
    serialNumber: string;
    visualInspection: boolean | null;
    drawCurrent: number | null;
    drawCurrentState: boolean | null;
    fanSpeed: number | null;
    fanSpeedState: boolean | null;
    overallFanState: boolean | null;
    status: boolean | null;
    dateTime: string;
}

export interface ApiResponse {
    status: string;
    statusDescription: string;
    data: {
        tests: FanTest[];
        totalRecords: number;
        totalFailed: number;
    };
}

export const fanTestApi = createApi({
    reducerPath: 'fanTestApi',
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
    tagTypes: ['fanTestList'],
    endpoints: (build) => ({
        getFanTests: build.mutation<ApiResponse, { data: {}, page: string }>({
            query(data) {
                return {
                    url: `test/get/fan-test/${data.page}`,
                    method: 'POST',
                    body: data.data,
                }
            },
            invalidatesTags: [{ type: 'fanTestList', id: "getValveTests" }]
        }),
        getFanTestQ: build.query<ApiResponse, { data: {}, page: string }>({
            query(data) {
                return {
                    url: `test/get/fan-test/${data.page}`,
                    method: 'POST',
                    body: data.data,
                }
            },
            providesTags: [{ type: 'fanTestList', id: 'getValveTestQ' }]
        }),
    }),
})

export const {
    useGetFanTestQQuery,
    useGetFanTestsMutation
} = fanTestApi