import { createApi, fetchBaseQuery } from '@reduxjs/toolkit/query/react'
import config from '../config/config';

export interface OPValveTest {
    testId: number;
    deviceId: number;
    serialNumber: string;
    physicalInspectionState: boolean;
    startOpeningPressure: number;
    startOpeningFlowrate: number;
    valveStartOpeningState: boolean;
    fullyOpeningPressure: number;
    fullyOpeningFlowrate: number;
    valveFullyOpeningState: boolean;
    closingPressure: number;
    closingFlowrate: number;
    valveClosingState: boolean;
    overallOpValveState: boolean;
    status: boolean | null;
    dateTime: string;
}

export interface ApiResponse {
    status: string;
    statusDescription: string;
    data: {
        tests: OPValveTest[];
        totalRecords: number;
        totalFailed: number;
    };
}

export const opValveApi = createApi({
    reducerPath: 'opValveApi',
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
    tagTypes: ['opValveList'],
    endpoints: (build) => ({
        getOPValveTests: build.mutation<ApiResponse, { data: {}, page: string }>({
            query(data) {
                return {
                    url: `test/get/op-valve-test/${data.page}`,
                    method: 'POST',
                    body: data.data,
                }
            },
            invalidatesTags: [{ type: 'opValveList', id: "getValveTests" }]
        }),
        getOPValveTestQ: build.query<ApiResponse, { data: {}, page: string }>({
            query(data) {
                return {
                    url: `test/get/op-valve-test/${data.page}`,
                    method: 'POST',
                    body: data.data,
                }
            },
            providesTags: [{ type: 'opValveList', id: 'getValveTestQ' }]
        }),
    }),
})

export const {
    useGetOPValveTestQQuery,
    useGetOPValveTestsMutation,
} = opValveApi