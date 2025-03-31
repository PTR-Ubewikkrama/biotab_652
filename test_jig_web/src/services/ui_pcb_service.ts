import { createApi, fetchBaseQuery } from '@reduxjs/toolkit/query/react'
import config from '../config/config';

export interface UIPcbTest {
    testId: number;
    deviceMac: string;
    serialNumber: string;
    physicalInspectionState: boolean | null;
    redLedState: boolean | null;
    whiteLedState: boolean | null;
    ledRingOnState: boolean | null;
    ledRingFadeState: boolean | null;
    overallUiPcbState: boolean | null;
    status: boolean | null;
    dateTime: string;
}

export interface ApiResponse {
    status: string;
    statusDescription: string;
    data: {
        tests: UIPcbTest[];
        totalRecords: number;
        totalFailed: number;
    };
}

export const uiPcbApi = createApi({
    reducerPath: 'uiPcbApi',
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
    tagTypes: ['uiPcbList'],
    endpoints: (build) => ({
        getUIPcbTests: build.mutation<ApiResponse, { data: {}, page: string }>({
            query(data) {
                return {
                    url: `test/get/ui-pcb-test/${data.page}`,
                    method: 'POST',
                    body: data.data,
                }
            },
            invalidatesTags: [{ type: 'uiPcbList', id: "getValveTests" }]
        }),
        getUIPcbTestQ: build.query<ApiResponse, { data: {}, page: string }>({
            query(data) {
                return {
                    url: `test/get/ui-pcb-test/${data.page}`,
                    method: 'POST',
                    body: data.data,
                }
            },
            providesTags: [{ type: 'uiPcbList', id: 'getValveTestQ' }]
        }),
    }),
})

export const {
    useGetUIPcbTestsMutation,
    useGetUIPcbTestQQuery,
} = uiPcbApi