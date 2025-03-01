import { createApi, fetchBaseQuery } from '@reduxjs/toolkit/query/react'
import config from '../config/config';

export interface LatchButtonTest {
    testId: number;
    qrCode: string;
    deviceMac: string;
    buttonOnTestStatus: boolean;
    buttonOffTestStatus: boolean;
    ledOnTestStatus: boolean;
    latchButtonStatus: boolean;
    dateTime: string;
}

export interface ApiResponse {
    status: string;
    statusDescription: string;
    data: {
        latchButtonTests: LatchButtonTest[];
        totalRecords: number;
        totalFailed: number;
    };
}

export const latchButtonTestApi = createApi({
    reducerPath: 'latchButtonTestApi',
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
    tagTypes: ['latchButtonTestList'],
    endpoints: (build) => ({
        getLatchButtonTests: build.mutation<ApiResponse, { data: {}, page: string }>({
            query(data) {
                return {
                    url: `test/get/latch-button-test/${data.page}`,
                    method: 'POST',
                    body: data.data,
                }
            },
            invalidatesTags: [{ type: 'latchButtonTestList', id: "getAirPumpTests" }]
        }),
        getLatchButtonTestQ: build.query<ApiResponse, { data: {}, page: string }>({
            query(data) {
                return {
                    url: `test/get/latch-button-test/${data.page}`,
                    method: 'POST',
                    body: data.data,
                }
            },
            providesTags: [{ type: 'latchButtonTestList', id: 'getAirPumpQ' }]
        }),
    }),

})

export const {
    useGetLatchButtonTestQQuery,
    useGetLatchButtonTestsMutation
} = latchButtonTestApi