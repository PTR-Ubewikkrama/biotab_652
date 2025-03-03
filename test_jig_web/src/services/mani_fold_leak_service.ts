import { createApi, fetchBaseQuery } from '@reduxjs/toolkit/query/react'
import config from '../config/config';

export interface ManiFoldLeakTest {
    testId: number;
    deviceId: number;
    serialNumber: string;
    physicalInspectionState: boolean | null;
    leakageFlowrate: number;
    manifoldLeakState: boolean | null;
    overallManifoldLeakState: boolean
    status: boolean | null;
    dateTime: string;
}

export interface ApiResponse {
    status: string;
    statusDescription: string;
    data: {
        tests: ManiFoldLeakTest[];
        totalRecords: number;
        totalFailed: number;
    };
}

export const maniFoldLeakApi = createApi({
    reducerPath: 'maniFoldLeakApi',
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
    tagTypes: ['maniFoldLeakList'],
    endpoints: (build) => ({
        getManiFoldLeakTests: build.mutation<ApiResponse, { data: {}, page: string }>({
            query(data) {
                return {
                    url: `test/get/mani-fold-leak-test/${data.page}`,
                    method: 'POST',
                    body: data.data,
                }
            },
            invalidatesTags: [{ type: 'maniFoldLeakList', id: "getValveTests" }]
        }),
        getManiFoldLeakTestQ: build.query<ApiResponse, { data: {}, page: string }>({
            query(data) {
                return {
                    url: `test/get/mani-fold-leak-test/${data.page}`,
                    method: 'POST',
                    body: data.data,
                }
            },
            providesTags: [{ type: 'maniFoldLeakList', id: 'getValveTestQ' }]
        }),
    }),
})

export const {
    useGetManiFoldLeakTestQQuery,
    useGetManiFoldLeakTestsMutation
} = maniFoldLeakApi