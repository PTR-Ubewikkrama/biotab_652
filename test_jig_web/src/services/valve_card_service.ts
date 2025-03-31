import { createApi, fetchBaseQuery } from '@reduxjs/toolkit/query/react'
import config from '../config/config';

export interface ValveCardTest {
    testId: number;
    deviceMac: string;
    location: string;
    serialNumber: string;
    physicalInspectionState: boolean | null;
    rail: string;
    valve1: string;
    valve3: string;
    valve5: string;
    valve7: string;
    valve2: string;
    valve4: string;
    valve6: string;
    valve8: string;
    amperageTest: string;
    shiftRegisterTest: string;
    overallValveCardState: string;
    status: boolean | null;
    dateTime: string;
}

export interface ApiResponse {
    status: string;
    statusDescription: string;
    data: {
        tests: ValveCardTest[];
        totalRecords: number;
        totalFailed: number;
    };
}

export const valveCardApi = createApi({
    reducerPath: 'valveCardApi',
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
        getValveCardTests: build.mutation<ApiResponse, { data: {}, page: string }>({
            query(data) {
                return {
                    url: `test/get/valve-card-test/${data.page}`,
                    method: 'POST',
                    body: data.data,
                }
            },
            invalidatesTags: [{ type: 'valveCardList', id: "getValveTests" }]
        }),
        getValveCardTestQ: build.query<ApiResponse, { data: {}, page: string }>({
            query(data) {
                return {
                    url: `test/get/valve-card-test/${data.page}`,
                    method: 'POST',
                    body: data.data,
                }
            },
            providesTags: [{ type: 'valveCardList', id: 'getValveTestQ' }]
        }),
    }),
})

export const {
    useGetValveCardTestQQuery,
    useGetValveCardTestsMutation
} = valveCardApi