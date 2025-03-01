import { createApi, fetchBaseQuery } from '@reduxjs/toolkit/query/react'
import config from '../config/config';

export interface ValveTest {
    testId: number;
    deviceId: number;
    qrCode: string;
    airChamberLoadingPressure: number;
    airChamberStatus: boolean;
    v1OutletPressureAfter10MsOnTime: number;
    v1OutletOnStatus: boolean;
    v1OutletPressureAfter10MsOffTime: number;
    v1OutletOffStatus: boolean;
    v2OutletPressureAfter10MsOnTime: number;
    v2OutletOnStatus: boolean;
    v2OutletPressureAfter10MsOffTime: number;
    v2OutletOffStatus: boolean;
    v3OutletPressureAfter10MsOnTime: number;
    v3OutletOnStatus: boolean;
    v3OutletPressureAfter10MsOffTime: number;
    v3OutletOffStatus: boolean;
    valveStatus: boolean;
    status: boolean | null;
    dateTime: string;
}

interface Data {
    valveTests: ValveTest[];
    totalRecords: number;
    totalFailed: number;
}

interface ApiResponseValve {
    status: string;
    statusDescription: string;
    data: Data;
}

export const valveApi = createApi({
    reducerPath: 'valveApi',
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
    tagTypes: ['valveList'],
    endpoints: (build) => ({
        getValveTests: build.mutation<ApiResponseValve, { data: {}, page: string }>({
            query(data) {
                return {
                    url: `test/get/valve-test/all`,
                    method: 'POST',
                    body: data.data,
                }
            },
            invalidatesTags: [{ type: 'valveList', id: "getValveTests" }]
        }),
        getValveTestQ: build.query<ApiResponseValve, { data: {}, page: string }>({
            query(data) {
                return {
                    url: `test/get/valve-test/${data.page}`,
                    method: 'POST',
                    body: data.data,
                }
            },
            providesTags: [{ type: 'valveList', id: 'getValveTestQ' }]
        }),
    }),
})

export const {
    useGetValveTestsMutation,
    useGetValveTestQQuery
} = valveApi