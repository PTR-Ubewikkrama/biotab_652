import { createApi, fetchBaseQuery } from '@reduxjs/toolkit/query/react'
import config from '../config/config';

export interface BTDeviceDto {
    deviceId: number;
    deviceCode: string;
    powerPcbCode: string;
    powerPcbCodeStatus: string;
    pumpCode: string;
    pumpCodeStatus: string;
    fanCode: string;
    fanCodeStatus: string;
    uiPcbCode: string;
    uiPcbCodeStatus: string;
    encoderCode: string;
    encoderCodeStatus: string;
    mainPcbCode: string;
    mainPcbCodeStatus: string;
    manifoldCode: string;
    manifoldCodeStatus: string;
    valveCardInsideCableSetCode: string;
    valveCardInsideCableSetCodeStatus: string;
    valveCardInputOutputCableSetCode: string;
    valveCardInputOutputCableSetCodeStatus: string;
    overPressureValveCode: string;
    overPressureValveCodeStatus: string;
    powerCableCode: string;
    uiCableCode: string;
    displayCode: string;
    frontBracketAssemblyCode: string;
    frontBracketAssemblyCodeStatus: string;
    powerAdaptorCode: string;
    powerAdaptorCodeStatus: string;
    enclosureTopCode: string;
    enclosureBottomCode: string;
    backVentCode: string;
    fanMountCode: string;
    encoderSupporterCode: string;
    pcbHolderCode: string;
    valveCards: string[];
    createdBy: string;
    dateTime: string;
}

export interface ApiResponse {
    status: string;
    statusDescription: string;
    data: {
        devices: BTDeviceDto[];
        total: number;
    };
}

export const btDeviceApi = createApi({
    reducerPath: 'btDeviceApi',
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
    tagTypes: ['btDeviceList'],
    endpoints: (build) => ({
        getBTDevices: build.mutation<ApiResponse, { data: {}, page: string }>({
            query(data) {
                return {
                    url: `bt_device/get/${data.page}`,
                    method: 'POST',
                    body: data.data,
                }
            },
            invalidatesTags: [{ type: 'btDeviceList', id: "getAirPumpTests" }]
        }),
        getBTDevicesQ: build.query<ApiResponse, { data: {}, page: string }>({
            query(data) {
                return {
                    url: `bt_device/get/${data.page}`,
                    method: 'POST',
                    body: data.data,
                }
            },
            providesTags: [{ type: 'btDeviceList', id: 'getAirPumpQ' }]
        }),
    }),

})

export const {
    useGetBTDevicesMutation,
    useGetBTDevicesQQuery
} = btDeviceApi