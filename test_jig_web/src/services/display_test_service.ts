import { createApi, fetchBaseQuery } from '@reduxjs/toolkit/query/react'
import config from '../config/config';

export interface DisplayTest {
    testId: number;
    deviceId: number;
    serialNumber: string;
    physicalInspectionState: boolean | null;
    backLightOn: boolean | null;
    redScreenOn: boolean | null;
    greenScreenOn: boolean | null;
    blueScreenOn: boolean | null;
    colorPatch: boolean | null;
    BTDisplayText: boolean | null;
    screenOff: boolean | null;
    overallDisplayStatus: boolean | null;
    status: boolean | null;
    dateTime: string;
}

export interface ApiResponse {
    status: string;
    statusDescription: string;
    data: {
        tests: DisplayTest[];
        totalRecords: number;
        totalFailed: number;
    };
}

export const displayTestApi = createApi({
    reducerPath: 'displayTestApi',
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
    tagTypes: ['displayTestList'],
    endpoints: (build) => ({
        getDisplayTests: build.mutation<ApiResponse, { data: {}, page: string }>({
            query(data) {
                return {
                    url: `test/get/display-test/${data.page}`,
                    method: 'POST',
                    body: data.data,
                }
            },
            invalidatesTags: [{ type: 'displayTestList', id: "getDisplayTests" }]
        }),
        getDisplayTestQ: build.query<ApiResponse, { data: {}, page: string }>({
            query(data) {
                return {
                    url: `test/get/display-test/${data.page}`,
                    method: 'POST',
                    body: data.data,
                }
            },
            providesTags: [{ type: 'displayTestList', id: 'getDisplayTestQ' }]
        }),
    }),
})

export const {
    useGetDisplayTestsMutation,
    useGetDisplayTestQQuery
} = displayTestApi