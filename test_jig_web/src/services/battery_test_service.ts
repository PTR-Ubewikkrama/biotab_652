import { createApi, fetchBaseQuery } from '@reduxjs/toolkit/query/react'
import config from '../config/config';

export interface BatteryTest {
    testId: number;
    deviceMac: string;
    qrCode: string;
    maximumCurrent: number;
    maxCurrentDrawnTime: number;
    maxCurrentCutOff: boolean;
    normalCurrent: number;
    normalCurrentDrawnTime: number;
    normalCurrentCutOff: boolean;
    batteryStatus: boolean;
    dateTime: string;
}

export interface ApiResponse {
    status: string;
    statusDescription: string;
    data: {
        batteryTests: BatteryTest[];
        totalRecords: number;
        totalFailed: number;
    };
}

export const batteryTestApi = createApi({
    reducerPath: 'batteryTestApi',
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
    tagTypes: ['batteryTestList'],
    endpoints: (build) => ({
        getBatteryTests: build.mutation<ApiResponse, { data: {}, page: string }>({
            query(data) {
                return {
                    url: `test/get/battery-test/${data.page}`,
                    method: 'POST',
                    body: data.data,
                }
            },
            invalidatesTags: [{ type: 'batteryTestList', id: "getAirPumpTests" }]
        }),
        getBatteryTestQ: build.query<ApiResponse, { data: {}, page: string }>({
            query(data) {
                return {
                    url: `test/get/battery-test/${data.page}`,
                    method: 'POST',
                    body: data.data,
                }
            },
            providesTags: [{ type: 'batteryTestList', id: 'getAirPumpQ' }]
        }),
    }),

})

export const {
    useGetBatteryTestQQuery,
    useGetBatteryTestsMutation
} = batteryTestApi