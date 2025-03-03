import { createApi, fetchBaseQuery } from '@reduxjs/toolkit/query/react'
import config from '../config/config';

export interface ValveTest {
    testId: number;
    deviceId: number;
    serialNumber: string;
    idleVoltageLowThresh: number;
    idleVoltageUpThresh: number;
    idleCurrentUpThresh: number;
    loadVoltageLowThresh: number;
    loadVoltageUpThresh: number;
    loadCurrentUpThresh: number;
    setPressure: number;
    idleVoltage: number;
    idleVoltageStatus: boolean | null;
    idleCurrent: number;
    idleCurrentStatus: boolean | null;
    coilResistance: number;
    operatingCurrent: number;
    peakPower: number;
    averagePower: number;
    flowRate: number;
    flowRateStatus: boolean | null;
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
                    url: `test/get/valve-test/${data.page}`,
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