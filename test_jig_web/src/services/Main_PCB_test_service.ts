import { createApi, fetchBaseQuery } from '@reduxjs/toolkit/query/react'
import config from '../config/config';

export interface MainPCBTest {
    testId: number;
    deviceMac: string;
    serialNumber: string;
    softwareVersion: string;
    batchNumber: string;
    testResultData: MainPCBTestUnit[];
    status: boolean | null;
    dateTime: string;
}

export interface MainPCBTestUnit {
    testName: string;
    testType: string;
    validationType: string;
    actualValue: string;
    minValue: number;
    maxValue: number;
    unit: string;
    status: boolean | null;
    dateTime: string;
}

export interface ApiResponse {
    status: string;
    statusDescription: string;
    data: {
        tests: MainPCBTest[];
        totalRecords: number;
        totalFailed: number;
    };
}

export const mainPCBTestApi = createApi({
    reducerPath: 'mainPCBTestApi',
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
    tagTypes: ['mainPCBTestList'],
    endpoints: (build) => ({
        getMainPCBTests: build.mutation<ApiResponse, { data: {}, page: string }>({
            query(data) {
                return {
                    url: `test/get/main-pcb-test/${data.page}`,
                    method: 'POST',
                    body: data.data,
                }
            },
            invalidatesTags: [{ type: 'mainPCBTestList', id: "getMainPCBTests" }]
        }),
        getMainPCBTestQ: build.query<ApiResponse, { data: {}, page: string }>({
            query(data) {
                return {
                    url: `test/get/main-pcb-test/${data.page}`,
                    method: 'POST',
                    body: data.data,
                }
            },
            providesTags: [{ type: 'mainPCBTestList', id: 'getMainPCBTestQ' }]
        }),
    }),
})

export const {
    useGetMainPCBTestsMutation,
    useGetMainPCBTestQQuery
} = mainPCBTestApi