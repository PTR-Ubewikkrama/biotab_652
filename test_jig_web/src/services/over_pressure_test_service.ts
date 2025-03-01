import { createApi, fetchBaseQuery } from '@reduxjs/toolkit/query/react'
import config from '../config/config';

export interface OverPressureTest {
    testId: number;
    deviceMac: string;
    qrCode: string;
    maxPressure: number;
    maxPressureTime: number;
    maxPressureFlowRate: number;
    normalPressure: number;
    normalPressureTime: number;
    normalPressureFlowRate: number;
    overPressureValveStatus: boolean;
    status: boolean;
    dateTime: string;
}

export interface ApiResponse {
    status: string;
    statusDescription: string;
    data: {
        overPressureValveTests: OverPressureTest[];
        totalRecords: number;
        totalFailed: number;
    };
}

export const overPressureTestApi = createApi({
    reducerPath: 'overPressureTestApi',
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
    tagTypes: ['overPressureTestList'],
    endpoints: (build) => ({
        getOverPressureTests: build.mutation<ApiResponse, { data: {}, page: string }>({
            query(data) {
                return {
                    url: `test/get/over-pressure-test/${data.page}`,
                    method: 'POST',
                    body: data.data,
                }
            },
            invalidatesTags: [{ type: 'overPressureTestList', id: "getAirPumpTests" }]
        }),
        getOverPressureQ: build.query<ApiResponse, { data: {}, page: string }>({
            query(data) {
                return {
                    url: `test/get/over-pressure-test/${data.page}`,
                    method: 'POST',
                    body: data.data,
                }
            },
            providesTags: [{ type: 'overPressureTestList', id: 'getAirPumpQ' }]
        }),
    }),

})

export const {
    useGetOverPressureQQuery,
    useGetOverPressureTestsMutation
} = overPressureTestApi